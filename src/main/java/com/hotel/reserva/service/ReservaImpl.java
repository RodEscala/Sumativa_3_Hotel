package com.hotel.reserva.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hotel.reserva.api.request.ReservaUpdateRequest;
import com.hotel.reserva.exceptionhandler.DatabaseTransactionException;
import com.hotel.reserva.exceptionhandler.ResourceNotFoundException;
import com.hotel.reserva.model.Reserva;
import com.hotel.reserva.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaImpl implements  ReservaService{
    private final ReservaRepository reservaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReservaService.class);
    

    @Override
    public Reserva saveReserva(Reserva reservaToSave) {
        logger.info("Creando reserva con Request: {} - metodo saveReserva",reservaToSave);
        try {
            Reserva savedReserva = reservaRepository.save(reservaToSave);
            logger.info("Reserva creada satisfacotriamente. Reserva ID: {} - metodo saveReserva",savedReserva.getId());

            return savedReserva;
        } catch (Exception e) {
            logger.error("Error creando reserva - metodo saveReserva",e);

            throw new DatabaseTransactionException("Error creando reserva",e);
        }
    }

    @Override
    public Optional<Reserva> findReservaById(Long id){
        logger.info("Encontrando la reserva por ID {} - metodo findReservaById",id);
        return reservaRepository.findById(id);
    }

    @Override
    public List<Reserva> findAllReservas(){
        logger.info("Buscar todas las reservas - metodo findAllReservas");
        return reservaRepository.findAll();

    }

    @Override
    public void deleteReserva(Long id){
        logger.info("Borrar una reserva por el ID. {} - metodo deleteReserva",id);
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isEmpty()){
            logger.info("Reserva no encontrada. {} - metodo deleteReserva");
            throw new ResourceNotFoundException("Reserva no encontrada");
        }
        logger.info("Borrando reserva. - metodo deleteReserva");
        reservaRepository.deleteById(id);
        logger.info("Reserva borrada exitosamente. {} - metodo deleteReserva");
        
    }



    @Override 
    public Reserva updateReserva(Long id, ReservaUpdateRequest updateRequest){
        logger.info("actualizando reserva por el ID: {} - metodo updateReserva",id, updateRequest);
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada"));
        if (updateRequest.getHabitacion() != null){
            logger.info("actualizando Habitacion de reserva: {} - metodo updateReserva", updateRequest.getHabitacion());
            reserva.setHabitacion(updateRequest.getHabitacion());

        }
        if(updateRequest.getDisponibilidad() != null){
            logger.info("actualizando disponibilidad de reserva: {} - metodo updateReserva", updateRequest.getDisponibilidad());
            reserva.setDisponibilidad(updateRequest.getDisponibilidad());
        }
        Reserva updateReserva = reservaRepository.save(reserva);
        return updateReserva;
    }
}
