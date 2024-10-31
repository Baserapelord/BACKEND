package pe.com.utp.service.support.user.business;

import org.springframework.web.multipart.MultipartFile;
import pe.com.utp.service.support.user.dto.BodyResponse;
import pe.com.utp.service.support.user.dto.UsuarioRequest;
import pe.com.utp.service.support.user.dto.UsuarioResponse;
import pe.com.utp.service.support.user.exception.DBException;

import java.util.List;


public interface UsuarioService {

	public BodyResponse createUser(UsuarioRequest request, String correlatorId, MultipartFile imagen) throws DBException;
	public BodyResponse updateUser(UsuarioRequest request, String correlatorId, MultipartFile imagen) throws DBException;
	public BodyResponse deleteUser(Long idUser, String correlatorId) throws DBException;
	public List<UsuarioResponse> listUser();

}
