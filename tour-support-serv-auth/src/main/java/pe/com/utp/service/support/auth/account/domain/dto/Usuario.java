package pe.com.utp.service.support.auth.account.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
@Table(name = "usuario")
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idUsuario;
    @NotNull
    @Column(name = "nombre")
    private String nombre;
    @NotNull
    @Column(name = "apellido_paterno")
    private String apellidoPaterno;
    @NotNull
    @Column(name = "apellido_materno")
    private String apellidoMaterno;
    @NotNull
    @Column(name = "correo")
    private String correo;
    @NotNull
    @Column(name = "telefono")
    private String telefono;
    @NotNull
    @Column(name = "contrasena")
    private String contrasena;
    @NotNull
    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;
    @NotNull
    @Column(name = "fecha_registro")
    private String fechaRegistro;
    @NotNull
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "estado")
    private Boolean estado;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id")
    private Rol rol;
}