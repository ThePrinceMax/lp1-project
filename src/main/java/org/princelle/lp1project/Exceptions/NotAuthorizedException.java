package org.princelle.lp1project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends Exception{

	private static final long serialVersionUID = 1L;

	public NotAuthorizedException(String message){
		super(message);
	}
}