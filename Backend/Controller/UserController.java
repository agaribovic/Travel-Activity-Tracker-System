package com.team1.demo.Controller;


import com.team1.demo.Entity.Hotel;
import com.team1.demo.Entity.Reservation;
import com.team1.demo.Entity.Users;
import com.team1.demo.Services.HotelService;
import com.team1.demo.Services.ReservationService;
import com.team1.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    private UserService userService;
    private HotelService hotelService;
    private ReservationService reservationService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, HotelService hotelService, ReservationService reservationService) {

        this.userService = userService;
        this.hotelService = hotelService;
        this.reservationService = reservationService;
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String redirectToPanel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        //System.out.println(name);
        if (authentication.getAuthorities().toString().contains("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        else if (authentication.getAuthorities().toString().contains("ROLE_USER")) {
           // authentication.getPrincipal().toString();

            Users user = userService.findByUsername(name);

            if(user == null) {
                // can't log in
            }
            return "redirect:/userpanel/" + user.getUserID();
        }
        else if (authentication.getAuthorities().toString().contains("ROLE_SUPERVISOR")) {
         //   System.out.println("super");
            return "redirect:/supervisor";
        }
        return "/login";
    }

    @RequestMapping(value = "/login")
    public String showLoginView(Users user, Model model) {
        //System.out.println("ugh");
        model.addAttribute("user",user);
        return "auth/loginregister";
    }

    @RequestMapping(value = "/")
    public String showStartPage(){
        return "views/index";
    }



    @RequestMapping(value = "/admin")
    public String showAdminPanelView(Model model) {
        //System.out.println("ugh");
        Users user = new Users();
        Hotel hotel = new Hotel();
        Iterable<Users> usersList = userService.findAll();
        Iterable<Hotel> hotelsList = hotelService.findAll();
        // ovo ne smije biti postavljano ovako, podesiti kad se mapa implementira
        user.setLongitude(0);
        user.setLatitude(0);
        hotel.setLatitude(0);
        hotel.setLongitude(0);
        model.addAttribute("user", user);
        model.addAttribute("hotel", hotel);
        model.addAttribute("usersList", usersList);
        model.addAttribute("hotelsList", hotelsList);
        return "views/admin";
    }

    @RequestMapping(value = "/userpanel")
    public String showUserPanelView() {
        //System.out.println("ugh");
        return "views/userpanel";
    }

    @RequestMapping(value = "/supervisor")
    public String showSupervisorPanelView(){
        return "views/supervisor";
    }

    @RequestMapping(value = "/userpanel/{id}")
    public String showUserPanel(Model model, @PathVariable("id") String id) {
        Users user = userService.findById(Long.parseLong(id));

        if(user == null) {

            return "redirect:/?error=user-is-null";
        }
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        Iterable<Hotel> hotels = hotelService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("hotels", hotels);
        model.addAttribute("reservation", reservation);
        model.addAttribute("history", reservationService.findByUser(user));
        // pakuje varijable
        return "views/userpanel";
    }

    @RequestMapping(value = "/aboutus")
    public String showAboutUsPag(){
        return "views/aboutuspage";
    }

    @RequestMapping(value = "/contactus")
    public String showContactUsPag(){
        return "views/contactuspage";
    }
}
