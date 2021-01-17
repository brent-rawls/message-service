package com.tts.demo.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.demo.entities.UserEntity;
import com.tts.demo.services.UserService;

@Controller
public class FollowController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/follow/{username}")
    public String follow(@PathVariable(value="username") String username, HttpServletRequest request) {
		UserEntity loggedInUser = userService.getLoggedInUser();
		UserEntity userToFollow = userService.findByUsername(username);
		List<UserEntity> followers = userToFollow.getFollowers();
		followers.add(loggedInUser);
		userToFollow.setFollowers(followers);
	    userService.save(userToFollow);
	    return "redirect:" + request.getHeader("Referer");
	}
	@PostMapping(value = "/unfollow/{username}")
	public String unfollow(@PathVariable(value="username") String username, HttpServletRequest request) {
	    UserEntity loggedInUser = userService.getLoggedInUser();
	    UserEntity userToUnfollow = userService.findByUsername(username);
	    List<UserEntity> followers = userToUnfollow.getFollowers();
	    followers.remove(loggedInUser);
	    userToUnfollow.setFollowers(followers);
	    userService.save(userToUnfollow);
	    return "redirect:" + request.getHeader("Referer");
	}    
		
	
}
