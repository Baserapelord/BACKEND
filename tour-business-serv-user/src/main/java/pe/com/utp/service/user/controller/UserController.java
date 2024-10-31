package pe.com.utp.service.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.com.utp.service.user.dto.BodyResponse;
import pe.com.utp.service.user.dto.HeaderRequest;
import pe.com.utp.service.user.dto.UserResponse;
import pe.com.utp.service.user.exception.DBException;
import pe.com.utp.service.user.exception.ServiceException;
import pe.com.utp.service.user.feignclient.UserServiceClient;
import pe.com.utp.service.user.utils.UserUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tours-business-serv-user/v1")
public class UserController implements UserApi {
    @Autowired
    UserServiceClient userServiceClient;

    @Override
    public ResponseEntity<List<UserResponse>> listUsuarios(
            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestHeader(value = "correlatorId", required = false) String correlatorId,
            @RequestHeader(value = "messageId", required = false) String messageId,
            @RequestHeader(value = "requestDate", required = false) String requestDate,
            @RequestHeader(value = "originSystem", required = false) String originSystem,
            @RequestHeader(value = "targetSystem", required = false) String targetSystem,
            @RequestHeader(value = "country", required = false) String country

    ) throws ServiceException {
        List<UserResponse> listUser = userServiceClient.getListUsers(authorization);
        return ResponseEntity.ok(listUser);
    }

    @Override
    public  ResponseEntity<BodyResponse> createUser(
            @RequestPart("usuario") String usuario,
            @RequestPart("imagen") MultipartFile imagen
    ) throws DBException, JsonProcessingException {
        System.out.println("usuario1:"+usuario);
        System.out.println("imagen:"+imagen);

        // Log the image content type and size
        System.out.println("Imagen nombre: " + imagen.getOriginalFilename());
        System.out.println("Imagen tamaño: " + imagen.getSize());
        System.out.println("Imagen tipo de contenido: " + imagen.getContentType());


        BodyResponse response = userServiceClient.createUser("1", usuario, imagen);
        System.out.println("response2_:"+response);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<BodyResponse> updateUser(
            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestHeader(value = "correlatorId", required = false) String correlatorId,
            @RequestHeader(value = "messageId", required = false) String messageId,
            @RequestHeader(value = "requestDate", required = false) String requestDate,
            @RequestHeader(value = "originSystem", required = false) String originSystem,
            @RequestHeader(value = "targetSystem", required = false) String targetSystem,
            @RequestHeader(value = "country", required = false) String country,
            @RequestPart("usuario") String usuario,
            @RequestPart("imagen") MultipartFile imagen
    ) throws DBException, JsonProcessingException {
        return ResponseEntity.ok(userServiceClient.updateUser(authorization,usuario,imagen));
    }

    @Override
    public ResponseEntity<byte[]> getImage(String fileName) throws IOException {
        return ResponseEntity.ok(userServiceClient.getImagenCliente(fileName));
    }
}
