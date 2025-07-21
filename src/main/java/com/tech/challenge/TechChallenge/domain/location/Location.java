package com.tech.challenge.TechChallenge.domain.location;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Active não pode ser null")
    @JsonView(Json.Base.class)
    private Boolean active;

    @Schema(description = "Endereço da localização")
    @JsonView(Json.Base.class)
    private String address;

    @Schema(description = "Número da localização")
    @JsonView(Json.Base.class)
    private String number;

    @Schema(description = "Bairro da localização")
    @JsonView(Json.Base.class)
    private String neighborhood;

    @Schema(description = "Complemento da descrição")
    @JsonView(Json.Base.class)
    private String complement;

    @Schema(description = "CEP da localização")
    @JsonView(Json.Base.class)
    private String zipCode;

    @Schema(description = "Cidade da localização")
    @JsonView(Json.Base.class)
    private String city;

    @Schema(description = "Estado da localização")
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
