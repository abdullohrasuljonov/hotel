package uz.pdp.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.hotel.entity.Hotel;
import uz.pdp.hotel.repository.HotelRepository;

import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping()
    public Page<Hotel> getAllHotel(@RequestParam int page){
        Pageable pageable= PageRequest.of(page,10);
        return hotelRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        return optionalHotel.orElseGet(Hotel::new);
    }


    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists)
            return "Name already exist!";
        Hotel newHotel=new Hotel();
        newHotel.setName(hotel.getName());
        return "Hotel saved!";
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id,@RequestBody Hotel hotel){
        boolean exists = hotelRepository.existsByName(hotel.getName());
        if (exists)
            return "Name already exist!";
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            Hotel hotel1 = optionalHotel.get();
            hotel1.setName(hotel.getName());
            return "Hotel edited!";
        }
        return "Hotel not found!";
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
            hotelRepository.deleteById(id);
            return "Hotel deleted!";
        }
        return "Hotel not found!";
    }
}















