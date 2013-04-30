package rs.codecentric.photo_album;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import rs.codecentric.photo_album.entity.Picture;

import com.vaadin.event.LayoutEvents;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ComponentImage extends CustomComponent implements LayoutEvents {

	private static final long serialVersionUID = 778997066610225248L;

	private VerticalLayout mainLayout;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * @param picture - The picture to be displayed
	 */
	public ComponentImage(Picture picture) {
		mainLayout = new VerticalLayout();
		setCompositionRoot(mainLayout);
		mainLayout.setMargin(true);
		mainLayout.addStyleName("layout-pointer"); // make it look as clickable

		// Image as a resource
		final byte[] pictureContent = picture.getContent();
		StreamSource streamSource = new StreamSource() {
			private static final long serialVersionUID = -8024596230163700883L;
			public InputStream getStream() {
				 return new ByteArrayInputStream(pictureContent);
			}
		};
		StreamResource resource = new StreamResource(streamSource, picture.getName() + ".png");
		mainLayout.addComponent(new Image("", resource));

		// The title of the picture
		Label labelTitle = new Label(picture.getName());
		labelTitle.setWidth("100%");
		mainLayout.addComponent(labelTitle);
		mainLayout.setComponentAlignment(labelTitle, Alignment.TOP_CENTER);

		// The description of the picture
		Label labelDescription = new Label("Here goes image description");
		labelDescription.setWidth("100%");
		mainLayout.addComponent(labelDescription);
		mainLayout.setComponentAlignment(labelDescription, Alignment.TOP_CENTER);
	}

	/**
	 * Published proxy LayoutClickListener for the mainLayout
	 * @param listener - A LayoutClickListener that should be forwarded to mainLayout's LayoutClickListener
	 */
	void addLayoutClickListener(LayoutEvents.LayoutClickListener listener) {
		mainLayout.addLayoutClickListener(listener);
	}

}
