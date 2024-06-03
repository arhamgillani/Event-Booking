package com.example.eventbookingsystem.services;

import com.example.eventbookingsystem.models.Booking;
import com.example.eventbookingsystem.models.Event;
import com.example.eventbookingsystem.models.Users;
import com.example.eventbookingsystem.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventService eventService;
    private final UserService userService;

    public BookingService(BookingRepository bookingRepository, EventService eventService, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.eventService = eventService;
        this.userService = userService;
    }

    public Booking bookTickets(Long eventId, Long userId, int numberOfTickets) {
        Optional<Event> eventOptional = eventService.getEventById(eventId);
        Optional<Users> userOptional = userService.findById(userId);
        if (eventOptional.isPresent() && userOptional.isPresent()) {
            Booking booking = new Booking();
            booking.setEvent(eventOptional.get());
            booking.setUser(userOptional.get());
            booking.setNumberOfTickets(numberOfTickets);
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Event or User not found");
        }
    }

    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}