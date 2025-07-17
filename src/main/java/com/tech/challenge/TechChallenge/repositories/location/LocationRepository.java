package com.tech.challenge.TechChallenge.repositories.location;

import com.tech.challenge.TechChallenge.domain.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
