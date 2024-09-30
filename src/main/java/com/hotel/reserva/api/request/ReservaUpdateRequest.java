package com.hotel.reserva.api.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ReservaUpdateRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="reserva_seq")
    @SequenceGenerator(name = "reserva_seq",sequenceName="reserva_seq",allocationSize=1,initialValue=1)
    private Long id;

    @NotNull
    @Column(unique=true)
    @Size(min=1, max=30 )
    @Pattern(regexp = "[A-Za-z0-9 ]+")
    @NotBlank(message= "El campo habitacion puede estar vacio")
    private String habitacion;

    @NotNull
    @Column
    @Size(min = 1, max = 30)
    @Pattern(regexp = "[A-Za-z0-9 ]+")
    @NotBlank(message ="El campo disponibilidad no puede estar vacio")
    private String disponibilidad;

}