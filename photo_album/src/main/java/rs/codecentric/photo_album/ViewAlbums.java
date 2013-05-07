package rs.codecentric.photo_album;

import java.util.List;

import rs.codecentric.photo_album.entity.Album;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ViewAlbums extends VerticalLayout implements View {

	private static final long serialVersionUID = 8938589162825804564L;

	/**
	 * Test function, shows notification with the given string
	 * @param s - string to show in the notification
	 */
	public void info(String s) {
		new Notification(s, Notification.Type.ERROR_MESSAGE).show(MyVaadinUI.getCurrent().getPage());
	}


	/**
	 * Main enter() function
	 */
	public void enter(ViewChangeEvent event) {

		// main layout
		VerticalLayout layoutMain = new VerticalLayout();
		addComponent(layoutMain);

		final HorizontalLayout albumsLayout = new HorizontalLayout();

		// TODO: welcome message, logout link
		Label lblWelcomeMessage = new Label("Welcome, " + MyVaadinUI.getCurrentUser().getUserName());
		layoutMain.addComponent(lblWelcomeMessage);

		// text field and a button to create a new album
		HorizontalLayout layoutNewAlbum = new HorizontalLayout();
		final TextField tfAlbumName = new TextField();
		Button btnCreateNewAlbum = new Button("Create new album");
		btnCreateNewAlbum.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -6338202717744987032L;

			@Override
			public void buttonClick(ClickEvent event) {
				MyVaadinUI.getCurrentUserDAO().createAlbum4User(MyVaadinUI.getCurrentUser(), tfAlbumName.getValue().toString());
				displayUserAlbums(albumsLayout);
				tfAlbumName.setValue("");
			}

		});
		layoutNewAlbum.addComponent(tfAlbumName);
		layoutNewAlbum.addComponent(btnCreateNewAlbum);
		layoutMain.addComponent(layoutNewAlbum);

		// place the album icons in a horizontal (wrappable) layout
		layoutMain.addComponent(albumsLayout);

		// display user albums
		displayUserAlbums(albumsLayout);
	}

	/**
	 * Displays all albums for a current user
	 */
	private void displayUserAlbums(AbstractOrderedLayout layout) {
		layout.removeAllComponents();

		List<Album> userAlbums = MyVaadinUI.getCurrentUserDAO().getAllPictureAlbums4User(MyVaadinUI.getCurrentUser().getUserId());
		if (!userAlbums.equals(null)) {
			setMargin(true);

			for (Album album : userAlbums) {
				ComponentAlbum componentAlbum = new ComponentAlbum(album);
				layout.addComponent(componentAlbum);

				final Long albumId = album.getAlbumId();

				// wrap the photo album icon + title into a clickable layout
				componentAlbum.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
					private static final long serialVersionUID = -3641352615876874152L;
					@Override
					public void layoutClick(LayoutEvents.LayoutClickEvent event) {
						// TODO: switch the view to a single album view
						if (event.getButton().equals(MouseButton.LEFT))
							((MyVaadinUI)UI.getCurrent()).navigateTo("album/" + String.valueOf(albumId));
					}
				});
			}
		}
	}

}
