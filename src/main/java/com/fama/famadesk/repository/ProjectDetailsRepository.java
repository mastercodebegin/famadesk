package com.fama.famadesk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.ProjectDetails;

@Repository
public interface ProjectDetailsRepository extends JpaRepository<ProjectDetails, Integer> {

	@Query("select p.projectName from ProjectDetails p where p.endDate is null")
	Optional<List<String>> getAllConfiguredProject();

	@Query("select p from ProjectDetails p where p.projectName =:projectName and p.endDate is null")
	Optional<List<ProjectDetails>> findByProjectName(@Param("projectName") String projectName);

	@Query("select p from ProjectDetails p where p.addedBy.userid=:userid and p.endDate is null")
	Optional<List<ProjectDetails>> getProjectByUser(@Param("userid") Integer userid);

	@Query("select p from ProjectDetails p where p.accountAssociated.id=:accountid and p.endDate is null")
	Optional<List<ProjectDetails>> getProjectsByAccountId(@Param("accountid") Integer accountid);

	@Query("select p from ProjectDetails p where p.addedBy.userid=:userid and p.id=:projectid and p.endDate is null")
	Optional<ProjectDetails> getProjectDetailByUserAndProject(@Param("projectid") Integer projectid,
			@Param("userid") Integer userid);

}
