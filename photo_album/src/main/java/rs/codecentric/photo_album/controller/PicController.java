package rs.codecentric.photo_album.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.jboss.resteasy.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import rs.codecentric.photo_album.dao.ILoginDAO;
import rs.codecentric.photo_album.dao.IUserAdminDAO;
import rs.codecentric.photo_album.dto.UploadItem;
import rs.codecentric.photo_album.dto.UserPictures4Display;
import rs.codecentric.photo_album.entity.Album;
import rs.codecentric.photo_album.entity.Picture;
import rs.codecentric.photo_album.entity.User;


/**
 * @author vladimir.vasic@codecentric.de
 * 
 */
@Controller
//@RequestMapping("/{userId}/updatePhotoAlbum/{albumId}")
@SessionAttributes("UploadItem")
public class PicController {
	@Autowired
	ILoginDAO loginService;
	@Autowired
	IUserAdminDAO userService;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/addPictureToPhotoAlbum.htm", method = RequestMethod.GET)
	public String showAddPictureToPhotoAlbumForm(Model model) {
		log.info("Displays add picture to photo albums page");
		UploadItem uploadItem = new UploadItem();
		model.addAttribute("UploadItem", uploadItem);
		return "addPictureToAlbum";
	}

	@RequestMapping(value = "/addPictureToPhotoAlbum.htm", method = RequestMethod.POST)
	public String showNewPictureAddedToPhotoAlbumForm(@RequestParam(value = "albumId") Long albumId, @ModelAttribute("UploadItem") UploadItem uploadItem, Model model) {
		log.info("Displays new picture added to photo albums page");
		if (uploadItem.getFileData().getName() != null && uploadItem.getFileData().getName().trim().length() > 0) {
			Album pictureAlbum = userService.loadPictureAlbumById(albumId);
			if (pictureAlbum.getAlbumPictures() == null || pictureAlbum.getAlbumPictures().isEmpty()) {
				pictureAlbum.setAlbumPictures(new ArrayList<Picture>());
			}

			Picture picture = new Picture();
			picture.setName(uploadItem.getFileData().getOriginalFilename());
			byte[] encodedPicture = Base64.encodeBytesToBytes(uploadItem.getFileData().getBytes());
			picture.setContent(encodedPicture);
			picture.setCreateDateTime(new Date());
			picture.setAlbum(pictureAlbum);
			pictureAlbum.getAlbumPictures().add(picture);
			if (userService.updatePictureAlbum(pictureAlbum)) {
				log.info("Picture album updated seccessfully.");
			} else {
				log.info("PICTURE ALBUM NOT UPDATED!!!");
			}
		}

		ArrayList<Picture> friendsPictures = new ArrayList<Picture>();
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = loginService.getUserByUsername(userName);
		if (user.getFriends() != null && !user.getFriends().isEmpty()) {
			Iterator<User> iterator = user.getFriends().iterator();
			while (iterator.hasNext()) {
				User friend = iterator.next();
				if (friend.getUserAlbums() != null && !friend.getUserAlbums().isEmpty()) {
					for (Album picAl : friend.getUserAlbums()) {
						if (picAl.getAlbumPictures() != null && !picAl.getAlbumPictures().isEmpty()) {
							for (Picture pic : picAl.getAlbumPictures()) {
								friendsPictures.add(pic);
							}
						}
					}
				}
			}
		}

		Album pictureAlbum = userService.loadPictureAlbumById(albumId);

		UserPictures4Display userPictures4Display = new UserPictures4Display(pictureAlbum, friendsPictures);

		model.addAttribute("UserPictures4Display", userPictures4Display);
		return "editPhotoAlbum";
	}

}

