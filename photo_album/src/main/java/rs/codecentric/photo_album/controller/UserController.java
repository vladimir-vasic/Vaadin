package rs.codecentric.photo_album.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import rs.codecentric.photo_album.MyVaadinUI;
import rs.codecentric.photo_album.entity.Album;
import rs.codecentric.photo_album.entity.Picture;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification;

@Controller
public class UserController implements IUserController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public void saveAlbum(File file, ClickEvent event, String title, Long albumId) {
		if (file != null && file.length() != 0) {
			if (MyVaadinUI.getCurrentUser() != null && !CollectionUtils.isEmpty(MyVaadinUI.getCurrentUserDAO().getAllPictureAlbums4User(MyVaadinUI.getCurrentUser().getUserId()))) {
				Album pictureAlbum = MyVaadinUI.getCurrentUserDAO().loadPictureAlbumById(albumId);
				if (CollectionUtils.isEmpty(pictureAlbum.getAlbumPictures())) {
					pictureAlbum.setAlbumPictures(new ArrayList<Picture>());
				}

				Picture pic = new Picture();
				pic.setAlbum(pictureAlbum);
				try {
//					byte[] encodedPicture = Base64.encodeBytesToBytes(FileUtils.readFileToByteArray(file));
//					pic.setContent(encodedPicture);
					pic.setContent(FileUtils.readFileToByteArray(file));
				} catch (IOException exc) {
					log.error("ERROR WHILE READING FILE", exc);
				}
				pic.setCreateDateTime(new Date());
				pic.setName(title);
				pictureAlbum.getAlbumPictures().add(pic);
				if (MyVaadinUI.getCurrentUserDAO().updatePictureAlbum(pictureAlbum)) {
					log.info("Picture album updated seccessfully.");
				} else {
					log.info("PICTURE ALBUM NOT UPDATED!!!");
				}
			} else {
				log.error("NO USER ALBUMS DEFINED!!!");
				Notification.show("NO USER ALBUMS DEFINED!!!");
			}
		} else {
			log.error("NO IMAGE UPLOADED!!!");
			Notification.show("NO IMAGE UPLOADED!!!");
		}
	}

}
