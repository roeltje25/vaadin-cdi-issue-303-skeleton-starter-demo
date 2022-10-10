package org.vaadin.example;

import javax.annotation.PostConstruct;

import com.vaadin.cdi.annotation.NormalRouteScoped;
import com.vaadin.cdi.annotation.RouteScopeOwner;

/**
 * Data provider bean scoped for each user session.
 */
@NormalRouteScoped
@RouteScopeOwner(ParentLayout.class)
public class GreetService1
{
    private static volatile long instantiated = 0;

    @PostConstruct
    private void init()
    {
        instantiated++;
    }

    public String greet(String name)
    {
        if( name == null || name.isEmpty( ) )
        {
            return "Hello anonymous user";
        } else
        {
            return "Hello " + name;
        }
    }

    public long getInstantiated()
    {
        return instantiated;
    }
}
