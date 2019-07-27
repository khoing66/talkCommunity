create table user
(
    id int auto_increment,
    account_id varchar(100),
    name varchar(50),
    token char(50),
    gym_create bigint,
    gym_modify bigint,
    constraint user_pk
        primary key (id)
)