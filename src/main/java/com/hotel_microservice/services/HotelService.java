package com.hotel_microservice.services;

import com.hotel_microservice.config.ModelConfig;
import com.hotel_microservice.config.ResourceNotFoundException;
import com.hotel_microservice.models.Hotel;
import com.hotel_microservice.models.Room;
import com.hotel_microservice.repository.HotelRepository;
import com.hotel_microservice.repository.RoomRepository;
import com.hotel_microservice.request.HotelRequest;
import com.hotel_microservice.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    /*
    * While creating an hotel,there should be some values that are default.
    * 1.A building cannot be an hotel without having atleast a room,hence why i initialized a value
    * 2.An hotel cannot exist without having a rating
    * */
    @Autowired
    public HotelRepository hotelRepository;

    @Autowired
    private ModelConfig modelConfig;

    @Autowired
    private RoomRepository roomRepository;

    public BaseResponse<Hotel> createHotel(HotelRequest hotelRequest){
        try {
            Hotel hotel = modelConfig.HotelClass(hotelRequest);
            hotel.setIsDeleted(false);
            hotel.setHotelRooms(hotelRequest.getHotelRooms() == 0 ? 1L : hotelRequest.getHotelRooms());
            hotel.setHotelStar((hotelRequest.getHotelStar() == 0.0|| hotelRequest.getHotelStar() > 5.0) ? 1.0d : hotelRequest.getHotelStar());
            Hotel resp = hotelRepository.save(hotel);
            return new BaseResponse()
                    .setData(resp)
                    .setResponseCode(HttpStatus.CREATED.value())
                    .setResponseMessage(resp.getHotelName() + " details was successfully " + HttpStatus.CREATED.getReasonPhrase());

            }
        catch (Exception e){
            return  new BaseResponse()
                       .setData(null)
                       .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                       .setResponseMessage(e.getMessage());
            }

    }

    public BaseResponse<List<Hotel>> getAllHotel(){
        try {
            return hotelRepository.getAllHotels().map(hotel -> {
                return new BaseResponse()
                        .setData(hotel)
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("Hotels  were successfully retrieved");

            }).orElseThrow(() -> new ResourceNotFoundException("No hotel was not found"));

        }
        catch (Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
        }


    }


    public BaseResponse<Hotel> getHotelById(Long id) {
        try {
            return hotelRepository.getHotelById(id).map(hotel -> {
                return new BaseResponse()
                        .setData(hotel)
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("Hotel with Id ::: " + id + " details was successfully found");

            }).orElseThrow(() -> new ResourceNotFoundException("HotelID with id ::: " + id + " was not found"));

        }
        catch (Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
        }


    }


    public BaseResponse updateHotelDetails(Long id, HotelRequest hotelRequest) {
        try {
                return hotelRepository.getHotelById(id).map(hotel -> {
                    hotel.setHotelName(hotelRequest.getHotelName());
                    hotel.setHotelRooms(hotelRequest.getHotelRooms() == 0 ? 1L : hotelRequest.getHotelRooms());
                    hotel.setHotelStar(hotelRequest.getHotelStar() == 0|| hotelRequest.getHotelStar() > 5 ? 1d : hotelRequest.getHotelStar());
                    Hotel resp = hotelRepository.save(hotel);
                    return new BaseResponse()
                            .setData(resp)
                            .setResponseCode(HttpStatus.OK.value())
                            .setResponseMessage(resp.getHotelName() + " details was successfully modified");

                    }).orElseThrow(() -> new ResourceNotFoundException("HotelID with id ::: " + id + " was not found"));

        }
        catch (Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
            }

        }



        /*
        * In a realtime scenario,its not advised to fully delete data,so a soft delete is done instead
        *
        * */

    public BaseResponse deleteHotel(Long id) {
        try {
            return hotelRepository.getHotelById(id).map(hotel -> {
                hotel.setIsDeleted(true);
                hotelRepository.save(hotel);
                return new BaseResponse()
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("Hotel with an Id of " + id + "  was successfully deleted");
            }).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + id ));

        }
        catch (Exception e){
                return  new BaseResponse()
                        .setData(null)
                        .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setResponseMessage(e.getMessage());
        }

    }


    public ResponseEntity<?> removeHotelById(Long id) {
            return hotelRepository.findById(id).map(post -> {
                hotelRepository.delete(post);
                return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Hotel with id ::: " + id + " was not found"));

    }

    public BaseResponse checkIfAvailable(Double star, String roomType) {
        try{
            if(hotelRepository.findByStar(star) == null) {
                throw new ResourceNotFoundException(" No hotel with  " + star + " star was found");
            }

            return roomRepository.findByRoomType(roomType.trim()).map(room -> {
                return new BaseResponse()
                        .setData(room)
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("All available room were successfully retrieved");
            }).orElseThrow(() -> new ResourceNotFoundException("No available room with  was  found"));

        }
        catch(Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
        }
    }

    public BaseResponse<Room> makeReservation(Long id) {

        try {
            return roomRepository.getAvailableRoom(id).map(room -> {
                room.setIsReserved(true);
                Room resp = roomRepository.save(room);
                return new BaseResponse()
                        .setData(resp)
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("Room with room number " + room.getRoomNumber() + " has successfully been booked");

            }).orElseThrow(() -> new ResourceNotFoundException("Room with  " + id + " was not found"));

        }
        catch (Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
        }
    }
}
