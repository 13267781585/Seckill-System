package com.gzhu.config.session;

import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionDeleteListener implements ApplicationListener<SessionDeletedEvent> {
    @Override
    public void onApplicationEvent(SessionDeletedEvent sessionDeletedEvent) {
        System.out.println("session delete....");
    }
}
