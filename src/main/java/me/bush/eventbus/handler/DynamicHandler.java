package me.bush.eventbus.handler;

import me.bush.eventbus.event.Event;
import me.bush.eventbus.handler.handlers.ASMHandler;
import me.bush.eventbus.handler.handlers.LambdaHandler;

/**
 * Started: 11/14/2021
 *
 * @author me.bush
 */
public interface DynamicHandler {

    /**
     * Implemented by the classes created at runtime in {@link LambdaHandler} and {@link ASMHandler}.
     *
     * @param event The event to pass to the listener method.
     */
    void invoke(Event event);
}
