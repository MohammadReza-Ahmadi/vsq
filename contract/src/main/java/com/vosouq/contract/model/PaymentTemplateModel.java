package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTemplateModel {

    private Long amount;
    private Timestamp dueDate;
}