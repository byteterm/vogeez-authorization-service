package net.vogeez.authorization.service.event;

import lombok.Getter;
import lombok.Setter;
import net.vogeez.authorization.service.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Getter
@Setter
public class CreateVerificationTokenEvent extends ApplicationEvent {

    private User user;

    public CreateVerificationTokenEvent(User user) {
        super(user);

        this.user = user;
    }
}
