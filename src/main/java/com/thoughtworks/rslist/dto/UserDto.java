package com.thoughtworks.rslist.dto;


import com.thoughtworks.rslist.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String userName;
    private String gender;
    private int age;
    private String email;
    private String phone;
    private int voteNum;

    @OneToMany(targetEntity = RsEventDto.class, cascade = CascadeType.REMOVE, mappedBy = "userDto")
    private List<RsEventDto> rsEventDtos;

    @OneToMany(targetEntity = VoteDto.class, cascade = CascadeType.REMOVE, mappedBy = "userDto")
    private List<VoteDto> voteDto;

    public User userDtotoUser() {
        return new User(this.userName, this.gender, this.age, this.email, this.phone);
    }
}
