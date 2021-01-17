package com.tts.demo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.demo.entities.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long>  {
    Tag findByPhrase(String phrase);

}

