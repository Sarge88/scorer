package com.gkalapis.scorer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.gkalapis.scorer.domain.entities.Team;

@Transactional
public interface TeamRepository extends CrudRepository<Team, Long> {
}