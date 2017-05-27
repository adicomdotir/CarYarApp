package ir.adicom.caryar;

public class CarInfo {

	private int id;
	private String type;
	private int price;
	private int kilometer;
	private long date;

	public CarInfo(){}

	public CarInfo(String t, int p, int k, long d, String date2) {
		this.type = t;
		this.price = p;
		this.kilometer = k;
		this.date = d;
		this.date2 = date2;
	}

	public CarInfo(int id, String t, int p, int k, long d, String date2) {
		this.id = id;
		this.type = t;
		this.price = p;
		this.kilometer = k;
		this.date = d;
		this.date2 = date2;
	}

	public String getDate2() {
		return date2;
	}

	public void setDate2(String date2) {
		this.date2 = date2;
	}

	private String date2;

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getKilometer() {
		return kilometer;
	}

	public void setKilometer(int kilometer) {
		this.kilometer = kilometer;
	}
}