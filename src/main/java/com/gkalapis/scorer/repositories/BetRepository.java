package com.gkalapis.scorer.repositories;

import com.gkalapis.scorer.domain.entities.Bet;
import com.gkalapis.scorer.domain.entities.Match;
import com.gkalapis.scorer.domain.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BetRepository extends CrudRepository<Bet, Long> {

    Bet findByUserAndMatch(User user, Match match);

    List<Bet> findByMatch(Match match);

    @Query("SELECT b FROM Bet b join User u on b.user.id=u.id where u.name = :userName")
    List<Bet> findByUserName(@Param("userName") String userName);
}
