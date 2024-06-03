package com.example.eventbookingsystem.services;

import com.example.eventbookingsystem.models.Event;
import com.example.eventbookingsystem.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    public void testCreateEvent() {
        Event event = new Event();
        event.setName("Test Event");
        event.setLocation("Test Location");
        event.setDateTime(LocalDateTime.now());

        when(eventRepository.save(event)).thenReturn(event);

        Event createdEvent = eventService.createEvent(event);

        assertNotNull(createdEvent);
        assertEquals(event, createdEvent);
        verify(eventRepository).save(event);
    }


    @Test
    public void testUpdateEvent_EventNotFound() {
        Long eventId = 1L;
        Event eventDetails = new Event();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> eventService.updateEvent(eventId, eventDetails));
        assertEquals("Event not found", exception.getMessage());
    }

    @Test
    public void testDeleteEvent() {
        Long eventId = 1L;

        eventService.deleteEvent(eventId);

        verify(eventRepository).deleteById(eventId);
    }

    @Test
    public void testGetAllEvents() {
        List<Event> events = List.of(new Event(), new Event());

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> result = eventService.getAllEvents();

        assertEquals(events, result);
        verify(eventRepository).findAll();
    }

    @Test
    public void testGetEventById_EventFound() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.getEventById(eventId);

        assertNotNull(result);
        assertEquals(event, result.get());
        verify(eventRepository).findById(eventId);
    }

    @Test
    public void testGetEventById_EventNotFound() {
        Long eventId = 1L;

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        Optional<Event> result = eventService.getEventById(eventId);

        assertEquals(Optional.empty(), result);
        verify(eventRepository).findById(eventId);
    }
}