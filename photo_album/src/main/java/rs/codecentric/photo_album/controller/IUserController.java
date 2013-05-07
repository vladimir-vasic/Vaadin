package rs.codecentric.photo_album.controller;

import java.io.File;

import com.vaadin.ui.Button.ClickEvent;

public interface IUserController {

	public void saveAlbum(File file, ClickEvent event, String title, Long albumId);

}
