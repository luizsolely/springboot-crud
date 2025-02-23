package com.crud.springboot.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	@NotEmpty(message = "The First Name is required")
	private String firstName;
	
	@NotEmpty(message = "The Last Name is required")
	private String lastName;
	
	@NotEmpty(message = "The Email is required")
	@Email
	private String email;
	
	private String phone;
	private String address;
	
	@NotEmpty(message = "The Status is required")
	private String status;
	
}
