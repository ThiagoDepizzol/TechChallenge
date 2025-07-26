package com.tech.challenge.TechChallenge.domain.generic;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.StringJoiner;

@Getter
@Setter
@MappedSuperclass
public abstract class DefaultEntity implements Serializable {

    public interface Json {
        interface Base {
        }
    }

    @NotNull
    @Column(nullable = false)
    @JsonView(Json.Base.class)
    private Boolean active = true;

    @JsonView(Json.Base.class)
    private Instant lastModifyDate = Instant.now();

    public DefaultEntity() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DefaultEntity.class.getSimpleName() + "[", "]")
                .add("active=" + active)
                .add("lastModifyDate=" + lastModifyDate)
                .toString();
    }
}
