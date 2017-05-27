package rcp.conf;

public enum YetkiEnum {

	ADMIN(1, "Y�netici"), KULLANICI(2, "Kullan�c�");

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
