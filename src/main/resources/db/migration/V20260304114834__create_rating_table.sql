    create table ratings (
        id varchar(255) not null,
        created_at timestamp(6) not null,
        updated_at timestamp(6) not null,
        entity_type varchar(255),
        rating integer,
        user_id varchar(255) not null,
        primary key (id)
    );
    
    alter table if exists ratings 
       add constraint FKb3354ee2xxvdrbyq9f42jdayd 
       foreign key (user_id) 
       references users;
