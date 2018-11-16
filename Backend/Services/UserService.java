package com.team1.demo.Services;

import com.team1.demo.Entity.Users;
import com.team1.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }


    //public Users findOne(Long id) {
     //   return userRepository.findOne(id);
   // }


    public void save(Users user) {
         userRepository.save(user);
    }


    public void delete(Users user ) {
        userRepository.delete(user);
    }

    public Users findById(Long id) {
        return userRepository.findByUserID(id);
    }

    public Optional<Users> findOne(Long id) { return userRepository.findById(id); }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    public Users getOne(Long id) {
        return userRepository.getOne(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

/*
    public void userDeleted(Long id) {
        userRepository.userDeleted(id);
    }
*/

    public void updateUser(String firstName, String lastName, String username, double longitude, double latitude, String role, String email,Long id){
        userRepository.updateUser(firstName, lastName, username, longitude, latitude, role, email, id);
    }

    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String getNewToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public Users findByConfirmToken(String token) {
        return userRepository.findByConfirmToken(token);
    }

    public Users findByPasswordToken(String token) {
        return userRepository.findByPasswordToken(token);
    }



    /*public Users findOne(Long id){
        userRepository.find
    }*/
}
