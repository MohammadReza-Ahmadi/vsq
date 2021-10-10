package com.vosouq.bookkeeping.model.posting;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class PostingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    private String title;

    @ToString.Exclude
    @OneToMany(mappedBy = "postingModel"/*, fetch = FetchType.EAGER, cascade = CascadeType.DETACH*/)
    private List<VoucherModel> voucherModels;

    private String description;
}
