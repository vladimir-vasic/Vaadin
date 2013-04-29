package rs.codecentric.photo_album;

import rs.codecentric.photo_album.entity.Album;

import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ComponentAlbum extends CustomComponent implements LayoutEvents {

	private static final long serialVersionUID = 9096335418009381951L;
	private static final String ALBUM_ICON_FILENAME = "album_icon.png";

	/**
	 * The main layout of the component
	 */
	private VerticalLayout mainLayout;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 * 
	 * @param album - The album to be displayed
	 */
	public ComponentAlbum(Album album) {
		mainLayout = new VerticalLayout();
		setCompositionRoot(mainLayout);
		mainLayout.setMargin(true);
		mainLayout.addStyleName("layout-pointer"); // make it look as clickable

		// Image as a resource
		ThemeResource resource = new ThemeResource("icons/" + ALBUM_ICON_FILENAME);
		mainLayout.addComponent(new Image("", resource)); // TODO: get the icon from the random image from the album

		// The title of the photo album
		Label label = new Label(album.getAlbumName());
		label.setWidth("100%");
		mainLayout.addComponent(label);
		mainLayout.setComponentAlignment(label, Alignment.TOP_CENTER);
	}

	/**
	 * Published proxy LayoutClickListener for the mainLayout
	 * @param listener - A LayoutClickListener that should be forwarded to mainLayout's LayoutClickListener
	 */
	void addLayoutClickListener(LayoutEvents.LayoutClickListener listener) {
		mainLayout.addLayoutClickListener(listener);
	}

}
