package rs.codecentric.photo_album;

import java.util.HashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import rs.codecentric.photo_album.dao.IUserAdminDAO;
import rs.codecentric.photo_album.entity.User;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Component
@Scope("session")
@Theme("mytheme")
public class MyVaadinUI extends UI {

	private static final long serialVersionUID = -1732049758205648248L;

//	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
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

	public static User getCurrentUser() {
		org.springframework.security.core.userdetails.User usr = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return getCurrentUserDAO().loadUserByUsername(usr.getUsername());
	}

	public static IUserAdminDAO getCurrentUserDAO() {
		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
		return (IUserAdminDAO) helper.getBean("userAdminDAO");
	}

	@Override
	protected void init(VaadinRequest request) {

		// Create a navigator to control the defined views
		navigator = new Navigator(this, this);

		// Create and register the views
		for (String s : views.keySet()) {
            navigator.addView(s, views.get(s));
        }		

	}

}