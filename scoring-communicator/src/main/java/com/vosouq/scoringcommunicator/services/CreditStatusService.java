package com.vosouq.scoringcommunicator.services;

import com.vosouq.scoringcommunicator.controllers.dtos.res.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CreditStatusService {

    List<ScoreGaugeRes> getScoreGauges();

    ScoreStatusRes getScoreStatus(@PathVariable Long userId);

    List<TripleRes> getVosouqStatus(Long userId);

    List<TripleRes> getVosouqStatus(Long userId, boolean checkAccess);

    ScoreReportRes getScoreReport(@PathVariable Long userId);

    List<TripleRes> getLoansStatus(Long userId);

    List<ChequesStatusRes> getChequesStatus(Long userId);

    List<ScoreTimeSeriesRes> getScoreTimeSeries(Long userId, Integer numberOfDays);

    ScoreDetailsRes getScoreDetails(Long userId);

    List<ScoreChangeRes> getScoreChanges(Long userId);

    List<UserScoreRes> getUsersScores(List<Long> userIds, Integer pageSize, Integer page, boolean scoreDescSort);
}
