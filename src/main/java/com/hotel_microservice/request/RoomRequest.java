package com.hotel_microservice.request;


import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoomRequest {
    private String roomType;
    private Integer roomNumber;
    private Boolean isReserved;

}
