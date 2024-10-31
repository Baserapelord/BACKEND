package pe.com.utp.service.support.user.business.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import pe.com.utp.service.support.user.business.UsuarioService;
import pe.com.utp.service.support.user.dto.BodyResponse;
import pe.com.utp.service.support.user.dto.UsuarioRequest;
import pe.com.utp.service.support.user.dto.UsuarioResponse;
import pe.com.utp.service.support.user.exception.DBException;
import pe.com.utp.service.support.user.model.Rol;
import pe.com.utp.service.support.user.model.User;
import pe.com.utp.service.support.user.repository.UserRepository;
import pe.com.utp.service.support.user.util.UserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

	private UserRepository repository;
	BCryptPasswordEncoder encryp = new BCryptPasswordEncoder();
	public UsuarioServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public BodyResponse createUser(UsuarioRequest request, String correlatorId, MultipartFile imagen) throws DBException{

		BodyResponse response = new BodyResponse();

		try {
			String img = UserUtil.guardarImagen(imagen, request.getCorreo());
			request.setImagen(img);
			request.setContrasena(encryp.encode(request.getContrasena()));
			System.out.println("user1: "+request);
			repository.save(setUser(request));

			response.setIdTransaction(correlatorId);
			response.setCode("0");
			response.setMessage("Proceso exitoso");

		} catch (DataAccessException e) {
			throw new DBException("-1", "[Error]: no se pudo registrar el usuario en la base de datos 'tours', [DETALLE]: " + e.getMessage(), e);
		}

        return response;
	}

	@Override
	public BodyResponse updateUser(UsuarioRequest request, String correlatorId, MultipartFile imagen) throws DBException {
		BodyResponse response = new BodyResponse();

		try {
			UserUtil.eliminarImagen(request.getImagen());
			String img = UserUtil.guardarImagen(imagen, request.getImagen());
			request.setImagen(img);

			request.setContrasena(encryp.encode(request.getContrasena()));
			System.out.println("user2: "+request);
			User user=repository.save(setUser(request));
			System.out.println("user3: "+user);
			response.setIdTransaction(correlatorId);
			response.setCode("0");
			response.setMessage("Proceso exitoso");

		} catch (DataAccessException e) {
			throw new DBException("-1", "[Error]: no se pudo actualizar el usuario en la base de datos 'tours', [DETALLE]: " + e.getMessage(), e);
		}

		return response;
	}

	@Override
	public BodyResponse deleteUser(Long idUser, String correlatorId) throws DBException {
		BodyResponse response = new BodyResponse();

		try {
			User user = repository.findById(idUser).orElse(null);

			user.setEstado(false);
			repository.save(user);

			response.setIdTransaction(correlatorId);
			response.setCode("0");
			response.setMessage("Proceso exitoso");

		} catch (DataAccessException e) {
			throw new DBException("-1", "[Error]: no se pudo eliminar el usuario en la base de datos 'quinta_categoria', [DETALLE]: " + e.getMessage(), e);
		}

		return response;
	}

	@Override
	public List<UsuarioResponse> listUser() {
		List<User> listaUsuarios = repository.findAll();

		List<UsuarioResponse> listaUsuariosResponse =
				listaUsuarios.stream()
						.map(this::setUserResponse)
						.collect(Collectors.toList());

		return listaUsuariosResponse;
	}


	public User setUser(UsuarioRequest request) {
		User user = new User();
		user.setIdUsuario(Long.parseLong(request.getIdUsuario()));
		user.setNombre(request.getNombre());
		user.setApellidoPaterno(request.getApellidoPaterno());
		user.setApellidoMaterno(request.getApellidoMaterno());
		user.setCorreo(request.getCorreo());
		user.setTelefono(request.getTelefono());
		user.setContrasena(request.getContrasena());
		user.setFechaNacimiento(request.getFechaNacimiento());
		user.setFechaRegistro(request.getFechaRegistro());
		user.setImagen(request.getImagen());
		System.out.println("estado1: "+request.getEstado());
		user.setEstado(Boolean.parseBoolean(request.getEstado()));
		System.out.println("estado2: "+user.getEstado());
		user.setRol(new Rol(2L, ""));
		return user;
	}

	public UsuarioResponse setUserResponse(User user) {
		UsuarioResponse userResponse = new UsuarioResponse();

		userResponse.setIdUsuario(String.valueOf(user.getIdUsuario()));
		userResponse.setNombre(user.getNombre());
		userResponse.setApellidoPaterno(user.getApellidoPaterno());
		userResponse.setApellidoMaterno(user.getApellidoMaterno());
		userResponse.setCorreo(user.getCorreo());
		userResponse.setTelefono(user.getTelefono());
		userResponse.setFechaNacimiento(user.getFechaNacimiento());
		userResponse.setFechaRegistro(user.getFechaRegistro());
		userResponse.setImagen(user.getImagen());

		userResponse.setEstado(String.valueOf(user.getEstado()));
		userResponse.setRol(user.getRol().getTipoRol());
		return userResponse;
	}

}
