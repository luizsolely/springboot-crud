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
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/edit")
	public String editUser(Model model, @RequestParam int id) {
	    User user = userRepository.findById(id).orElse(null);
	    if (user == null) {
	        return "redirect:/users";
	    }

	    UserDTO userDTO = new UserDTO();
	    userDTO.setFirstName(user.getFirstName());
	    userDTO.setLastName(user.getLastName());
	    userDTO.setEmail(user.getEmail());
	    userDTO.setPhone(user.getPhone());
	    userDTO.setAddress(user.getAddress());
	    userDTO.setStatus(user.getStatus());

	    model.addAttribute("user", user);
	    model.addAttribute("userDTO", userDTO);

	    return "users/edit";
	}
	
	@PostMapping("/edit")
	public String editUser(
	        Model model, 
	        @RequestParam int id, 
	        @Valid @ModelAttribute UserDTO userDTO, 
	        BindingResult result
	) {
	    User editedUser = userRepository.findById(id).orElse(null);
	    if (editedUser == null) {
	        return "redirect:/users";
	    }

	    model.addAttribute("user", editedUser);
	    
	    User existingUser = userRepository.findByEmail(userDTO.getEmail());
	    if (existingUser != null && existingUser.getId() != editedUser.getId()) {
	        result.addError(new FieldError("userDTO", "email", userDTO.getEmail(), false, null, null, "Email address is already used."));
	    }

	    if (result.hasErrors()) {
	        return "users/edit";
	    }

	    editedUser.setFirstName(userDTO.getFirstName());
	    editedUser.setLastName(userDTO.getLastName());
	    editedUser.setEmail(userDTO.getEmail());
	    editedUser.setPhone(userDTO.getPhone());
	    editedUser.setAddress(userDTO.getAddress());
	    editedUser.setStatus(userDTO.getStatus());

	    userRepository.save(editedUser);
	    return "redirect:/users";
	}

	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam int id) {
		User user = userRepository.findById(id).orElse(null);
		
		if(user != null) {
			userRepository.delete(user);
		}
		return "redirect:/users";
	}
	
}
