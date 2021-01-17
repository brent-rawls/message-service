package com.tts.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.demo.entities.MessageEntity;
import com.tts.demo.entities.UserEntity;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
	List<MessageEntity> findAllByOrderByCreatedAtDesc();
    List<MessageEntity> findAllByUserOrderByCreatedAtDesc(UserEntity user);
    List<MessageEntity> findAllByUserInOrderByCreatedAtDesc(List<UserEntity> users);
    List<MessageEntity> findByTags_PhraseOrderByCreatedAtDesc(String phrase);
    
	//public List<MessageEntity> findAll();
}