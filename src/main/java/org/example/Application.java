package org.example;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.RequestOptions;
import com.stripe.param.*;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

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
            throw new RuntimeException("There is already an payment method like this for this customer  !");
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

        Stripe.apiKey = API_KEY;

        // Globally
        // Stripe.apiKey=API_KEY;
        // Customer customer = getCustomer("cus_RxF9tk78H3cv84");
        // System.out.println(customer);


//        Per request
//        RequestOptions requestOptions = RequestOptions.builder()
//                .setApiKey(API_KEY)
//                .build();
//        Customer customer1 = Customer.retrieve("cus_RxF9tk78H3cv84", requestOptions);
//        System.out.println(customer1);

//        Print all the customers of a specific account
//        RequestOptions requestOptions = RequestOptions.builder()
//                .setApiKey(API_KEY)
//                .setStripeAccount("acct_1R2qCPGPc1wYRbRf")
//                .build();
//
//        CustomerListParams params = CustomerListParams.builder().build();
//        CustomerCollection customers = Customer.list(params, requestOptions);
//        System.out.println(customers);


//        RequestOptions requestOptions = RequestOptions.builder()
//                .setApiKey(API_KEY)
//                .setStripeAccount("acct_1R2qCPGPc1wYRbRf")
//                .build();
//        Customer customer = Customer.retrieve("cus_RxF9tk78H3cv84",requestOptions);
//        System.out.println(customer);


        // Create a customer with no params
//        CustomerCreateParams params =
//                CustomerCreateParams.builder().build();
//
//        try {
//            Customer customer = Customer.create(params);
//            System.out.println(customer);
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        // Fetch the created customer
//        Customer customer = Customer.retrieve("cus_Rxz0aUttBIIPok");
//        System.out.println(customer);


        // Create a customer with scalar values
//        CustomerCreateParams params = CustomerCreateParams.builder()
//                .setEmail("jenny.rosen@example.com") // POST BODY ->  email=jenny.rosen@example.com&name=Jenny%20Rosen
//                .setName("Jenny Rosen")
//                .build();
//
//        Customer customer = Customer.create(params);

//        Customer customer = Customer.retrieve("cus_Rxz44ICfW7Q88r");
//        System.out.println(customer);

        // Create a customer with enum
//        CustomerCreateParams params = CustomerCreateParams.builder()
//                .setTaxExempt(
//                        CustomerCreateParams.TaxExempt.REVERSE
//                )
//                .build();
//        Customer customer = Customer.create(params);
//        System.out.println(customer);

        // Create a customer with a nested object
//        CustomerCreateParams params =
//                CustomerCreateParams.builder()
//                        .setPaymentMethod("pm_card_visa")
//                        .setInvoiceSettings(
//                                CustomerCreateParams.InvoiceSettings.builder()
//                                        .setDefaultPaymentMethod("pm_card_visa")
//                                        .build()
//                        )
//                        .build();
//
//        Customer customer = Customer.create(params);
//        System.out.println(params);

//        Customer customer = Customer.retrieve("cus_Rxz44ICfW7Q88r");

        // Create a customer with a list of strings
//        CustomerCreateParams params =
//                CustomerCreateParams.builder()
//                        .addPreferredLocale("en")
//                        .addPreferredLocale("es")
//                        .build();
//
//        Customer customer = Customer.create(params);
//        System.out.println(customer);

        // Update the email address for a customer
//        CustomerUpdateParams params =
//                CustomerUpdateParams.builder()
//                        .setEmail("jr-2@example.com")
//                        .build();
//
//        Customer customer = Customer.retrieve("cus_RxzHcn3fbK0Lbm");
//        Customer updatedCustomer = customer.update(params);
//        System.out.println(customer.getEmail());
//        System.out.println(updatedCustomer.getEmail());

        // Update customer with nested params
//        CustomerUpdateParams params =
//                CustomerUpdateParams.builder()
//                        .setInvoiceSettings(
//                                CustomerUpdateParams
//                                        .InvoiceSettings
//                                        .builder()
//                                        .addCustomField(
//                                                CustomerUpdateParams
//                                                        .InvoiceSettings
//                                                        .CustomField
//                                                        .builder()
//                                                        .setName("VAT")
//                                                        .setValue("ABC123")
//                                                        .build()
//                                        )
//                                        .addCustomField(
//                                                CustomerUpdateParams
//                                                        .InvoiceSettings
//                                                        .CustomField
//                                                        .builder()
//                                                        .setName("VAT2")
//                                                        .setValue("XYZ987")
//                                                        .build()
//                                        )
//                                        .build()
//                        )
//                    .build();
//
//        Customer customer = Customer.retrieve("cus_RxzHcn3fbK0Lbm");
//        Customer updatedCustomer = customer.update(params);
//        System.out.println(updatedCustomer.getInvoiceSettings().getCustomFields());


        // Fetch a list of customers
