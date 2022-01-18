package com.ja.dupermen.event;

import me.bush.eventbus.event.Event;

public class TickEvent extends Event {
    public static class Pre extends TickEvent {}
    public static class Post extends TickEvent {}

    protected boolean isCancellable() {
        return false;
    }
}
