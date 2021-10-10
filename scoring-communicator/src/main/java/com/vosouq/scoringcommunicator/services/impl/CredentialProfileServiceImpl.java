package com.vosouq.scoringcommunicator.services.impl;

import com.vosouq.scoringcommunicator.infrastructures.Constants;
import com.vosouq.scoringcommunicator.infrastructures.utils.CreditScoringUtil;
import com.vosouq.scoringcommunicator.models.CredentialProfile;
import com.vosouq.scoringcommunicator.repositories.ScoringCalculatorRepository;
import com.vosouq.scoringcommunicator.services.CredentialProfileService;
import com.vosouq.scoringcommunicator.services.UserBusinessService;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import static com.vosouq.scoringcommunicator.infrastructures.utils.CreditScoringUtil.isNull;

@Service
public class CredentialProfileServiceImpl implements CredentialProfileService {

    private JongoService jongoService;
    private MongoCollection collection;
    private UserBusinessService userBusinessService;
    private ScoringCalculatorRepository scoringCalculatorRepository;

    public CredentialProfileServiceImpl(JongoService jongoService, UserBusinessService userBusinessService, ScoringCalculatorRepository scoringCalculatorRepository) {
        this.jongoService = jongoService;
        this.userBusinessService = userBusinessService;
        this.scoringCalculatorRepository = scoringCalculatorRepository;
        this.collection = this.jongoService.getMongoCollection(CredentialProfile.collectionName);
    }

    @Override
    public CredentialProfile initializeProfile(Long userId) {
        CredentialProfile profile = getUserProfile(userId);
        if (isNull(profile))
            profile = create(userId);
        return profile;
    }

    @Override
    public void updateProfilePublicView(int publicity) {
        collection.update("{" + CredentialProfile.Fields.userId.name() + " : # }", userBusinessService.getOnlineUserId())
                .with("{$set: {" + CredentialProfile.Fields.publicView.name() + ": #}}", publicity);
    }

    @Override
    public CredentialProfile getUserProfile(Long userId) {
        return collection.findOne("{" + CredentialProfile.Fields.userId.name() + ": #}", userId).as(CredentialProfile.class);
    }

    private CredentialProfile create(Long userId) {
        // call scoring-calculator endpoint to initialize user profile in scoring-calculator microservice
        scoringCalculatorRepository.createUserProfile(userId);
        // create CredentialProfile in current microservice
        CredentialProfile profile = new CredentialProfile(userId, Constants.ZERO_INT);
        collection.save(profile);
        return profile;
    }
}
