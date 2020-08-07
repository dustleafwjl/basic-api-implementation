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
    private int userId;
    private int eventId;
    private int voteNum;
    private String voteTime;

//    @ManyToOne
//    private UserDto userDto;
//    @Id
//    @ManyToOne
//    private RsEventDto rsEventDto;
}
