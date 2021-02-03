package com.example.crms.services.customers;

import java.util.List;

import com.example.crms.dao.RecordNotFoundException;
import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;

/**
 * This interface defines the functionality we want the Customer Management Service
 * to offer. 
 */
public interface CustomerManagementService 
{
	/**
	 * Takes a customer domain object and saves it in the database
	 */
	public Customer newCustomer(Customer newCustomer);
	
	/**
	 * The specified customer is updated in the database.
	 * @throws CustomerNotFoundException 
	 */
	public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException;
	
	/**
	 * The specified customer is removed from the database
	 * @throws CustomerNotFoundException 
	 * @throws RecordNotFoundException 
	 */
	public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException;
	
	/**
	 * Finds the customer by Id
	 */
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException;

	/**
	 * Finds customers by their name. 
	 */
	public List<Customer> findCustomersByName (String name);

	/**
	 * Returns a complete list of the customers in the system.
	 */
	public List<Customer> getAllCustomers();

	/**
	 * For the specified customer, returns the customer object together
	 * will all calls associated with that customer
	 * @throws CustomerNotFoundException 
	 */
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException;
	
	
	/**
	 * Records a call against a particular customer
	 */
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException;
}
