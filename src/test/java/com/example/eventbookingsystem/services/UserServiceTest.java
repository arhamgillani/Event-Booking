package com.example.eventbookingsystem.services;

import com.example.eventbookingsystem.models.Users;
import com.example.eventbookingsystem.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterUser() throws Exception {
        Users user = new Users(1L, "user", "password");
        String encodedPassword = "password";
        when(passwordEncoder.encode(user.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(user)).thenReturn(user);

        Users registeredUser = userService.registerUser(user);

        assertEquals(encodedPassword, registeredUser.getPassword());
        verify(passwordEncoder).encode(user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    public void testAuthenticateUser_Valid() throws Exception {
        Users user = new Users(1L, "testUser", "password");
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);

        Users authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());

        assertEquals(user, authenticatedUser);
        verify(userRepository).findByUsername(user.getUsername());
        verify(passwordEncoder).matches(user.getPassword(), user.getPassword());
    }

    @Test
    public void testAuthenticateUser_Invalid() {
        String username = "user";
        String password = "wrongPassword";

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.authenticateUser(username, password));
        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    public void testFindById_Found() throws Exception {
        Long userId = 1L;
        Users user = new Users(1L, "testUser", "password");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<Users> foundUser = userService.findById(userId);

        assertEquals(Optional.of(user), foundUser);
        verify(userRepository).findById(userId);
    }

    @Test
    public void testFindById_NotFound() throws Exception {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<Users> foundUser = userService.findById(userId);

        assertEquals(Optional.empty(), foundUser);
        verify(userRepository).findById(userId);
    }

    @Test
    public void testLoadUserByUsername_Found() throws Exception {
        String username = "testUser";
        Users user = new Users(1L, "testUser", "password");
        when(userRepository.findByUsername(username)).thenReturn(user);

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        verify(userRepository).findByUsername(username);
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
        assertEquals("User not found", exception.getMessage());
    }
}