create table question
(
	id int auto_increment,
	title varchar(50),
	description TEXT,
	tag varchar(256),
	gmt_create bigint,
	gmt_modify bigint,
	creator_id int,
	view_count int,
	comment_count int,
	like_count int,
	constraint question_pk
		primary key (id)
);