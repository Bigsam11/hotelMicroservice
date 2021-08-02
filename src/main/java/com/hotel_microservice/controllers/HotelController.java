package com.hotel_microservice.controllers;


import com.hotel_microservice.models.Hotel;
import com.hotel_microservice.models.Room;
import com.hotel_microservice.request.HotelRequest;
import com.hotel_microservice.response.BaseResponse;
import com.hotel_microservice.services.HotelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/hotelmanager")
@RestController
@Slf4j
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping("/hotel/createHotel")
    public BaseResponse createHotel(@Valid @RequestBody HotelRequest hotel){
       BaseResponse<Hotel> hotelResponse = hotelService.createHotel(hotel);
        log.info("This service was called inside createHotel controller with response ::" + hotelResponse);
        return hotelResponse;
    }

    @GetMapping("/hotel/getAllHotels")
    public BaseResponse getAllHotel(){
       BaseResponse<List<Hotel>> hotelResponse = hotelService.getAllHotel();
        log.info("This service was called inside getAllHotel controller with response ::" + hotelResponse);
       return  hotelResponse;
    }

    @GetMapping("/hotel/getHotelById/{id}")
    public BaseResponse getHotelById(@PathVariable("id") Long Id){
        BaseResponse<Hotel> hotelResponse = hotelService.getHotelById(Id);
        log.info("This service was called inside getHotelById controller with response ::" + hotelResponse);
        return  hotelResponse;
    }

    @PutMapping("/hotel/updateHotel/{id}")
    public BaseResponse updateHotel(@PathVariable("id") Long id,@RequestBody HotelRequest hotelRequest){
        BaseResponse<Hotel> hotelResponse = hotelService.updateHotelDetails(id,hotelRequest);
        log.info("This service was called inside updateHotel controller with response ::" + hotelResponse);
        return  hotelResponse;
    }

    @GetMapping("/hotel/{star}/getAvailableRooms/{roomType}")
    public BaseResponse getAvailableRooms(@PathVariable("star") Double star,@PathVariable("roomType") String roomType){
        BaseResponse<Room> hotelResponse = hotelService.checkIfAvailable(star,roomType);
        log.info("This service was called inside makeReservations controller with response ::" + hotelResponse);
        return  hotelResponse;
    }



    @DeleteMapping("/hotel/softDeleteHotel/{id}")
    public BaseResponse softDeleteHotel(@PathVariable("id") Long id){
        BaseResponse<Hotel> hotelResponse = hotelService.deleteHotel(id);
        log.info("This service was called inside softDelete controller with response ::" + hotelResponse);
        return  hotelResponse;
    }

    @PutMapping("/hotel/reserveRoom/{roomId}")
    public BaseResponse reserveRoom(@PathVariable("roomId") Long id){
        BaseResponse<Room> hotelResponse = hotelService.makeReservation(id);
        log.info("This service was called inside reserveRoom controller with response ::" + hotelResponse);
        return  hotelResponse;
    }

    @DeleteMapping("/hotel/hardDeleteHotel/{id}")
    public ResponseEntity hardDeleteHotel(@PathVariable("id") Long id){
        ResponseEntity<?> hotelResponse = hotelService.removeHotelById(id);
        log.info("This service was called inside hardDelete controller with response ::" + hotelResponse);
        return  hotelResponse;
    }

}
