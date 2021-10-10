package com.vosouq.scoringcommunicator.services.impl;

import com.vosouq.scoringcommunicator.controllers.dtos.raws.*;
import com.vosouq.scoringcommunicator.controllers.dtos.res.*;
import com.vosouq.scoringcommunicator.infrastructures.Messages;
import com.vosouq.scoringcommunicator.models.*;
import com.vosouq.scoringcommunicator.repositories.ScoringCalculatorRepository;
import com.vosouq.scoringcommunicator.repositories.UserProfileRepositoryMOC;
import com.vosouq.scoringcommunicator.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vosouq.scoringcommunicator.infrastructures.Constants.*;
import static com.vosouq.scoringcommunicator.infrastructures.utils.CreditScoringUtil.*;

@Service
public class CreditStatusServiceImpl implements CreditStatusService {

    private CredentialProfileService credentialProfileService;
    private CredentialCheckRequestService credentialCheckRequestService;
    private ScoringCalculatorRepository scoringCalculatorRepository;
    private ValidationService validationService;
    private UserBusinessService userBusinessService;
    private Messages messages;
    //todo: should be replace by actual service
    private UserProfileRepositoryMOC userProfileRepositoryMOC;

    @Value("${creditStatus.percentile.total}")
    private int percentileTotal;

    public CreditStatusServiceImpl(CredentialProfileService credentialProfileService, CredentialCheckRequestService credentialCheckRequestService, ScoringCalculatorRepository scoringCalculatorRepository, ValidationService validationService, UserBusinessService userBusinessService, Messages messages, UserProfileRepositoryMOC userProfileRepositoryMOC) {
        this.credentialProfileService = credentialProfileService;
        this.credentialCheckRequestService = credentialCheckRequestService;
        this.scoringCalculatorRepository = scoringCalculatorRepository;
        this.validationService = validationService;
        this.userBusinessService = userBusinessService;
        this.messages = messages;
        this.userProfileRepositoryMOC = userProfileRepositoryMOC;
    }

    @Override
    public List<ScoreGaugeRes> getScoreGauges() {
        return scoringCalculatorRepository.getScoreGauges()
                .stream().map(r -> new ScoreGaugeRes((r.getStart() > ZERO_INT ? r.getStart() - ONE_INT : r.getStart()), r.getEnd(), r.getTitle(), r.getColor()))
                .collect(Collectors.toList());
    }

    @Override
    public ScoreStatusRes getScoreStatus(Long userId) {
        CredentialProfile profile = credentialProfileService.initializeProfile(userId);
        validateUserAccess(userId);
        ScoreStatusRaw raw = scoringCalculatorRepository.getScoreStatus(userId);
        if (userBusinessService.isNotOnlineUser(userId))
            raw.setOtherUserProfile(userProfileRepositoryMOC.getUserProfile(userId));

        return new ScoreStatusRes(
                raw.getOtherUserProfile(),
                raw.getScore(),
                raw.getMaxScore(),
                raw.getLastScoreChange(),
                raw.getLastUpdateDate(),
                profile.getPublicView(),
                raw.getScoreGaugeRaws().stream().map(r -> new ScoreGaugeRes((r.getStart() > ZERO_INT ? r.getStart() - ONE_INT : r.getStart()), r.getEnd(), r.getTitle(), r.getColor())).collect(Collectors.toList()));
    }

    @Override
    public List<TripleRes> getVosouqStatus(Long userId) {
        return getVosouqStatus(userId, false);
    }

    @Override
    public List<TripleRes> getVosouqStatus(Long userId, boolean checkAccess) {
        credentialProfileService.initializeProfile(userId);
        if (checkAccess)
            validateUserAccess(userId);
        VosouqStatusRaw vsqRaw = scoringCalculatorRepository.getVosouqStatus(userId);
        boolean vsqNegStatus = vsqRaw.getNegativeStatusCount() > ZERO_INT;
        return List.of(
                new TripleRes(messages.get("TripleRes.membershipDuration.title"), vsqRaw.getMembershipDurationMonth().toString(), messages.getUnitTitle(UnitType.MONTH)),
                new TripleRes(messages.get("TripleRes.doneTrades.title"), vsqRaw.getDoneTradesCount().toString(), messages.getUnitTitle(UnitType.NUMBER)),
                new TripleRes(messages.get("TripleRes.undoneTrades.title"), vsqRaw.getUndoneTradesCount().toString(), messages.getUnitTitle(UnitType.NUMBER)),
                new TripleRes(messages.get("TripleRes.negativeStatus.title"), messages.getHavingTitle(vsqNegStatus)),
                new TripleRes(messages.get("TripleRes.delayAvg.title"), vsqRaw.getDelayDaysCountAvg().toString(), messages.getUnitTitle(UnitType.NUMBER)),
                new TripleRes(messages.get("TripleRes.recommendToOthers.title"), vsqRaw.getRecommendToOthersCount().toString(), messages.getUnitTitle(UnitType.NUMBER))
        );
    }

