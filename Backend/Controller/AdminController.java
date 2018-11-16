package com.team1.demo.Controller;

import com.team1.demo.Entity.Hotel;
import com.team1.demo.Entity.Reservation;
import com.team1.demo.Entity.Users;
import com.team1.demo.Services.HotelService;
import com.team1.demo.Services.ReservationService;
import com.team1.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    private UserService userService;
    private HotelService hotelService;
    private ReservationService reservationService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AdminController(UserService userService, HotelService hotelService, ReservationService reservationService, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userService = userService;
        this.hotelService = hotelService;
        this.reservationService = reservationService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/adduserpage")
    public String showAddUserPage(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "views/adduserpage";
    }

    @RequestMapping(value = "/addhotelpage")
    public String showAddHotelPage(Model model) {
        Hotel hotel = new Hotel();
        model.addAttribute("hotel", hotel);
        return "views/addhotelpage";
    }

    //  LISTS
//  shows a page with list of users
    //  @RequestParam(name = "searchInput", required = false) String searchInput
    @RequestMapping(value = "/userslistpage")
    public String showUsersListPage(Model model) {
        Iterable<Users> usersList = userService.findAll();
        model.addAttribute("usersList", usersList);
        model.addAttribute("user", new Users());
        return "views/userslistpage";
    }

    //  shows a page with list of hotels
    @RequestMapping(value = "/hotelslistpage")
    public String showHotelsListPage(Model model) {
        Iterable<Hotel> hotelsList = hotelService.findAll();
        model.addAttribute("hotelsList", hotelsList);
        model.addAttribute("hotel", new Hotel());
        return "views/hotelslistpage";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUser(@Valid Users user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
           List<FieldError> fieldErrors = bindingResult.getFieldErrors();
           String result = "";

           for (int i = 0; i <  fieldErrors.size() - 1; i++) {
               result += fieldErrors.get(i).getField();
               result += ", ";
           }
           result += fieldErrors.get(fieldErrors.size() - 1).getField();
           if(fieldErrors.size() > 1)
               redirectAttributes.addFlashAttribute("failMessage", "Something went wrong! " + result + " fields are not valid. User not added.");
            else
               redirectAttributes.addFlashAttribute("failMessage", "Something went wrong! " + result + " field is not valid. User not added.");

            return "redirect:/admin";

        }
        else
        {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.save(user);
            redirectAttributes.addFlashAttribute("successMessage", "User added!");
            return "redirect:/userslistpage" ;
        }
        //return "";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.GET)
    public String userAddedResult() {
        return "admin";
    }

    @RequestMapping(value = "/addhotel", method = RequestMethod.POST)
    public String addHotel(@Valid Hotel hotel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            //return "redirect:/userpanel";
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String result = "";

            for (int i = 0; i <  fieldErrors.size() - 1; i++) {
                result += fieldErrors.get(i).getField();
                result += ", ";
            }
            result += fieldErrors.get(fieldErrors.size() - 1).getField();
            if(fieldErrors.size() > 1)
                redirectAttributes.addFlashAttribute("failMessage", "Something went wrong! " + result + " fields are not valid. Hotel not added.");
            else
                redirectAttributes.addFlashAttribute("failMessage", "Something went wrong! " + result + " field is not valid. Hotel not added.");
            return "redirect:/hotelslistpage";

        }
        else
        {
            hotelService.save(hotel);
            redirectAttributes.addFlashAttribute("successMessage", "Hotel added!");
            return "redirect:/hotelslistpage" ;
        }

    }

    @RequestMapping(value = "/addhotel", method = RequestMethod.GET)
    public String hotelAddedResult() {
        return "admin";
    }

    @RequestMapping(value = "/userslist", method = RequestMethod.GET)
    public String listUsers(Model model) {
        Iterable<Users> userslist = userService.findAll();
        model.addAttribute("users", userslist);
        return "views/userslist";
    }

    @RequestMapping(value = "/edit/user/{id}")
    public String editUser(Model model , @PathVariable("id") String id) {
          Users user = userService.findById(Long.parseLong(id));
     //     System.out.println(user.getUserID());
          model.addAttribute("user", user);
        //System.out.println("post pakovanje " + user.getUserID() + " " + user.getFirstName());
       // userService.delete(user);
        return "views/editprofile";

    }

    @RequestMapping(value = "/edit/user/{id}", method = RequestMethod.POST)

    public String editUserProfile(@PathVariable("id") Long id, @RequestParam Map params, RedirectAttributes redirectAttributes) {
        Users user = userService.findOne(id).get();

        if(user == null) {
            redirectAttributes.addFlashAttribute("failMessage", "Error updating user!");

        }
        else {

            /*userService.updateUser(params.get("firstName").toString(),
                                   params.get("lastName").toString(),
                                   params.get("username").toString(),
                                   Double.parseDouble(params.get("longitude").toString()),
                                   Double.parseDouble(params.get("latitude").toString()),
                                   params.get("role").toString(),
                                   params.get("email").toString(),
                                   id);*/
            user.setFirstName(params.get("firstName").toString());
            user.setLastName(params.get("lastName").toString());
            user.setUsername(params.get("username").toString());
            user.setLongitude(Double.parseDouble(params.get("longitude").toString()));
            user.setLatitude(Double.parseDouble(params.get("latitude").toString()));
            user.setRole(params.get("role").toString());
            user.setEmail(params.get("email").toString());
            userService.save(user);

            redirectAttributes.addFlashAttribute("successMessage", "User updated!");
            return "redirect:/userslistpage";
        }

        return "";
    }

    @RequestMapping(value = "/deleteuser/{id}")
    public String deleteUser(Model model , @PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        if(id == null) {
            redirectAttributes.addFlashAttribute("failMessage", "Error deleting user!");
        }
        else {
            Users user = userService.findById(Long.parseLong(id));
            List<Reservation> reservations = (List<Reservation>) reservationService.findByUser(user);
            for (Reservation reservation : reservations) {
                reservationService.delete(reservation);
            }

            userService.delete(user);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted!");
            return "redirect:/userslistpage";
        }
        return "";

    }


    @RequestMapping(value = "/deletehotel/{id}")
    public String deleteHotel(@PathVariable("id") String id, RedirectAttributes redirectAttributes) {
        if(id == null) {
            redirectAttributes.addFlashAttribute("failMessage", "Error deleting hotel!");
        } else {
            Hotel hotel = hotelService.findByHotelId(Long.parseLong(id));
            List<Reservation> reservations = (List<Reservation>) reservationService.findByHotel(hotel);
            for (Reservation reservation: reservations) {
                reservationService.delete(reservation);
            }
            hotelService.delete(hotel);
            redirectAttributes.addFlashAttribute("successMessage", "Hotel deleted!");
            return "redirect:/hotelslistpage";
        }
        return "";

    }

   @RequestMapping(value = "/gethotel/{id}")
    @ResponseBody
    public Hotel getHotelInfo(@PathVariable("id") String id) {
        Hotel hotel = hotelService.findByHotelId(Long.parseLong(id));
        if(hotel != null) {
            return hotel;
        }
        else {
            return new Hotel();
        }

    }

    //updatelonglat
    @RequestMapping(value = "/updatelonglat/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String getTest(@PathVariable("id") String id, @RequestBody Object data) {
        Users user = userService.findById(Long.parseLong(id));
        //double longitude = Double.parseDouble(data)
        System.out.println(data);
        return "";
    }
    @RequestMapping(value = "/edit/hotel/{id}")

    public String editHotel(Model model , @PathVariable("id") String id) {
        Hotel hotel = hotelService.getOne(Long.parseLong(id));
        model.addAttribute("hotel", hotel);
        return "views/edithotel";

    }

    @RequestMapping(value = "/edit/hotel/{id}", method = RequestMethod.POST)
    public String editHotelInfo(@PathVariable("id") Long id, @RequestParam Map params, RedirectAttributes redirectAttributes) {
        if(id == null) {
            // error
            redirectAttributes.addFlashAttribute("failMessage", "Error updating hotel!");
        }
        else {
            hotelService.updateHotel(params.get("name").toString(),
                    params.get("description").toString(),
                    params.get("location").toString(),
                    params.get("address").toString(),
                    Double.parseDouble(params.get("longitude").toString()),
                    Double.parseDouble(params.get("latitude").toString()),
                    id);
            redirectAttributes.addFlashAttribute("successMessage", "Hotel updated");
            return "redirect:/hotelslistpage";
        }

        return "";
    }

    @RequestMapping(value = "/searchuser", method = RequestMethod.POST)
    public String searchUserByUsername(@RequestParam Map params, RedirectAttributes redirectAttributes, Model model) {
        List<Users> list = new ArrayList<>();
        Users user = userService.findByUsername(params.get("searchInput").toString());
        list.add(user);

        if(list != null) {
            model.addAttribute("usersList", list);

            return "redirect:/userslistpage";
        }
        else {
            redirectAttributes.addAttribute("failMessage", "User not found");
            return "redirext:/userslistpage";
        }


    }
}
