create table book_uuid(
    id binary(16) not null,
    isbn varchar(256),
    publisher varchar(256),
    title varchar(256),
    primary key (id)
) engine = InnoDB;