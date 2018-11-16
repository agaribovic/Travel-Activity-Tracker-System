package com.team1.demo.Repository;

import com.team1.demo.Entity.Hotel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// komunicira s bazom
@Repository
public interface HotelRepository extends CrudRepository<Hotel, Long> {
    Hotel getOne(Long id);
//    Hotel findOne(Long id);
    Hotel findByHotelID(Long id);
    //Hotel findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "update Hotel h set h.name=:name, h.description=:description, h.location=:location, h.address=:address, h.longitude=:longitude, h.latitude=:latitude where h.hotelID=:id")
    void updateHotel(@Param("name") String name, @Param("description") String description, @Param("location") String location, @Param("address") String address,@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("id") Long id);

}
