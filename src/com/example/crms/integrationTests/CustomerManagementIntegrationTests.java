package com.example.crms.integrationTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.crms.config.ApplicationConfig;
import com.example.crms.domain.Action;
import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;
import com.example.crms.services.callHandlings.CallHandlingService;
import com.example.crms.services.customers.CustomerManagementService;
import com.example.crms.services.customers.CustomerNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
public class CustomerManagementIntegrationTests 
{
	@Autowired
	private CustomerManagementService customerService;
	
	@Autowired
	private CallHandlingService callsService;
	
	@Test
	public void testCreatingACustomerRecord()
	{
		Customer testCustomer = new Customer("9191", "VPP", "email", "telephone","notes"); 
		customerService.newCustomer(testCustomer);
		
		List<Customer> allCustomers = customerService.getAllCustomers();
		int numberOfCustomers = allCustomers.size();
		
		assertEquals(1, numberOfCustomers);
	}
	
	@Test
	public void testFindingACustomer()
	{
		Customer testCustomer = new Customer("9191", "VPP", "email", "telephone","notes"); 
		customerService.newCustomer(testCustomer);
		
		try 
		{
			Customer foundCustomer = customerService.findCustomerById("9191");
			assertEquals(testCustomer, foundCustomer);
		} 
		catch (CustomerNotFoundException e) 
		{
			fail("Customer not found");
		}
	}
	
	@Test
	public void testAddingACallToACustomer()
	{
		Customer testCustomer = new Customer("9191", "VPP", "email", "telephone","notes"); 
		customerService.newCustomer(testCustomer);
		
		Call testCall = new Call("Just a test call");
		List<Action> actions = new ArrayList<Action>();
		
		try 
		{
			callsService.recordCall("9191", testCall, actions);
			
			Customer foundCustomer = customerService.getFullCustomerDetail("9191");
			Call foundCall = foundCustomer.getCalls().get(0);
			assertEquals(testCall, foundCall);
		} 
		catch (CustomerNotFoundException e) 
		{
			fail("Customer was not found");
		}
	}
}
