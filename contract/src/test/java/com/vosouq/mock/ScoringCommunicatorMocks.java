package com.vosouq.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class ScoringCommunicatorMocks {
    public static void setupMockScoringCommunicatorResponse(WireMockServer mockService) {
        mockService.stubFor(
                WireMock.get(WireMock.urlPathEqualTo("/credit-status/scores"))
                        .willReturn(WireMock.aResponse()
                                .withBody("{{{sorted-user-score-helper request.query.userIds}}}")
                                .withHeader("Content-Type", "application/json")
                                .withStatus(200)
                                .withTransformers("response-template"))

        );
    }
}
