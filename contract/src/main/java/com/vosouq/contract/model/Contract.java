package com.vosouq.contract.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "contract")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "contract_type")
public abstract class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Timestamp createDate;
    private Timestamp updateDate;

    @ManyToOne
    private ContractTemplate contractTemplate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ContractState state;

    @NotNull
    private Long sellerId;
    private Long buyerId;

    @NotNull
    private String title;
    private Long price;
    private Long terminationPenalty;

    @OneToOne
    private ContractDelivery delivery;

    @OneToOne
    private ContractBuyerApproval buyerApproval;

    @Size(max = 100)
    private String province;

    @Size(max = 100)
    private String city;

    @Size(max = 100)
    private String neighbourhood;

    private String description;

    private Boolean multiStepPayment;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    @JsonIgnore
    private List<Payment> payments;

    private Timestamp sellerSignDate;
    private Timestamp buyerSignDate;
    private Timestamp feeDepositDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "contract_files")
    private List<FileAddress> files;

    @OneToOne
    private Suggestion suggestion;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "other_attachment_files")
    private List<FileAddress> otherAttachments;

    private String signDescription;

}
