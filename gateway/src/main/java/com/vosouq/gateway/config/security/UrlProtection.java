package com.vosouq.gateway.config.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

class UrlProtection {

    static void protect(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/auth/register").hasRole("NOT_LOGGED_IN")
                .antMatchers("/auth/verify").hasRole("NOT_LOGGED_IN")
                .antMatchers("/auth/login").hasRole("NOT_LOGGED_IN")
                .antMatchers("/auth/bio-login").hasRole("NOT_LOGGED_IN")
                .antMatchers("/auth/password").hasAnyRole("LIMITED", "USER")

                .antMatchers("/auth/kyc/verify-user").hasRole("NOT_LOGGED_IN")
                .antMatchers("/auth/kyc/spoof-check").hasRole("NOT_LOGGED_IN")

                .antMatchers("/profile/user/register").hasRole("NOT_LOGGED_IN")
                .antMatchers("/profile/user/verify").hasRole("NOT_LOGGED_IN")
                .antMatchers("/profile/user/login").hasRole("NOT_LOGGED_IN")
                .antMatchers("/profile/user/bio-login").hasRole("NOT_LOGGED_IN")
                .antMatchers("/profile/user/password").hasAnyRole("LIMITED", "USER")
                .antMatchers("/profile/users/device/{deviceId}/info").hasRole("NOT_LOGGED_IN")

                .antMatchers("/kyc/verify-user").hasRole("NOT_LOGGED_IN")
                .antMatchers("/kyc/spoof-check").hasAnyRole("NOT_LOGGED_IN", "LIMITED", "USER")
                .antMatchers("/kyc/**").hasAnyRole("LIMITED", "USER")

                .antMatchers("/scoring/gauge").hasAnyRole("LIMITED", "USER")
                .antMatchers("/scoring-communicator/gauge").hasAnyRole("LIMITED", "USER")

                .antMatchers("/profile/users/token/fcm").hasAnyRole("LIMITED", "USER")

                .antMatchers("/profile/**").hasRole("USER")
                .antMatchers("/contract/**").hasRole("USER")
                .antMatchers("/messaging/**").hasAnyRole("LIMITED", "USER")

                .antMatchers("/scoring/**").hasRole("USER")
                .antMatchers("/scoring-communicator/credit-status/score-gauges").hasAnyRole("LIMITED", "USER")
                .antMatchers("/scoring-communicator/**").hasRole("USER")

                .antMatchers("/bookkeeping/**").hasRole("USER")

                .antMatchers("/payment-gateway/**").denyAll();

    }

}
