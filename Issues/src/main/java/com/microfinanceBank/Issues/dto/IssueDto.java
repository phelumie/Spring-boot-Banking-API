package com.microfinanceBank.Issues.dto;

import com.microfinanceBank.Issues.entity.IssueStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueDto {
        private Long id;
        private Long accountNumber;
        private String issue;
        private LocalDate creationDate;
        private LocalDateTime time;
        private IssueStatus status;



}
