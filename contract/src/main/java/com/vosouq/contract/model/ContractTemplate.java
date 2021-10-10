package com.vosouq.contract.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity(name = "contract_template")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "template_type")
public abstract class ContractTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Timestamp createDate;

    private Timestamp updateDate;

    @NotNull
    private Long sellerId;

    private Boolean favorite;

    @NotNull
    private String title;

    private Long price;

    @Size(max = 100)
    @NotNull
    private String province;

    @Size(max = 100)
    @NotNull
    private String city;

    @Size(max = 100)
    private String neighbourhood;

    private String description;

    @NotNull
    @Column(columnDefinition = "integer default 1")
    private Integer numberOfRepeats;

    @NotNull
    private Boolean viewable;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "contract_template_files")
    private List<FileAddress> files;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean repeatable;

    @Override
    public String toString() {
        return "ContractTemplate{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", sellerId=" + sellerId +
                ", favorite=" + favorite +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", description='" + description + '\'' +
                ", numberOfRepeats=" + numberOfRepeats +
                ", viewable=" + viewable +
                ", files=" + files +
                ", repeatable=" + repeatable +
                '}';
    }
}
