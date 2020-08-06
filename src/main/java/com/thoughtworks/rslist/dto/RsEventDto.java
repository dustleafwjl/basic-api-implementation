package com.thoughtworks.rslist.dto;

import com.thoughtworks.rslist.domain.User;
import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rsevent")
@Data
public class RsEventDto {
    @Id
    @GeneratedValue
    private int id;
    private String eventName;
    private String keyWord;
}
