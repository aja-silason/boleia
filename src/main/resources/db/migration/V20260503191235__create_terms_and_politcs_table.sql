
    create table politcs (
        id varchar(255) not null,
        created_at timestamp(6) not null,
        updated_at timestamp(6) not null,
        description text,
        title varchar(255),
        primary key (id)
    );

    create table terms (
        id varchar(255) not null,
        created_at timestamp(6) not null,
        updated_at timestamp(6) not null,
        description text,
        title varchar(255),
        primary key (id)
    );
