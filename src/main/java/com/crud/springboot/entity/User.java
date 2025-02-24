package com.crud.springboot.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	
	private String firstName;
	private String lastName;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	private String phone;
	private String address;
	
	@Enumerated(EnumType.STRING)
    private UserStatus status;
	
	private Date CreatedAt;
	
}
