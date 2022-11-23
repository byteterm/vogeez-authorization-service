package net.vogeez.authorization.service.event.listener;

import lombok.RequiredArgsConstructor;
import net.vogeez.authorization.service.event.CreateVerificationTokenEvent;
import net.vogeez.authorization.service.event.RegistrationCompleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Component
@RequiredArgsConstructor
@Transactional
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        eventPublisher.publishEvent(new CreateVerificationTokenEvent(event.getUser()));
    }
}
