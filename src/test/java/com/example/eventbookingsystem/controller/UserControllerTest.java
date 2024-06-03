package com.example.eventbookingsystem.controller;


import com.example.eventbookingsystem.controllers.UserController;
import com.example.eventbookingsystem.models.Users;
import com.example.eventbookingsystem.response.MessageResponse;
import com.example.eventbookingsystem.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {

    private MockMvc mockMvc;

    private UserService userService = mock(UserService.class);

    private UserController userController;

    @BeforeEach
    public void setup() {
        userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testRegisterUser() throws Exception {
        Users user = new Users();
        user.setUsername("user");
        user.setPassword("password");

        Users registeredUser = new Users();
        registeredUser.setUsername("user");
        registeredUser.setPassword("password");


        when(userService.registerUser(user)).thenReturn(registeredUser);

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"username\":\"user\", \"password\":\"password\"}"))
                .andExpect(status().isOk());
    }
//    @Test
//    public void testRegisterUser() throws Exception {
//        Users user = new Users();
//        user.setUsername("user");
//        user.setPassword("password");
//
//        when(userService.registerUser(any(Users.class))).thenReturn(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\": \"user\", \"password\": \"password\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("user"));
//    }

    @Test
    public void testLoginUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String username = "user";
        String password = "password";

        Users authenticatedUser = new Users();
        authenticatedUser.setUsername(username);
        authenticatedUser.setPassword(password);

        when(userService.authenticateUser(username, password)).thenReturn(authenticatedUser);

        MvcResult result = mockMvc.perform(post("/api/users/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String responseBody = response.getContentAsString();

        MessageResponse expectedResponse = new MessageResponse("Login Successfully", authenticatedUser, false);
        String expectedResponseBody = objectMapper.writeValueAsString(expectedResponse);

        assertEquals(expectedResponseBody, responseBody);
    }
}
