create table author_uuid(
    id varchar(36) not null,
    first_name varchar(256),
    last_name varchar(256),
    primary key (id)
) engine = InnoDB;