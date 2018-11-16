package com.team1.demo.Repository;

import com.team1.demo.Entity.Hotel;
import com.team1.demo.Entity.Reservation;
import com.team1.demo.Entity.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Iterable<Reservation> findByUser(Users user);
    Iterable<Reservation> findByHotel(Hotel hotel);
    Iterable<Reservation> findAll();
}
