package com.thoughtworks.rslist.repository;


import com.thoughtworks.rslist.dto.VoteDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface VoteRepository extends PagingAndSortingRepository<VoteDto, Integer> {

//    @Query("select v from VoteDto v where v.user.id = :userId and v.rsevent.id = :eventId")
//    public List<VoteDto> findAccordingToUserIdAndEventId(int userId, int eventId, Pageable pageable);
}


