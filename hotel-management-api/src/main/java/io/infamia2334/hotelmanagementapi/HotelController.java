package io.infamia2334.hotelmanagementapi;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
// import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping
    public ResponseEntity<List<Hotel>> getAll() {
        try {
            List<Hotel> hotels = hotelRepository.findAll();
            return new ResponseEntity<>(hotels, HttpStatus.OK) ;    
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotel(@PathVariable String id) {
        try {
            Optional<Hotel> hotel = hotelRepository.findById(id);
            if(hotel.isPresent()) {
                return new ResponseEntity<>(hotel.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            } 
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping
    public ResponseEntity<Hotel> insertHotel(@RequestBody Hotel hotel) {
        try {
            hotelRepository.save(hotel);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable("id") String id, @RequestBody Hotel hotel) {
        try {
            Optional<Hotel> hotelData = hotelRepository.findById(id);
            if(hotelData.isEmpty()) {
                System.out.println("Hotel not found");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            else {
                Hotel updatedHotel = hotelData.get();
                updatedHotel.setName(hotel.getName());
                updatedHotel.setAddress(hotel.getAddress());
                updatedHotel.setPricePerNight(hotel.getPricePerNight());
                updatedHotel.setReviews(hotel.getReviews());
                return new ResponseEntity<>(hotelRepository.save(updatedHotel), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Hotel> deleteHotel(@PathVariable("id") String id) {
        try {
            hotelRepository.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/price/{maxPrice}")
    public ResponseEntity<List<Hotel>> getByPricePerNight(@PathVariable("maxPrice") int maxPrice) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("pricePerNight").lt(maxPrice));
            List<Hotel> hotels = mongoTemplate.find(query, Hotel.class);
            if(hotels.isEmpty()){
                System.out.println("Empty Hotel");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
