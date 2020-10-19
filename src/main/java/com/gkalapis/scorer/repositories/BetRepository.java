package com.gkalapis.scorer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gkalapis.scorer.domain.entities.Bet;
import com.gkalapis.scorer.domain.entities.Match;
import com.gkalapis.scorer.domain.entities.User;

import java.util.List;

@Transactional
public interface BetRepository extends CrudRepository<Bet, Long> {

    Bet findByUserAndMatch(User user, Match match);

    List<Bet> findByMatch(Match match);

    List<Bet> findByUserId(@Param("userId") String userId);
}
