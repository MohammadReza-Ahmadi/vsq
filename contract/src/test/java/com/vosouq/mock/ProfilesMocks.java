package com.vosouq.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class ProfilesMocks {
    public static void setupMockProfileResponse(WireMockServer mockService) {

        mockService.stubFor(
                WireMock.get(WireMock.urlPathMatching("/users/([0-9]*)"))
                        .willReturn(WireMock.aResponse()
                                .withBody(
                                        "{\n" +
                                                "  \"imageUrl\": \"http://mock.com/img.jpg\",\n" +
                                                "  \"name\": \"user-{{request.path.[1]}}\",\n" +
                                                "  \"userId\": {{request.path.[1]}}\n" +
                                                "}")
                                .withHeader("Content-Type", "application/json")
                                .withStatus(200)
                                .withTransformers("response-template"))
        );

        mockService.stubFor(
                WireMock.post(WireMock.urlPathEqualTo("/users/notification"))
                        .willReturn(WireMock.ok())
        );
    }
}
