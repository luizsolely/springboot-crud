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
import org.springframework.web.bind.annotation.PostMapping;

import com.crud.springboot.entity.User;
import com.crud.springboot.entity.UserDTO;
import com.crud.springboot.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User Management", description = "Operações para gerenciar usuários")
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;
    
    @Operation(
        summary = "Listar usuários",
        description = "Retorna a lista de usuários ordenada pelo ID em ordem ascendente."
    )
    @GetMapping({ "", "/" })
    public String getUsers(Model model) {
        var users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("users", users);
        return "users/index";
    }
    
    @Operation(
        summary = "Exibir formulário de criação",
        description = "Exibe o formulário para criação de um novo usuário."
    )
    @GetMapping("/create")
    public String createUser(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "users/create";
    }
    
    @Operation(
        summary = "Criar usuário",
        description = "Cria um novo usuário a partir dos dados informados. Valida o email para evitar duplicatas."
    )
    @PostMapping("/create")
    public String createUser(
            @Valid @ModelAttribute UserDTO userDTO, 
            BindingResult result
    ) {
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
    
    @Operation(
        summary = "Exibir formulário de edição",
        description = "Exibe o formulário para editar um usuário existente, identificado pelo ID informado."
    )
    @GetMapping("/edit")
    public String editUser(
            Model model, 
            @Parameter(description = "ID do usuário a ser editado", example = "1") @RequestParam int id
    ) {
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
    
    @Operation(
        summary = "Editar usuário",
        description = "Atualiza os dados de um usuário existente com base no ID e nos dados informados. Valida a unicidade do email."
    )
    @PostMapping("/edit")
    public String editUser(
            Model model, 
            @Parameter(description = "ID do usuário a ser editado", example = "1") @RequestParam int id, 
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
    
    @Operation(
        summary = "Excluir usuário",
        description = "Exclui um usuário existente com base no ID informado."
    )
    @GetMapping("/delete")
    public String deleteUser(
            @Parameter(description = "ID do usuário a ser excluído", example = "1") @RequestParam int id
    ) {
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            userRepository.delete(user);
        }
        return "redirect:/users";
    }
}
