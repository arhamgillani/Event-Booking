package com.example.eventbookingsystem.controller;


import com.example.eventbookingsystem.controllers.BookingController;
import com.example.eventbookingsystem.models.Booking;
import com.example.eventbookingsystem.models.Event;
import com.example.eventbookingsystem.models.Users;
import com.example.eventbookingsystem.services.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    public void testBookTickets() throws Exception {
        Long eventId = 1L;
        Long userId = 2L;
        int numberOfTickets = 3;
        Booking booking = new Booking(1L, new Event(eventId, "Test Event", "Test Location", LocalDateTime.now()), new Users(userId, "Test User"), numberOfTickets);

        when(bookingService.bookTickets(eventId, userId, numberOfTickets)).thenReturn(booking);

        ResponseEntity<Booking> response = bookingController.bookTickets(eventId, userId, numberOfTickets);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(booking, response.getBody());
        verify(bookingService).bookTickets(eventId, userId, numberOfTickets);
    }

    @Test
    public void testGetBookingsByUser() throws Exception {
        Long userId = 1L;
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking(1L, new Event(1L, "Event 1", "Location 1", LocalDateTime.now()), new Users(userId, "Test User"), 2));
        bookings.add(new Booking(2L, new Event(2L, "Event 2", "Location 2", LocalDateTime.now()), new Users(userId, "Test User"), 3));

        when(bookingService.getBookingsByUser(userId)).thenReturn(bookings);

        ResponseEntity<List<Booking>> response = bookingController.getBookingsByUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookings, response.getBody());
        verify(bookingService).getBookingsByUser(userId);
    }

    @Test
    public void testCancelBooking() throws Exception {
        Long bookingId = 1L;

        bookingController.cancelBooking(bookingId);

        verify(bookingService).cancelBooking(bookingId);
    }
}