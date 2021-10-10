package com.vosouq.scoringcommunicator.controllers;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.scoringcommunicator.controllers.dtos.res.*;
import com.vosouq.scoringcommunicator.services.CreditStatusService;
import com.vosouq.scoringcommunicator.services.UserBusinessService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@VosouqRestController
@RequestMapping(value = "/credit-status")
public class CreditStatusController {

    private UserBusinessService userBusinessService;
    private CreditStatusService creditStatusService;

    public CreditStatusController(UserBusinessService userBusinessService, CreditStatusService creditStatusService) {
        this.userBusinessService = userBusinessService;
        this.creditStatusService = creditStatusService;
    }

    @GetMapping(value = {"/score-gauges"})
    public List<ScoreGaugeRes> getScoreGauge() {
        return creditStatusService.getScoreGauges();
    }

    /**
     * @param userId : other userId
     * @return
     */
    @GetMapping(value = {"", "/{userId}"})
    public ScoreStatusRes getScoreStatus(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getScoreStatus(userId);
    }

    @GetMapping(value = {"/vosouq", "/vosouq/{userId}"})
    public List<TripleRes> getVosouqStatus(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getVosouqStatus(userId, true);
    }

    @GetMapping(value = {"/report/{userId}"})
    public ScoreReportRes getScoreReport(@PathVariable Long userId) {
        return creditStatusService.getScoreReport(userId);
    }

    @GetMapping(value = {"/loans", "/loans/{userId}"})
    public List<TripleRes> getLoansStatus(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getLoansStatus(userId);
    }

    @GetMapping(value = {"/cheques", "/cheques/{userId}"})
    public List<ChequesStatusRes> getChequesStatus(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getChequesStatus(userId);
    }

    @GetMapping(value = {"/time-series/filter/{numberOfDays}", "/time-series/{userId}/filter/{numberOfDays}"})
    public List<ScoreTimeSeriesRes> getScoreTimeSeries(@PathVariable(required = false) Long userId, @PathVariable Integer numberOfDays) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getScoreTimeSeries(userId, numberOfDays);
    }

    @GetMapping(value = {"/details", "/details/{userId}"})
    public ScoreDetailsRes getScoreDetails(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getScoreDetails(userId);
    }

    @GetMapping(value = {"/changes", "/changes/{userId}"})
    public List<ScoreChangeRes> getScoreChanges(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getScoreChanges(userId);
    }

    @GetMapping(value = "/scores")
    public List<UserScoreRes> getUsersScores(@RequestParam List<Long> userIds, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer page, @RequestParam(required = false) boolean scoreDescSort) {
        return creditStatusService.getUsersScores(userIds, pageSize, page, scoreDescSort);
    }
}
