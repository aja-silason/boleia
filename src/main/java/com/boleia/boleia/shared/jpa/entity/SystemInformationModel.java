package com.boleia.boleia.shared.jpa.entity;

import com.boleia.boleia.shared.jpa.models.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "system_information")
public class SystemInformationModel extends BaseModel {

    @Column(name = "application_version")
    private String applicationVersion;

    @Column(name = "central_phone_number")
    private String centralPhoneNumber;

}
