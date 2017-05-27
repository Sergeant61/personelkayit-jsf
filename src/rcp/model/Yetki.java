package rcp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Yetki {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int yetki;
	
	public Yetki(){}
	
	public Yetki(int id, int yetki) {
		this.id = id;
		this.yetki = yetki;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYetki() {
		return yetki;
	}
	public void setYetki(int yetki) {
		this.yetki = yetki;
	}
}
