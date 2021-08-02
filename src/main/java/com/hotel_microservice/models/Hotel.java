package com.hotel_microservice.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel extends AuditModel {

    @Id
    @Column(name = "hotel_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hotel Name is mandatory")
    private String hotelName;

    @NotNull
    @Max(value=5,message = "You can't assign more than 5 stars")
    private Double hotelStar;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Room> room = new HashSet<>();

    @NotNull
    private Long hotelRooms;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted = false;


}
