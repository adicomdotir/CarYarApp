package ir.adicom.caryar.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.DaoException;

/**
 * Created by adicom on 11/15/17.
 */

@Entity
public class Car {
    @Id
    private Long id;
    private String name;
    private String plaque;
    private int year;
    private String color;
    @ToMany(referencedJoinProperty = "carId")
    private List<Fuel> fuels;
    @ToMany(referencedJoinProperty = "carId")
    private List<EngineOil> engineOils;
    @ToMany(referencedJoinProperty = "carId")
    private List<Service> services;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 709963916)
    private transient CarDao myDao;

    @Generated(hash = 53612888)
    public Car(Long id, String name, String plaque, int year, String color) {
        this.id = id;
        this.name = name;
        this.plaque = plaque;
        this.year = year;
        this.color = color;
    }
    @Generated(hash = 625572433)
    public Car() {
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
    public String getPlaque() {
        return this.plaque;
    }
    public void setPlaque(String plaque) {
        this.plaque = plaque;
    }
    public int getYear() {
        return this.year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 671701453)
    public List<Fuel> getFuels() {
        if (fuels == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FuelDao targetDao = daoSession.getFuelDao();
            List<Fuel> fuelsNew = targetDao._queryCar_Fuels(id);
            synchronized (this) {
                if (fuels == null) {
                    fuels = fuelsNew;
                }
            }
        }
        return fuels;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1005911069)
    public synchronized void resetFuels() {
        fuels = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1453733274)
    public List<EngineOil> getEngineOils() {
        if (engineOils == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            EngineOilDao targetDao = daoSession.getEngineOilDao();
            List<EngineOil> engineOilsNew = targetDao._queryCar_EngineOils(id);
            synchronized (this) {
                if (engineOils == null) {
                    engineOils = engineOilsNew;
                }
            }
        }
        return engineOils;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1657555289)
    public synchronized void resetEngineOils() {
        engineOils = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1460522435)
    public List<Service> getServices() {
        if (services == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ServiceDao targetDao = daoSession.getServiceDao();
            List<Service> servicesNew = targetDao._queryCar_Services(id);
            synchronized (this) {
                if (services == null) {
                    services = servicesNew;
                }
            }
        }
        return services;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1227775301)
    public synchronized void resetServices() {
        services = null;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 679603784)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCarDao() : null;
    }
}
