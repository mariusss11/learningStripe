
# Stripe Learning Project

## This project is a simple Java application for learning how to use Stripe's API. It demonstrates how to create customers, retrieve customer details, list all customers, and attach payment methods using the Stripe Java SDK.

### Prerequisites

Before running this project, ensure you have the following:

- Java 8 or later installed
- A Stripe account and API key
- Maven (if using dependency management)

### Setup

Clone this repository or copy the code into your project.

Install Stripe's Java SDK if not already installed:

```xml
<dependency>
    <groupId>com.stripe</groupId>
    <artifactId>stripe-java</artifactId>
    <version>latest_version</version>
</dependency>
```

Set your Stripe API key as an environment variable:

I set up my variable using IntelliJ IDEA's Environmental Variables. [Here](https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html) you can learn more

### Features

1. **Create a Customer**

    - `createCustomerWithMap()`: Creates a customer using a HashMap.
    - `createCustomerWithCustomerCreateParams()`: Creates a customer using `CustomerCreateParams`.

2. **Retrieve a Customer**

    - `getCustomer(String customerId)`: Retrieves a customer by ID.

3. **List Customers**

    - `getAllCustomers()`: Fetches and lists all customers (default limit: 10).

4. **Add Payment Method**

    - `addPaymentMethod()`: Attaches a payment method to a customer after checking if the method already exists.

### Running the Application

Ensure the STRIPE_API_KEY environment variable is set.

Run the application:

```bash
java -jar your-application.jar
```

Or, if using an IDE, run the main method in `Application.java`.

### Notes

- Ensure you use test API keys and payment methods from Stripe’s testing documentation.
- The assert statements ensure that critical values are not null.
- Exception handling should be improved for production readiness.
