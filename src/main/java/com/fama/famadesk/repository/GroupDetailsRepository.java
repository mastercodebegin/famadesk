package com.fama.famadesk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.GroupDetails;

@Repository
public interface GroupDetailsRepository extends JpaRepository<GroupDetails, Integer> {

	@Query("select g from GroupDetails g where g.projectDetails.id=:projectid and g.projectDetails.endDate is null and g.isRemoved=0")
	Optional<List<GroupDetails>> getGroupByProjectId(@Param("projectid") Integer projectid);

	@Query("select g from GroupDetails g where g.id=:groupid and g.addedBy.userid=:accountAdminId and g.isRemoved=0")
	Optional<GroupDetails> getDetailsByGroupAndAccountAdmin(@Param("groupid") Integer groupid,
			@Param("accountAdminId") Integer accountAdminId);

}
