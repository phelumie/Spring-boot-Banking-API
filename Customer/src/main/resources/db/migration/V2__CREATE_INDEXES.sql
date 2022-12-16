CREATE INDEX customer_index ON customer(email);
CREATE INDEX account_index ON account(acct_num, nuban_no, customer_id, debit_card_id);