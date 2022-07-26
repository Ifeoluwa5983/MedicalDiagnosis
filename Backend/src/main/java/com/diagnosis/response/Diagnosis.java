package com.diagnosis.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data

public class Diagnosis {
    @JsonProperty("Content-Type")
    private String contentType;
    @JsonProperty("Issue")
    public Issue issue;
    @JsonProperty("Specialisation")
    public ArrayList<Specialisation> specialisation;
}
