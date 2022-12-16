CREATE TABLE loan (
  loan_id VARCHAR(255) NOT NULL,
   borrower_id BIGINT NOT NULL,
   branch_id BIGINT NULL,
   `description` VARCHAR(255) NOT NULL,
   status VARCHAR(255) NULL,
   is_fully_paid BIT(1) NOT NULL,
   borrower_detail_id BIGINT NULL,
   installment_count INT NULL,
   number_of_payments INT NULL,
   monthly_installment_amount DECIMAL NULL,
   interest_to_be_paid DECIMAL NULL,
   principal_loan_amount DECIMAL NULL,
   remaining_principal DECIMAL NULL,
   application_date date NULL,
   due_date date NULL,
   loan_issued_date date NULL,
   fully_paid_date date NULL,
   loan_risk_analysis_id BIGINT NULL,
   loan_type VARCHAR(255) NULL,
   bank_loan_offer_id INT NULL,
   p2p_loan_offer_id INT NULL,
   CONSTRAINT pk_loan PRIMARY KEY (loan_id)
);

CREATE TABLE borrower_details (
  id BIGINT AUTO_INCREMENT NOT NULL,
   occupation VARCHAR(255) NULL,
   email VARCHAR(255) NULL,
   mobile_number VARCHAR(255) NULL,
   monthly_income DOUBLE NOT NULL,
   marital_status VARCHAR(255) NULL,
   children INT NOT NULL,
   dob date NULL,
   CONSTRAINT pk_borrowerdetails PRIMARY KEY (id)
);
CREATE TABLE loan_offer (
  id INT NOT NULL,
   loan_name VARCHAR(255) NOT NULL,
   loan_range VARCHAR(255) NULL,
   interest DOUBLE NOT NULL,
   late_payment_interest DOUBLE NOT NULL,
   CONSTRAINT pk_loanoffer PRIMARY KEY (id)
);
CREATE TABLE loan_payments (
  id BIGINT AUTO_INCREMENT NOT NULL,
   payment_amount DECIMAL NOT NULL,
   payment_date date NOT NULL,
   time time NOT NULL,
   loan_id VARCHAR(255) NULL,
   CONSTRAINT pk_loanpayments PRIMARY KEY (id)
);
CREATE TABLE p2p_loan_offer (
  id INT NOT NULL,
   loan_name VARCHAR(255) NOT NULL,
   loan_range VARCHAR(255) NULL,
   interest DOUBLE NOT NULL,
   late_payment_interest DOUBLE NOT NULL,
   CONSTRAINT pk_p2ploanoffer PRIMARY KEY (id)
);
CREATE TABLE un_approved_loans (
  loan_id VARCHAR(255) NOT NULL,
   date datetime NULL,
   CONSTRAINT pk_unapprovedloans PRIMARY KEY (loan_id)
);
CREATE TABLE un_approved_loans_reasons (
  un_approved_loans_loan_id VARCHAR(255) NOT NULL,
   reasons VARCHAR(255) NULL
);

ALTER TABLE un_approved_loans_reasons ADD CONSTRAINT fk_unapprovedloans_reasons_on_un_approved_loans FOREIGN KEY (un_approved_loans_loan_id) REFERENCES un_approved_loans (loan_id);


CREATE TABLE loan_risk_analysis (
  id BIGINT AUTO_INCREMENT NOT NULL,
   loan_worthiness_analysis BIT(1) NOT NULL,
   monthly_payment DECIMAL NULL,
   amount_left_after_expenses DECIMAL NULL,
   credit_score_rating INT NOT NULL,
   CONSTRAINT pk_loanriskanalysis PRIMARY KEY (id)
);



ALTER TABLE p2p_loan_offer ADD CONSTRAINT uc_p2ploanoffer_loan_name UNIQUE (loan_name);
ALTER TABLE loan_payments ADD CONSTRAINT FK_LOANPAYMENTS_ON_LOAN FOREIGN KEY (loan_id) REFERENCES loan (loan_id);

ALTER TABLE loan_offer ADD CONSTRAINT uc_loanoffer_loan_name UNIQUE (loan_name);

ALTER TABLE loan ADD CONSTRAINT FK_LOAN_ON_BANK_LOAN_OFFER FOREIGN KEY (bank_loan_offer_id) REFERENCES loan_offer (id);

ALTER TABLE loan ADD CONSTRAINT FK_LOAN_ON_BORROWER_DETAIL FOREIGN KEY (borrower_detail_id) REFERENCES borrower_details (id);

ALTER TABLE loan ADD CONSTRAINT FK_LOAN_ON_LOANRISKANALYSIS FOREIGN KEY (loan_risk_analysis_id) REFERENCES loan_risk_analysis (id);

ALTER TABLE loan ADD CONSTRAINT FK_LOAN_ON_P2P_LOAN_OFFER FOREIGN KEY (p2p_loan_offer_id) REFERENCES p2p_loan_offer (id);
