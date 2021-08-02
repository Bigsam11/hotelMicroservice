package com.hotel_microservice.services;

import com.hotel_microservice.config.ModelConfig;
import com.hotel_microservice.config.ResourceNotFoundException;
import com.hotel_microservice.models.Hotel;
import com.hotel_microservice.models.Room;
import com.hotel_microservice.repository.HotelRepository;
import com.hotel_microservice.repository.RoomRepository;
import com.hotel_microservice.request.RoomRequest;
import com.hotel_microservice.response.BaseResponse;
import com.hotel_microservice.response.SummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public HotelRepository hotelRepository;

    @Autowired
    private ModelConfig modelConfig;


    public BaseResponse<Hotel> createRoom(Long hotelId, RoomRequest roomRequest) {
        try {
            return hotelRepository.getHotelById(hotelId).map(hotel -> {
                Room roomResp = modelConfig.RoomClass(roomRequest);
                roomResp.setHotel(hotel);
                roomResp.setIsDeleted(false);
                roomResp.setIsReserved(false);
                Room response = roomRepository.save(roomResp);
                return new BaseResponse()
                        .setData(response)
                        .setResponseCode(HttpStatus.CREATED.value())
                        .setResponseMessage("Room with Id:: "+response.getId() + "details was successfully saved");

            }).orElseThrow(() -> new ResourceNotFoundException("HotelID " + hotelId + " was not found"));

        }
        catch (Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
        }
    }

    public BaseResponse<List<Hotel>> getAllRooms() {
        try {
            return roomRepository.getAllRooms().map(hotel -> {
                return new BaseResponse()
                        .setData(hotel)
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("Rooms  were successfully retrieved");

            }).orElseThrow(() -> new ResourceNotFoundException("No room was  found"));

        }
        catch (Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
        }
    }

    public BaseResponse<Room> getRoomById(Long id) {
        try {
            return roomRepository.getRoomById(id).map(room -> {
                return new BaseResponse()
                        .setData(room)
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("Room with Id:: " + id + "  was successfully retrieved");

            }).orElseThrow(() -> new ResourceNotFoundException("Room with " + id + " was not found"));

        }
        catch (Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
        }
    }

    public BaseResponse<Room> updateRoom(Long roomId, RoomRequest roomRequest) {
        try {
            return roomRepository.findById(roomId).map(room -> {

                Room resp = roomRepository.save(room);
                return new BaseResponse()
                        .setData(resp)
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("Room with Id:: " + roomId + "  was successfully modified");
            }).orElseThrow(() -> new ResourceNotFoundException("RoomId " + roomId + " was not found"));

        }
        catch(Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
            }

    }

    public BaseResponse<Room> softDeleteRoom(Long roomId) {
        try {
            return roomRepository.findById(roomId).map(room -> {

                room.setIsDeleted(true);
                Room resp = roomRepository.save(room);
                return new BaseResponse()
                        .setData(resp)
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("Room with Id:: " + roomId + "  was successfully deleted");
            }).orElseThrow(() -> new ResourceNotFoundException("RoomId " + roomId + " was not found"));

        }
        catch(Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
        }

    }

    public ResponseEntity<?> removeRoomById(Long hotelId,Long roomId) {
        return roomRepository.findById(roomId).map(room -> {
            roomRepository.delete(room);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Room with id ::: " + roomId + " was not found"));

    }

    public BaseResponse getAllRoomSummary() {
        try {

                List<Room> room = roomRepository.getAllRoom();

                SummaryResponse summaryResponse = new SummaryResponse();
                summaryResponse.setTotalRooms(room);

                for (Room item:room) {
                    if(item.getIsReserved() == false){
                        summaryResponse.availableRooms.add(item);
                    }
                    else if(item.getIsReserved() == true){
                        summaryResponse.reservedRooms.add(item);
                    }
                }
                summaryResponse.setAvailableRoomsNum(summaryResponse.getAvailableRooms().size());
                summaryResponse.setReservedRoomsNum(summaryResponse.getReservedRooms().size());
                summaryResponse.setTotalRoomsNum(summaryResponse.getTotalRooms().size());
                return new BaseResponse()
                        .setData(summaryResponse)
                        .setResponseCode(HttpStatus.OK.value())
                        .setResponseMessage("Summary successfully retrieved");

        }
        catch(Exception e){
            return  new BaseResponse()
                    .setData(null)
                    .setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .setResponseMessage(e.getMessage());
        }

    }
}
