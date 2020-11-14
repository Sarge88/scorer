package com.gkalapis.scorer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gkalapis.scorer.domain.entities.Match;

@Transactional
public interface MatchRepository extends CrudRepository<Match, Long>{

    List<Match> findByStatus(@Param("status") String status);

    //@Query("SELECT max(match.round) FROM Match match WHERE match.status='FINISHED'")
    //int findMaxFinishedRound();
    //List<Match> findByRound(@Param("round") int round);
    //@Query(SELECT * FROM scorer.match WHERE scorer.match.status='SCHEDULED';
}
