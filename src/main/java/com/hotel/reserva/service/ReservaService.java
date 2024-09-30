package com.hotel.reserva.service;

import java.util.List;
import java.util.Optional;

import com.hotel.reserva.api.request.ReservaUpdateRequest;
import com.hotel.reserva.model.Reserva;

public interface ReservaService {
    Reserva saveReserva(Reserva reserva);
    Optional<Reserva> findReservaById(Long id);
    Reserva updateReserva(Long id,ReservaUpdateRequest updateRequest);
    void deleteReserva(Long id);
    List<Reserva> findAllReservas();

}
