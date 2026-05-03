package com.boleia.boleia.shared.jpa.entity;

import com.boleia.boleia.shared.jpa.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "chat_support")
public class ChatSupportModel extends BaseModel {

    @Column(name = "message")
    private String message;

    @OneToMany(mappedBy = "chat_support", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private UserModel user;

}
