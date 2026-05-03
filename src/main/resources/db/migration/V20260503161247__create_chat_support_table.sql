
    create table chat_support (
        id varchar(255) not null,
        created_at timestamp(6) not null,
        updated_at timestamp(6) not null,
        message text,
        user_id varchar(255),
        primary key (id)
    );

    alter table if exists chat_support 
       add constraint FK5fw5wf5odj3lsynihe420dejv 
       foreign key (user_id) 
       references users;
