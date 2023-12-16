package com.ascencio.dev.springwebfluxapi.repository;

import com.ascencio.dev.springwebfluxapi.domain.model.Programs;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramsRepository extends ReactiveCrudRepository<Programs, Integer> {
}
