package rcp.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import rcp.dao.DAO;
import rcp.entity.SorgulaEntity;
import rcp.model.HangiKurs;
import rcp.model.Person;
import rcp.model.PersonList;

@ManagedBean
@SessionScoped
public class PersonelBean {

	// HttpServletRequest request = (HttpServletRequest)
	// FacesContext.getCurrentInstance().getExternalContext().getRequest();
	// String ipAddress = request.getRemoteAddr();

	private Person personAdd; // Kullanýcý Bilgilerinin bulunduðu entity
	private String personel; // Sil ve güncelle fonk için kullanýlan combonun
								// verisini taþarý
	private SorgulaEntity sorgulaEntity; // sorgulama bölümü entitysi

	private List<HangiKurs> selectedHangiKursList; // Seçili olan kurslarýn
													// listesi (add)
	private List<String> selectedHangiKursListText; // Seçili olan kurslarýn
													// listesi (update)

	private List<HangiKurs> hangiKursList; // Tüm kurslarýn Tip listesi
	private List<String> hangiKursListText; // Tüm kurslarýn string listesi

	private List<PersonList> filtirPersonelList; // sorgula filitresi
	private List<PersonList> personelList; // person listesinin tablo için
											// dönüþtürülmüþ hali
	private List<String> personelListText; // Sil ve güncelle fonk için
											// kullanýlan combonun verisi

	private boolean tabloView = true;

	@PostConstruct
	public void init() {
		this.personAdd = new Person();
		this.sorgulaEntity = new SorgulaEntity();
		this.personelList = DAO.getInstance().getPersonList();
		this.hangiKursList = DAO.getInstance().getHangiKursList();
		convertListString(personelList);
		convertHangiKursListString(hangiKursList);
	}

	public void addPersonelFonk() {
		personAdd.setHangiKurs(selectedHangiKursList);
		personAdd.setGorusmeZamani(Calendar.getInstance().getTime());
		personAdd.setUser(LoginBean.getMevcutUser());
		DAO.getInstance().addPerson(personAdd);
		MessagesView.messages(1, "Bilgi", "Kayýt iþleminiz baþarýyla gerçekleþmiþtir.");
		this.personelList = DAO.getInstance().getPersonList();
		convertListString(personelList); 
		this.personAdd = new Person();
		this.selectedHangiKursList = new ArrayList<HangiKurs>();
	}

	public void updatePersonelFonk() {
		convertHangiKursStringList(this.selectedHangiKursListText);
		personAdd.setHangiKurs(selectedHangiKursList);
		personAdd.setUser(LoginBean.getMevcutUser());

		DAO.getInstance().updatePerson(personAdd);
		this.personelList = DAO.getInstance().getPersonList();
		convertListString(personelList);
		this.personAdd = new Person();
		this.selectedHangiKursList = new ArrayList<HangiKurs>();
		this.selectedHangiKursListText = new ArrayList<>();

	}

	public void listSorguFonk() {
		this.personelList = DAO.getInstance().sorgula(this.sorgulaEntity);
	}

	public void setUpdate() {
		this.personelList = DAO.getInstance().getPersonList();
	}

	public void convertListString(List<PersonList> personelList) {

		this.personelListText = new ArrayList<>();

		for (int i = 0; i < personelList.size(); i++) {

			this.personelListText.add(personelList.get(i).getId() + " " + personelList.get(i).getAd() + " "
					+ personelList.get(i).getSoyad());
		}

	}

	public void convertHangiKursListString(List<HangiKurs> hangiKurs) {

		this.hangiKursListText = new ArrayList<>();

		for (int i = 0; i < hangiKurs.size(); i++) {

			this.hangiKursListText.add(hangiKurs.get(i).getKursAdi());
		}

	}

	public void convertHangiKursStringList(List<String> hangi) {

		this.selectedHangiKursList = new ArrayList<>();

		for (int l = 0; l < hangi.size(); l++) {

			for (int i = 0; i < this.hangiKursList.size(); i++) {

				if (hangi.get(l).equals(this.hangiKursList.get(i).getKursAdi())) {
					HangiKurs hangiKurs = new HangiKurs();
					hangiKurs.setId(this.hangiKursList.get(i).getId());
					hangiKurs.setKursAdi(this.hangiKursList.get(i).getKursAdi());

					this.selectedHangiKursList.add(hangiKurs);

				}
			}

		}

		System.out.println("SÝZE " + this.selectedHangiKursList.size());

	}

