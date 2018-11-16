package com.team1.demo.Repository;

import com.team1.demo.Entity.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends CrudRepository<Users, Long> {

    Users findByUserID(Long id);

    Users findByUsername(String username);

    Users findByEmail(String email);

    Users findByConfirmToken(String token);

    Users findByPasswordToken(String token);


    @Query("SELECT DISTINCT u.role from Users u")
    Set<String> findDistinctRole();


    Users getOne(Long id);



   /* @Transactional
    @Modifying
    @Query(value = "update Users u set u.isDeleted=true where u.userID=:id")
   // @Query(value = "update USERS set is_deleted=true where userid=?1", nativeQuery = true)
    void userDeleted(@Param("id") Long id);*/

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update Users u set u.firstName=:firstName, u.lastName=:lastName, u.username=:username,  u.longitude=:longitude, u.latitude=:latitude, u.role=:role, u.email=:email where u.userID=:id")
    void updateUser(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("username") String username,  @Param("longitude") double longitude, @Param("latitude") double latitude, @Param("role") String role, @Param("email") String email, @Param("id") Long id);
}
