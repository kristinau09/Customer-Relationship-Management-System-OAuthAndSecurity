package com.example.crms.services.customers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crms.dao.CustomerDao;
import com.example.crms.dao.RecordNotFoundException;
import com.example.crms.domain.Call;
import com.example.crms.domain.Customer;

@Transactional
@Service
public class CustomerManagementServiceProductionImpl implements
		CustomerManagementService 
{

	private CustomerDao dao;
	
	@Autowired
	public CustomerManagementServiceProductionImpl(CustomerDao dao)
	{
		this.dao = dao;
	}
	
	@Override
	public Customer newCustomer(Customer newCustomer) 
	{
		if (newCustomer.getCustomerId() == null)
		{
			// generate an ID using some kind of business rule.
			String newId = java.util.UUID.randomUUID().toString();
			newCustomer.setCustomerId(newId);
		}
		dao.create(newCustomer);
		return newCustomer;
	}

	@Override
	public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException
	{
		try
		{
			dao.update(changedCustomer);
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}

	@Override
	public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException 
	{
		try
		{
			dao.delete(oldCustomer);
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException 
	{
		try
		{
			return dao.getById(customerId);
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}

	@Override
	public List<Customer> findCustomersByName(String name) 
	{
		return dao.getByName(name);
	}

	@Override
	public List<Customer> getAllCustomers() 
	{
		return dao.getAllCustomers();
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		try
		{
			return dao.getFullCustomerDetail(customerId);			
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}
	

	@Override
	public void recordCall(String customerId, Call callDetails)
			throws CustomerNotFoundException 
	{
		try
		{
			dao.addCall(callDetails, customerId);			
		}
		catch (RecordNotFoundException e)
		{
			throw new CustomerNotFoundException();
		}
	}

}
