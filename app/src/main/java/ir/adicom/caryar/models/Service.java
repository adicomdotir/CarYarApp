package ir.adicom.caryar.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by adicom on 11/12/17.
 */

@Entity
public class Service {
    @Id
    private Long id;
    private String title;
    private String date;
    private int partPrice;
    private int expertPrice;
    private Long carId;
    @Generated(hash = 1764429945)
    public Service(Long id, String title, String date, int partPrice,
            int expertPrice, Long carId) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.partPrice = partPrice;
        this.expertPrice = expertPrice;
        this.carId = carId;
    }
    @Generated(hash = 552382128)
    public Service() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getPartPrice() {
        return this.partPrice;
    }
    public void setPartPrice(int partPrice) {
        this.partPrice = partPrice;
    }
    public int getExpertPrice() {
        return this.expertPrice;
    }
    public void setExpertPrice(int expertPrice) {
        this.expertPrice = expertPrice;
    }
    public Long getCarId() {
        return this.carId;
    }
    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
