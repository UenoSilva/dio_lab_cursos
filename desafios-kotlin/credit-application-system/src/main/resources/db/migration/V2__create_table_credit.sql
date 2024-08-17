CREATE TABLE tb_credits (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   credit_code UUID NOT NULL,
   credit_value DECIMAL NOT NULL,
   day_first_installment date NOT NULL,
   number_of_installment INT NOT NULL,
   status SMALLINT,
   customer_id BIGINT,
   CONSTRAINT pk_tb_credits PRIMARY KEY (id)
);

ALTER TABLE tb_credits ADD CONSTRAINT uc_tb_credits_credit_code UNIQUE (credit_code);

ALTER TABLE tb_credits ADD CONSTRAINT FK_TB_CREDITS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES tb_clients (id);