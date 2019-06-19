package com.firstaware.common.util;

import org.springframework.context.ApplicationEvent;

public class ExitEvent extends ApplicationEvent {

    private int code;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ExitEvent(Object source, int code) {
        super(source);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
