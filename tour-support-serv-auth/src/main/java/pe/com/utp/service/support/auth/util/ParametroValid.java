package pe.com.utp.service.support.auth.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParametroValid {

    private static final Logger LOG = LoggerFactory.getLogger(ParametroValid.class);




    private static String validarFecha(String request) {

        String valid = "";
        if (request == null) {
            return Constants.ES_NULO;
        }

        // Expresión regular para validar el formato de fecha
        String regex = "\\d{4}-\\d{2}-\\d{2}";

        // Verifica si el string coincide con el formato de fecha
        if (Pattern.matches(regex, request)) {

            // El formato es correcto, ahora verifica si la fecha es válida
            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMATO_FECHA);
            dateFormat.setLenient(false);
            try {
                dateFormat.parse(request);

            } catch (ParseException e) {
                valid = e.getMessage();
            }
        }
        return valid;
    }

    public static Boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile(Constants.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}



