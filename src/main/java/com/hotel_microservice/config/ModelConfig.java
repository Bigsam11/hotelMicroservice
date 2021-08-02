package com.hotel_microservice.config;

import com.hotel_microservice.models.Hotel;
import com.hotel_microservice.models.Room;
import com.hotel_microservice.request.HotelRequest;
import com.hotel_microservice.request.RoomRequest;
import org.springframework.stereotype.Service;

@Service
public class ModelConfig {

    public Hotel HotelClass(HotelRequest hotelRequest){
        Hotel hotel = new Hotel();
        hotel.setHotelRooms(hotelRequest.getHotelRooms());
        hotel.setHotelName(hotelRequest.getHotelName());
        hotel.setHotelRooms(hotelRequest.getHotelRooms());
        hotel.setHotelStar(hotelRequest.getHotelStar());
        return  hotel;
    }


    public Room RoomClass(RoomRequest roomRequest){
        Room room = new Room();
        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setRoomType(roomRequest.getRoomType());
        room.setIsReserved(roomRequest.getIsReserved());
        return  room;
    }


}
