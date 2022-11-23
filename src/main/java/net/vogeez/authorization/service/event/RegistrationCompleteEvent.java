package net.vogeez.authorization.service.event;

import lombok.*;
import net.vogeez.authorization.service.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private User user;

    public RegistrationCompleteEvent(User user) {
        super(user);

        this.user = user;
    }
}
