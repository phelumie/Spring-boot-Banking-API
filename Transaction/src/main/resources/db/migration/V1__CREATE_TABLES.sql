CREATE TABLE transaction (
  transaction_detail_transaction_id BIGINT NOT NULL AUTO_INCREMENT,
   source_account BIGINT NOT NULL,
   `description` VARCHAR(255) NOT NULL,
   transaction_date date NOT NULL,
   time time NOT NULL,
   transaction_type VARCHAR(255) NULL,
   desc_acct BIGINT NOT NULL,
   amount DECIMAL NOT NULL,
   CONSTRAINT pk_transaction PRIMARY KEY (transaction_detail_transaction_id)
);

CREATE TABLE transaction_detail (
  transaction_id BIGINT NOT NULL AUTO_INCREMENT,
   transaction_status VARCHAR(255) NULL,
   location_id BIGINT NULL,
   transaction_date date NOT NULL,
   time time NOT NULL,
   CONSTRAINT pk_transactiondetail PRIMARY KEY (transaction_id)
);

CREATE TABLE location (
  id BIGINT AUTO_INCREMENT NOT NULL,
   vendor VARCHAR(255) NULL,
   location VARCHAR(255) NULL,
   CONSTRAINT pk_location PRIMARY KEY (id)
);

ALTER TABLE transaction_detail ADD CONSTRAINT FK_TRANSACTIONDETAIL_ON_LOCATION FOREIGN KEY (location_id) REFERENCES location (id);
ALTER TABLE transaction ADD CONSTRAINT FK_TRANSACTION_ON_TRANSACTIONDETAIL_TRANSACTIONID FOREIGN KEY (transaction_detail_transaction_id) REFERENCES transaction_detail (transaction_id);