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
public class VoucherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "posting_model_code", nullable = false)
    private PostingModel postingModel;

    @ToString.Exclude
    @OneToMany(mappedBy = "voucherModel"/*, fetch = FetchType.EAGER, cascade = CascadeType.DETACH*/)
    @OrderBy(value = "identifier")
    private List<VoucherRowModel> voucherRowModels;
}
