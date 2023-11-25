create table users(
    id bigserial primary key,
    password varchar(500),
    first_name varchar(30),
    last_name varchar(30),
    email varchar(50)
)