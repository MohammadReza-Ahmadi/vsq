package com.vosouq.kyc.repository;

import com.vosouq.kyc.model.AddKycInfoRequest;
import com.vosouq.kyc.model.CompleteKycInfoRequest;
import com.vosouq.kyc.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile")
public interface ProfileRepository {

    @PutMapping("/users/kyc/complete")
    User completeKycStatus(@RequestBody CompleteKycInfoRequest completeKycInfoRequest);

    @PostMapping("/users/kyc")
    void addKycInfo(@RequestBody AddKycInfoRequest addKycInfoRequest);

}
