package com.diagnosis.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Symptoms {

    private String token;

    @ToString.Exclude
    private String symptoms;

    private String gender;
    private int yearOfBirth;
    private String language;
    private String format;
}
