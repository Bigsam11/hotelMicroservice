package com.hotel_microservice.repository;

import com.hotel_microservice.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query(value = "SELECT u FROM Room u WHERE u.isDeleted = false")
    List<Room> getAllRoom();

    @Query(value = "SELECT u FROM Room u WHERE u.isDeleted = false AND u.id = :id ")
    Optional<Object> getRoomById(Long id);

    @Query(value = "SELECT u FROM Room u WHERE u.isDeleted = false AND u.roomType = :roomType AND u.isReserved = false ")
    Optional<List<Room>> findByRoomType(String roomType);

    @Query(value = "SELECT u FROM Room u WHERE u.isDeleted = false  AND u.isReserved = false AND u.id =:id ")
    Optional<Room> getAvailableRoom(Long id);


    @Query(value = "SELECT u FROM Room u WHERE u.isDeleted = false")
    Optional<List<Room>> getAllRooms();
}
