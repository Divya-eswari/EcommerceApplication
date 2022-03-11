package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.testUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.example.demo.testUtils.createUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {


    //creating an object of user controller
    //this object is to call all the methods of the User controller to test
    private UserController userController;

    //creating a mock object of user repository
    private UserRepository userRepo = mock(UserRepository.class);

    //creating a mock object of cart repository
    private CartRepository cartRepo = mock(CartRepository.class);

    //creating a mock object of BCrypt password encoder
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);


    //to call this method before every test method
    @Before
    public void setUp() {

        //creating new object of user controller
        userController = new UserController();

        //injecting the fields into user controller object
        testUtils.injectObject(userController, "userRepository", userRepo );
        testUtils.injectObject(userController, "cartRepository", cartRepo);
        testUtils.injectObject(userController, "bCryptPasswordEncoder", encoder);
    }

    //creating a happy use-case
    @Test
    public void happy_use_case_creating_user() throws Exception {

        when(encoder.encode("Divya123")).thenReturn("thisIsHashed");

        //creating a user request
        CreateUserRequest req = new CreateUserRequest();
        req.setUsername("Divya");
        req.setPassword("Divya123");
        req.setConfirmPassword("Divya123");

        //testing create user from user controller
        ResponseEntity<User> response = userController.createUser(req);

        //doing assertions
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        //getting user response and doing assertions
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("Divya", user.getUsername());
        assertEquals("thisIsHashed", user.getPassword());



    }




    //test to find user by id
    @Test
    public void findingUserById(){

        //creating user object from testutils
        User user = createUser();

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        //finding user
        ResponseEntity<User> response = userController.findById(1L);

        //doing assertions
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }




    //test to test user not found case
    @Test
    public void userNotFoundById(){
        ResponseEntity<User> response = userController.findById(1L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }



    //test to find user by username
    @Test
    public void findingUserByUsername(){

        User user = createUser();
        when(userRepo.findByUsername("Divya")).thenReturn(user);
        ResponseEntity<User> response = userController.findByUserName("Divya");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        assertEquals(1L, user.getId());
        assertEquals("Divya", user.getUsername());
        assertEquals("Divya123", user.getPassword());
    }




    @Test
    public void UserNotFoundByUsername(){

        ResponseEntity<User> response = userController.findByUserName("Divya");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }


}
