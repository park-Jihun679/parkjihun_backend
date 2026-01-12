create table account
(
    account_id     BINARY(16) NOT NULL,
    account_no     VARCHAR(20) UNIQUE NOT NULL,
    account_name   VARCHAR(32) NOT NULL,
    bank_code   VARCHAR(4) NOT NULL,
    balance     DECIMAL(18, 2) NOT NULL,
    daily_total_withdrawal     DECIMAL(18, 2) NOT NULL,
    daily_total_transfer     DECIMAL(18, 2) NOT NULL,
    created_date   datetime NOT NULL,
    updated_date   datetime NOT NULL,
    is_deleted BIT  NOT NULL,
    primary key (account_id)
);