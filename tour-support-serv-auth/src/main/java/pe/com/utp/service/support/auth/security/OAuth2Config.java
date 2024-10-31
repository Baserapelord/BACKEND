package pe.com.utp.service.support.auth.security;




import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import pe.com.utp.service.support.auth.exception.oauth.CustomOauthException;


@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter  implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private AuthenticationFailureBadCredentialsEvent event;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        this.event = event;
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception {

        configurer.exceptionTranslator(exception -> {
            if (exception instanceof OAuth2Exception) {
                OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
                return ResponseEntity
                        .status(oAuth2Exception.getHttpErrorCode())
                        .body(new CustomOauthException(oAuth2Exception.getMessage(),this.event));

            } else {
                throw exception;
            }
        });
    }


}