package com.vosouq.bookkeeping.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(
        uniqueConstraints=
        @UniqueConstraint(columnNames={"userId", "iban"})
)
public class IbanAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    @NonNull
    private String iban;

    @NonNull
    private String bankName;
}
