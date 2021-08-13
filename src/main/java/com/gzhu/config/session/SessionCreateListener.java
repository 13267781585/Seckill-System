package com.gzhu.config.session;

import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionCreateListener implements ApplicationListener<SessionCreatedEvent> {

    @Override
    public void onApplicationEvent(SessionCreatedEvent sessionCreatedEvent) {
        System.out.println("session create!!!");
    }
}
