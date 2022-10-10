package org.vaadin.example;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.vaadin.cdi.annotation.RouteScopeOwner;
import com.vaadin.cdi.annotation.RouteScoped;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route(value = "other", layout = ParentLayout.class)
@CdiComponent
@RouteScoped
public class OtherView extends VerticalLayout implements BeforeEnterObserver
{
    private static final Logger logger = Logger.getLogger( OtherView.class.getCanonicalName( ) );
    private static final long serialVersionUID = 63096061475150887L;

    static volatile long instantiated = 0;

    @Inject
    @RouteScopeOwner(OtherView.class)
    private GreetService2 greetService;
    @Inject
    Event<Bleep> bleeps;

    @Override
    public void beforeEnter(BeforeEnterEvent event)
    {
        greetService.getInstantiated( );
    }

    @PostConstruct
    public void init()
    {
        getElement( ).setAttribute( "instantiated", "" + ++instantiated );
        getElement( ).setAttribute( "greeter-instantiated", "" + greetService.getInstantiated( ) );

        // Use TextField for standard text input
        TextField textField = new TextField( "Your name" );
        textField.addThemeName( "bordered" );

        // Button click listeners can be defined as lambda expressions
        Button button = new Button( "Say hello", e -> Notification
                .show( greetService.greet( textField.getValue( ) ) ) );

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button is more prominent look.
        button.addThemeVariants( ButtonVariant.LUMO_PRIMARY );

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut( Key.ENTER );

        // Use custom CSS classes to apply styling. This is defined in
        // shared-styles.css.
        addClassName( "centered-content" );
        Button navButton = new Button( "to main" );
        navButton.addClickListener( e -> {
            logger.info( "Sending bleep" );
            bleeps.fire( Bleep.INSTANCE );
            getUI( ).get( ).navigate( "" );
        } );
        add( textField, button, navButton );
    }

}
