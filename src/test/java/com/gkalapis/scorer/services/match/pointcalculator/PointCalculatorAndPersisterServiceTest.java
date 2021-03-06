package com.gkalapis.scorer.services.match.pointcalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gkalapis.scorer.domain.entities.Bet;
import com.gkalapis.scorer.domain.entities.Match;
import com.gkalapis.scorer.domain.entities.User;
import com.gkalapis.scorer.repositories.BetRepository;
import com.gkalapis.scorer.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class PointCalculatorAndPersisterServiceTest {

    @InjectMocks
    private PointCalculatorAndPersisterServiceImpl pointCalculatorAndPersister;

    @Mock
    private BetRepository betRepository; 

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.initMocks(PointCalculatorAndPersisterServiceTest.class);
    }
    
    @Test
    public void shouldCalculatePointsCorrectlyWhenMatchResultIsDraw() {
        //GIVEN
        Match match = createMatch(2, 2);

        List<BetWrapper> betWrapperList = Arrays.asList(
                new BetWrapper(new Bet(createDefaultUser(), match, 2, 2), 3),
                new BetWrapper(new Bet(createDefaultUser(), match, 1, 1), 1),
                new BetWrapper(new Bet(createDefaultUser(), match, 3, 1), 0),
                new BetWrapper(new Bet(createDefaultUser(), match, 0, 4), 0)
        );

        test(match, betWrapperList);
    }

    @Test
    public void shouldCalculatePointsCorrectlyWhenHomeTeamWon() {
        //GIVEN
        Match match = createMatch(3, 1);

        List<BetWrapper> betWrapperList = Arrays.asList(
                new BetWrapper(new Bet(createDefaultUser(), match, 3, 1), 3),
                new BetWrapper(new Bet(createDefaultUser(), match, 1, 0), 1),
                new BetWrapper(new Bet(createDefaultUser(), match, 2, 2), 0),
                new BetWrapper(new Bet(createDefaultUser(), match, 0, 1), 0)
        );

        test(match, betWrapperList);
    }

    @Test
    public void shouldCalculatePointsCorrectlyWhenAwayTeamWon() {
        //GIVEN
        Match match = createMatch(0, 2);

        List<BetWrapper> betWrapperList = Arrays.asList(
                new BetWrapper(new Bet(createDefaultUser(), match, 0, 2), 3),
                new BetWrapper(new Bet(createDefaultUser(), match, 2, 4), 1),
                new BetWrapper(new Bet(createDefaultUser(), match, 2, 2), 0),
                new BetWrapper(new Bet(createDefaultUser(), match, 3, 0), 0)
        );

        test(match, betWrapperList);
    }

    @Test
    public void shouldUpdateUserPointsCorrectly() {
        //GIVEN
        Match match = createMatch(2, 2);
        int userPoints = 6;
        User user = createUser(userPoints);
        Bet bet = new Bet(user, match, 2, 2);

        Mockito.when(betRepository.findByMatch(match)).thenReturn(Arrays.asList(bet));

        //WHEN
        pointCalculatorAndPersister.calculateAndSavePoints(match);

        //THEN
        Assertions.assertEquals(bet.getGainedPoints().intValue(), 3);
        Assertions.assertEquals(user.getPoints(), userPoints + 3);
    }

    private void test(Match match, List<BetWrapper> betWrapperList) {
        mockBetList(match, betWrapperList);

        //WHEN
        pointCalculatorAndPersister.calculateAndSavePoints(match);

        //THEN
        for (BetWrapper betWrapper : betWrapperList) {
        	Assertions.assertEquals(betWrapper.getBet().getGainedPoints(), betWrapper.getExpectedGainedPoints());
        }
    }

    private void mockBetList(Match match, List<BetWrapper> betWrapperList) {
        List<Bet> betList = new ArrayList<>();
        for (BetWrapper betWrapper : betWrapperList) {
            betList.add(betWrapper.getBet());
        }
        Mockito.when(betRepository.findByMatch(match)).thenReturn(betList);
    }


    private Match createMatch(int homeTeamGoal, int awayTeamGoal) {
        return new Match(1L, null, null, null, null, homeTeamGoal, awayTeamGoal, "FINISHED", 1, null);
    }

    private User createUser(int points) {
        return new User("testUser", points, "asd");
    }

    private User createDefaultUser() {
        return createUser(0);
    }
}