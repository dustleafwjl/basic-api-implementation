package com.thoughtworks.rslist.dto;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rsevent")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RsEventDto {
    @Id
    @GeneratedValue
    private int id;
    private String eventName;
    private String keyWord;

    @ManyToOne
    private UserDto userDto;

    public RsEvent rsEventDtoToRsEvent() {
        return new RsEvent(this.eventName, this.keyWord, userDto.getId());
    }
}
