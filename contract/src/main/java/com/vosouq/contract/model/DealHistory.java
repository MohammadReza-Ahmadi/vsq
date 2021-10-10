package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "deal_history")
public class DealHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long subjectId;
    private String subjectType;
    private Long sellerId;
    private Long buyerId;
    @Enumerated(EnumType.STRING)
    private Actor actor;
    @Enumerated(EnumType.STRING)
    private Action action;
    @NotNull
    private Timestamp actionDate;
    @NotNull
    private Timestamp createDate;
    @NotNull
    private Timestamp updateDate;
    private String title;
    @Enumerated(EnumType.STRING)
    private ActionState actionState;
}
