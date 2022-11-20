package net.vogeez.authorization.service.endpoint;

import lombok.Getter;

/**
 * @author : Niklas Tat
 * @since : 0.5
 */
@Getter
public enum ViewNames {
    AUTHENTICATION("authentication", "/"),
    ACCOUNT_DASHBOARD("account/dashboard", "/account/dashboard"),
    ACCOUNT_PROFILE("account/profile", "/account/profile");
    private final String viewName;
    private final String url;

    ViewNames(String viewName, String url) {
        this.viewName = viewName;
        this.url = url;
    }

    @Override
    public String toString() {
        return this.viewName;
    }
}
