package com.hotel_microservice.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel_microservice.models.Room;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class SummaryResponse {
    public Integer responseCode;
    public String responseMessage;
    public List<Room> reservedRooms = new ArrayList<>();
    public List<Room> totalRooms;
    public List<Room> availableRooms = new ArrayList<>();
    public Integer availableRoomsNum;
    public Integer reservedRoomsNum;
    public Integer totalRoomsNum;


}
