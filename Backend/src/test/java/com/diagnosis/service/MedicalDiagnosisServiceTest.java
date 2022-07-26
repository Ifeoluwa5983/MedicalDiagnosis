package com.diagnosis.service;

import com.diagnosis.DiagnosisException.DiagnosisException;
import com.diagnosis.dtos.DiagnosedIssuesDto;
import com.diagnosis.request.Symptoms;
import com.diagnosis.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MedicalDiagnosisServiceTest {

    @Autowired
    MedicalDiagnosisService medicalDiagnosisService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void login() throws DiagnosisException {
        LoginResponse loginResponse = medicalDiagnosisService.login();
        assertNotNull(loginResponse);
        log.info("Login Response -> {} ",loginResponse);
    }
    @Test
    void diagnose() throws DiagnosisException {
        int[] symptom = new int[] { 235};
        Symptoms symptoms = new Symptoms();
        symptoms.setGender("female");
        symptoms.setYearOfBirth(1998);
        symptoms.setSymptoms(Arrays.toString(symptom));
        Object diagnosis = medicalDiagnosisService.getDiagnosis(symptoms);
        assertNotNull(diagnosis);
        log.info("Diagnosis ->{}", diagnosis);
    }

    @Test
    void createDiagnosedIssues() throws DiagnosisException {
        DiagnosedIssuesDto diagnosedIssuesDto = new DiagnosedIssuesDto();
        diagnosedIssuesDto.setIssueName("Malaria");
        diagnosedIssuesDto.setAccuracy(70);
        diagnosedIssuesDto.setValid(true);
        assertNotNull(diagnosedIssuesDto);
        medicalDiagnosisService.createDiagnosedIssues(diagnosedIssuesDto);
    }
}