package com.hotel.reserva.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name="reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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





    //Join a otra tabla
    // Uno a muchos
    // @OneToMany
    // Uno a uno
    // @OneToOne
    // Etc
    // por ejemplo seria de esta forma para una 
    // tabla(imaginaria)

    // se explica solo.
    // @ManyToOne(cascade = CascadeType.All)
    // @JoinColumn(name = "hotel_id",referencedColumnName="id")
    // private Hotel hotel;

    //de esta forma seria en la otra tabla
    // @OneToMany(mappedBy = "Hotel",cascade = CascadeType.ALL, FetchType.LAZY)
    // @JsonIgnore
    // @private List<Reserva> reservas


}

