package com.vosouq.gateway.repository;

import com.vosouq.gateway.config.FeignMultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "kyc", configuration = FeignMultipartSupportConfig.class)
public interface KycRepository {

    @PostMapping(value = "/verify-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void verifyUserKyc(@RequestParam(name = "nationalCode") String nationalCode,
                                        @RequestParam(name = "method") String method,
                                        @RequestPart("video") MultipartFile video);

    @PostMapping(value = "/spoof-check", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void userSpoofCheck(@RequestParam(name = "nationalCode") String nationalCode,
                                      @RequestPart MultipartFile image01,
                                      @RequestPart MultipartFile image02,
                                      @RequestPart MultipartFile image03);

}
