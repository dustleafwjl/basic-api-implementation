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
    @JoinColumn(name = "vote_num")
    private int voteNum;
    @JoinColumn(name = "vote_time")
    private String voteTime;

    @ManyToOne(targetEntity = UserDto.class)
    @JoinColumn(name = "user_id")
    private UserDto userDto;

    @ManyToOne(targetEntity = RsEventDto.class)
    @JoinColumn(name = "rs_id")
    private RsEventDto rsEventDto;
}
