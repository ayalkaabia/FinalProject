package com.example.finalprojectsqliteversion;
import java.util.ArrayList;

public interface CustomersDAO {
    Boolean isCustomerExists(String email,String password);
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerID);
    ArrayList<Customer> getAllCustomers();
    Customer getOneCustomer(int customerID);
}
