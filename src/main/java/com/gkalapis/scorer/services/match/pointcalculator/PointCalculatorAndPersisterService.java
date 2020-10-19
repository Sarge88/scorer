package com.gkalapis.scorer.services.match.pointcalculator;

import com.gkalapis.scorer.domain.entities.Match;

public interface PointCalculatorAndPersisterService {

    void calculateAndSavePoints(Match match);
}