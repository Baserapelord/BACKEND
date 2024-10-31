package pe.com.utp.service.user.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.com.utp.service.user.config.FeignConfiguration;
import pe.com.utp.service.user.dto.BodyResponse;
import pe.com.utp.service.user.dto.UserResponse;

import java.util.List;
///tours-support-serv-user/v1
@FeignClient(name = "tours-support-serv-user", configuration = FeignConfiguration.class)
public interface UserServiceClient {
    @GetMapping("/tours-support-serv-user/v1/listUser")
    List<UserResponse> getListUsers(
            @RequestHeader(value = "Authorization", required = true) String authorization
    );

    @PostMapping(value="/tours-support-serv-user/v1/createUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BodyResponse createUser(
//            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestHeader(value = "correlatorId", required = false) String correlatorId,
            @RequestPart("usuario") String usuario,
            @RequestPart("imagen") MultipartFile imagen
    );
    @PutMapping(value="/tours-support-serv-user/v1/updateUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BodyResponse updateUser(
            @RequestHeader(value = "Authorization", required = true) String authorization,
            @RequestPart("usuario") String usuario,
            @RequestPart("imagen") MultipartFile imagen
    );


    @GetMapping("/tours-support-serv-user/v1/imgcliente/{fileName}")
    byte[] getImagenCliente(
            @PathVariable String fileName
    );


}