    @Override
    public ScoreReportRes getScoreReport(Long userId) {
        // create and return user CredentialProfile if it's not exist
        CredentialProfile profile = credentialProfileService.initializeProfile(userId);

        ChequesStatusRaw chqRaw = scoringCalculatorRepository.getChequesStatus(userId);
        LoansStatusRaw lnRaw = scoringCalculatorRepository.getLoansStatus(userId);
        boolean chqNegStatus = chqRaw.getUnfixedReturnedChequesTotalBalance() > ZERO_INT;
        boolean lnNegStatus = lnRaw.getPastDueLoansAmount().add(lnRaw.getArrearsLoansAmount()).add(lnRaw.getSuspiciousLoansAmount()).compareTo(BigDecimal.ZERO) > ZERO_INT;
        // get user's vosouqStatus items
        List<TripleRes> triples = new ArrayList<>(getVosouqStatus(userId));
        // add user's cheques and loans items
        triples.add(new TripleRes(messages.get("TripleRes.unfixedReturnedCheque.title"), messages.getHavingTitle(chqNegStatus)));
        triples.add(new TripleRes(messages.get("TripleRes.loansWithNegativeStatus.title"), messages.getHavingTitle(lnNegStatus)));
        if (isTrue(profile.getPublicView()))
            return new ScoreReportRes(triples, ProfileAccessStatus.GRANTED);
        CredentialCheckRequest request = credentialCheckRequestService.getOnlineUserRequestForOtherUser(userId);
        if (isNotNull(request))
            return new ScoreReportRes(triples, resolveAccessStatus(RequestStatus.resolve(request.getStatus())));
        return new ScoreReportRes(triples, ProfileAccessStatus.LIMITED);
    }

    private ProfileAccessStatus resolveAccessStatus(RequestStatus status) {
        switch (status) {
            case WAITING:
                return ProfileAccessStatus.WAITING;
            case ACCEPTED:
                return ProfileAccessStatus.GRANTED;
            case REJECTED:
                return ProfileAccessStatus.LIMITED;
        }
        return null;
    }

    @Override
    public List<TripleRes> getLoansStatus(Long userId) {
        credentialProfileService.initializeProfile(userId);
        validateUserAccess(userId);
        LoansStatusRaw lnRaw = scoringCalculatorRepository.getLoansStatus(userId);
        return List.of(
                new TripleRes(messages.get("TripleRes.currentLoansCount.title"), lnRaw.getCurrentLoansCount().toString(), messages.getUnitTitle(UnitType.ITEM)),
                new TripleRes(messages.get("TripleRes.pastDueLoansAmount.title"), lnRaw.getPastDueLoansAmount().toString(), messages.getUnitTitle(UnitType.RIAL)),
                new TripleRes(messages.get("TripleRes.arrearsLoansAmount.title"), lnRaw.getArrearsLoansAmount().toString(), messages.getUnitTitle(UnitType.RIAL)),
                new TripleRes(messages.get("TripleRes.suspiciousLoansAmount.title"), lnRaw.getSuspiciousLoansAmount().toString(), messages.getUnitTitle(UnitType.RIAL))
        );
    }

    @Override
    public List<ChequesStatusRes> getChequesStatus(Long userId) {
        credentialProfileService.initializeProfile(userId);
        validateUserAccess(userId);
        ChequesStatusRaw raw = scoringCalculatorRepository.getChequesStatus(userId);
        List<ChequesStatusRes> resList = new ArrayList<>(THREE_INT);
        resList.add(new ChequesStatusRes(
                messages.get("ChequesStatusRes.title.inLast3Months"),
                raw.getUnfixedReturnedChequesCountOfLast3Months(),
                raw.getUnfixedReturnedChequesTotalBalance()));

        resList.add(new ChequesStatusRes(
                messages.get("ChequesStatusRes.title.inLastYear"),
                raw.getUnfixedReturnedChequesCountOfLast3Months() + raw.getUnfixedReturnedChequesCountBetweenLast3To12Months(),
                raw.getUnfixedReturnedChequesTotalBalance()));

        resList.add(new ChequesStatusRes(
                messages.get("ChequesStatusRes.title.moreThanOneYear"),
                raw.getUnfixedReturnedChequesCountOfMore12Months() + raw.getUnfixedReturnedChequesCountOfLast5Years(),
                raw.getUnfixedReturnedChequesTotalBalance()));

        return resList;
    }

