package com.team1.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationID;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    Users user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotelid", nullable = false)
    Hotel hotel;

    private double userLongitude;
    private double userLatitude;
    private double hotelLongitude;
    private double hotelLatitude;

    public double getHotelLongitude() {
        return hotelLongitude;
    }

    public void setHotelLongitude(double hotelLongitude) {
        this.hotelLongitude = hotelLongitude;
    }

    public double getHotelLatitude() {
        return hotelLatitude;
    }

    public void setHotelLatitude(double hotelLatitude) {
        this.hotelLatitude = hotelLatitude;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }



    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }


    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }



    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }
}
