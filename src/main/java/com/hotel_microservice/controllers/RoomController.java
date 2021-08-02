package com.hotel_microservice.controllers;

import com.hotel_microservice.models.Hotel;
import com.hotel_microservice.models.Room;
import com.hotel_microservice.request.HotelRequest;
import com.hotel_microservice.request.RoomRequest;
import com.hotel_microservice.response.BaseResponse;
import com.hotel_microservice.response.SummaryResponse;
import com.hotel_microservice.services.HotelService;
import com.hotel_microservice.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/api/v1/roommanager")
@RestController
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @PostMapping("/hotel/{hotelId}/room/createRoom")
    public BaseResponse createHotel(@PathVariable("hotelId") Long hotelId, @Valid @RequestBody RoomRequest roomRequest){
        BaseResponse<Hotel> hotelResponse = roomService.createRoom(hotelId,roomRequest);
        return hotelResponse;
    }

    @GetMapping("/room/getAllRooms")
    public BaseResponse getAllRooms(){
        BaseResponse<List<Hotel>> hotelResponse = roomService.getAllRooms();
        return  hotelResponse;
    }

    @GetMapping("/room/getAllRoomSummary")
    public BaseResponse getAllRoomSummary(){
        BaseResponse<Room> roomResponse = roomService.getAllRoomSummary();
        return  roomResponse;
    }

    @GetMapping("/room/getRoomById/{id}")
    public BaseResponse getHotel(@PathVariable("id") Long Id){
        BaseResponse<Room> roomResponse = roomService.getRoomById(Id);
        return  roomResponse;
    }

    @PutMapping("/room/{roomId}")
    public BaseResponse updateRoom(@PathVariable("roomId") Long roomId,@RequestBody RoomRequest roomRequest ){
        BaseResponse<Room> roomResponse = roomService.updateRoom(roomId,roomRequest);
        return  roomResponse;
    }

    @DeleteMapping("/room/softDeleteRoom/{roomId}")
    public BaseResponse softDeleteRoom(@PathVariable("roomId") Long roomId){
        BaseResponse<Room> roomResponse = roomService.softDeleteRoom(roomId);
        return  roomResponse;
    }

    @DeleteMapping("/room/hardDeleteRoom/{roomId}")
    public BaseResponse hardDeleteRoom(@PathVariable("roomId") Long roomId){
        BaseResponse<Room> roomResponse = roomService.softDeleteRoom(roomId);
        return  roomResponse;
    }
}
