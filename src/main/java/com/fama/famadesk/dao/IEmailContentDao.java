package com.fama.famadesk.dao;


import java.util.List;

import com.fama.famadesk.dao.basedao.IBaseDao;
import com.fama.famadesk.model.EmailContent;


public interface IEmailContentDao extends IBaseDao<EmailContent>
{
	 List<EmailContent> getEmailContentByMessageUniqueId(String emailUniqueId);
	 EmailContent getNewestEmailContentByEmailUniqueId(String emailUniqueId);  
	
}
