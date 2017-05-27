package rcp.conf;

public enum YetkiEnum {

	ADMIN(1, "Yönetici"), KULLANICI(2, "Kullanýcý");

	int id;
	String value;

	YetkiEnum(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

}
