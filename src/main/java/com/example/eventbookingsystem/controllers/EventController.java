package com.example.eventbookingsystem.controllers;

import com.example.eventbookingsystem.models.Event;
import com.example.eventbookingsystem.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/createEvent")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.ok(createdEvent);
    }

    @PutMapping("/updateEvent")
    public ResponseEntity<Event> updateEvent(@RequestParam Long eventId, @RequestBody Event eventDetails) {
        Event updatedEvent = eventService.updateEvent(eventId, eventDetails);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/deleteEvent")
    public ResponseEntity<Void> deleteEvent(@RequestParam Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/getEventById")
    public ResponseEntity<Event> getEventById(@RequestParam Long eventId) {
        Optional<Event> event = eventService.getEventById(eventId);
        return event.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}