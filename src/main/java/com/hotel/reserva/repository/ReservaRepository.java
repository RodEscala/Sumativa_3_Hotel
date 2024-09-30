package com.hotel.reserva.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.reserva.model.Reserva;



public interface  ReservaRepository extends JpaRepository<Reserva, Long>{

    
}