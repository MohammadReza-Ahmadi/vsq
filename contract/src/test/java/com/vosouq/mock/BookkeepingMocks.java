package com.vosouq.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

public class BookkeepingMocks {
    public static void setupMockBookkeepingResponse(WireMockServer mockService) {
        mockService.stubFor(
                WireMock.post(WireMock.urlPathMatching("/contracts/([a-z0-9]*)/settlement/([a-z0-9]*)"))
                        .willReturn(WireMock.ok())
        );

    }
}
