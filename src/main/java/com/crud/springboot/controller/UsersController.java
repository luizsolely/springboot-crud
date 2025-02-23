package com.crud.springboot.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crud.springboot.entity.User;
import com.crud.springboot.entity.UserDTO;
import com.crud.springboot.repository.UserRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping({ "", "/" })
	public String getUsers(Model model) {
		var users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		model.addAttribute("users", users);
		
		return "users/index";
	}
	
	@GetMapping("/create")
	public String createUser(Model model) {
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userDTO", userDTO);
		
		return "users/create";
	}
	
	@PostMapping("/create")
	public String createUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult result) {
		if(userRepository.findByEmail(userDTO.getEmail()) != null) {
			result.addError(new FieldError("userDTO", "email", userDTO.getEmail(), false, null, null, "Email address is already used."));
		}
		
		if(result.hasErrors()) {
			return "users/create";
		}
		
		User newUser = new User();
		newUser.setFirstName(userDTO.getFirstName());
		newUser.setLastName(userDTO.getLastName());
		newUser.setEmail(userDTO.getEmail());
		newUser.setPhone(userDTO.getPhone());
		newUser.setAddress(userDTO.getAddress());
		newUser.setStatus(userDTO.getStatus());
		newUser.setCreatedAt(new Date());
		
		userRepository.save(newUser);
		
		return "redirect:/users";
	}
	
	
}
