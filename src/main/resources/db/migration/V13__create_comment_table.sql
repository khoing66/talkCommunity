create table comment
(
	id bigint auto_increment,
	parent_id bigint not null,
	type int not null,
	commentor varchar(256) not null,
	gmt_create bigint not null,
	gmt_modify bigint not null,
	like_count bigint default 0,
	constraint comment_pk
		primary key (id)
);