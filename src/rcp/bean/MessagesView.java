package rcp.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessagesView {

	/**
	 * 
	 * @param i info,warn,error,fatal
	 * @param hata
	 * @param message
	 */
	public static void messages(int i, String hata, String message) {

		switch (i) {
		case 1:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, hata, message));
			break;
		case 2:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, hata, message));
			break;
		case 3:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, hata, message));
			break;
		case 4:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, hata, message));
			break;
		}

	}
}