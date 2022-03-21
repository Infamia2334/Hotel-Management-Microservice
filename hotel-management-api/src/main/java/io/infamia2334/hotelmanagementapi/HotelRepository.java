package io.infamia2334.hotelmanagementapi;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String > {
    // Hotel findById(String id);
    List<Hotel> findByPricePerNightLessThan(int max);
}
