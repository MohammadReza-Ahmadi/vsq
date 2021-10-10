package com.vosouq.contract.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ContractStepStatus {
    CURRENT,
    DONE,
    WAIT;
}