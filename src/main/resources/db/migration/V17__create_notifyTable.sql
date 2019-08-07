create table notification
(
    id bigint auto_increment primary key,
    notifier varchar(256) not null,
    receiver varchar(256) not null,
    outerid bigint not null,
    type int not null,
    gmt_create bigint not null,
    status int default 0 not null
);