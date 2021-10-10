package com.vosouq.bookkeeping.model.journalizing;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class JournalizingPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private Long number;

    @Temporal(TemporalType.DATE)
    @Column(unique = true, nullable = false)
    private Date date;

    @Temporal(TemporalType.TIME)
    @Column(unique = true, nullable = false)
    private Date time;

    @ToString.Exclude
    @OneToMany(mappedBy = "journalizingPage")
    private Set<JournalEntry> journalEntrySet;
}
