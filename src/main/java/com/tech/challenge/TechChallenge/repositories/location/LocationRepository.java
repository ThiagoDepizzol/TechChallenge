package com.tech.challenge.TechChallenge.repositories.location;

import com.tech.challenge.TechChallenge.domain.location.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(nativeQuery = true, //
            value = "select locations.* " +//
                    "from loc_locations locations " +//
                    "where locations.active = true ")
    Page<Location> findAllActive(Pageable pageable);

    @Query(nativeQuery = true, //
            value = "select locations.* " +//
                    "from loc_locations locations " +//
                    "where locations.active = true " +//
                    "  and locations.id = :locationId " +//
                    "limit 1")
    Optional<Location> findOneActiveById(@Param("locationId") Long locationId);

    @Query(nativeQuery = true, //
            value = "select locations.* " +//
                    "from loc_locations locations " +//
                    "where locations.zip_code = :zipCode " +//
                    "limit 1 ")
    Optional<Location> findOneByZipCode(@Param("zipCode") String zipCode);

}
