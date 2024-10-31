package pe.com.utp.service.support.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserUtil {

    public static String validStringNullOrEmpty(String string) {

        return (Objects.isNull(string) || string.isEmpty()) ? null : string;

    }

    public static Map<String, Object> validToken(String authorization) {

        String token = authorization.replaceFirst(Constantes.BASIC, Constantes.TEXTO_VACIO);
        Map<String, Object> response = new HashMap<>();

        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(Constantes.SECRET_KEY.getBytes()))  // Convertir clave a Base64
                    .parseClaimsJws(token)
                    .getBody();

            System.out.println(claims);
            response.put("id", claims.getId());
            response.put("user_name", claims.get("user_name", String.class));
            response.put("rol", claims.get("descripcion rol", String.class));
            response.put("fecha y hora", claims.get("fecha y hora", String.class));
            response.put("expiration", claims.getExpiration());
            response.put("estado", true);

        } catch (Exception e) {
            response.put("mensaje", "Token no válido o clave incorrecta");
            response.put("estado", false);
        }

        return response;
    }

    public static Date convertStringToDate(String dateString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Manejo de errores
        }
    }

    public static String guardarImagen( MultipartFile imagen, String correo){

        try {
            // Ruta de destino en la carpeta resources
            String uploadDir = "src/main/resources/imagenes/usuarios/";
            Path uploadPath = Paths.get(uploadDir);

            // Crear el directorio si no existe
            Files.createDirectories(uploadPath);

            // Obtener el nombre original del archivo
            String imagenName = correo +imagen.getOriginalFilename();

            // Construir la ruta completa
            Path filePath = uploadPath.resolve(imagenName);

            // Guardar el archivo
            Files.copy(imagen.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);



            return imagenName;
        } catch (IOException e) {
            return "Error al subir el archivo: " + e.getMessage();
        }

    }

    public static void eliminarImagen(String nombreImg) {
        try {
            // Ruta base de los archivos
            String uploadDir = "src/main/resources/imagenes/usuarios/";
            Path uploadPath = Paths.get(uploadDir);

            // Construir la ruta completa del archivo a eliminar
            Path filePath = uploadPath.resolve(nombreImg);

            // Eliminar el archivo
            Files.delete(filePath);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

}
