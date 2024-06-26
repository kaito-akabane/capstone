package com.example.ProjectCC.repository;

import com.example.ProjectCC.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    public List<User> findAllByName(String userName);
}