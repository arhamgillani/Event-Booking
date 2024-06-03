package com.example.eventbookingsystem.controller;

import com.example.eventbookingsystem.controllers.EventController;
import com.example.eventbookingsystem.models.Event;
import com.example.eventbookingsystem.services.EventService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @Test
    public void testCreateEvent() throws Exception {
        Event event = new Event("Test Event", "Test Location", LocalDateTime.now());
        Event createdEvent = new Event(1L, "Test Event", "Test Location", LocalDateTime.now());

        when(eventService.createEvent(event)).thenReturn(createdEvent);

        ResponseEntity<Event> response = eventController.createEvent(event);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createdEvent, response.getBody());
        verify(eventService).createEvent(event);
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Long eventId = 1L;
        Event eventDetails = new Event("Updated Event", "Updated Location", LocalDateTime.now());
        Event updatedEvent = new Event(eventId, "Updated Event", "Updated Location", LocalDateTime.now());

        when(eventService.updateEvent(eventId, eventDetails)).thenReturn(updatedEvent);

        ResponseEntity<Event> response = eventController.updateEvent(eventId, eventDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEvent, response.getBody());
        verify(eventService).updateEvent(eventId, eventDetails);
    }

    @Test
    public void testDeleteEvent() throws Exception {
        Long eventId = 1L;

        eventController.deleteEvent(eventId);

        verify(eventService).deleteEvent(eventId);
    }

    @Test
    public void testGetAllEvents() throws Exception {
        List<Event> events = new ArrayList<>();
        events.add(new Event(1L, "Event 1", "Location 1", LocalDateTime.now()));
        events.add(new Event(2L, "Event 2", "Location 2", LocalDateTime.now()));

        when(eventService.getAllEvents()).thenReturn(events);

        ResponseEntity<List<Event>> response = eventController.getAllEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events, response.getBody());
        verify(eventService).getAllEvents();
    }

    @Test
    public void testGetEventById_Found() throws Exception {
        Long eventId = 1L;
        Event event = new Event(eventId, "Test Event", "Test Location", LocalDateTime.now());

        when(eventService.getEventById(eventId)).thenReturn(Optional.of(event));

        ResponseEntity<Event> response = eventController.getEventById(eventId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(event, response.getBody());
        verify(eventService).getEventById(eventId);
    }

    @Test
    public void testGetEventById_NotFound() throws Exception {
        Long eventId = 1L;

        when(eventService.getEventById(eventId)).thenReturn(Optional.empty());

        ResponseEntity<Event> response = eventController.getEventById(eventId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(eventService).getEventById(eventId);
    }
}