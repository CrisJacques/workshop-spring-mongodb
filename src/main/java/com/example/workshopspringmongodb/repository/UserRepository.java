package com.example.workshopspringmongodb.repository;

import com.example.workshopspringmongodb.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
}
