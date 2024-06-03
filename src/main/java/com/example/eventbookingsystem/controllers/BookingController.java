package com.example.eventbookingsystem.controllers;

import com.example.eventbookingsystem.models.Booking;
import com.example.eventbookingsystem.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {


    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookTickets")
    public ResponseEntity<Booking> bookTickets(@RequestParam Long eventId, @RequestParam Long userId, @RequestParam int numberOfTickets) {
        Booking booking = bookingService.bookTickets(eventId, userId, numberOfTickets);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/getBookingsByUser")
    public ResponseEntity<List<Booking>> getBookingsByUser(@RequestParam Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/cancelBooking")
    public ResponseEntity<Void> cancelBooking(@RequestParam  Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}