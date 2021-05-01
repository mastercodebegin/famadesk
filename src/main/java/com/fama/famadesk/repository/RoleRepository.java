package com.fama.famadesk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {

	@Query(value = "select r from Roles r ")
	Optional<List<Roles>> getAvailableRoles();
}
