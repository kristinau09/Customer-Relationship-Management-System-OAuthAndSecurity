package com.example.crms.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
@EnableTransactionManagement
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
	 * <bean id="customerService" class="com.example.crms.services.customers.CustomerManagementServiceProductionImpl">
	 * <constructor-arg ref="customerDao"/> </bean>
	 */
	@Bean
	public CustomerManagementService customerService(CustomerDao customerDao) {
		return new CustomerManagementServiceProductionImpl(customerDao);
	}
	
	/*
	 * <bean id="diaryService" class="com.example.crms.services.diaryManagement.DiaryManagementServiceProductionImpl">
	 * <constructor-arg ref="actionDao"/> </bean>
	 */
	@Bean
	public DiaryManagementService diaryService(ActionDao actionDao) {
		return new DiaryManagementServiceProductionImpl(actionDao);
	}
	
	/*
	 * <bean id="callService" class="com.example.crms.services.callHandlings.CallHandlingServiceImpl">
	 * <constructor-arg ref="customerService"/> 
	 * <constructor-arg ref="diaryService"/> </bean>
	 */
     @Bean
     public CallHandlingService callService(CustomerManagementService  customers, DiaryManagementService diary) {
    	 return new CallHandlingServiceImpl(customers, diary);
     }
     /*<!--  Data source - warning, the application name is hardcoded into the URL for database! -->
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
		<property name="driverClassName" value="org.hsqldb.jdbcDriver"/>
		<property name="url" value="jdbc:hsqldb:file:../webapps/crm/WEB-INF/database.dat;shutdown=true"/>
		<property name="username" value="sa"/>
		<property name="password" value=""/>
	</bean>
	*/
     
     @Bean
     @Profile("prod")
     public DataSource dataSourceProduction() {
    	 
    	 BasicDataSource ds = new BasicDataSource();
    	 ds.setDriverClassName("org.hsqldb.jdbcDriver");
    	 ds.setUrl("jdbc:hsqldb:file:../webapps/crm/WEB-INF/database.dat;shutdown=true");
    	 ds.setUsername("sa");
    	 ds.setPassword("");
    	 
    	 return ds;
     }  
     /*
     <!--  Data source for testing only-->
 	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
 		<property name="driverClassName" value="org.hsqldb.jdbcDriver"/>
 		<property name="url" value="jdbc:hsqldb:file:databaseTESTING.dat;shutdown=true"/>
 		<property name="username" value="sa"/>
 		<property name="password" value=""/>
 	</bean>
 	*/
     
     @Bean
     @Profile("test")
     public DataSource dataSourceTesting() {
    	 
    	 BasicDataSource ds = new BasicDataSource();
    	 ds.setDriverClassName("org.hsqldb.jdbcDriver");
    	 ds.setUrl("jdbc:hsqldb:file:databaseTESTING.dat;shutdown=true");
    	 ds.setUsername("sa");
    	 ds.setPassword("");
    	 
    	 return ds;
     }  

     
	/*
	<bean id="entityManagerFactory" 
      class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
		
        <property name="jpaVendorAdapter">
	       <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
	         <property name="showSql" value="true"/>
		     <property name="generateDdl" value="true"/>
	       </bean>
        </property>
     	
        <property name="dataSource" ref="dataSource"/>		
    </bean>*/
     
     @Bean
     //wrapper bean for an EntityManagerFactory and it is from the class LocalContainerEntityManagerFactoryBean
     public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    	 
    	 LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    	 
    	 emf.setDataSource(dataSource);
    	 
    	 HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    	 jpaVendorAdapter.setShowSql(true);
    	 jpaVendorAdapter.setGenerateDdl(true);
    	 
    	 emf.setJpaVendorAdapter(jpaVendorAdapter);
    	 
    	 return emf;   	 
     }
	 /*
     <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
		<property name="entityManagerFactory" ref="entityManagerFactory"/>
	 </bean>
	 */
     @Bean
     public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
    	 
    	 JpaTransactionManager transaction = new JpaTransactionManager();    	 
    	 transaction.setEntityManagerFactory(emf);
    	 return transaction;
    	 
     }

}























