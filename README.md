# Huiling Image Sharing Platform(辉零图库)

> This project is refactored and extended based on the open-source [Yu-Picture Project by Yupi](https://github.com/liyupi/yu-picture). Special thanks to Mr. Yupi for his contributions! Strictly prohibited for any commercial use. [Demo: https://lumenglover.com](https://lumenglover.com)

## Project Overview

**Huiling** is an image community platform designed for learning and life sharing, aiming to provide users with a secure and friendly image sharing environment.
![image](https://github.com/user-attachments/assets/d2efeb42-4d57-4f92-a07d-9524c7f7d1a9)

---

## Core Features

### Image Management
- Supports public and private galleries
- Multi-level category management
- Object storage via Tencent Cloud COS
![image](https://github.com/user-attachments/assets/35df5e9c-946f-4fd2-b8c3-62abf8323a2c)

### User Management
- Manage all the users 
- Set the role 
![image](https://github.com/user-attachments/assets/fe3a6040-daf8-474f-a6be-4ed1e2340452)


## Main Functions

### User System
- Login / Registration
- User profile management

### Image Management
- Image upload
- Image editing
- Image categorization


## Tech Stack

### Frontend
- **Core Framework:** Vue 3
- **Language:** TypeScript
- **State Management:** Pinia
- **UI Frameworks:**
  - Ant Design Vue (PC)
  - Vant (Mobile)
- **Routing:** Vue Router
- **HTTP:** Axios
- **Build Tool:** Vite

### Backend
- Spring Boot 2.7.x
- MySQL 8.0 + MyBatis Plus
- Redis 6.x
- ElasticSearch 7.17.x
- Sa-Token authentication


### Storage Solutions
- **MySQL:** Business data storage
- **Redis:** Caching, counters, rate limiting
- **ElasticSearch:** Full-text search
- **Tencent Cloud COS:** Image storage

---


