package com.splitwisely.backend.repository;

import com.splitwisely.backend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
}
