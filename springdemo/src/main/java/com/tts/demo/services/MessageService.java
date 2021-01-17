package com.tts.demo.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tts.demo.entities.MessageEntity;
import com.tts.demo.entities.Tag;
import com.tts.demo.entities.UserEntity;
import com.tts.demo.repositories.MessageRepository;
import com.tts.demo.repositories.TagRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private TagRepository tagRepository;
	
	
	public List<MessageEntity> findAll() {
		List<MessageEntity> messages = messageRepository.findAllByOrderByCreatedAtDesc();
		return formatMessages(messages);
	}
	
	public List<MessageEntity> findAllByUser(UserEntity user) {
		List<MessageEntity> messages = messageRepository.findAllByUserOrderByCreatedAtDesc(user);
		return formatMessages(messages);
	}
	
	public List<MessageEntity> findAllByUsers(List<UserEntity> users) {
		List<MessageEntity> messages = messageRepository.findAllByUserInOrderByCreatedAtDesc(users);
		return formatMessages(messages);
	}
	public void save(MessageEntity message) {
		handleTags(message);           
		messageRepository.save(message);
	}
	
	private void handleTags(MessageEntity message) {
	    List<Tag> tags = new ArrayList<Tag>(); 
	    Pattern pattern = Pattern.compile("#\\w+");
	    Matcher matcher = pattern.matcher(message.getContent());
	    while (matcher.find())
	    {
	       String phrase = matcher.group().substring(1).toLowerCase();
	       Tag tag = tagRepository.findByPhrase(phrase);
	       if(tag == null) {
	           tag = new Tag();
	           tag.setPhrase(phrase);
	           tagRepository.save(tag);
	       }
	       tags.add(tag);
	   }    
	   message.setTags(tags);
	} 
	
	private List<MessageEntity> formatMessages(List<MessageEntity> messages) {
	    addTagLinks(messages);
	    return messages;
	}
	private void addTagLinks(List<MessageEntity> messages) {
	    Pattern pattern = Pattern.compile("#\\w+");
	    for(MessageEntity message: messages) {
	        String content = message.getContent();
	        Matcher matcher = pattern.matcher(content);
	        Set<String> tags = new HashSet<String>();
	        while(matcher.find()) {
	            tags.add(matcher.group());
	        }
	        for(String tag : tags) {
	            content = content.replaceAll(tag, 
	            "<a class=\"tag\" href=\"/tweets/" + tag.substring(1).toLowerCase() + "\">" + tag + "</a>");
	        }
	        message.setContent(content);
	    }
	}
	
	public List<MessageEntity> findAllWithTag(String tag){
	    List<MessageEntity> messages = messageRepository.findByTags_PhraseOrderByCreatedAtDesc(tag);
	    return formatMessages(messages);
	}
	
}
