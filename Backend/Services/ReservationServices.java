package com.team1.demo.Services;

import com.team1.demo.Entity.Hotel;
import com.team1.demo.Entity.Reservation;
import com.team1.demo.Entity.Users;
import com.team1.demo.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Iterable<Reservation> findByUser(Users user) {
        return reservationRepository.findByUser(user);
    }
    public Iterable<Reservation> findByHotel(Hotel hotel) { return reservationRepository.findByHotel(hotel); }

    public void save(Reservation reservation) {
        reservationRepository.save(reservation);
    }

    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

    public Iterable<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}