    @Override
    public List<ScoreTimeSeriesRes> getScoreTimeSeries(Long userId, Integer numberOfDays) {
        credentialProfileService.initializeProfile(userId);
        validateUserAccess(userId);
        List<ScoreTimeSeriesRaw> raws = scoringCalculatorRepository.getScoreTimeSeries(userId, numberOfDays);
        return raws.stream().map(r -> new ScoreTimeSeriesRes(r.getScore_date(), r.getScore())).collect(Collectors.toList());
    }

    @Override
    public ScoreDetailsRes getScoreDetails(Long userId) {
        credentialProfileService.initializeProfile(userId);
        validateUserAccess(userId);
        ScoreDetailsRes scoreDetailsRes = new ScoreDetailsRes();
        scoreDetailsRes.setChartItems(new ArrayList<>());
        scoreDetailsRes.setDetails(new ArrayList<>());

        // load data from scoring-engine
        List<ScoreGaugeRaw> scoreGauges = scoringCalculatorRepository.getScoreGauges();
        ScoreBoundariesRaw scoreBoundariesRaw = scoringCalculatorRepository.getScoreBoundaries();
        ScoreDetailsRaw scoreDetailsRaw = scoringCalculatorRepository.getScoreDetails(userId);

        // set chartItems
        List<ScoreDistributionRaw> scoreDistributionRaws = scoringCalculatorRepository.getScoreDistributions();
        for (ScoreDistributionRaw sdr : scoreDistributionRaws) {
            String color = resolveChartItemColor(scoreGauges, sdr.getFromScore(), sdr.getToScore(), scoreDetailsRaw.getScore());
            scoreDetailsRes.addNewChartItem(sdr.getFromScore(), sdr.getToScore(), color);
        }

        // set Details
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.identities"), scoreDetailsRaw.getIdentitiesScore(), scoreBoundariesRaw.getIdentitiesMaxScore());
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.histories"), scoreDetailsRaw.getHistoriesScore(), scoreBoundariesRaw.getHistoriesMaxScore());
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.volumes"), scoreDetailsRaw.getVolumesScore(), scoreBoundariesRaw.getVolumesMaxScore());
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.timeliness"), scoreDetailsRaw.getTimelinessScore(), scoreBoundariesRaw.getTimelinessMaxScore());

        // set Percentile
        int level = (scoreDetailsRaw.getScore() * percentileTotal) / scoreBoundariesRaw.getMaxScore();
        scoreDetailsRes.setPercentileData(percentileTotal, level);
        return scoreDetailsRes;
    }

    @Override
    public List<ScoreChangeRes> getScoreChanges(Long userId) {
        credentialProfileService.initializeProfile(userId);
        validateUserAccess(userId);
        List<ScoreChangeRaw> raws = scoringCalculatorRepository.getScoreChanges(userId);
        return raws.stream().map(r -> new ScoreChangeRes(r.getChangeReason(), r.getChangeDate(), r.getScoreChange())).collect(Collectors.toList());
    }

    @Override
    public List<UserScoreRes> getUsersScores(List<Long> userIds, Integer pageSize, Integer page, boolean scoreDescSort) {
        pageSize = getDefaultValIfNull(pageSize, PAGE_SIZE_DEFAULT);
        page = getDefaultValIfNull(page, PAGE_NO_DEFAULT);
        List<UserScoreRaw> raws = scoringCalculatorRepository.getUsersScores(userIds, pageSize, page, scoreDescSort);
        return raws.stream().map(r -> new UserScoreRes(r.getUserId(), r.getScore())).collect(Collectors.toList());
    }

    private void validateUserAccess(Long userId) {
        if (userBusinessService.isNotOnlineUser(userId))
            validationService.validateUserAccess(userId);
    }

    private String resolveChartItemColor(List<ScoreGaugeRaw> scoreGaugeRes, Integer min, Integer max, Integer score) {
        if (min <= score && max >= score) {
            return scoreGaugeRes.stream().filter(g -> g.getStart() <= score && g.getEnd() >= score).findFirst().get().getColor();
        }
        return messages.get("ScoreDetailsRes.ChartItem.color.gray");
    }
}
