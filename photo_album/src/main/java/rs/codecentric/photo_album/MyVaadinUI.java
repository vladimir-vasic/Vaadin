package rs.codecentric.photo_album;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author xpoft
 */
@Component
@Scope("prototype")
public class MyVaadinUI extends UI implements ErrorHandler {

	private static final long serialVersionUID = -601974378876819152L;

	@Override
	protected void init(final VaadinRequest request) {
		VaadinSession.getCurrent().setErrorHandler(this);
		setSizeFull();
	}

	/**
	 * Exception on action
	 */
	@Override
	public void error(com.vaadin.server.ErrorEvent event) {
		// connector event
		if (event.getThrowable().getCause() instanceof AccessDeniedException) {
			AccessDeniedException accessDeniedException = (AccessDeniedException) event
					.getThrowable().getCause();
			Notification.show(accessDeniedException.getMessage(),
					Notification.Type.ERROR_MESSAGE);

			// Cleanup view. Now Vaadin ignores errors and always shows the
			// view. :-(
			// since beta10
			setContent(null);
			return;
		}

		// Error on page load. Now it doesn't work. User sees standard error
		// page.
		if (event.getThrowable() instanceof AccessDeniedException) {
			AccessDeniedException exception = (AccessDeniedException) event
					.getThrowable();

			Label label = new Label(exception.getMessage());
			label.setWidth(-1, Unit.PERCENTAGE);

			Link goToMain = new Link("Go to main", new ExternalResource("/"));

			VerticalLayout layout = new VerticalLayout();
			layout.addComponent(label);
			layout.addComponent(goToMain);
			layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
			layout.setComponentAlignment(goToMain, Alignment.MIDDLE_CENTER);

			VerticalLayout mainLayout = new VerticalLayout();
			mainLayout.setSizeFull();
			mainLayout.addComponent(layout);
			mainLayout.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

			setContent(mainLayout);
			Notification.show(exception.getMessage(),
					Notification.Type.ERROR_MESSAGE);
			return;
		}

		DefaultErrorHandler.doDefault(event);
	}
}