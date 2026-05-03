    create table chat_support (
        id varchar(255) not null,
        created_at timestamp(6) not null,
        updated_at timestamp(6) not null,
        message text,
        user_id varchar(255),
        primary key (id)
    );

    alter table if exists chat_support 
       drop constraint if exists UK_m3o7q61qrunwh6utronok8m3w;

    alter table if exists chat_support 
       add constraint UK_m3o7q61qrunwh6utronok8m3w unique (user_id);

    alter table if exists chat_support 
       add constraint fk_driver_user 
       foreign key (user_id) 
       references users;