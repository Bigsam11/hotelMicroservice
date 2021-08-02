package com.hotel_microservice.repository;

import com.hotel_microservice.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long> {


    @Query(value = "SELECT u FROM Hotel u WHERE u.isDeleted = false ")
    Optional<List<Hotel>> getAllHotels();

    @Query(value = "SELECT u FROM Hotel u WHERE u.isDeleted = false AND u.id = :id ")
    Optional<Hotel> getHotelById(@Param("id")  Long id);

    @Query(value = "SELECT u FROM Hotel u WHERE u.isDeleted = false AND u.hotelStar = :star ")
    boolean existsByStar(Double star);

    @Query(value = "SELECT u FROM Hotel u WHERE u.isDeleted = false AND u.hotelStar = :star ")
    Optional<List<Hotel>> findByStar(Double star);
}
