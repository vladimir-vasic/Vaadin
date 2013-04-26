package rs.codecentric.photo_album.services;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import rs.codecentric.photo_album.dao.ILoginDAO;

public class StartupService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ILoginDAO loginDAO;
	
	String message;
	 
	public String getMessage() {
	  return message;
	}
 
	public void setMessage(String message) {
	  this.message = message;
	}
 
	@PostConstruct
	public void initIt() throws Exception {
	  log.info(message);
	  loginDAO.insertInitialUser();
	}
	
	
}
