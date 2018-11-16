package com.team1.demo.Controller;

import com.team1.demo.Entity.Hotel;
import com.team1.demo.Entity.Reservation;
import com.team1.demo.Services.HotelService;
import com.team1.demo.Services.ReservationService;
import com.team1.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SupervisorController {
    private UserService userService;
    private HotelService hotelService;
    private ReservationService reservationService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SupervisorController(UserService userService, HotelService hotelService, ReservationService reservationService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.hotelService = hotelService;
        this.reservationService = reservationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*@RequestMapping(value = "/getreservations", method = RequestMethod.GET)
    @ResponseBody
    public List<Hotel> getReservationsList() {
        List<Hotel> reservations = (List<Hotel>) hotelService.findAll();
        if(reservations != null) {
            return reservations;
        }
        else {
            return null;
        }
    }*/

    @RequestMapping(value = "/getreservations", method = RequestMethod.GET)
    @ResponseBody
    public List<Reservation> getReservationsList() {
        List<Reservation> reservations = (List<Reservation>) reservationService.findAll();
        if(reservations != null) {
            return reservations;
        }
        else {
            return null;
        }
    }
}
