package ir.adicom.caryar.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 *
 * Created by YasharPanahi on 6/9/17.
 */

@Entity
public class EngineOil {
    @Id
    private Long id;
    private String name;
    private int nowKilometer;
    private int maxKilometer;
    private int price;
    private String date;
    private Long carId;
    @Generated(hash = 229818481)
    public EngineOil(Long id, String name, int nowKilometer, int maxKilometer,
            int price, String date, Long carId) {
        this.id = id;
        this.name = name;
        this.nowKilometer = nowKilometer;
        this.maxKilometer = maxKilometer;
        this.price = price;
        this.date = date;
        this.carId = carId;
    }
    @Generated(hash = 1587947175)
    public EngineOil() {
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
    public int getNowKilometer() {
        return this.nowKilometer;
    }
    public void setNowKilometer(int nowKilometer) {
        this.nowKilometer = nowKilometer;
    }
    public int getMaxKilometer() {
        return this.maxKilometer;
    }
    public void setMaxKilometer(int maxKilometer) {
        this.maxKilometer = maxKilometer;
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
