package rs.codecentric.photo_album;

import rs.codecentric.photo_album.entity.Album;
import rs.codecentric.photo_album.entity.Picture;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class ViewAlbum extends VerticalLayout implements View {

	private static final long serialVersionUID = -2361949976423525591L;

	@Override
	public void enter(ViewChangeEvent event) {

		String parameters = event.getParameters();
		Album album = new Album(); // TODO: Get album from parameters 
		Picture[] pictures = (Picture[]) album.getAlbumPictures().toArray();

		setWidth("400px");

		// place the image thumbnails in a horizontal (wrappable) layout
		final HorizontalLayout hlayout = new HorizontalLayout();
		addComponent(hlayout);
		//hlayout.setWidth("400px");

		for (int i = 0; i < pictures.length; i++) {
			ComponentImage componentImage = new ComponentImage(pictures[i]);
			hlayout.addComponent(componentImage);

			// wrap the photo album icon + title into a clickable layout
			componentImage.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {

				private static final long serialVersionUID = 4010312568756089212L;

				@Override
				public void layoutClick(LayoutEvents.LayoutClickEvent event) {
					// TODO switch the view to a single album
					if (event.getButton().equals(MouseButton.LEFT)) {
						//((PhotoalbumUI)UI.getCurrent()).navigateTo("album");
						Notification.show("Image clicked", Notification.Type.HUMANIZED_MESSAGE);
					}
				}

			});

			//TODO upload new image
		}

	}

}
