package com.microfinanceBank.Issues.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "issues")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Issues {

    @Id
    private Long id;
    @Indexed
    private Long accountNumber;
    private String issue;
    private IssueStatus status;
    private LocalDate creationDate;
    private LocalDateTime time;

    @Transient
    public static final  String SEQUENCE_NAME="user_sequence";

  }
