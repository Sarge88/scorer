package com.gkalapis.scorer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gkalapis.scorer.domain.entities.User;

import java.util.List;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>{

    //List<User> findAllByOrderByPointsDesc();

    User findByName(String name);

    List<User> findAll();
}