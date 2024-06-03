package com.example.eventbookingsystem.services;

import com.example.eventbookingsystem.models.Event;
import com.example.eventbookingsystem.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, Event eventDetails) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setName(eventDetails.getName());
            event.setLocation(eventDetails.getLocation());
            event.setDateTime(eventDetails.getDateTime());
            return eventRepository.save(event);
        } else {
            throw new RuntimeException("Event not found");
        }
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Long eventId) {
        return eventRepository.findById(eventId);
    }
}
