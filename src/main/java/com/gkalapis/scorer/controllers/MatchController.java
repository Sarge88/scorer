package com.gkalapis.scorer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gkalapis.scorer.domain.entities.Match;
import com.gkalapis.scorer.repositories.MatchRepository;

@RestController
public class MatchController {

    @Autowired
    private MatchRepository matchRepository;

    @RequestMapping("/matches/findByStatus")
    public List<Match> list(String status) { return matchRepository.findByStatus(status); }
}