package rs.codecentric.photo_album.dao;

import rs.codecentric.photo_album.entity.User;

public interface ILoginDAO {

	public void insertInitialUser();
	
	public User getUserByUsername(String userName);
	
}
