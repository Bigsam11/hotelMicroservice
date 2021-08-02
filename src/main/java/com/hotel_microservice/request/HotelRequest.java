package com.hotel_microservice.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel_microservice.models.AuditModel;
import com.hotel_microservice.models.Hotel;
import com.hotel_microservice.models.Room;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HotelRequest  {
    private String hotelName;
    private Double hotelStar;
    private Long hotelRooms;
}
