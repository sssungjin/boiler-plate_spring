package com.sontcamp.homework.repository;

import com.sontcamp.homework.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMemoryRepository extends JpaRepository<User, Long> {

}
