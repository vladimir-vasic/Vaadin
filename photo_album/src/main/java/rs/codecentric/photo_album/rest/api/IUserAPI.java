package rs.codecentric.photo_album.rest.api;

import rs.codecentric.photo_album.entity.User;
import rs.codecentric.photo_album.exception.BusinessException;

public interface IUserAPI {

	public User loadUserByUsernameAndPassword(String userName, String userPassword) throws BusinessException;
	
}
