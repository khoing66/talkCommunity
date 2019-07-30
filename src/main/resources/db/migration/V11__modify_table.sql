alter table QUESTION alter column ID INTEGER auto_increment;
alter table QUESTION alter column DESCRIPTION text;
alter table QUESTION add constraint QUESTION_pk primary key (ID);
