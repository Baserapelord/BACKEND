package pe.com.utp.service.support.auth.security;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import pe.com.utp.service.support.auth.account.domain.dto.Usuario;
import pe.com.utp.service.support.auth.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private JwtTokenStore store;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtAccessTokenConverter accessTokenConv;
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // TODO Auto-generated method stub
        super.configure(security);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("user")
                .secret(new BCryptPasswordEncoder().encode("user"))
                .authorizedGrantTypes("password","authorization_code","refresh_token","implicit")
                .scopes("read","write","trust")
                .accessTokenValiditySeconds(24 * 60 * 60)
                .refreshTokenValiditySeconds(5 * 60 * 60);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        LocalDateTime fechaHoraActual = LocalDateTime.now();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraActualString = fechaHoraActual.format(formato);

        TokenEnhancerChain tokenChain = new TokenEnhancerChain();
        tokenChain.setTokenEnhancers(Arrays.asList(new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

                String email = authentication.getName();
                Usuario usuario = usuarioRepo.findByCorreo(email);

                Map<String, Object> infoAdicional = new HashMap<>();
                infoAdicional.put("id", usuario.getIdUsuario());
                infoAdicional.put("nombre", usuario.getNombre());
                infoAdicional.put("apellido_paterno", usuario.getApellidoPaterno());
                infoAdicional.put("apellido_materno", usuario.getApellidoMaterno());
                infoAdicional.put("imagen", usuario.getImagen());
                infoAdicional.put("email", email);

                infoAdicional.put("fecha y hora", fechaHoraActualString);

                infoAdicional.put("descripcion rol", usuario.getRol().getTipoRol());

                System.out.println("usuario1: "  + usuario);
//
                infoAdicional.put("id_"+usuario.getRol().getTipoRol().toLowerCase(), usuario.getIdUsuario());


                DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);

                token.setAdditionalInformation(infoAdicional);

                return token;
            }
        },accessTokenConv));

        endpoints.tokenStore(store)
                .authenticationManager(manager)
                .accessTokenConverter(accessTokenConv)
                .tokenEnhancer(tokenChain);
    }


}
