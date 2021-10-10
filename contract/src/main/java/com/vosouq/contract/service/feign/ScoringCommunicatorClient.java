package com.vosouq.contract.service.feign;

import com.vosouq.contract.model.UserScore;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="scoring-communicator")
public interface ScoringCommunicatorClient {

    @GetMapping("/credit-status/scores")
    List<UserScore> getUsersScores(@RequestParam List<Long> userIds,
                                   @RequestParam int page,
                                   @RequestParam int pageSize,
                                   @RequestParam boolean scoreDescSort);

    @GetMapping("/credit-status/scores")
    List<UserScore> getUsersScores(@RequestParam List<Long> userIds);
}
