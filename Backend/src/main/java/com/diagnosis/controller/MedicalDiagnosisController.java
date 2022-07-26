package com.diagnosis.controller;

import com.diagnosis.DiagnosisException.DiagnosisException;
import com.diagnosis.dtos.DiagnosedIssuesDto;
import com.diagnosis.request.Symptoms;
import com.diagnosis.service.MedicalDiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class MedicalDiagnosisController {

    @Autowired
    MedicalDiagnosisService medicalDiagnosisService;

    @PostMapping("/diagonise")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> diagonise(@RequestBody @Valid Symptoms symptoms) throws DiagnosisException {
        Object object = medicalDiagnosisService.getDiagnosis(symptoms);
        return ResponseEntity.status(200).body(object);
    }

    @PostMapping
    public ResponseEntity<?> createDiagnosedIssues(@RequestBody @Valid DiagnosedIssuesDto diagnosedIssuesDto) throws DiagnosisException {
        medicalDiagnosisService.createDiagnosedIssues(diagnosedIssuesDto);
        return ResponseEntity.status(200).body("Successfully Created");
    }
}
