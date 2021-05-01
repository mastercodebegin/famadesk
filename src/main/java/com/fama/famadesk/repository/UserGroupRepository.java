package com.fama.famadesk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer> {

	@Query("select u from UserGroup u where u.group.id=:groupid and u.user.userid=:userid and u.isUserRemoved=0")
	UserGroup getDetailsByGroupAndUser(@Param("groupid") Integer groupid, @Param("userid") Integer userid);

	@Query("select u from UserGroup u where u.group.id=:groupid and u.isUserRemoved=0")
	Optional<List<UserGroup>> getDetailsByGroupid(@Param("groupid") Integer groupid);
}
