package ir.adicom.caryar;

public class CarInfo {
	
	private int id;
	private String type;
	private int price;
	private int kilometer;
	private long date;

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

	public CarInfo(){}

	public CarInfo(String t, int p, int k, long d) {
		super();
		this.type = t;
		this.price = p;
		this.kilometer = k;
		this.date = d;
	}
}