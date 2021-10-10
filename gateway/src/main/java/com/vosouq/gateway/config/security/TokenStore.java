package com.vosouq.gateway.config.security;

import com.vosouq.commons.model.OnlineUser;
import com.vosouq.gateway.model.BioToken;
import com.vosouq.gateway.model.Token;
import com.vosouq.gateway.model.TokenType;
import com.vosouq.gateway.repository.BioTokenRepository;
import com.vosouq.gateway.repository.TokenRepository;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Configuration
@Transactional
public class TokenStore {

    private final TokenRepository tokenRepository;
    private final BioTokenRepository bioTokenRepository;
    private final OnlineUser onlineUser;

    public TokenStore(TokenRepository tokenRepository,
                      BioTokenRepository bioTokenRepository,
                      OnlineUser onlineUser) {

        this.tokenRepository = tokenRepository;
        this.bioTokenRepository = bioTokenRepository;
        this.onlineUser = onlineUser;
    }

    public Optional<Token> get(String token) {
        return tokenRepository.findByToken(token);
    }

    public void refresh(Token token) {
        token.setExpireDate(new Date(System.currentTimeMillis() + (100000 * 60 * 1000L)));
        tokenRepository.save(token);
    }

    public Token createToken(Long userId,
                             Long deviceId,
                             String phoneNumber,
                             boolean kycCompleted) {

        Token token = new Token();
        token.setToken(UUID.randomUUID().toString());
        token.setUserId(userId);
        token.setDeviceId(deviceId);
        token.setPhoneNumber(phoneNumber);
        token.setTokenType(kycCompleted ? TokenType.FULL_ACCESS : TokenType.RESTRICTED);
        token.setClientId(onlineUser.getClientId());
        token.setClientName(onlineUser.getClientName());
        token.setExpireDate(new Date(System.currentTimeMillis() + (100000 * 60 * 1000L)));

        return tokenRepository.save(token);
    }

    public BioToken createBioToken(Long userId,
                                   Long deviceId,
                                   String phoneNumber,
                                   String token) {

        BioToken bioToken = new BioToken();
        bioToken.setUserId(userId);
        bioToken.setDeviceId(deviceId);
        bioToken.setPhoneNumber(phoneNumber);
        bioToken.setToken(token);
        bioToken.setCreateDate(new Date());

        return bioTokenRepository.save(bioToken);
    }

    public void revokeToken(Long userId, Long deviceId) {
        List<Token> tokens = tokenRepository.findAllByUserIdAndDeviceId(userId, deviceId);
        tokens.forEach(tokenRepository::delete);
    }

    public void revokeBioToken(Long userId, Long deviceId) {
        List<BioToken> bioTokens = bioTokenRepository.findAllByUserIdAndDeviceId(userId, deviceId);
        bioTokens.forEach(bioTokenRepository::delete);
    }

}
