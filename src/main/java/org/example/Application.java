package org.example;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.stripe.param.PaymentMethodAttachParams;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Application {

    public static void createCustomerWithMap() throws StripeException {
        // The params of the customer we will create
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", "test2@gmail.com");

        // Creating the customer attaching the params
        Customer newCustomer = Customer.create(customerParams);
        System.out.println("The ID OF THE CREATE CUSTOMER:" + newCustomer.getId());
    }

    public static void createCustomerWithCustomerCreateParams() throws StripeException {
        // Alternative method: Creating a customer using the 'CustomerCreateParams' class
        CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                .setName("Test Name")
                .setEmail("test@gmail.com")
                .build();
        Customer newCustomerWithCustomerCreateParams = Customer.create(customerCreateParams);
    }

    public static Customer getCustomer(String customerId) throws StripeException {
        assert customerId != null : "Customer ID should not be null";

        // Should provide the Customer ID
        Customer retrievedCustomer = Customer.retrieve(customerId);
//        System.out.println(retrievedCustomer);
        return retrievedCustomer;
    }

    public static List<Customer> getAllCustomers() throws StripeException {
        // The limit is from 1 to 100. Default is 10
        CustomerListParams params = CustomerListParams.builder().setLimit(10L).build();
        CustomerCollection customers = Customer.list(params);

        // Make a list from the response
        List<Customer> customerList = customers.getData();

        System.out.println("The customers emails are: ");
        for (Customer customer : customerList) {
            System.out.println(customer.getEmail());
        }
        return customerList;
    }

    public static void addPaymentMethod() throws StripeException {

        Customer customer = Application.getCustomer("cus_RxF9tk78H3cv84");

        var listOfPaymentMethod = customer.listPaymentMethods().getData();
//        for (PaymentMethod paymentMethod  : listOfPaymentMethod) {
//            System.out.println(paymentMethod.getId());
//            System.out.println(paymentMethod.getCard().getLast4());
//        }
        PaymentMethod paymentMethod = PaymentMethod.retrieve("pm_card_visa"); // Use a valid test PaymentMethod ID

        if (listOfPaymentMethod.stream().anyMatch(card -> Objects.equals(
                card.getCard().getLast4(), paymentMethod.getCard().getLast4()))
        ) {
            throw new RuntimeException("THERE IS ALREADY AN PAYMENT METHOD LIKE THIS FOR THIS CUSTOMER!");
//            return;
        }

        // Attach a payment method to a customer from https://docs.stripe.com/testing?testing-method=payment-methods
        paymentMethod.attach(
                PaymentMethodAttachParams.builder().setCustomer(customer.getId()).build()
        );

    }



    public static void main(String[] args) throws StripeException {

        final String API_KEY = System.getenv("STRIPE_API_KEY");

        assert API_KEY != null : "API_KEY SHOULD NOT BE NULL";

        Stripe.apiKey=API_KEY;

        Application.addPaymentMethod();




    }
}