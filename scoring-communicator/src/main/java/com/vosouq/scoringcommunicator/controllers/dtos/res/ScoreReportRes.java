package com.vosouq.scoringcommunicator.controllers.dtos.res;

import com.vosouq.scoringcommunicator.models.ProfileAccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreReportRes {
    private List<TripleRes> items;
    private ProfileAccessStatus accessStatus;
}
