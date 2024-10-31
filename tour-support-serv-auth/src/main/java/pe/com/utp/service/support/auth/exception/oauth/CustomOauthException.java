package pe.com.utp.service.support.auth.exception.oauth;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;


@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    private final AuthenticationFailureBadCredentialsEvent event;


    public CustomOauthException(String msg, AuthenticationFailureBadCredentialsEvent event) {
        super(msg);
        this.event = event;
    }

    public AuthenticationFailureBadCredentialsEvent getEvent() {
        return event;
    }
}
