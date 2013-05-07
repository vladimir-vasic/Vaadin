package rs.codecentric.photo_album;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import rs.codecentric.photo_album.controller.IUserController;
import rs.codecentric.photo_album.entity.Album;
import rs.codecentric.photo_album.entity.Picture;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

public class ViewAlbum extends VerticalLayout implements View {

	private static final long serialVersionUID = -2361949976423525591L;

	private final Image image = new Image("Uploaded Image");
	public File file;

	public static IUserController getCurrentUserController() {
		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
		return (IUserController) helper.getBean("userController");
	}

	public void enter(ViewChangeEvent event) {

		String parameters = event.getParameters();
		final Long albumId = Long.valueOf(parameters);

		final VerticalLayout layoutMain = new VerticalLayout();
		addComponent(layoutMain);

		// placeholder for the image thumbnails
		final HorizontalLayout hlayout = new HorizontalLayout();

		setWidth("400px");

		// Todo upload image
		final HorizontalLayout layoutUploadImage = new HorizontalLayout();
		layoutMain.addComponent(layoutUploadImage);

		// Create the upload with a caption and set receiver later
		Upload upload = new Upload("Upload Image Here", null);
		upload.setButtonCaption("Start Upload");
		layoutUploadImage.addComponent(upload);

		image.setVisible(false);
		layoutUploadImage.addComponent(image);

		class ImageUploader implements Receiver, SucceededListener {
			private static final long serialVersionUID = -5243802467085826825L;

			public OutputStream receiveUpload(String filename, String mimeType) {
				// Create upload stream
				FileOutputStream fos = null; // Stream to write to
				try {
					// Open the file for writing.
					file = new File("C:/" + filename);
					fos = new FileOutputStream(file);
				} catch (final java.io.FileNotFoundException e) {
					Notification.show("Could not open file<br/>", e.getMessage(), Notification.Type.ERROR_MESSAGE);
					return null;
				}
				return fos; // Return the output stream to write to
			}

			@Override
			public void uploadSucceeded(SucceededEvent event) {
				// Show the uploaded file in the image viewer
				image.setVisible(true);
				image.setSource(new FileResource(file));
			}
		};

		final ImageUploader uploader = new ImageUploader(); 
		upload.setReceiver(uploader);
		upload.addSucceededListener(uploader);

		// save
		Button saveAlbumBtn = new Button("Save Album");
		saveAlbumBtn.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -6338202717744987032L;

			@Override
			public void buttonClick(Button.ClickEvent event) {
				getCurrentUserController().saveAlbum(file, event, image.getCaption(), albumId);
				displayImages(albumId, hlayout);
			}

		});
		layoutUploadImage.addComponent(saveAlbumBtn);

		layoutMain.addComponent(hlayout);
		displayImages(albumId, hlayout);

	}

	public void displayImages(long albumId, AbstractOrderedLayout layout) {
		Album album = MyVaadinUI.getCurrentUserDAO().loadPictureAlbumById(albumId);
		List<Picture> pictures = album.getAlbumPictures();

		layout.removeAllComponents();
		if (!CollectionUtils.isEmpty(pictures)) {
			for (Picture picture : pictures) {
				ComponentImage componentImage = new ComponentImage(picture);
				layout.addComponent(componentImage);

				// wrap the photo album icon + title into a clickable layout
				componentImage.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

					private static final long serialVersionUID = 4010312568756089212L;

					public void layoutClick(LayoutEvents.LayoutClickEvent event) {
						// TODO switch the view to a single album
						if (event.getButton().equals(MouseButton.LEFT)) {
							//((PhotoalbumUI)UI.getCurrent()).navigateTo("album");
							Notification.show("Image clicked", Notification.Type.HUMANIZED_MESSAGE);
						}
					}

				});
			}
		}

	}

}
