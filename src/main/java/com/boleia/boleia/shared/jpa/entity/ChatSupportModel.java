package com.boleia.boleia.shared.jpa.entity;

import com.boleia.boleia.shared.jpa.models.BaseModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "chat_support")
public class ChatSupportModel extends BaseModel {

    @Column(name = "message")
    private String message;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "fk_driver_user"))
    private UserModel user;

}
