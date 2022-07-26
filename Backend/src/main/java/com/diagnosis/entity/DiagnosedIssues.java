package com.diagnosis.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "diagnosed_issues")
public class DiagnosedIssues {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String IssueName;

//    private List<String> specialization;

    private int accuracy;

    private String profName;

    private boolean isValid;
}
