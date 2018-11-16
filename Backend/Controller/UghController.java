package com.team1.demo.Controller;

import com.team1.demo.Entity.Hotel;
import com.team1.demo.Entity.Users;
import com.team1.demo.Services.HotelService;
import com.team1.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.soap.SOAPBinding;

@Controller
public class UghController {

    private UserService userService;
    private HotelService hotelService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UghController(UserService userService, HotelService hotelService,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.hotelService = hotelService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public void insertSomething() {
        //Users user = new Users("janedoe", bCryptPasswordEncoder.encode("pass"), "Jane", "Doe", 12.123, 13.134, "ROLE_USER");
        //Users admin = new Users("admin", bCryptPasswordEncoder.encode("adminpass"), "Administrator", "McAdministrator",0.00, 0.00, "ROLE_ADMIN");

       /* userService.save(user);
       userService.save(admin);
        Users supervisor = new Users("super", bCryptPasswordEncoder.encode("superpass"), "Supervisor", "McSupervisor",0.00, 0.00, "ROLE_SUPERVISOR");
        userService.save(supervisor);
        Hotel hotel = new Hotel("Hotel Bosnia", "Hotel se nalazi u samom centru grada, u neposrednoj blizini starog dijela grada, Baščaršije. Naš hotel u svojoj ponudi ima 72 komforne i moderno opremljene sobe. Hotel Bosnia obezbjeđuje i usluge banket i ketering servisa u nekoj od naših sala.", "Sarajevo");
        hotelService.save(hotel);*/

    }
}
