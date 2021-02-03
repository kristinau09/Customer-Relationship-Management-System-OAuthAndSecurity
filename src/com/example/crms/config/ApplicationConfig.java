package com.example.crms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.crms.dao.ActionDao;
import com.example.crms.dao.ActionDaoJpaImpl;
import com.example.crms.dao.CustomerDao;
import com.example.crms.dao.CustomerDaoJpaImpl;
import com.example.crms.services.callHandlings.CallHandlingService;
import com.example.crms.services.callHandlings.CallHandlingServiceImpl;
import com.example.crms.services.customers.CustomerManagementService;
import com.example.crms.services.customers.CustomerManagementServiceProductionImpl;
import com.example.crms.services.diaryManagement.DiaryManagementService;
import com.example.crms.services.diaryManagement.DiaryManagementServiceProductionImpl;

@Configuration
public class ApplicationConfig {
		
	//<bean id="customerDao" class="com.example.crms.dao.CustomerDaoJpaImpl"></bean>
	@Bean
	public CustomerDao customerDao() {
		return new CustomerDaoJpaImpl();
	}
	
	//<bean id="actionDao" class="com.example.crms.dao.ActionDaoJpaImpl"></bean>
	@Bean
	public ActionDao actionDao() {
		return new ActionDaoJpaImpl();
	}
	
	
	/*
	 * <bean id="customerService" class=
	 * "com.example.crms.services.customers.CustomerManagementServiceProductionImpl">
	 * <constructor-arg ref="customerDao"/> </bean>
	 */
	@Bean
	public CustomerManagementService customerService(CustomerDao customerDao) {
		return new CustomerManagementServiceProductionImpl(customerDao);
	}
	
	/*
	 * <bean id="diaryService" class=
	 * "com.example.crms.services.diaryManagement.DiaryManagementServiceProductionImpl">
	 * <constructor-arg ref="actionDao"/> </bean>
	 */
	@Bean
	public DiaryManagementService diaryService(ActionDao actionDao) {
		return new DiaryManagementServiceProductionImpl(actionDao);
	}
	
	/*
	 * <bean id="callService"
	 * class="com.example.crms.services.callHandlings.CallHandlingServiceImpl">
	 * <constructor-arg ref="customerService"/> <constructor-arg
	 * ref="diaryService"/> </bean>
	 */
     @Bean
     public CallHandlingService callService(CustomerManagementService  customers, DiaryManagementService diary) {
    	 return new CallHandlingServiceImpl(customers, diary);
     }
	

}
