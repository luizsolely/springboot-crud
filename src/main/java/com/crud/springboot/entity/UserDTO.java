package com.crud.springboot.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	@NotEmpty(message = "First Name is required.")
	private String firstName;
	
	@NotEmpty(message = "Last Name is required.")
	private String lastName;
	
	@NotEmpty(message = "Invalid Email format.")
	@Email
	private String email;
	
	private String phone;
	private String address;
	private UserStatus status;
	
}