	public void guncelleFonk() {
		int id = Integer.valueOf(this.personel.substring(0, this.personel.indexOf(" ")));
		Person person = new Person();
		this.selectedHangiKursListText = new ArrayList<>();

		for (int i = 0; i < this.personelList.size(); i++) {

			if (this.personelList.get(i).getId() == id) {
				person.setId(id);
				person.setAd(personelList.get(i).getAd());
				person.setSoyad(personelList.get(i).getSoyad());
				person.setTelefon(personelList.get(i).getTelefon());
				person.setEmail(personelList.get(i).getEmail());
				person.setGorusmeZamani(personelList.get(i).getGorusmeZamani());

				int gorusmeTipi = 0;
				if (personelList.get(i).getGorusmeTipi().equals("Telefonla")) {
					gorusmeTipi = 1;
				} else {
					gorusmeTipi = 2;
				}

				person.setGorusmeTipi(gorusmeTipi);
				person.setMesaj(personelList.get(i).getMesaj());
				this.setSelectedHangiKursListText(personelList.get(i).getHangiKurs());

			}
		}

		this.personAdd = person;

	}

	public void silFonk() {

		int id = Integer.valueOf(this.personel.substring(0, this.personel.indexOf(" ")));

		Person person = new Person();

		person.setId(id);

		DAO.getInstance().deletePerson(person);

		this.personAdd = new Person();
		this.sorgulaEntity = new SorgulaEntity();
		this.personelList = DAO.getInstance().getPersonList();
		this.hangiKursList = DAO.getInstance().getHangiKursList();
		convertListString(personelList);

	}

	public Person getPersonAdd() {
		return personAdd;
	}

	public void setPersonAdd(Person personAdd) {
		this.personAdd = personAdd;
	}

	public List<HangiKurs> getSelectedHangiKursList() {
		return selectedHangiKursList;
	}

	public void setSelectedHangiKursList(List<HangiKurs> selectedHangiKursList) {
		this.selectedHangiKursList = selectedHangiKursList;
	}

	public List<HangiKurs> getHangiKursList() {
		return hangiKursList;
	}

	public void setHangiKursList(List<HangiKurs> hangiKursList) {
		this.hangiKursList = hangiKursList;
	}

	public List<PersonList> getPersonelList() {
		return personelList;
	}

	public void setPersonelList(List<PersonList> personelList) {
		this.personelList = personelList;
	}

	public List<PersonList> getFiltirPersonelList() {
		return filtirPersonelList;
	}

	public void setFiltirPersonelList(List<PersonList> filtirPersonelList) {
		this.filtirPersonelList = filtirPersonelList;
	}

	public boolean isTabloView() {
		return tabloView;
	}

	public void setTabloView(boolean tabloView) {
		this.tabloView = tabloView;
	}

	public SorgulaEntity getSorgulaEntity() {
		return sorgulaEntity;
	}

	public void setSorgulaEntity(SorgulaEntity sorgulaEntity) {
		this.sorgulaEntity = sorgulaEntity;
	}

	public String getPersonel() {
		return personel;
	}

	public void setPersonel(String personel) {
		this.personel = personel;
	}

	public List<String> getPersonelListText() {
		return personelListText;
	}

	public void setPersonelListText(List<String> personelListText) {
		this.personelListText = personelListText;
	}

	public List<String> getSelectedHangiKursListText() {
		return selectedHangiKursListText;
	}

	public void setSelectedHangiKursListText(List<String> selectedHangiKursListText) {
		this.selectedHangiKursListText = selectedHangiKursListText;
	}

	public List<String> getHangiKursListText() {
		return hangiKursListText;
	}

	public void setHangiKursListText(List<String> hangiKursListText) {
		this.hangiKursListText = hangiKursListText;
	}

}
