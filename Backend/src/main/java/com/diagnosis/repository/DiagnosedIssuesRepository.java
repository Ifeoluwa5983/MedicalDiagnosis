package com.diagnosis.repository;

import com.diagnosis.entity.DiagnosedIssues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosedIssuesRepository extends JpaRepository<DiagnosedIssues, Long> {
}
