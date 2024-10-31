/*
package pe.com.utp.service.support.payroll.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import pe.com.utp.service.support.payroll.dto.BodyResponse;
import pe.com.utp.service.support.payroll.exception.DBException;
import pe.com.utp.service.support.payroll.model.Planilla;
import pe.com.utp.service.support.payroll.repository.PayrollRepository;
import pe.com.utp.service.support.payroll.repository.PayrollRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Slf4j
@Service
public class PayrollRepositoryImpl implements PayrollRepository {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	@Transactional
	public BodyResponse register(Planilla request) throws DBException {
		BodyResponse response = new BodyResponse();
		try {
			// Lógica para registrar la planilla en la base de datos
			entityManager.persist(request);
			return response;
		} catch (Exception e) {
			// Lanza tu excepción personalizada en caso de error
			throw new DBException("DB_ERR", "Error al registrar la planilla", e);
		}

		return response;
	}

}
*/
