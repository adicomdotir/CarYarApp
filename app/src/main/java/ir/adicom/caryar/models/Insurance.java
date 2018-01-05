package ir.adicom.caryar.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by adicom on 1/5/18.
 */

@Entity
public class Insurance {
    @Id
    private Long id;
    private String name;
    private int price;
    private String date;
    private Long carId;
    @Generated(hash = 393489752)
    public Insurance(Long id, String name, int price, String date, Long carId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.date = date;
        this.carId = carId;
    }
    @Generated(hash = 247287177)
    public Insurance() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Long getCarId() {
        return this.carId;
    }
    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
