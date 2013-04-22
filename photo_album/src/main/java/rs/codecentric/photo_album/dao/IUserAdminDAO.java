package rs.codecentric.photo_album.dao;

import java.util.List;

import rs.codecentric.photo_album.entity.Album;
import rs.codecentric.photo_album.entity.Picture;
import rs.codecentric.photo_album.entity.User;

public interface IUserAdminDAO {
	
	public Boolean createNewUser(String userName, String userPassword, String userEmail);
	
	public Boolean createAlbum4User(User albumOwner, String albumName);
	
	public List<Album> getAllPictureAlbums4User(Long userId);
	
	public User loadUserByUsrPwd(String userName, String userPassword);
	
	public List<User> getAllUsers();
	
	public User loadUserById(Long userId);
	
	public Boolean updateUser(User user);
	
	public Boolean deleteUser(User user);
	
	public Album loadPictureAlbumById(Long pictureAlbumId);
	
	public Boolean updatePictureAlbum(Album pictureAlbum);
	
	public Boolean deletePictureAlbumById(Album pictureAlbum);
	
	public List<User> getPosibleFriends(Long userId);
	
	public Picture getPictureByName(String pictureName);
	
	public Picture getPictureById(Long pictureId);
}
