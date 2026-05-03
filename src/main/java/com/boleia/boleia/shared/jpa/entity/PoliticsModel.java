package com.boleia.boleia.shared.jpa.entity;

import com.boleia.boleia.shared.jpa.models.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "politcs")
public class PoliticsModel extends BaseModel {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

}
