package com.example.travel.repository;

import com.example.travel.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraryRepo extends JpaRepository<Itinerary, Integer> {
    List<Itinerary> findByUserEmail(String email);
}
