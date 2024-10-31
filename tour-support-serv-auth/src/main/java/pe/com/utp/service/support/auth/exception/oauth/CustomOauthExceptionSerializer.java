package pe.com.utp.service.support.auth.exception.oauth;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import pe.com.utp.service.support.auth.account.domain.dto.Usuario;
import pe.com.utp.service.support.auth.repository.UsuarioRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
;import static pe.com.utp.service.support.auth.util.ParametroValid.validarEmail;


@Component
public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException>  {

    @Autowired
    UsuarioRepository usuarioRepository;

    public CustomOauthExceptionSerializer() {
        super(CustomOauthException.class);
    }

    @Override
    public void serialize(CustomOauthException value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        AuthenticationFailureBadCredentialsEvent event = value.getEvent();
        String username = (String) event.getAuthentication().getPrincipal();
        Usuario existeUsuario = usuarioRepository.findByCorreo(username);

        String tipoFallo = "Contraseña Incorrecta";

        if(!validarEmail(username)){
            tipoFallo = "Email no Válido";
        }else if(existeUsuario==null){
            tipoFallo = "El Usuario no Existe";
        }


        jsonGenerator.writeNumberField("code", value.getHttpErrorCode());
        jsonGenerator.writeBooleanField("status", false);
        jsonGenerator.writeObjectField("respuesta", tipoFallo);
        jsonGenerator.writeObjectField("errors", Arrays.asList(value.getOAuth2ErrorCode(),value.getMessage()));
        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                jsonGenerator.writeStringField(key, add);
            }
        }
        jsonGenerator.writeEndObject();
    }


}