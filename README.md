# Easy Store

## Description of the Project

This is an online shopping platform that aims to make shopping easier by allowing to filter products based on multiple metrics and more secure by requiring authentication to add, alter or delete products.


## User Stories

- As a shopper, I want to browse products by categories so that I can easily find items I’m interested in.

- As a shopper, I want to add products to my shopping cart so that I can purchase them later.

- As a shopper, I want to view, update, and clear my shopping cart so that I can prepare for checkout.

- As a shopper, I want to place an order so that I can purchase items in my cart.

- As an admin, I want to add, update, or delete products so that I can manage the store inventory.

- As an admin, I want to view all orders so that I can monitor sales and manage fulfillment.

- As a developer, I want to use RESTful endpoints so that I can integrate the backend with the frontend application.

- As a QA engineer, I want to test user registration functionality to ensure it works as expected

## Setup

- Open and run package titled, "capstone-starter"
- Open package title, "capstone-client-web-application"
- Select browser of choice

### Prerequisites

- IntelliJ IDEA: Ensure you have IntelliJ IDEA installed, which you can download from [here](https://www.jetbrains.com/idea/download/).
- Java SDK: Make sure Java SDK is installed and configured in IntelliJ.

### Running the Application in IntelliJ

Follow these steps to get your application running within IntelliJ IDEA:

1. Open IntelliJ IDEA.
2. Select "Open" and navigate to the directory where you cloned or downloaded the project.
3. After the project opens, wait for IntelliJ to index the files and set up the project.
4. Find the main class with the `public static void main(String[] args)` method.
5. Right-click on the file and select 'Run 'YourMainClassName.main()'' to start the application.

## Technologies Used

- Java: JDK 17
- java.Math.BigDecimal
- java.util.List
- java.sql.*
- org.springframework.beans.factory.annotation.Autowired;
- org.springframework.http.HttpStatus;
- org.springframework.security.access.prepost.PreAuthorize;
- org.springframework.web.bind.annotation.*
- javax.servlet.http.HttpServletRequest;
- javax.servlet.http.HttpServletResponse;
- java.io.IOException;
- Spring Framework sercurity and servlet

## Demo

![cart-screen.png](app-run-pics%2Fcart-screen.png)
![homescreen.png](app-run-pics%2Fhomescreen.png)
![homescreen-logged-in.png](app-run-pics%2Fhomescreen-logged-in.png)
![postman-opt.png](app-run-pics%2Fpostman-opt.png)
![postman-reqired.png](app-run-pics%2Fpostman-reqired.png)
![product-filter.png](app-run-pics%2Fproduct-filter.png)
![profile-update-page.png](app-run-pics%2Fprofile-update-page.png)
![sql-db-post-update.png](app-run-pics%2Fsql-db-post-update.png)
![sql-profiles-pre-edit.png](app-run-pics%2Fsql-profiles-pre-edit.png)

## Future Work

Outline potential future enhancements or functionalities you might consider adding:

- Design improvements

- A mechanism for checking out

- A more robust price totaling and inventory management system

- Ability to add product picture to cart page

## Team Members

- **Joseph Pressley** - CategoriesController class, added authentication, fixed bugs in filter by price and bug in update() in ProductController class and all mechanisms to read form and write to sql database
- **Raymond Mouron** - skeleton code and API setup

## Thanks

- Thank you to [Raymond Mouron] for continuous support and guidance.