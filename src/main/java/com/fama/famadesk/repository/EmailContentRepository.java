package com.fama.famadesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fama.famadesk.model.EmailContent;



@Repository
public interface EmailContentRepository extends JpaRepository<EmailContent, Integer> {
	@Query (value = "select * from email_content where message_unique_id=:emailUniqueId",nativeQuery=true)
	public List<EmailContent> getEmailContentByMessageUniqueId(@Param("emailUniqueId")String emailUniqueId);

	@Query (value = "select * from email_content where message_unique_id=:emailUniqueId LIMIT 1",nativeQuery=true)
	public EmailContent getNewestEmailContentByEmailUniqueId(@Param("emailUniqueId")String emailUniqueId);

	@Query (value = "select * from email_content where id=:id",nativeQuery=true)
	public EmailContent findByEmailContentId(@Param("id")Integer id);

}
