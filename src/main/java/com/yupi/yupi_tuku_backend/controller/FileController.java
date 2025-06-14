package com.yupi.yupi_tuku_backend.controller;

import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import com.yupi.yupi_tuku_backend.annotation.AuthCheck;
import com.yupi.yupi_tuku_backend.common.BaseResponse;
import com.yupi.yupi_tuku_backend.common.ResultUtils;
import com.yupi.yupi_tuku_backend.constant.UserConstant;
import com.yupi.yupi_tuku_backend.exception.BusinessException;
import com.yupi.yupi_tuku_backend.exception.ErrorCode;
import com.yupi.yupi_tuku_backend.manager.CosManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Resource
    private CosManager cosManager;
    /**
     * 测试文件上传
     *
     * @param multipartFile
     * @return
     */

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/upload")
/**
 * 上传文件到服务器并存储到腾讯云 COS
 *
 * @param multipartFile 前端上传的文件
 * @return 返回文件在 COS 中的路径
 */
    public BaseResponse<String> testUploadFile(@RequestPart("file") MultipartFile multipartFile) {
        // 获取上传文件的原始文件名
        String filename = multipartFile.getOriginalFilename();
        // 构造文件在 COS 中的存储路径，格式为 /test/文件名
        String filepath = String.format("/test/%s", filename);

        // 定义临时文件变量
        File file = null;
        try {
            // 创建一个临时文件，filepath 是文件路径的前缀，null 表示不指定后缀
            file = File.createTempFile(filepath, null);
            // 将上传的文件内容写入到临时文件中
            multipartFile.transferTo(file);

            // 调用 COS 管理工具类，将临时文件上传到腾讯云 COS
            cosManager.putObject(filepath, file);

            // 返回文件在 COS 中的路径，表示上传成功
            return ResultUtils.success(filepath);
        } catch (Exception e) {
            // 捕获异常并记录错误日志，包括文件路径和异常信息
            log.error("file upload error, filepath = " + filepath, e);
            // 抛出业务异常，表示文件上传失败
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            // 无论是否发生异常，最终都会执行以下代码
            if (file != null) {
                // 删除临时文件，释放磁盘空间
                boolean delete = file.delete();
                if (!delete) {
                    // 如果文件删除失败，记录错误日志
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }
    /**
     * 测试文件下载
     *
     * @param filepath 文件路径
     * @param response 响应对象
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/test/download/")
    public void testDownloadFile(String filepath, HttpServletResponse response) throws IOException {
        COSObjectInputStream cosObjectInput = null;
        try {
            COSObject cosObject = cosManager.getObject(filepath);
            cosObjectInput = cosObject.getObjectContent();
            // 处理下载到的流
            byte[] bytes = IOUtils.toByteArray(cosObjectInput);
            // 设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filepath);
            // 写入响应
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
        } catch (Exception e) {
            log.error("file download error, filepath = " + filepath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "下载失败");
        } finally {
            if (cosObjectInput != null) {
                cosObjectInput.close();
            }
        }
    }


}
