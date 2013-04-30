package rs.codecentric.photo_album;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import rs.codecentric.photo_album.dao.IUserAdminDAO;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Component
@Scope("session")
public class MyVaadinUI extends UI {

	private static final long serialVersionUID = -1732049758205648248L;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Global navigator object (private, use UI.navigateTo() instead)
	 */
	private Navigator navigator;

//	@Autowired
//	IUserAdminDAO userAdminDAO;
	
	/**
	 * Defines all the views in the navigation
	 */
	protected static final HashMap<String, Class<? extends View>> views = new HashMap<String, Class<? extends View>>() {
		private static final long serialVersionUID = -1882021778283265535L;

		{
			put("albums", ViewAlbums.class);
			put("album", ViewAlbum.class);
			//put("login", ViewLogin.class);
			put("", ViewAlbums.class);
		}
	};

	public Navigator getNavigator() {
		return navigator;
	}

	/**
	 * Global navigation is handled here
	 * @param view    The name of the view to which to navigate.
	 */
	public void navigateTo(String view) {
		getNavigator().navigateTo(view);
	}

	@Override
	protected void init(VaadinRequest request) {

		// Create a navigator to control the defined views
		navigator = new Navigator(this, this);

		// Create and register the views
		for (String s : views.keySet()) {
            navigator.addView(s, views.get(s));
        }		

		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
		final IUserAdminDAO userAdminDAO = (IUserAdminDAO) helper.getBean("userAdminDAO");

		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 6508940859014631144L;

			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
				User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				log.info("LOGGED USER: {}", user);
				if (userAdminDAO != null) {
					Notification.show("Autowiring successful.");
					rs.codecentric.photo_album.entity.User usr = userAdminDAO.loadUserByUsername(user.getUsername());
					log.info("USER: {}", usr.getUserName());
				} else {
					Notification.show("Autowiring FAILED!!!");
				}
			}
		});
		layout.addComponent(button);
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		log.info("LOGGED USER: {}", user);
	}

}