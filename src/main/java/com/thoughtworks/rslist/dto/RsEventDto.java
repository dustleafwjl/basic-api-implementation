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
import java.util.List;

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
    private int voteNum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDto userDto;

    @OneToMany(targetEntity = VoteDto.class, cascade = CascadeType.REMOVE, mappedBy = "rsEventDto")
    private List<VoteDto> voteDto;

    public RsEvent rsEventDtoToRsEvent() {
        return new RsEvent(this.eventName, this.keyWord, userDto.getId());
    }
}
