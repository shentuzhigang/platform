create table question
(
  id int auto_increment primary key,
  title varchar(256) null,
  description TEXT null,
  gmt_create BIGINT null,
  gmt_modified BIGINT null,
  comment_count int default 0 null,
  view_count int default 0 null,
  like_count int default 0 null,
  tag varchar(256) null
);

