package com.thoughtworks.rslist.repository;


import com.thoughtworks.rslist.dto.VoteDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoteRepository extends CrudRepository<VoteDto, Integer>{
}


