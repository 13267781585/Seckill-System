package com.gzhu.config.session;

import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionExpireListener implements ApplicationListener<SessionExpiredEvent> {
    @Override
    public void onApplicationEvent(SessionExpiredEvent sessionExpiredEvent) {
        System.out.println("session expire!!!");
    }
}
