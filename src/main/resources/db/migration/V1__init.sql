create table example
(
    example_id     BINARY(16) not null,
    created_by     BINARY(16),
    created_date   datetime(6),
    updated_by     BINARY(16),
    updated_date   datetime(6),
    example_code   varchar(255),
    example_string varchar(255),
    primary key (example_id)
);