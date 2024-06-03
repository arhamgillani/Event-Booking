package com.example.eventbookingsystem.repository;

import com.example.eventbookingsystem.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
