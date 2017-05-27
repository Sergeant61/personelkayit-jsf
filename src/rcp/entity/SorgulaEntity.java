package rcp.entity;

import java.util.Date;
import java.util.List;

public class SorgulaEntity {

	private String ad;
	private String soyad;
	private Date basTarihi;
	private Date sonTarihi;
	private List<String> hangiKurs;
	private int type;

	public SorgulaEntity() {

	}

	public SorgulaEntity(String ad, String soyad, Date basTarihi, Date sonTarihi, List<String> hangiKurs, int type) {
		this.ad = ad;
		this.soyad = soyad;
		this.basTarihi = basTarihi;
		this.sonTarihi = sonTarihi;
		this.hangiKurs = hangiKurs;
		this.type = type;
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

	public Date getBasTarihi() {
		return basTarihi;
	}

	public void setBasTarihi(Date basTarihi) {
		this.basTarihi = basTarihi;
	}

	public Date getSonTarihi() {
		return sonTarihi;
	}

	public void setSonTarihi(Date sonTarihi) {
		this.sonTarihi = sonTarihi;
	}

	public List<String> getHangiKurs() {
		return hangiKurs;
	}

	public void setHangiKurs(List<String> hangiKurs) {
		this.hangiKurs = hangiKurs;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
