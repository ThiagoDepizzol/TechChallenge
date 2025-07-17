package com.tech.challenge.TechChallenge.domain.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.tech.challenge.TechChallenge.domain.location.Location;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

@Getter
@Setter
@Entity
@Table(name = "usr_users")
public class User implements Serializable {

    public interface Json {

        interface Base {
        }

        interface Sensitive {
        }

        interface WithLocation extends Base, Location.Json.Base {
        }

        interface All extends Base, WithLocation {
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Json.Base.class)
    private Long id;

    @JsonView(Json.Base.class)
    private Boolean active;

    @JsonView(Json.Base.class)
    private String name;

    @JsonView(Json.Base.class)
    private String login;

    @JsonView(Json.Sensitive.class)
    private String password;

    @JsonView(Json.Base.class)
    private Instant lastModifyDate;

    @JoinColumn(name = "loc_location_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonView(Json.WithLocation.class)
    private Location location;

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

    public User loadLocation() {
        Hibernate.initialize(location);
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("active='" + active + "'")
                .add("name='" + name + "'")
                //                .add("login='" + login + "'")
                //                .add("password='" + password + "'")
                .add("lastModifyDate=" + lastModifyDate)
                //                .add("location=" + location)
                .toString();
    }

}
