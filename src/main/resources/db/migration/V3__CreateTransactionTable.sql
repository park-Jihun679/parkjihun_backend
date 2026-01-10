create table account_transaction
(
    transaction_id     BINARY(16) NOT NULL,
    account_id     BINARY(16) NOT NULL,
    account_no     VARCHAR(20) NOT NULL,
    direction     ENUM('IN', 'OUT') NOT NULL,
    amount     DECIMAL(18, 2) NOT NULL,
    before_balance     DECIMAL(18, 2) NOT NULL,
    after_balance     DECIMAL(18, 2) NOT NULL,
    other_bank_code   VARCHAR(4),
    other_account_no     VARCHAR(20),
    description     VARCHAR(40)   NOT NULL,
    created_date   datetime NOT NULL,
    primary key (transaction_id),
    FOREIGN KEY (account_id) REFERENCES account (account_id)
);

CREATE INDEX idx_tx_account_date
ON account_transaction (account_no, created_date DESC);