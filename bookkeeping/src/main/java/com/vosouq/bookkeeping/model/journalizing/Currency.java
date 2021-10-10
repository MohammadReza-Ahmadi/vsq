package com.vosouq.bookkeeping.model.journalizing;

import com.vosouq.bookkeeping.enumeration.CurrencyCode;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Currency {

    /**
     * Id of the currency which its value should be code of each currency. for example: USD, IRR,...
     */

    @Id
    @NonNull
    @Enumerated(EnumType.STRING)
    private CurrencyCode code;

    @ToString.Exclude
    @OneToMany(mappedBy = "currency")
    private Set<Account> accountSet;

    /**
     * symbol of the currency, for example: $,ریال,...
     */
    @Column(nullable = false)
    private String symbol;

    /**
     * title of the currency, for example: US Dollar, Iran Rial,...
     */
    @Column(nullable = false)
    private String title;


    /**
     * exchange rate of the currency.
     * It's used when for example an account is created with USD and it needs to convert to IRR currency.
     */
    private Float rate;
}
