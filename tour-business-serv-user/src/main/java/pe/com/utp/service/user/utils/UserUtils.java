package pe.com.utp.service.user.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserUtils {

    public static int removeCommasAndPeriods(String cadenaUit) {
        cadenaUit = cadenaUit.replace(Constantes.COMA, Constantes.TEXTO_VACIO);
        cadenaUit = cadenaUit.replace(Constantes.PUNTO, Constantes.TEXTO_VACIO);
        return Integer.valueOf(cadenaUit);
    }

    public static Map<String, Object> validToken(String authorization) {

        String token = authorization.replaceFirst(Constantes.BASIC, Constantes.TEXTO_VACIO);
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println("token: " + token);
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

    public static String dateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(Constantes.FORMATO_DATE);
        return formatter.format(date);
    }

}
