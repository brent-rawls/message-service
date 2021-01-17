package com.tts.demo.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tts.demo.entities.MessageEntity;
import com.tts.demo.entities.UserEntity;
import com.tts.demo.services.MessageService;
import com.tts.demo.services.UserService;


@Controller
public class MessageController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	
	
	//CRUD methods / REST Resources
	
	// methods from slides
	
	@GetMapping(value= {"/messages", "/"})
	public String getFeed(Model model){
	    List<MessageEntity> messages = messageService.findAll();
	    model.addAttribute("messageList", messages);
	    return "feed";
	}
	@GetMapping(value = "/messages/new")
	public String getMessageForm(Model model) {
	    model.addAttribute("message", new MessageEntity());
	    return "newMessage";
	}
	@PostMapping(value = "/messages")
	public String submitTweetForm(@Valid MessageEntity message, BindingResult bindingResult, Model model) {
	    UserEntity user = userService.getLoggedInUser();
	    if (!bindingResult.hasErrors()) {
	        message.setUser(user);
	        messageService.save(message);
	        model.addAttribute("successMessage", "Message successfully posted!");
	        model.addAttribute("message", new MessageEntity());
	    }
	    return "newMessage";
	}
	
	@GetMapping(value = "/messages/{tag}")
	public String getTweetsByTag(@PathVariable(value="tag") String tag, Model model) {
	    List<MessageEntity> messages = messageService.findAllWithTag(tag);
	    model.addAttribute("messageList", messages);
	    model.addAttribute("tag", tag);
	    return "taggedMessages";
	}
	
	
	
	// methods from class
	
	/*
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public MessageEntity createMessage(@ModelAttribute("message") MessageEntity messageEntity) {
		return messageService.save(messageEntity);
	}
		
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public List<MessageEntity> listMessages() {
		return messageService.findAll();
	}
	*/

	/*
	@RequestMapping(value = "messages/{messageId}", method = RequestMethod.GET)
	public MessageEntity getMessage(@PathVariable("messageId") Long messageId) {
		Optional<MessageEntity> messageEntity = messageService.findById(messageId);
		if (messageEntity.isPresent()) {
			return messageEntity.get();
		} else {
			//for real, throw a 404 error
			return null;
		}
		
		//this will work, but without optional/error
		//return messageRepository.findById(messageId);
	}	
	
	//update
	@RequestMapping(value = "messages/{messageId}", method = RequestMethod.PUT)
	public MessageEntity updateMessage(@ModelAttribute("message") MessageEntity messageEntity) {
		//check that id exists and belongs to user, throw exception elsewise
		return messageRepository.save(messageEntity);
	}
	*/
	
	//delete
	/*
	@RequestMapping(value = "messages/{messageId}", method = RequestMethod.DELETE)
	public void deleteMessage(@PathParam("messageID") Long messageId) {
		messageRepository.deleteById(messageId);
	}
	*/
	
	
}
