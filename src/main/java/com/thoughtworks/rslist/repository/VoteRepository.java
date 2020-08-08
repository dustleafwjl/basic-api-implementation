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

    @Query(value = "select * from vote v where v.user_id = :userId", nativeQuery = true)
    public List<VoteDto> findAccordingToUserId(int userId, Pageable pageable);

    @Query(value = "select * from vote v where v.rs_id = :eventId", nativeQuery = true)
    public List<VoteDto> findAccordingToEventId(int eventId, Pageable pageable);

    @Query(value = "select * from vote v where v.rs_id = :eventId and v.user_id = :userId ", nativeQuery = true)
    List<VoteDto> findAccordingToEventIdAndUserId(int eventId, int userId, Pageable pageable);
}


