package rcp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import rcp.conf.YetkiEnum;
import rcp.dao.DAO;
import rcp.entity.UserEntity;
import rcp.entity.UserKaydet;
import rcp.entity.YetkiKaydet;
import rcp.model.HangiKurs;
import rcp.model.Person;
import rcp.model.PersonList;
import rcp.model.User;
import rcp.model.Yetki;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4165989573695827461L;

	private UserKaydet userKaydet;
	private Integer yetkiSelect;
	private List<YetkiKaydet> yetkiList;
	private List<User> userList;

	private UserEntity userEntity;
	private boolean kullaniciIcerede = false;

	private static User mevcutUser;
	private User user;
	private int yetki = 0;
	private static int yetkiGenel = 0;
	private String yetkiMetin = "Yetkiniz Bulunmamaktadýr.";

	@PostConstruct
	public void init() {
		DAO.getInstance();
		this.userEntity = new UserEntity();
		this.user = new User();
		this.userKaydet = new UserKaydet();
		this.userList = DAO.getInstance().getUserList();
		this.yetkiList = DAO.getInstance().getYetkiList();
	}

	public String login() {
		User user = DAO.getInstance().checkUser(this.userEntity);
		if (user == null) {
			return "fail.xhtml";
		} else {
			this.mevcutUser = user;
			yetkiFonk();
			this.kullaniciIcerede = true;
			return "guv/anasayfa.xhtml?faces-redirect=true";
		}
	}

	public void addUserFonk() {

		if (userKaydet.getPassword().equals(userKaydet.getPasswordCheck())) {

			if (this.userKaydet.getUserName().length() > 5 && this.userKaydet.getPassword().length() > 5) {

				this.user.setUserName(this.userKaydet.getUserName());
				this.user.setPassword(this.userKaydet.getPassword());

				List<Yetki> yetkis = new ArrayList<>();

				Yetki yetki = null;

				if (yetkiSelect == null) {
					yetki = new Yetki(YetkiEnum.KULLANICI.getId(), YetkiEnum.KULLANICI.getId());
				} else {

					if (YetkiEnum.ADMIN.getId() == yetkiSelect) {

						yetki = new Yetki(yetkiSelect, YetkiEnum.ADMIN.getId());

					} else if (YetkiEnum.KULLANICI.getId() == yetkiSelect) {

						yetki = new Yetki(yetkiSelect, YetkiEnum.KULLANICI.getId());
					}
				}

				yetkis.add(yetki);

				this.user.setYetki(yetkis);

				DAO.getInstance().addUser(this.user);
				MessagesView.messages(1, "Bilgi", "Kayýt iþleminiz baþarýyla gerçekleþmiþtir.");
				this.user = new User();
				this.userKaydet = new UserKaydet();
				this.yetkiList = DAO.getInstance().getYetkiList();
				this.userList = DAO.getInstance().getUserList();

			} else {
				MessagesView.messages(3, "Hata", "Girdiðiniz user,paralo en az 6 karekterli olmalýdýr.");
			}

		} else {
			MessagesView.messages(3, "Hata", "Girdiðiniz paralolar eþleþmiyor.");
		}

	}

	private void yetkiFonk() {
		List<Yetki> yetkis = LoginBean.mevcutUser.getYetki();

		for (int i = 0; i < yetkis.size(); i++) {

			if (YetkiEnum.ADMIN.getId() == yetkis.get(i).getYetki()) {
				this.yetki = YetkiEnum.ADMIN.getId();
				this.yetkiMetin = YetkiEnum.ADMIN.getValue();
				LoginBean.setYetkiGenel(this.yetki);
				break;
			} else if (YetkiEnum.KULLANICI.getId() == yetkis.get(i).getYetki()) {
				LoginBean.setYetkiGenel(YetkiEnum.KULLANICI.getId());
				this.yetkiMetin = YetkiEnum.KULLANICI.getValue();
			}

		}

	}

	public String loginOut() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		this.kullaniciIcerede = false;
		return "/index.xhtml?faces-redirect=true";
	}

	public void deleteFonk() {

		DAO.getInstance().deleteUser(this.user);
		this.userEntity = new UserEntity();
		this.user = new User();
		this.userKaydet = new UserKaydet();
		this.userList = DAO.getInstance().getUserList();
	}

	public void guncelleFonk() {

		this.userKaydet.setUserName(this.user.getUserName());
		this.userKaydet.setPassword(this.user.getPassword());
		this.userKaydet.setPasswordCheck(this.user.getPassword());

		for (int i = 0; i < user.getYetki().size(); i++) {
			this.yetkiSelect = user.getYetki().get(i).getYetki();
		}

	}
	
	public void updateUserFonk() {
		
		
		if (userKaydet.getPassword().equals(userKaydet.getPasswordCheck())) {

			if (this.userKaydet.getUserName().length() > 5 && this.userKaydet.getPassword().length() > 5) {

				this.user.setUserName(this.userKaydet.getUserName());
				this.user.setPassword(this.userKaydet.getPassword());

				List<Yetki> yetkis = new ArrayList<>();

				Yetki yetki = null;

				if (yetkiSelect == null) {
					yetki = new Yetki(YetkiEnum.KULLANICI.getId(), YetkiEnum.KULLANICI.getId());
				} else {

					if (YetkiEnum.ADMIN.getId() == yetkiSelect) {

						yetki = new Yetki(yetkiSelect, YetkiEnum.ADMIN.getId());

					} else if (YetkiEnum.KULLANICI.getId() == yetkiSelect) {

						yetki = new Yetki(yetkiSelect, YetkiEnum.KULLANICI.getId());
					}
				}

				yetkis.add(yetki);

				this.user.setYetki(yetkis);
				
//				System.out.println(user.getYetki().get(0).getYetki());
//				System.out.println(user.getUserName());
//				System.out.println(user.getPassword());
//				System.out.println(user.getId());

				DAO.getInstance().updateUser(user);
				MessagesView.messages(1, "Bilgi", "Kayýt iþleminiz baþarýyla gerçekleþmiþtir.");
				this.user = new User();
				this.userKaydet = new UserKaydet();
				this.yetkiList = DAO.getInstance().getYetkiList();
				this.userList = DAO.getInstance().getUserList();

			} else {
				MessagesView.messages(3, "Hata", "Girdiðiniz user,paralo en az 6 karekterli olmalýdýr.");
			}

		} else {
			MessagesView.messages(3, "Hata", "Girdiðiniz paralolar eþleþmiyor.");
		}
		

	}

	public UserKaydet getUserKaydet() {
		return userKaydet;
	}

	public void setUserKaydet(UserKaydet userKaydet) {
		this.userKaydet = userKaydet;
	}

	public Integer getYetkiSelect() {
		return yetkiSelect;
	}

	public void setYetkiSelect(Integer yetkiSelect) {
		this.yetkiSelect = yetkiSelect;
	}

	public List<YetkiKaydet> getYetkiList() {
		return yetkiList;
	}

	public void setYetkiList(List<YetkiKaydet> yetkiList) {
		this.yetkiList = yetkiList;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public boolean isKullaniciIcerede() {
		return kullaniciIcerede;
	}

	public void setKullaniciIcerede(boolean kullaniciIcerede) {
		this.kullaniciIcerede = kullaniciIcerede;
	}

	public static User getMevcutUser() {
		return mevcutUser;
	}

	public static void setMevcutUser(User mevcutUser) {
		LoginBean.mevcutUser = mevcutUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getYetki() {
		return yetki;
	}

	public void setYetki(int yetki) {
		this.yetki = yetki;
	}

	public static int getYetkiGenel() {
		return yetkiGenel;
	}

	public static void setYetkiGenel(int yetkiGenel) {
		LoginBean.yetkiGenel = yetkiGenel;
	}

	public String getYetkiMetin() {
		return yetkiMetin;
	}

	public void setYetkiMetin(String yetkiMetin) {
		this.yetkiMetin = yetkiMetin;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
