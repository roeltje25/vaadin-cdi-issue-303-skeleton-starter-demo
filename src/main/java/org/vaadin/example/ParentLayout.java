package org.vaadin.example;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.vaadin.cdi.annotation.RouteScopeOwner;
import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;

/**
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@CdiComponent
@RouteScoped
public class ParentLayout extends VerticalLayout implements RouterLayout, BeforeEnterObserver
{
    Logger logger = Logger.getLogger( ParentLayout.class.getCanonicalName( ) );

    private static volatile long instantiated = 0;

    @Inject
    @RouteScopeOwner(ParentLayout.class)
    Instance<GreetService1> instance;

    @PostConstruct
    public void init()
    {
        getElement( ).setAttribute( "instantiated", "" + ++instantiated );
    }

    public void onEvent(@Observes(notifyObserver = Reception.IF_EXISTS) Bleep event)
    {
        logger.info( "Event received" );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event)
    {
        logger.info( "GreetService1 resolvable: " + instance.isResolvable( ) );
        logger.info( "GreetService1 unsatisfied: " + instance.isUnsatisfied( ) );
    }

}
