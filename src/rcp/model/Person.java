package rcp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String ad;

	private String soyad;

	private String telefon;

	private String email;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<HangiKurs> hangiKurs;

	@Column(name = "gorusme_tipi")
	private int gorusmeTipi;

	private String mesaj;

	@Column(name = "gorusme_zamani")
	private Date gorusmeZamani;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Person() {
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

	public List<HangiKurs> getHangiKurs() {
		return hangiKurs;
	}

	public void setHangiKurs(List<HangiKurs> hangiKurs) {
		this.hangiKurs = hangiKurs;
	}

	public int getGorusmeTipi() {
		return gorusmeTipi;
	}

	public void setGorusmeTipi(int gorusmeTipi) {
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
