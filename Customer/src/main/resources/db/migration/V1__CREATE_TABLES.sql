CREATE TABLE customer (
  id BIGINT AUTO_INCREMENT NOT NULL,
   email VARCHAR(255) NOT NULL,
   keycloak_id VARCHAR(255) NOT NULL,
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   password VARCHAR(255) NULL,
   image_url VARCHAR(255) NULL,
   contact_number VARCHAR(255) NULL,
   customer_address_id BIGINT NULL,
   customer_details_id BIGINT NULL,
   status VARCHAR(255) NOT NULL,
   creation_date date NOT NULL,
   time time NOT NULL,
   CONSTRAINT pk_customer PRIMARY KEY (id)
);
CREATE TABLE account (
  acct_num BIGINT NOT NULL,
   nuban_no VARCHAR(255) NULL,
   balance DECIMAL NOT NULL,
   account_type VARCHAR(255) NOT NULL,
   status VARCHAR(255) NOT NULL,
   customer_id BIGINT NULL,
   debit_card_id BIGINT NULL,
   date_created datetime NULL,
   last_activity datetime NULL,
   CONSTRAINT pk_account PRIMARY KEY (acct_num)
);

CREATE TABLE customer_details (
  id BIGINT AUTO_INCREMENT NOT NULL,
   dob date NULL,
   occupation VARCHAR(255) NULL,
   marital_status VARCHAR(255) NULL,
   disability VARCHAR(255) NULL,
   CONSTRAINT pk_customerdetails PRIMARY KEY (id)
);
CREATE TABLE debit_card (
  id BIGINT AUTO_INCREMENT NOT NULL,
   card_no VARCHAR(255) NOT NULL,
   cvv_no INT NOT NULL,
   card_type VARCHAR(255) NULL,
   card_status VARCHAR(255) NULL,
   issued_date date NOT NULL,
   expire_date date NOT NULL,
   last_activity date NULL,
   CONSTRAINT pk_debitcard PRIMARY KEY (id)
);

CREATE TABLE address (
  id BIGINT AUTO_INCREMENT NOT NULL,
   street VARCHAR(255) NULL,
   city VARCHAR(255) NULL,
   state VARCHAR(255) NULL,
   country VARCHAR(255) NULL,
   postal_code VARCHAR(255) NULL,
   CONSTRAINT pk_address PRIMARY KEY (id)
);

ALTER TABLE debit_card ADD CONSTRAINT uc_debitcard_cardno UNIQUE (card_no);

ALTER TABLE account ADD CONSTRAINT uc_account_acct_num UNIQUE (acct_num);

ALTER TABLE account ADD CONSTRAINT uc_account_nubanno UNIQUE (nuban_no);

ALTER TABLE account ADD CONSTRAINT FK_ACCOUNT_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE account ADD CONSTRAINT FK_ACCOUNT_ON_DEBITCARD FOREIGN KEY (debit_card_id) REFERENCES debit_card (id);












ALTER TABLE customer ADD CONSTRAINT uc_customer_email UNIQUE (email);

ALTER TABLE customer ADD CONSTRAINT uc_customer_keycloakid UNIQUE (keycloak_id);

ALTER TABLE customer ADD CONSTRAINT FK_CUSTOMER_ON_CUSTOMERDETAILS FOREIGN KEY (customer_details_id) REFERENCES customer_details (id);

ALTER TABLE customer ADD CONSTRAINT FK_CUSTOMER_ON_CUSTOMER_ADDRESS FOREIGN KEY (customer_address_id) REFERENCES address (id);