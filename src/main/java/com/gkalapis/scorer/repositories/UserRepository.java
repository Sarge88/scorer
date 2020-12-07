package com.gkalapis.scorer.repositories;

import com.gkalapis.scorer.domain.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRepository extends CrudRepository<User, Long>{


    User findByName(String name);

    List<User> findAll();

    List<User> findAllByOrderByPointsDesc();
}