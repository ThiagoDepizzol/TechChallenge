package com.tech.challenge.TechChallenge.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.tech.challenge.TechChallenge.domain.enumeration.UserType;
import com.tech.challenge.TechChallenge.domain.generic.DefaultEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

@Getter
@Setter
@Entity
@Table(name = "usr_users_authorities")
public class UserAuthority extends DefaultEntity implements Serializable {

    public interface Json {

        interface Base extends DefaultEntity.Json.Base {
        }

        interface WithUser extends Base, User.Json.Base {
        }


        interface All extends Base {
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Json.Base.class)
    private Long id;


    @NotNull
    @JoinColumn(name = "usr_user_id")
    @JsonIgnoreProperties(value = "authorities", allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonView(Json.WithUser.class)
    private User user;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonView(Json.Base.class)
    private UserType type = UserType.CUSTOMER;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthority user = (UserAuthority) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public boolean hasId() {
        return Objects.nonNull(getId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserAuthority.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                //                .add("user=" + user)
                .add("type=" + type)
                .toString();
    }
}
