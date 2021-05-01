package com.fama.famadesk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public User findByMobile(String mobile);

	@Query("select u from User u where u.createdBy.userid=:userid and u.isActive=1 and u.userStatus='VERIFIED'")
	Optional<List<User>> getVerifiedActiveAgent(@Param("userid") Integer userid);

	@Query("select u from User u where u.isActive=1 and u.userStatus='VERIFIED'")
	Optional<List<User>> getAllVerifiedActiveAgent();

}
