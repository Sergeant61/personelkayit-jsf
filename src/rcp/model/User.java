package rcp.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(unique = true)
	private String userName;

	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Yetki> yetki;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Yetki> getYetki() {
		return yetki;
	}

	public void setYetki(List<Yetki> yetki) {
		this.yetki = yetki;
	}

}
