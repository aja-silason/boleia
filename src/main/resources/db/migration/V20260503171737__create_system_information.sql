    create table system_information (
        id varchar(255) not null,
        created_at timestamp(6) not null,
        updated_at timestamp(6) not null,
        application_version varchar(255),
        central_phone_number varchar(255),
        primary key (id)
    );
