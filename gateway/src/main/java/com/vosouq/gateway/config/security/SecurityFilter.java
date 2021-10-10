package com.vosouq.gateway.config.security;

import com.vosouq.commons.model.OnlineUser;
import com.vosouq.gateway.model.Authorities;
import com.vosouq.gateway.model.Client;
import com.vosouq.gateway.model.Token;
import com.vosouq.gateway.model.TokenType;
import com.vosouq.gateway.repository.ClientRepository;
import com.vosouq.gateway.service.ProfileService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";

    private final ClientRepository clientRepository;
    private final TokenStore tokenStore;
    private final ProfileService profileService;

    public SecurityFilter(ClientRepository clientRepository,
                          TokenStore tokenStore,
                          ProfileService profileService) {

        this.clientRepository = clientRepository;
        this.tokenStore = tokenStore;
        this.profileService = profileService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(AUTHORIZATION);

        if (!StringUtils.isEmpty(authorization)) {

            Optional<Token> tokenOptional = tokenStore.get(authorization);

            tokenOptional.ifPresent(token -> {

                if (token.getExpireDate().compareTo(new Date()) > 0) {

                    OnlineUser onlineUser = new OnlineUser();
                    onlineUser.setUserId(token.getUserId());
                    onlineUser.setDeviceId(token.getDeviceId());
                    onlineUser.setPhoneNumber(token.getPhoneNumber());
                    onlineUser.setClientId(token.getClientId());
                    onlineUser.setClientName(token.getClientName());

                    request.setAttribute("onlineUser", onlineUser);

                    Authorities authorities =
                            token.getTokenType().equals(TokenType.RESTRICTED)
                                    ? Authorities.ROLE_LIMITED
                                    : Authorities.ROLE_USER;

                    tokenStore.refresh(token);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            null,
                            null,
                            Collections.singleton(authorities));

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            });

        } else {

            String clientId = request.getHeader(CLIENT_ID);
            String clientSecret = request.getHeader(CLIENT_SECRET);

            if (!StringUtils.isEmpty(clientId) && !StringUtils.isEmpty(clientSecret)) {

                Optional<Client> clientOptional = clientRepository.findByClientIdAndClientSecret(clientId, clientSecret);

                clientOptional.ifPresent(client -> {
                    OnlineUser onlineUser = new OnlineUser();
                    onlineUser.setUserId(-1);
                    onlineUser.setClientId(client.getClientId());
                    onlineUser.setClientName(client.getName());
                    request.setAttribute("onlineUser", onlineUser);

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            null,
                            null,
                            Collections.singleton(Authorities.ROLE_NOT_LOGGED_IN));
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                });

            }
        }

        filterChain.doFilter(request, response);
    }

}
