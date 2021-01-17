package com.tts.demo.repositories;

import com.tts.demo.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    List<UserEntity> findAll();

    UserEntity save(UserEntity user);

//    UserEntity findByUsernameAndFirstName(String username, String firstName);
//    @Query("SELECT * FROM USER WHERE")
//    UserEntity customQuery(String arg);
}
