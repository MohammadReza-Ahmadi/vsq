package com.vosouq.contract.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Config {
    public static final String GEOGRAPHIC_INFORMATION_INITIALIZED = "GEOGRAPHIC_INFORMATION_INITIALIZED";
    @Id
    private String name;
    @Column(length = 1024)
    private String value;

    public Config(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
