package org.vaadin.example;

import com.vaadin.cdi.annotation.UIScoped;

/**
 * Data provider bean scoped for each user session.
 */
@UIScoped
public class GreetService {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello anonymous user";
        } else {
            return "Hello " + name;
        }
    }
}
