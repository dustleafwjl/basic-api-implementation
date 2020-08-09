package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import com.thoughtworks.rslist.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @NotNull
    @Size(max = 8)
    private String userName;
    @NotNull
    private String gender;
    @NotNull
    @Min(18)
    @Max(100)
    private int age;
    @Email
    private String email;
    @Pattern(regexp = "^1[0-9]{10}")
    private String phone;
    @Builder.Default
    private int voteNum = 10;

    public User(String userName, String gender, int age, String email, String phone) {
        this.userName = userName;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    @JsonGetter("user_name")
    public String getUserName() {
        return userName;
    }

    @JsonSetter("user_name")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonGetter("user_gender")
    public String getGender() {
        return gender;
    }

    @JsonSetter("user_gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonGetter("user_age")
    public int getAge() {
        return age;
    }

    @JsonSetter("user_age")
    public void setAge(int age) {
        this.age = age;
    }

    @JsonGetter("user_email")
    public String getEmail() {
        return email;
    }

    @JsonSetter("user_email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonGetter("user_phone")
    public String getPhone() {
        return phone;
    }

    @JsonSetter("user_phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, gender, age, email, phone);
    }


    public UserDto userToUserDto() {
        return UserDto.builder().userName(this.getUserName()).age(this.getAge())
                .gender(this.getGender()).phone(this.getPhone()).voteNum(10).build();
    }
}
