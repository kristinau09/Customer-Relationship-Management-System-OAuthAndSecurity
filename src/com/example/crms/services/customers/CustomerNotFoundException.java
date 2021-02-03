package com.example.crms.services.customers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception type denoting that the required customer was not found.
 */
public class CustomerNotFoundException extends Exception 
{
	//added this just to stop the annoying warning in Eclipse.
	private static final long serialVersionUID = 1L;
}
