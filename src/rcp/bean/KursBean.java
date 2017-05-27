package rcp.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import rcp.dao.DAO;
import rcp.entity.KursKaydet;
import rcp.model.HangiKurs;

@ManagedBean
@SessionScoped
public class KursBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3375908235918043584L;

	private List<HangiKurs> hangiKursList;
	private HangiKurs hangiKurs;
	private HangiKurs selectKurs;
	private KursKaydet kursKaydet;

	@PostConstruct
	public void init() {
		this.hangiKursList = DAO.getInstance().getHangiKursList();
		hangiKurs = new HangiKurs();
		selectKurs = new HangiKurs();
		kursKaydet = new KursKaydet();

	}

	public void kursKaydetFonk() {

		hangiKurs.setKursAdi(kursKaydet.getKursAdi());
		DAO.getInstance().addKurs(hangiKurs);
		MessagesView.messages(1, "Bilgi", "Kayýt iþleminiz baþarýyla gerçekleþmiþtir.");
		
		this.hangiKursList = DAO.getInstance().getHangiKursList();
		hangiKurs = new HangiKurs();
		selectKurs = new HangiKurs();
		kursKaydet = new KursKaydet();

	}

	public void updateFonk() {

		kursKaydet.setKursAdi(selectKurs.getKursAdi());

	}

	public void deleteFonk() {
		hangiKurs.setId(selectKurs.getId());
		DAO.getInstance().deleteKurs(hangiKurs);
		MessagesView.messages(1, "Bilgi", "Silme iþleminiz baþarýyla gerçekleþmiþtir.");
		this.hangiKursList = DAO.getInstance().getHangiKursList();
		hangiKurs = new HangiKurs();
		selectKurs = new HangiKurs();
		kursKaydet = new KursKaydet();

	}

	public void kursUpdateFonk() {
		hangiKurs.setKursAdi(kursKaydet.getKursAdi());
		DAO.getInstance().updateKurs(hangiKurs);
		MessagesView.messages(1, "Bilgi", "Güncelleme iþleminiz baþarýyla gerçekleþmiþtir.");
		
		this.hangiKursList = DAO.getInstance().getHangiKursList();
		hangiKurs = new HangiKurs();
		selectKurs = new HangiKurs();
		kursKaydet = new KursKaydet();
	}

	public HangiKurs getHangiKurs() {
		return hangiKurs;
	}

	public void setHangiKurs(HangiKurs hangiKurs) {
		this.hangiKurs = hangiKurs;
	}

	public KursKaydet getKursKaydet() {
		return kursKaydet;
	}

	public void setKursKaydet(KursKaydet kursKaydet) {
		this.kursKaydet = kursKaydet;
	}

	public HangiKurs getSelectKurs() {
		return selectKurs;
	}

	public void setSelectKurs(HangiKurs selectKurs) {
		this.selectKurs = selectKurs;
	}

	public List<HangiKurs> getHangiKursList() {
		return hangiKursList;
	}

	public void setHangiKursList(List<HangiKurs> hangiKursList) {
		this.hangiKursList = hangiKursList;
	}

}
