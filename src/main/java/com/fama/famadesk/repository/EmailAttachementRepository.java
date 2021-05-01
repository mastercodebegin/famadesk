package com.fama.famadesk.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.fama.famadesk.model.EmailAttachment;




@Repository
public interface EmailAttachementRepository extends JpaRepository<EmailAttachment, Integer> {

}
