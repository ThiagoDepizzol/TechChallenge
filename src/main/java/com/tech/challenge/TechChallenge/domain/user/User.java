package com.tech.challenge.TechChallenge.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.tech.challenge.TechChallenge.domain.generic.DefaultEntity;
import com.tech.challenge.TechChallenge.domain.location.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "usr_users")
public class User extends DefaultEntity implements Serializable, UserDetails {

    public interface Json {

        interface Base extends DefaultEntity.Json.Base {
        }

        interface Sensitive {
        }

        interface WithLocation extends Base, Location.Json.Base {
        }

        interface WithAuthorities extends Base, UserAuthority.Json.Base {
        }

        interface All extends Base, WithLocation, WithAuthorities {
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Json.Base.class)
    private Long id;

    @JsonView(Json.Base.class)
    private String username;

    @JsonView(Json.Base.class)
    private String login;

    @JsonView(Json.Sensitive.class)
    private String password;

    @JoinColumn(name = "loc_location_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(Json.WithLocation.class)
    private Location location;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    @JsonView(Json.WithAuthorities.class)
    private Set<UserAuthority> userAuthorities = new HashSet<>();

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // sem roles
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public boolean hasId() {
        return Objects.nonNull(getId());
    }

    public User loadLocation() {
        Hibernate.initialize(location);
        return this;
    }

    public User loadAuthorities() {
        Hibernate.initialize(userAuthorities);
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                //                .add("login='" + login + "'")
                //                .add("password='" + password + "'")
                //                .add("location=" + location)
                //                .add("authorities=" + authorities)
                .toString();
    }
}
