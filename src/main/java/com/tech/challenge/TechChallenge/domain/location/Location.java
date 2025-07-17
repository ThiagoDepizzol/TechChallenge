package com.tech.challenge.TechChallenge.domain.location;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

@Getter
@Setter
@Entity
@Table(name = "loc_locations")
public class Location implements Serializable {

    public interface Json {

        interface Base {
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Json.Base.class)
    private Long id;

    @JsonView(Json.Base.class)
    private Boolean active;

    @JsonView(Json.Base.class)
    private String address;

    @JsonView(Json.Base.class)
    private String number;

    @JsonView(Json.Base.class)
    private String neighborhood;

    @JsonView(Json.Base.class)
    private String complement;

    @JsonView(Json.Base.class)
    private String zipCode;

    @JsonView(Json.Base.class)
    private String city;

    @JsonView(Json.Base.class)
    private String state;

    @JsonView(Json.Base.class)
    private Instant lastModifyDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Location.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("active=" + active)
                .add("address='" + address + "'")
                .add("number='" + number + "'")
                .add("neighborhood='" + neighborhood + "'")
                .add("complement='" + complement + "'")
                .add("zipCode='" + zipCode + "'")
                .add("city='" + city + "'")
                .add("state='" + state + "'")
                .add("lastModifyDate=" + lastModifyDate)
                .toString();
    }
}
