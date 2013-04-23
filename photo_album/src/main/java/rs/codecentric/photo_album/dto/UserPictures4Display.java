package rs.codecentric.photo_album.dto;

import java.util.ArrayList;

import rs.codecentric.photo_album.entity.Album;
import rs.codecentric.photo_album.entity.Picture;


/**
 * @author vladimir.vasic@codecentric.de
 *
 */
public class UserPictures4Display {

	private Album userPictureAlbum;
	private ArrayList<Picture> friendsPictures;
	
	public UserPictures4Display(){}
	
	/**
	 * @param userPictureAlbum
	 * @param friendsPictures
	 */
	public UserPictures4Display(Album userPictureAlbum, ArrayList<Picture> friendsPictures) {
		this.userPictureAlbum = userPictureAlbum;
		this.friendsPictures = friendsPictures;
	}
	
	// getters-setters
	public Album getUserPictureAlbum() {
		return userPictureAlbum;
	}
	public void setUserPictureAlbum(Album userPictureAlbum) {
		this.userPictureAlbum = userPictureAlbum;
	}
	public ArrayList<Picture> getFriendsPictures() {
		return friendsPictures;
	}
	public void setFriendsPictures(ArrayList<Picture> friendsPictures) {
		this.friendsPictures = friendsPictures;
	}
	
}
