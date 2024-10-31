package pe.com.utp.service.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.com.utp.service.user.dto.BodyResponse;
import pe.com.utp.service.user.dto.UserResponse;
import pe.com.utp.service.user.exception.DBException;
import pe.com.utp.service.user.exception.ServiceException;

import java.io.IOException;
import java.util.List;

@Validated
public interface UserApi {

    @GetMapping(value = "/listUser", produces = { "application/json;charset=utf-8" })
    ResponseEntity<List<UserResponse>>  listUsuarios(
            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestHeader(value = "correlatorId", required = false) String correlatorId,
            @RequestHeader(value = "messageId", required = false) String messageId,
            @RequestHeader(value = "requestDate", required = false) String requestDate,
            @RequestHeader(value = "originSystem", required = false) String originSystem,
            @RequestHeader(value = "targetSystem", required = false) String targetSystem,
            @RequestHeader(value = "country", required = false) String country

    ) throws ServiceException;

    @PostMapping(value = "/createUser" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<BodyResponse> createUser(
            @RequestPart("usuario") String usuario,
            @RequestPart("imagen") MultipartFile imagen
    ) throws DBException, JsonProcessingException;
    @PutMapping(value = "/updateUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    ) throws DBException, JsonProcessingException;


    @GetMapping(value = "/imgcliente/{fileName}")
    ResponseEntity<byte[]>  getImage(@PathVariable String fileName) throws IOException;
}



