package rcp.model;

import java.util.Date;
import java.util.List;

public class PersonList {

	private int id;

	private String ad;

	private String soyad;

	private String telefon;

	private String email;

	private List<String> hangiKurs;

	private String gorusmeTipi;

	private String mesaj;

	private Date gorusmeZamani;
	
	public PersonList(){}

	public PersonList(int id, String ad, String soyad, String telefon, String email, List<String> hangiKurs,
			String gorusmeTipi, String mesaj, Date gorusmeZamani) {
		this.id = id;
		this.ad = ad;
		this.soyad = soyad;
		this.telefon = telefon;
		this.email = email;
		this.hangiKurs = hangiKurs;
		this.gorusmeTipi = gorusmeTipi;
		this.mesaj = mesaj;
		this.gorusmeZamani = gorusmeZamani;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public String getSoyad() {
		return soyad;
	}

	public void setSoyad(String soyad) {
		this.soyad = soyad;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getHangiKurs() {
		return hangiKurs;
	}

	public void setHangiKurs(List<String> hangiKurs) {
		this.hangiKurs = hangiKurs;
	}

	public String getGorusmeTipi() {
		return gorusmeTipi;
	}

	public void setGorusmeTipi(String gorusmeTipi) {
		this.gorusmeTipi = gorusmeTipi;
	}

	public String getMesaj() {
		return mesaj;
	}

	public void setMesaj(String mesaj) {
		this.mesaj = mesaj;
	}

	public Date getGorusmeZamani() {
		return gorusmeZamani;
	}

	public void setGorusmeZamani(Date gorusmeZamani) {
		this.gorusmeZamani = gorusmeZamani;
	}
	
	

}
