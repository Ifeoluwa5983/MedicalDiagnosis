package com.diagnosis.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Issue {

    @JsonProperty("ID")
    public int iD;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("ProfName")
    public String profName;
    @JsonProperty("Icd")
    public String icd;
    @JsonProperty("IcdName")
    public String icdName;
    @JsonProperty("Accuracy")
    public int accuracy;
}
