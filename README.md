# Store Management System

Welcome to the Store Management System readme! This project  built using Java and follows Object-Oriented Design (OOD) and Object-Oriented Programming (OOP) principles. Below, you'll find detailed information on how to use the system, its features, and how to contribute to its development.

## Features

- **Product Management**: Add, remove, or update inventory for different types of products.
- **Order Management**: Create new orders, track order history, and perform undo operations.
- **Customer Management**: Save customer information along with their orders.
- **Shipping Options**: Choose from different shipping methods for each order.
- **Invoice Generation**: Automatically generate invoices for customers and accountants.
- **Dynamic Menu Interface**: User-friendly interface with interactive menus for easy navigation.
- **Data Backup and Restoration**: Backup and restore system state on demand.

## Explanation of System

The system manages the sale of products in a store, allowing users to add, remove, and update inventory, create orders, manage customers, and choose shipping options. It supports different product types such as website sales, wholesale, and in-store sales, each with specific features like invoice generation and shipping options.

Customers can place orders for products, specifying their name and contact information. Orders are tracked and can be canceled using the undo feature. The system also provides functionality to view detailed information about products, orders, and profits.


## Design Patterns Used

1. **Singleton (Store Management)**:
   - Ensures unique access to the store instance globally, facilitating centralized inventory management.

2. **Adapter (Product Adapter)**:
   - Simplifies interactions with products in the order class, abstracting complexities of product management.

3. **Factory (Product, Shipping, Invoice)**:
   - Provides centralized creation of various entities, promoting code maintainability and simplifying client code.

4. **Facade (Order Details)**:
   - Encapsulates order-related classes into a cohesive unit, improving code organization and maintainability.

5. **Command (Automatic System, Order)**:
   - Enables efficient execution of system commands and supports undo functionality for orders.

6. **Observer (Shipping)**:
   - Facilitates determination of cheapest shipping option by delegating waiting process to observer.

7. **Memento (Store Management)**:
   - Saves current system state and enables undo functionality, ensuring data integrity.

8. **Template (Invoice)**:
   - Centralizes invoice generation, promoting code reuse and flexibility in formatting.
