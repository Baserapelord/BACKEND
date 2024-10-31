package pe.com.utp.service.support.user.dto;

import lombok.*;
import pe.com.utp.service.support.user.model.Rol;


import jakarta.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UsuarioRequest {

    private String idUsuario;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String contrasena;
    private String telefono;
    private String fechaNacimiento;
    private String fechaRegistro;
    private String imagen;
    private String estado;
    private String idRol;



}
