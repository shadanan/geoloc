package io.elide.geoloc.models;

import com.yahoo.elide.annotation.ComputedAttribute;
import com.yahoo.elide.annotation.Include;
import com.yahoo.elide.annotation.ReadPermission;
import com.yahoo.elide.annotation.SharePermission;
import com.yahoo.elide.annotation.UpdatePermission;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Table;

@Entity
@Include(rootLevel = true)
@UpdatePermission(expression = "io.elide.geoloc.checks.IsOwner OR Prefab.Common.UpdateOnCreate")
@SharePermission(expression = "Prefab.Role.All")
@Table(name = "app_user")
public class User implements Principal {

    private long id;
    private String name;
    private String email;
    private String password;

    private Collection<User> friends = new ArrayList<>();
    private Collection<User> followers = new ArrayList<>();
    private Collection<Location> locations = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ReadPermission(expression = "Prefab.Role.None")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany
    public Collection<User> getFriends() {
        return friends;
    }

    public void setFriends(Collection<User> friends) {
        this.friends = friends;
    }

    @UpdatePermission(expression = "Prefab.Role.All")
    @ManyToMany(mappedBy = "friends")
    public Collection<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Collection<User> followers) {
        this.followers = followers;
    }

    @OneToMany(mappedBy = "user")
    public Collection<Location> getLocations() {
        return locations;
    }

    public void setLocations(Collection<Location> locations) {
        this.locations = locations;
    }

    @Transient
    @ComputedAttribute
    public Map<String, Double> getLocation() {
        LinkedHashMap<String, Double> result = new LinkedHashMap<>();

        Optional<Location> location = locations.stream()
            .max((o1, o2) -> o1.getLastUpdated().compareTo(o2.getLastUpdated()));
        if (location.isPresent()) {
            result.put("latitude", location.get().getLatitude());
            result.put("longitude", location.get().getLongitude());
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
