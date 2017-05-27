package rcp.converter;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import rcp.bean.KursBean;
import rcp.model.HangiKurs;


@FacesConverter("kursConverter")
public class KursConverter implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
            	KursBean service = (KursBean) fc.getExternalContext().getSessionMap().get("kursBean");
            	int id = Integer.parseInt(value);
            	return getMyDurum(id,service.getHangiKursList());
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid durum."));
            }
        } 
        else {
            return null;
        }
    }
 
    private Object getMyDurum(int id, List<HangiKurs> durumList) {
    	for (HangiKurs durum : durumList) {
			if(durum.getId() == id)
				return durum;
		}
    	return null;
	}

	public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((HangiKurs) object).getId());
        }
        else {
            return null;
        }
    }   
}         

