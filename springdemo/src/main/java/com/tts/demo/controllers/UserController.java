package com.tts.demo.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tts.demo.entities.MessageEntity;
import com.tts.demo.entities.UserEntity;
import com.tts.demo.services.MessageService;
import com.tts.demo.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	
	
	public UserController(UserService userService, MessageService messageService) {
		super();
		this.userService = userService;
		this.messageService = messageService;
	}



	@GetMapping(value = "/users/{username}")
	public String getUser(@PathVariable(value="username") String username, Model model) {
		//find username
		UserEntity user = userService.findByUsername(username);
		//get the messages by the username
		List<MessageEntity> userMessages = messageService.findAllByUser(user);
		
		model.addAttribute("user", user);
		model.addAttribute("messageList", userMessages);
		
		
		
		//for following and unfollowing
		UserEntity loggedInUser = userService.getLoggedInUser();
		List<UserEntity> following = loggedInUser.getFollowing();
		boolean isFollowing = false;
		for (UserEntity followedUser : following) {
		    if (followedUser.getUsername().equals(username)) {
		        isFollowing = true;
		    }
		}
		model.addAttribute("following", isFollowing);
		boolean isSelfPage = loggedInUser.getUsername().equals(username);
		model.addAttribute("isSelfPage", isSelfPage);
		
		//slides say return "user"
		return "users/userProfile";
	}
	
	@GetMapping(value = "/users")
	public String getUsers(Model model) {
		List<UserEntity> userList = userService.findAll();
		model.addAttribute("userList", userList);
		//HashMap<String, Integer> userCounts = countUserTweets(userList);
		//model.addAttribute("messageCount", userCounts);
		SetMessageCounts(userList, model);
		//following and unfollowing
		UserEntity loggedInUser = userService.getLoggedInUser();
		List<UserEntity> usersFollowing = loggedInUser.getFollowing();
		SetFollowingStatus(userList, usersFollowing, model);
		
		return "users/list";
	}
	
	private void SetFollowingStatus(List<UserEntity> users, List<UserEntity> usersFollowing, Model model) {
	    HashMap<String,Boolean> followingStatus = new HashMap<>();
	    String username = userService.getLoggedInUser().getUsername();
	    for (UserEntity user : users) {
	        if(usersFollowing.contains(user)) {
	            followingStatus.put(user.getUsername(), true);
	        }else if (!user.getUsername().equals(username)) {
	            followingStatus.put(user.getUsername(), false);
	    	}
	    }
	    model.addAttribute("followingStatus", followingStatus);
	}
	private void SetMessageCounts(List<UserEntity> users, Model model) {
	    HashMap<String,Integer> messageCounts = new HashMap<>();
	    for (UserEntity user : users) {
	        List<MessageEntity> messages = messageService.findAllByUser(user);
	        messageCounts.put(user.getUsername(), messages.size());
	    }
	    model.addAttribute("tweetCounts", messageCounts);
	}	
	
}
