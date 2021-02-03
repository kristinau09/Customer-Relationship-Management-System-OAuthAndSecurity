package com.example.crms.services.callHandlings;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crms.domain.Action;
import com.example.crms.domain.Call;
import com.example.crms.services.customers.CustomerManagementService;
import com.example.crms.services.customers.CustomerNotFoundException;
import com.example.crms.services.diaryManagement.DiaryManagementService;

@Transactional
@Service
public class CallHandlingServiceImpl implements CallHandlingService 
{
	private CustomerManagementService customerService;
	private DiaryManagementService diaryService;
	
	@Autowired
	public CallHandlingServiceImpl(CustomerManagementService cms, DiaryManagementService dms)
	{
		this.customerService = cms;
		this.diaryService = dms;
	}
	
	
	@Override
	public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException 
	{
		// 1: call the customer service to record the call
		customerService.recordCall(customerId, newCall);
		
		// 2: call the diary service to record the actions
		for (Action nextAction: actions)
		{
			diaryService.recordAction(nextAction);			
		}
	}

}