//        CustomerListParams params =
//                CustomerListParams.builder()
//                        .setLimit(3L)
//                        .build();
//
//        CustomerCollection customers =
//                Customer.list(params);
//
//        for (Customer customer : customers.getData()) {
//            System.out.println(customer.getId() + " " + customer.getEmail());
//        }


        // Fetch a list of customers, filtered by email
//        CustomerListParams params =
//                CustomerListParams.builder()
//                        .setEmail("jenny.rosen@example.com")
//                        .build();
//        // API ENDPOINT -> v1/customers?email=jenny.rosen@example.com
//        CustomerCollection customers =
//                Customer.list(params);
//        for (Customer customer : customers.getData()) {
//            System.out.println(customer.getId() + " " + customer.getEmail());
//        }

        // Delete a customer

        // Fetch API call
//        Customer customer = Customer.retrieve("cus_Rxz9gFqx4FHswZ");
//
//        // Delete API call
//        Customer deletedCustomer = customer.delete();
//
//        System.out.println(deletedCustomer);


        // Customer methods
        // First, create a payment intent to confirm
//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(1000L)
//                        .setCurrency("USD")
//                        .build();
//
//        PaymentIntent intent = PaymentIntent.create(params);
//
//        System.out.println(intent.getId());
//        System.out.println(intent.getStatus());

        // Second, confirm the payment intent
//        PaymentIntentConfirmParams params =
//                PaymentIntentConfirmParams.builder()
//                        .setPaymentMethod("pm_card_visa")
//                        .setReturnUrl("https://www.example.com")
//
//                .build();
//
//        PaymentIntent intent =
//                PaymentIntent.retrieve(
//                        "pi_3R43oCGPc1wYRbRf0RHUrETZ"
//        );
//        PaymentIntent confirmedIntent =
//                intent.confirm(params);
//
//        System.out.println(confirmedIntent);
//        System.out.println(confirmedIntent.getId());
//        System.out.println(confirmedIntent.getStatus());

        // Nested service methods
//        InvoiceLineItemCollectionListParams params =
//                InvoiceLineItemCollectionListParams.builder()
//                        .setLimit(5L)
//                        .build();
//
//        Invoice invoice = Invoice.retrieve(
//                "in_1R448LGPc1wYRbRfyQEpdmua"
//        );
//
//        InvoiceLineItemCollection lines =
//                invoice.getLines().list(params);
//        // v1/invoices/in_xxx/lines?limit=5
//
//        System.out.println(invoice.getId());
//        System.out.println(lines);


        // Passing request params in headers
//        CustomerCreateParams params =
//                CustomerCreateParams.builder()
//                        .setEmail("jenny.rosen@example.com")
//                        .build();
//
//        // This info will be passed into the request headers of the POST request
//        RequestOptions requestOptions =
//                RequestOptions.builder()
//                        .setStripeAccount("")
//                        .build();
//
//        Customer customer =
//                Customer.create(params, requestOptions);


        // PAGINATION

//        List<String> listIds = new ArrayList<>();
//
//        CustomerListParams params = CustomerListParams.builder()
//                .setLimit(100L)
//                .build();
//        Iterable<Customer> itCustomers = Customer.list(params).autoPagingIterable();
//        for (Customer customer : itCustomers) {
//            listIds.add(customer.getId());
//        }
//
//        System.out.println(listIds);
//        System.out.printf("# of customers: %d \n", listIds.size());



        // METADATA

//        CustomerCreateParams params=
//                CustomerCreateParams.builder()
//                        .setName("Jenny Rosen")
//                        .putMetadata("my_app_id","123")
//                        .putMetadata("my_app_username", "jenny_rosen")
//                        .build();
//        Customer customer = Customer.create(params);
//        System.out.println(customer);

        Customer retrivedCustomer = Customer.retrieve("cus_RyYy6mFdEG7h4D");
        System.out.println(retrivedCustomer.getMetadata());





    }
}