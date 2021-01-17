package com.tts.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.demo.entities.UserEntity;
import com.tts.demo.services.UserService;

@Controller
public class AuthorizationController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/login")
	public String login() {
		return "authorization/login";
	}

	@GetMapping(value="/signup")
	public String registration(Model model){
	    UserEntity user = new UserEntity();
	    model.addAttribute("user", user);
	    return "authorization/signup";
	}

	@PostMapping(value = "/signup")
	public String createNewUser(@Valid UserEntity user, BindingResult bindingResult, Model model) {
	    UserEntity userExists = userService.findByUsername(user.getUsername());
	    if (userExists != null) {
	        bindingResult.rejectValue("username", "error.user", "Username is already taken");
	    }
	    if (!bindingResult.hasErrors()) {
	        userService.create(user);
	        model.addAttribute("success", "Sign up successful!");
	        model.addAttribute("user", new UserEntity());
	    }
	    return "authorization/registration";
	}
}
