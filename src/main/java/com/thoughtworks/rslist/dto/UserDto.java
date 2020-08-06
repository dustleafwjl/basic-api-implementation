package com.thoughtworks.rslist.dto;


import com.thoughtworks.rslist.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

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


    public User userDtotoUser() {
        return new User(this.userName, this.gender, this.age, this.email, this.phone);
    }
}
