package rs.codecentric.photo_album;

import rs.codecentric.photo_album.entity.Album;
import rs.codecentric.photo_album.entity.User;

import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class ViewAlbums extends VerticalLayout implements View {

	private static final long serialVersionUID = 8938589162825804564L;

	public void enter(ViewChangeEvent event) {

		String parameters = event.getParameters();
//		User user = new User(); // TODO: Get user from parameters 
//		Album[] albums = (Album[]) user.getUserAlbums().toArray();

		setMargin(true);

		// place the album icons in a horizontal (wrappable) layout
		final HorizontalLayout hlayout = new HorizontalLayout();
		Button button = new Button("TEST");
		hlayout.addComponent(button);
		Button button2 = new Button("TEST2");
		hlayout.addComponent(button2);
		addComponent(hlayout);
		
//		for (int i = 0; i < albums.length; i++) {
//			ComponentAlbum componentAlbum = new ComponentAlbum(albums[i]);
//			hlayout.addComponent(componentAlbum);
//
//			// wrap the photo album icon + title into a clickable layout
//			componentAlbum.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
//
//				private static final long serialVersionUID = -3641352615876874152L;
//
//				@Override
//				public void layoutClick(LayoutEvents.LayoutClickEvent event) {
//					// TODO: switch the view to a single album view
//					if (event.getButton().equals(MouseButton.LEFT))
//						//albumComponent.addComponent(new Label("tiruriru"));
//						((MyVaadinUI)UI.getCurrent()).navigateTo("album");
//				}
//
//			});
//		}

		//TODO create new link
	}

}
