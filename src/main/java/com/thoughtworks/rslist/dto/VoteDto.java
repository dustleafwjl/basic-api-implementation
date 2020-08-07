package com.thoughtworks.rslist.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "vote")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    @Id
    @GeneratedValue
    private int id;
    private int voteNum;
    private String voteTime;

    @ManyToOne(targetEntity = UserDto.class)
    private UserDto userDto;

    @ManyToOne(targetEntity = RsEventDto.class)
    private RsEventDto rsEventDto;
}
