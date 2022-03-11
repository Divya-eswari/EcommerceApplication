package com.example.demo.controllers;

import com.example.demo.testUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.example.demo.testUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class CartControllerTest {


    //creating an object of cart controller
    //this object is to call all the methods of the cart controller to test
    private CartController cartController;

    //creating a mock object of user repository
    private final UserRepository userRepository = mock(UserRepository.class);

    //creating a mock object of cart repository
    private final CartRepository cartRepository = mock(CartRepository.class);

    //creating a mock object of item repository
    private final ItemRepository itemRepository = mock(ItemRepository.class);


    //to call this method before each test
    @Before
    public void setup(){

        //creating a new object of cart controller
        cartController = new CartController();

        //injecting the fields into cart controller object
        testUtils.injectObject(cartController, "userRepository", userRepository);
        testUtils.injectObject(cartController, "cartRepository", cartRepository);
        testUtils.injectObject(cartController, "itemRepository",itemRepository);
    }


    //test to check adding items to cart
    @Test
    public void addingItemsToCart(){

        //creating user
        User user = createUser();

        //creating item
        Item item = createItem();

        //getting cart
        Cart cart = user.getCart();

        //adding items to cart
        cart.addItem(item);

        //setting user to cart
        cart.setUser(user);

        //setting cart to user
        user.setCart(cart);

        //getting user
        when(userRepository.findByUsername("Divya")).thenReturn(user);

        //getting items
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));


        //creating cart request
        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Divya", 1, 1);

        //getting response
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);

        //doing assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        //getting response
        Cart responseCart = responseEntity.getBody();

        //doing assertion
        assertNotNull(responseCart);

        //getting list of items from the cart
        List<Item> items = responseCart.getItems();

        //doing assertions
        assertNotNull(items);
        assertEquals("Divya", responseCart.getUser().getUsername());

        verify(cartRepository, times(1)).save(responseCart);


    }


    //test to check add to cart option without user
    @Test
    public void addToCartWithoutUser(){

        //creating cart request with username as null
        ModifyCartRequest modifyCartRequest = createModifyCartRequest("", 1, 1);

        //getting response
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);


        //doing assertions
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());


    }



    //test to check add to cart without item
    @Test
    public void addToCartWithoutItem(){

        //creating user
        when(userRepository.findByUsername("Divya")).thenReturn(new User());

        //to get no items
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        //creating cart request
        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Divya", 1, 1);

        //getting response
        ResponseEntity<Cart> responseEntity = cartController.addTocart(modifyCartRequest);


        //doing assertions
        assertNotNull(responseEntity);
        verify(itemRepository, times(1)).findById(1L);
        assertEquals(404, responseEntity.getStatusCodeValue());


    }



    //test to check removing items from cart
    @Test
    public void removingItemsFromCart(){


        //getting user
        User user = createUser();

        //getting item
        Item item = createItem();

        //getting cart
        Cart cart = user.getCart();

        //adding item to cart
        cart.addItem(item);

        //setting user to cart
        cart.setUser(user);

        //setting cart to user
        user.setCart(cart);


        //getting user
        when(userRepository.findByUsername("Divya")).thenReturn(user);

        //getting items
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));


        //creating cart request
        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Divya", 1, 1);

        //getting response
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);


        //doing assertions
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());


        //getting response
        Cart responseCart = responseEntity.getBody();

        //doing assertions
        assertNotNull(responseCart);

        //getting list of items
        List<Item> items = responseCart.getItems();

        //doing assertions
        assertNotNull(items);
        assertEquals(0, items.size());
        assertEquals("Divya", responseCart.getUser().getUsername());

        verify(cartRepository, times(1)).save(responseCart);

    }




    //test to check removing from cart without user
    @Test
    public void removeFromCartWithoutUser(){

        //creating cart request with username as null
        ModifyCartRequest modifyCartRequest = createModifyCartRequest("", 1, 1);

        //getting response
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);


        //doing assertions
        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());

    }



    //test to check remove from cart without item
    @Test
    public void removeFromCartWithoutItem(){


        //getting user
        when(userRepository.findByUsername("Divya")).thenReturn(new User());

        //getting no item
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        //creating cart request
        ModifyCartRequest modifyCartRequest = createModifyCartRequest("Divya", 1, 1);

        //getting response
        ResponseEntity<Cart> responseEntity = cartController.removeFromcart(modifyCartRequest);

        //doing assertions
        assertNotNull(responseEntity);
        verify(itemRepository, times(1)).findById(1L);
        assertEquals(404, responseEntity.getStatusCodeValue());


    }



}
