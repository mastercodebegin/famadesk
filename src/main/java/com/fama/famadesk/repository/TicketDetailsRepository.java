package com.fama.famadesk.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.TicketDetails;

@Repository
public interface TicketDetailsRepository extends JpaRepository<TicketDetails, Integer> {

	@Query(value = "select t from TicketDetails t where t.ticketId=:ticketId")
	TicketDetails findByticketId(@Param("ticketId") String ticketId);

	@Query(value = "select t from TicketDetails t where t.assignedTo.userid=:assignedTo and t.ticketStatus='PENDING'")
	Optional<List<TicketDetails>> getPendingTicketByAssignee(@Param("assignedTo") Integer assignedTo);

	@Query(value = "select count(t.id) from TicketDetails t where t.assignedTo.userid=:assignedTo and t.ticketStatus='PENDING'")
	long getPendingTicketCountByAssignee(@Param("assignedTo") Integer assignedTo);

	@Query(value = "select count(t.id) from TicketDetails t where t.ticketStatus='PENDING'")
	long getPendingTicketCount();

}
