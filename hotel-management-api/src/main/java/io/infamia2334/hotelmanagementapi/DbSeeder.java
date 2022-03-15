package io.infamia2334.hotelmanagementapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {

    private HotelRepository hotelRepository;

    public DbSeeder(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Hotel marriot = new Hotel(
            "JW Marriot",
            16000,
            new Address("Kolkata", "India"),
            Arrays.asList(
                new Review("Infamia",  7,  false),
                new Review("Lester",  8,  true)
            )
        );

        Hotel itc = new Hotel(
            "ITC Sonar Bangla",
            11500,
            new Address("Kolkata", "India"),
            Arrays.asList(
                new Review("Mihila",  9,  false),
                new Review("Nikita",  7,  true)
            )
        );

        Hotel inferiorBhopali = new Hotel(
            "ITC Sonar Bangla",
            650,
            new Address("Bhopal", "India"),
            Arrays.asList(
                new Review("Hidaram",  2,  false),
                new Review("Gupta",  4,  true)
            )
        );
        //drop all hotels
        this.hotelRepository.deleteAll();

        //adding hotels
        List<Hotel> hotels = Arrays.asList(marriot, itc, inferiorBhopali);
        this.hotelRepository.saveAll(hotels);
        
    }
    
}
