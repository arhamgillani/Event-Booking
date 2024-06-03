package com.example.eventbookingsystem.services;


import com.example.eventbookingsystem.models.Booking;
import com.example.eventbookingsystem.models.Event;
import com.example.eventbookingsystem.models.Users;
import com.example.eventbookingsystem.repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookingService bookingService;

    @Test
    public void testBookTickets_EventOrUserNotFound() {
        Long eventId = 1L;
        Long userId = 1L;
        int numberOfTickets = 2;

        when(eventService.getEventById(eventId)).thenReturn(Optional.empty());
        when(userService.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookingService.bookTickets(eventId, userId, numberOfTickets));
        assertEquals("Event or User not found", exception.getMessage());
    }

    @Test
    public void testGetBookingsByUser() {
        Long userId = 1L;
        List<Booking> bookings = List.of(new Booking(), new Booking());

        when(bookingRepository.findByUserId(userId)).thenReturn(bookings);

        List<Booking> result = bookingService.getBookingsByUser(userId);

        assertEquals(bookings, result);
        verify(bookingRepository).findByUserId(userId);
    }

    @Test
    public void testCancelBooking() {
        Long bookingId = 1L;

        bookingService.cancelBooking(bookingId);

        verify(bookingRepository).deleteById(bookingId);
    }
}