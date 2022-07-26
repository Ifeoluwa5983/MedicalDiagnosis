package com.diagnosis.dtos;

import lombok.Data;

@Data
public class DiagnosedIssuesDto {

    private String IssueName;

//    private List<String> specialization;

    private int accuracy;

    private String profName;

    private boolean isValid;
}
