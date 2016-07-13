package io.elide.geoloc.models;

import com.yahoo.elide.annotation.ComputedAttribute;
import com.yahoo.elide.annotation.Exclude;
import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.UpdatePermission;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
@Include
public class Location {

    private long id;
    private double latitude;
    private double longitude;
    private Date lastUpdated;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Exclude
    public double getRawLatitude() {
        return latitude;
    }

    public void setRawLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Transient
    @ComputedAttribute
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        setRawLatitude(latitude);
        lastUpdated = new Date();
    }

    @Exclude
    public double getRawLongitude() {
        return longitude;
    }

    public void setRawLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Transient
    @ComputedAttribute
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        setRawLongitude(longitude);
        lastUpdated = new Date();
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @UpdatePermission(expression = "Prefab.Role.None")
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @OneToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
