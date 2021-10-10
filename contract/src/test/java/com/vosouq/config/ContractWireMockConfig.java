package com.vosouq.config;

import com.github.jknack.handlebars.Helper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.util.RandomGeneratorUtility;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@TestConfiguration
@Slf4j
public class ContractWireMockConfig {
    int score = 0;
    Helper<List<String>> userScoreHelper = (context, options) -> {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        context.forEach(element ->
                stringBuilder
                        .append("{\"score\": ")
                        .append(RandomGeneratorUtility.generateRandomNumber(0, 700) - 1)
                        .append(",\"userId\": ")
                        .append(element)
                        .append("},")
        );
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","))
                .append("]");
        log.debug("Json response: {}", stringBuilder.toString());
        return stringBuilder.toString();
    };

    Helper<List<String>> sortedUserScoreHelper = ((context, options) -> {
        StringBuilder stringBuilder = new StringBuilder();
        score = 699;
        stringBuilder.append("[");
        context.forEach(element -> {
                    if (score > 20) {
                        score -= RandomGeneratorUtility.generateRandomNumber(0, 20);
                    }
                    stringBuilder
                            .append("{\"score\": ")
                            .append(score)
                            .append(",\"userId\": ")
                            .append(element)
                            .append("},");
                }
        );
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","))
                .append("]");
        log.debug("Json response: {}", stringBuilder.toString());
        return stringBuilder.toString();
    });

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Rule
    public WireMockServer mockServer() {
        return new WireMockServer(
                options()
                        .port(9565)
                        .extensions(
                                new ResponseTemplateTransformer(false,
                                        "sorted-user-score-helper",
                                        sortedUserScoreHelper))
        );
    }

    @Bean
    @Scope("singleton")
    @Primary
    public OnlineUser getOnlineUser() {
        return new OnlineUser(
                1L,
                0L,
                "091200000",
                "test-client-id",
                "test-client-name");
    }
}
