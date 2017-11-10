package ir.adicom.caryar.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by adicom on 11/10/17.
 */

@Entity
public class Fuel {
    @Id
    private Long id;
    private String date;
    private int kilometer;
    private int price;
    private String type;
    @Generated(hash = 1039958607)
    public Fuel(Long id, String date, int kilometer, int price, String type) {
        this.id = id;
        this.date = date;
        this.kilometer = kilometer;
        this.price = price;
        this.type = type;
    }
    @Generated(hash = 1121116807)
    public Fuel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getKilometer() {
        return this.kilometer;
    }
    public void setKilometer(int kilometer) {
        this.kilometer = kilometer;
    }
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
