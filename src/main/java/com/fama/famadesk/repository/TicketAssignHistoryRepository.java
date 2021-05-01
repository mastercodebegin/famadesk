package com.fama.famadesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.TicketAssignHistory;

@Repository
public interface TicketAssignHistoryRepository extends JpaRepository<TicketAssignHistory, Integer> {

	@Query(value = "select t from TicketAssignHistory t where t.ticketDetails.id=:ticketDetailId")
	List<TicketAssignHistory> getTicketAssignHistoryByTicketId(@Param("ticketDetailId") Integer ticketDetailId);

}
