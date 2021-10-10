package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "suggestion")
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @CreatedDate
    @Column(name = "create_date")
    private Timestamp createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private Timestamp updateDate;

    @ManyToOne
    private ContractTemplate contractTemplate;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "suggestion_state")
    @Enumerated(EnumType.STRING)
    private SuggestionState suggestionState;

    @Column(name = "suggestion_state_date")
    private Timestamp suggestionStateDate;

    @NotNull
    @Column(name = "price")
    private Long price;

    @Column(name = "description")
    private String description;

    @Column(name = "pinned")
    private Boolean pinned;

}
