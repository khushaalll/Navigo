package com.example.travel.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "itinerary")
public class Itinerary {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false) // foreign key
    private User user;

    private int noOfDays;
    private String city;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfArrival;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfDeparture;
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @ElementCollection
    private List<String> placesToVisit;

    public Itinerary() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public User getUser() {
        return user;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getDateOfArrival() {
        return dateOfArrival;
    }

    public LocalDate getDateOfDeparture() {
        return dateOfDeparture;
    }

    public List<String> getPlacesToVisit() {
        return placesToVisit;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDateOfArrival(LocalDate dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public void setDateOfDeparture(LocalDate dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public void setPlacesToVisit(List<String> placesToVisit) {
        this.placesToVisit = placesToVisit;
    }

    // Parameterized constructor
    public Itinerary(User user, int noOfDays, String city, LocalDate dateOfArrival, LocalDate dateOfDeparture, List<String> placesToVisit, String notes) {
        this.user = user;
        this.noOfDays = noOfDays;
        this.city = city;
        this.dateOfArrival = dateOfArrival;
        this.dateOfDeparture = dateOfDeparture;
        this.placesToVisit = placesToVisit;
        this.notes=notes;
    }
}
