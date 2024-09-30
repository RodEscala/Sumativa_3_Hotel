package com.hotel.reserva.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.reserva.api.request.ReservaUpdateRequest;
import com.hotel.reserva.model.Reserva;
import com.hotel.reserva.service.ReservaService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/reserva")
@RequiredArgsConstructor
public class ReservaController {

    private static final Logger logger = LoggerFactory.getLogger(ReservaController.class);

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<EntityModel<Reserva>> createReserva(@RequestBody Reserva reserva) {
        logger.info("Creando un reserva con request: {} ",reserva);
        Reserva savedReserva = reservaService.saveReserva(reserva);
        logger.info("Reserva creada con request: {} ",savedReserva.getId());
        // return new ResponseEntity<>(savedReserva,HttpStatus.CREATED);

        EntityModel<Reserva> reservaModel = EntityModel.of(savedReserva);
        reservaModel.add(linkTo(methodOn(ReservaController.class).getReservaById(savedReserva.getId())).withSelfRel());
    
        return ResponseEntity.created(
            linkTo(methodOn(ReservaController.class).getReservaById(savedReserva.getId())).toUri())
            .body(reservaModel);
    }
        
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Reserva>> getReservaById(@PathVariable Long id) {
        logger.info("Creando un reserva con request: {} ",id);
        Optional<Reserva> reserva = reservaService.findReservaById(id);
        
        if (reserva.isPresent()) {
            EntityModel<Reserva> reservaModel = EntityModel.of(reserva.get());
            reservaModel.add(linkTo(methodOn(ReservaController.class).getReservaById(id)).withSelfRel());

            return ResponseEntity.ok(reservaModel);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        
        // return reserva.map(value -> {
        //     logger.info("Reserva encontrada por el ID {}",id);
        //     return new ResponseEntity<>(value,HttpStatus.OK);
        // }).orElseGet(() -> {
        //     logger.info("Reserva no encontrada por el id: {}",id);
        //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // });

    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<Reserva>>> getAllReservas() {
        logger.info("Obteniendo todas las reservas: {} ");
        // List<Reserva> reservas = reservaService.findAllReservas();
        
        List<EntityModel<Reserva>> reservas = reservaService.findAllReservas().stream()
                .map(reserva -> {
                    EntityModel<Reserva> reservaModel = EntityModel.of(reserva);
                    reservaModel.add(linkTo(methodOn(ReservaController.class).getReservaById(reserva.getId())).withSelfRel());
                    return reservaModel;
                }).toList();
        logger.info("Encontro {} Reservas  ",reservas.size());
        


        return ResponseEntity.ok(CollectionModel.of(reservas,
                linkTo(methodOn(ReservaController.class).getAllReservas()).withSelfRel()));
        // return new ResponseEntity<>(reservas,HttpStatus.OK);
        

    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Reserva>> updateReserva(@PathVariable Long id, @RequestBody ReservaUpdateRequest updateRequest) {
        logger.info("actualizando un reserva por el ID: {} y request {}",id,updateRequest);
        Reserva updateReserva = reservaService.updateReserva(id, updateRequest);
        logger.info("Reserva actualizada exitosamente. Reserva ID: {} ",updateReserva.getId());
      
        EntityModel<Reserva> reservaModel = EntityModel.of(updateReserva);
        reservaModel.add(linkTo(methodOn(ReservaController.class).getReservaById(id)).withSelfRel());

      
        // return new ResponseEntity<>(updateReserva,HttpStatus.OK);
    
        return ResponseEntity.ok(reservaModel);
    }


    
    @DeleteMapping("/{id}")
    public ResponseEntity<Reserva> deleteReserva(@PathVariable Long id) {
        logger.info("Eliminando una reserva por el ID: {} y request {}",id);
        reservaService.deleteReserva(id);
        logger.info("Reserva eliminada exitosamente. Reserva ID: {} ",id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

