package com.example.demo;


//I am using this test class to inject objects

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;

public class testUtils {

    public static void injectObject(Object target, String fieldName, Object toInject) {

        //to know private or not
        boolean wasPrivate = false;

        try {

            //getting field
            Field f = target.getClass().getDeclaredField(fieldName);

            //checking field f is private through is accessible
            //if it is a private field then it says accessible false

            if (!f.isAccessible()) {

                //so, if it is not accessible setting it to accessible true to make it private
                f.setAccessible(true);

                //to say it's private
                wasPrivate = true;

            }

            //setting target with the object tha I want to inject
            f.set(target, toInject);

            //if the field is private, I am setting accessibility again
            if (wasPrivate) {

                f.setAccessible(false);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    //method to define empty cart
    public static Cart emptyCart(){

        //creating cart object
        Cart cart = new Cart();

        //setting id
        cart.setId(1L);

        //setting user
        cart.setUser(null);

        //setting items
        cart.setItems(new ArrayList<Item>());

        //setting total bil
        cart.setTotal(BigDecimal.valueOf(0.0));

        return cart;

    }


    //method to define creating user
    public static User createUser(){

        //creating user object
        User user = new User();

        //setting id
        user.setId(1);

        //setting username
        user.setUsername("Divya");

        //setting password
        user.setPassword("Divya123");

        //setting empty cart
        user.setCart(emptyCart());

        return user;

    }

    //method to define creating item
    public static Item createItem(){

        //creating item object
        Item item = new Item();

        //setting id
        item.setId(1L);

        //setting name
        item.setName("Item1");

        //setting description
        item.setDescription("Item1 Created");

        //setting price
        item.setPrice(BigDecimal.valueOf(10.0));

        return item;


    }


    //method to define creating modify cart request
    public static ModifyCartRequest createModifyCartRequest(String username, long itemId, int quantity){

        //create modify cart request object
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();

        //setting username
        modifyCartRequest.setUsername(username);

        //setting item id
        modifyCartRequest.setItemId(itemId);

        //setting quantity
        modifyCartRequest.setQuantity(quantity);

        return modifyCartRequest;
    }
}
