package ru.javawebinar.topjavagraduation.repository.datajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjavagraduation.model.Restaurant;

import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
public interface IJpaRestaurantRepository extends IJpaManagedEntityRepository<Restaurant> {

    @Query("SELECT r from Restaurant r WHERE r.address=:address")
    List<Restaurant> findByAddress(@Param("address") String address);

    @Query("SELECT r from Restaurant r WHERE r.name=:name AND r.address=:address")
    Optional<Restaurant> findByNameAndAddress(@Param("name") String name, @Param("address") String address);
}
