# capstone - FinBank - Online Banking Customer Portal

This is a backup version of the capstone project from the following repo:
"git clone https://github.com/ayamzan/capstone.git"

## Setup Instructions

Clone the Repository

"git clone https://github.com/yijuchoo/capstone-finbank.git"

---
## Create Users and Roles
### Create roles
SELECT * FROM corebankingdb.role

INSERT INTO `` (`role_id`,`name`) VALUES (1,'ADMIN');

INSERT INTO `` (`role_id`,`name`) VALUES (2,'MANAGER');

INSERT INTO `` (`role_id`,`name`) VALUES (3,'GUEST');

INSERT INTO `` (`role_id`,`name`) VALUES (4,'TELLER');

--
### Create users
admin -> pwd = admin

others -> pwd = pass

**SELECT * FROM corebankingdb.user**

INSERT INTO `` (`enabled`,`id`,`password`,`user_name`) VALUES ('1',1,'$2a$12$zP/Ro/CM6q7R3n39g/FYye9DIE4ID4cOavX0eklB36H648aCw3Kni','admin');

INSERT INTO `` (`enabled`,`id`,`password`,`user_name`) VALUES ('1',2,'$2a$12$7I9pmVDVqo/l6bZPvBcsQOcF86ZxgBr0xFr9X3bw9wZ2wkLDd6Y1a','manager_1');

INSERT INTO `` (`enabled`,`id`,`password`,`user_name`) VALUES ('1',3,'$2a$12$7I9pmVDVqo/l6bZPvBcsQOcF86ZxgBr0xFr9X3bw9wZ2wkLDd6Y1a','guest_1');

INSERT INTO `` (`enabled`,`id`,`password`,`user_name`) VALUES ('1',4,'$2a$12$7I9pmVDVqo/l6bZPvBcsQOcF86ZxgBr0xFr9X3bw9wZ2wkLDd6Y1a','teller_1');

--
### Create user_user_roles
SELECT * FROM corebankingdb.user_user_roles

INSERT INTO `` (`user_id`,`user_roles_role_id`) VALUES (1,1);

INSERT INTO `` (`user_id`,`user_roles_role_id`) VALUES (2,2);

INSERT INTO `` (`user_id`,`user_roles_role_id`) VALUES (3,3);

INSERT INTO `` (`user_id`,`user_roles_role_id`) VALUES (4,4);



-------------------------------
## Technical Documentation

"https://chooyirou.notion.site/Technical-Documentation-Online-Banking-Customer-Portal-2b386085fd9e4402a225ed2b84411c0e"

-------------------------------

## About

This is a Group Capstone Project for NTUC LearningHub's Fullstack Java Developer Program

## Overview
FinBank is an online banking customer portal developed as a capstone project. The portal includes the following modules:
- **Customers**: Manage customer information.
- **Accounts**: Handle various bank accounts.
- **Transactions**: Record and view transactions.
- **Users**: Manage user roles and permissions.

## Features
- **User Management**: Admins can create and manage users with different roles such as Admin, Manager, Teller, and Guest.
- **Customer Management**: Add, edit, view, and delete customer records.
- **Account Management**: Manage customer bank accounts, including opening and closing accounts.
- **Transaction Management**: Record and track deposits, withdrawals, and transfers between accounts.

## Prerequisites
- **Java SE 11** (or later)
- **Spring Boot** (Maven-based project)
- **MySQL Database**
- **SQL Workbench**

-------------------------------

## OBJECTIVE

To implement a basic Online Banking Customer Portal that focus on Accounts, Customers, Transactions and Reporting modules.

Goal: Develop an Online Banking Customer Portal

Technology: MySQL Database, Java SE, Spring Boot, HTML, CSS and JS

Focus: Implement an application with larger scope.

Type of Application: Web Based Spring Application

Deliverable: Online Banking Customer Portal

Logging: Yes

Database Persistence: Yes

-------------------------------

Core Banking Application

This project involves the following,

1) Core Banking Web Application with focus on Accounts, Customers, Transactions and Reporting modules
2) Design and Define Database schema
3) Design and Define

   a. Accounts Module

   b. Customer Module

   c. Transactions Module

   d. Reporting Module

   e. Password Reset [self-help] -> NOT DONE

5) Implement Customer Registration and Login/Logout Modules using OAuth2 Security.
6) Implement MVC application using Spring Boot
7) Database Integration using JPA and Transaction Management using Spring.
8) Implement Unit Testing. -> NOT DONE
9) Logging Implementation.
10) Optional Tasks - Database Migration from Oracle to Postgres Database. -> NOT DONE
11) Optional Tasks - Deploying the solution to a Cloud Infrastructure [Google / Heroku] for better DevOps Lifecycle understanding. -> NOT DONE
12) Optional Tasks – Load sample data and perform benchmarking exercise. -> NOT DONE

