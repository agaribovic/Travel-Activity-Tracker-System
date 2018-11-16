package com.team1.demo.Controller;

import com.team1.demo.Entity.Users;
import com.team1.demo.Services.EmailService;
import com.team1.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class RegistrationController {
    private UserService userService;
    private EmailService emailService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationController(UserService userService, EmailService emailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*@RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registerUser(Model model, Users user) {

        model.addAttribute("user",user);
        return "auth/registration";
    }*/

    // registration
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String postAddNewUser(Model model, @Valid @ModelAttribute("user") Users user,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirect,
                                 HttpServletRequest request) {


        Users checkifExists = userService.findByUsername(user.getUsername());
        
        if (checkifExists != null){
            redirect.addFlashAttribute("failMessage","Username already taken.");
            bindingResult.reject("username");
            return "redirect:/login";
        }

        checkifExists = userService.findByEmail(user.getEmail());
        if (checkifExists != null){
            redirect.addFlashAttribute("failMessage","User already exists.");
            bindingResult.reject("email");
            return "redirect:/login";

        }

        if (bindingResult.hasErrors()){

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String result = "";
            for (int i = 0; i <  fieldErrors.size() - 1; i++) {
                result += fieldErrors.get(i).getField();
                result += ", ";
            }
            result += fieldErrors.get(fieldErrors.size() - 1).getField();

            if(fieldErrors.size() > 1)
                redirect.addFlashAttribute("failMessage", "User registration failed. " + result + " are not valid.");
            else
                redirect.addFlashAttribute("failMessage", "User registration failed. " + result + " is not valid.");

            return "redirect:/login";

        } else{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEnabled(false);
            user.setPasswordToken(userService.getNewToken());
            user.setConfirmToken(userService.getNewToken());
            user.setReactivateToken(userService.getNewToken());
            userService.save(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();
            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Confirm your email");
            registrationEmail.setText("Before we get started, please confirm your email address by clicking the link below:\n"
                    + appUrl + ":8080/account/confirmation?token=" + user.getConfirmToken());

            emailService.sendEmail(registrationEmail);

            redirect.addFlashAttribute("successMessage", "Check your email to confirm the registration." + user.getEmail());

            return "redirect:/login";
        }
    }

    // email confirmation
    @RequestMapping(value = "/account/confirmation", method = RequestMethod.GET)
    public String confirmCreatedAccount(@RequestParam("token") String token, RedirectAttributes redirect){

        Users user = userService.findByConfirmToken(token);

        if (user != null) {
            user.setEnabled(true);
            user.setRole("ROLE_USER");
            user.setConfirmToken(userService.getNewToken());
            userService.save(user);
            redirect.addFlashAttribute("successMessage", "Account created.");
        }
        else {
            redirect.addFlashAttribute("failMessage", "An error occurred! Account not created.");
        }
        return "redirect:/login";
    }


    // forgotten password
    @RequestMapping(value = "/password/forgotten", method = RequestMethod.GET)
    public String showForgotPassword() {
        return "auth/passwordreset";
    }

    @RequestMapping(value = "/password/forgotten", method = RequestMethod.POST)
    public String postForgotPassword(Model model, @RequestParam Map requestParams, HttpServletRequest request, RedirectAttributes redirectAttributes){

        Users user = userService.findByEmail(requestParams.get("email").toString());
        if (user == null){
            redirectAttributes.addFlashAttribute("failMessage", "User with this email doesn't exist.");
            return "redirect:/password/forgotten";
        }

        String appUrl = request.getScheme() + "://" + request.getServerName();
        SimpleMailMessage resetPasswordEmail = new SimpleMailMessage();
        resetPasswordEmail.setTo(user.getEmail());
        resetPasswordEmail.setSubject("Password reset");
        resetPasswordEmail.setText("We are almost there. Please click on the link below to set a new password.\n"
                + appUrl + ":8080/password/reset?token=" + user.getPasswordToken());

        emailService.sendEmail(resetPasswordEmail);
        redirectAttributes.addFlashAttribute("successMessage", "Check your email to reset your password.");

        return "redirect:/password/forgotten";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.GET)
    public String resetPassword(Model model, @RequestParam("token") String token, RedirectAttributes redirectAttributes){

        Users user = userService.findByPasswordToken(token);
        if (user == null){
            redirectAttributes.addFlashAttribute("failMessage", "User doesn't exist.");
            return "redirect:/password/forgotten";
        }

        model.addAttribute("token", token);

        return "auth/createNewPassword";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public String postResetPassword(@RequestParam Map requestParams, RedirectAttributes redirectAttributes){

        Users user = userService.findByPasswordToken(requestParams.get("token").toString());
        if (user == null){
            redirectAttributes.addFlashAttribute("failMessage", "User doesn't exist.");
            return "redirect:/password/forgotten";
        }

        user.setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password").toString()));
        user.setPasswordToken(userService.getNewToken());
        userService.save(user);

        redirectAttributes.addFlashAttribute("successMessage", "Password changed.");
        return "redirect:/login";
    }
}

