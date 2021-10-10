package com.vosouq.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class MessageMocks {
    public static void setupMockMessageResponse(WireMockServer mockService) {
        mockService.stubFor(
                WireMock.post(WireMock.urlPathEqualTo("/"))
                        .willReturn(WireMock.ok())
        );
    }
}
