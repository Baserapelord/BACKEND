package pe.com.utp.service.support.user.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import pe.com.utp.service.support.user.business.UsuarioService;
import pe.com.utp.service.support.user.dto.BodyResponse;
import pe.com.utp.service.support.user.dto.UsuarioRequest;
import pe.com.utp.service.support.user.dto.UsuarioResponse;
import pe.com.utp.service.support.user.exception.DBException;
import pe.com.utp.service.support.user.util.UserUtil;

import javax.activation.FileTypeMap;

@RestController
@RequestMapping("/tours-support-serv-user/v1")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UsuarioService usuarioService;

    @PostMapping(value = "/createUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BodyResponse> createUser(
//            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestHeader(value = "correlatorId", required = false) String correlatorId,

            @RequestPart("usuario") String usuario,
            @RequestPart("imagen") MultipartFile imagen
    ) throws DBException, JsonProcessingException {
        System.out.println("usuario11:"+usuario);
        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioRequest request = objectMapper.readValue(usuario, UsuarioRequest.class);

        BodyResponse response = new BodyResponse();

//
//        Map<String, Object> responseToken = UserUtil.validToken(authorization);
//
//        Boolean estado = (Boolean) responseToken.get("estado");
//        String rol = (String) responseToken.get("rol");


        try {
            response = usuarioService.createUser(request, correlatorId,imagen);
            return new ResponseEntity<>(response, HttpStatus.OK);
//            if (estado) {
//                if (rol.equals("Administrador")) {
//                    response = usuarioService.createUser(request, correlatorId,imagen);
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                } else {
//                    response.setIdTransaction(correlatorId);
//                    response.setCode("2");
//                    response.setMessage("Tu rol no tiene acceso a este servicio");
//                }
//            } else {
//                response.setIdTransaction(correlatorId);
//                response.setCode("1");
//                response.setMessage((String) responseToken.get("mensaje"));
//            }

        } catch (DBException e) {
            response.setIdTransaction(correlatorId);
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());

            return new ResponseEntity<BodyResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



    @PutMapping(value = "/updateUser", produces = {"application/json;charset=utf-8"})
    public ResponseEntity<BodyResponse> updateUser(
            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestHeader(value = "correlatorId", required = false) String correlatorId,
            @RequestHeader(value = "messageId", required = false) String messageId,
            @RequestHeader(value = "requestDate", required = false) String requestDate,
            @RequestHeader(value = "originSystem", required = false) String originSystem,
            @RequestHeader(value = "targetSystem", required = false) String targetSystem,
            @RequestHeader(value = "country", required = false) String country,
            @RequestParam("usuario")  String usuario,
            @RequestParam("imagen") MultipartFile imagen
    ) throws DBException, JsonProcessingException {
        System.out.println("usuario11: "+usuario);
        ObjectMapper objectMapper = new ObjectMapper();
        UsuarioRequest request = objectMapper.readValue(usuario, UsuarioRequest.class);

        BodyResponse response = new BodyResponse();

        Map<String, Object> responseToken = UserUtil.validToken(authorization);

        Boolean estado = (Boolean) responseToken.get("estado");
        String rol = (String) responseToken.get("rol");


        try {
            if (estado) {
                if (rol.equals("Cliente")) {
                    response = usuarioService.updateUser(request, correlatorId, imagen);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setIdTransaction(correlatorId);
                    response.setCode("2");
                    response.setMessage("Tu rol no tiene acceso a este servicio");
                }
            } else {
                response.setIdTransaction(correlatorId);
                response.setCode("1");
                response.setMessage((String) responseToken.get("mensaje"));
            }

        } catch (DBException e) {
            response.setIdTransaction(correlatorId);
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());

            return new ResponseEntity<BodyResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<BodyResponse>(response, HttpStatus.UNAUTHORIZED);

    }

    @GetMapping(value = "/listUser", produces = {"application/json;charset=utf-8"})
    public ResponseEntity<Object> listUser(
            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestHeader(value = "correlatorId", required = false) String correlatorId,
            @RequestHeader(value = "messageId", required = false) String messageId,
            @RequestHeader(value = "requestDate", required = false) String requestDate,
            @RequestHeader(value = "originSystem", required = false) String originSystem,
            @RequestHeader(value = "targetSystem", required = false) String targetSystem,
            @RequestHeader(value = "country", required = false) String country
    ){
        List<UsuarioResponse> response = new ArrayList<UsuarioResponse>();
        BodyResponse responseError = new BodyResponse();
        Map<String, Object> responseToken = UserUtil.validToken(authorization);
        Boolean estado = (Boolean) responseToken.get("estado");
        String rol = (String) responseToken.get("rol");
        if (estado) {
            if (rol.equals("Administrador")) {

                response = usuarioService.listUser();
                System.out.println("RESPONSE2:"+response);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                responseError.setIdTransaction(correlatorId);
                responseError.setCode("2");
                responseError.setMessage("Tu rol no tiene acceso a este servicio");
            }
        } else {
            responseError.setIdTransaction(correlatorId);
            responseError.setCode("1");
            responseError.setMessage((String) responseToken.get("mensaje"));
        }

        return new ResponseEntity<Object>(responseError, HttpStatus.UNAUTHORIZED);
    }



    @DeleteMapping(value = "/deleteUser/{idUser}", produces = {"application/json;charset=utf-8"})
    public ResponseEntity<BodyResponse> deleteUser(
            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestHeader(value = "correlatorId", required = false) String correlatorId,
            @RequestHeader(value = "messageId", required = false) String messageId,
            @RequestHeader(value = "requestDate", required = false) String requestDate,
            @RequestHeader(value = "originSystem", required = false) String originSystem,
            @RequestHeader(value = "targetSystem", required = false) String targetSystem,
            @RequestHeader(value = "country", required = false) String country,
            @PathVariable Long idUser
    ) throws DBException, JsonProcessingException {


        BodyResponse response = new BodyResponse();

        Map<String, Object> responseToken = UserUtil.validToken(authorization);

        Boolean estado = (Boolean) responseToken.get("estado");
        String rol = (String) responseToken.get("rol");


        try {
            if (estado) {
                if (rol.equals("Administrador")) {
                    response = usuarioService.deleteUser(idUser, correlatorId);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    response.setIdTransaction(correlatorId);
                    response.setCode("2");
                    response.setMessage("Tu rol no tiene acceso a este servicio");
                }
            } else {
                response.setIdTransaction(correlatorId);
                response.setCode("1");
                response.setMessage((String) responseToken.get("mensaje"));
            }

        } catch (DBException e) {
            response.setIdTransaction(correlatorId);
            response.setCode(e.getCode());
            response.setMessage(e.getMessage());

            return new ResponseEntity<BodyResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<BodyResponse>(response, HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/imgcliente/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
//        System.out.println("s:"+fileName);
//        // Obtener la imagen del archivo
//        Path imagePath = Paths.get("src/main/resources/imagenes/usuarios/" +fileName);
//        File imageFile = imagePath.toFile();
//        System.out.println("s2:"+imageFile);
//
//        // Verificar si el archivo existe y es accesible
//        if (Files.exists(imagePath) && Files.isReadable(imagePath)) {
//            byte[] imageBytes = Files.readAllBytes(imagePath);
//
//            // Obtener el tipo de contenido según el formato de la imagen
//            String contentType = FileTypeMap.getDefaultFileTypeMap().getContentType(imageFile);
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(contentType))
//                    .body(imageBytes);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
        // Ruta a la imagen (ajusta según tu proyecto)
        String imagePath = "src/main/resources/imagenes/usuarios/"+fileName;

        // Leer la imagen en un arreglo de bytes
        byte[] imageData = Files.readAllBytes(Paths.get(imagePath));

        // Configurar la respuesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageData);

    }

}
