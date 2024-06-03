package com.example.eventbookingsystem.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageResponse {
    private String message;
    private Object data;
    private boolean error;
}
