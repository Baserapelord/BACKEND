//package pe.com.claro.tmf.service.support.oac.repository.impl;
//
//import java.math.BigDecimal;
//import java.sql.Array;
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Struct;
//import java.sql.Timestamp;
//import java.time.OffsetDateTime;
//import java.time.ZoneId;
//import java.util.ArrayList;
//import java.sql.Date;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import lombok.extern.slf4j.Slf4j;
//import oracle.jdbc.OracleTypes;
//import pe.com.claro.maverick.core.starter.oracle.manager.MaverickDatabase;
//import pe.com.claro.tmf.service.support.oac.exception.DBException;
//import pe.com.claro.tmf.service.support.oac.repository.OACCustomerBillRepository;
//import pe.com.claro.tmf.service.support.oac.util.Constants;
//import pe.com.claro.tmf.service.support.oac.util.PropertiesExternos;
//import reactor.adapter.rxjava.RxJava2Adapter;
//import reactor.core.publisher.Flux;
//
//@Slf4j
//@Service
//public class OACCustomerBillRepositoryImpl implements OACCustomerBillRepository {
//
//	private final MaverickDatabase testDB;
//
//	@Autowired
//	private PropertiesExternos propertiesExternos;
//
//	private final String PARAMS_LOG_FORMAT="{} : {}";
//
//	public OACCustomerBillRepositoryImpl(@Autowired @Qualifier(value = "oac") MaverickDatabase testDB) {
//		this.testDB = testDB;
//	}
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Override
//	public Flux<CustomerBill> listCustomerBill(CustomerBillRequestDto customerbill) {
//		return RxJava2Adapter.flowableToFlux(testDB.builder().apply(con -> {
//		return this.listCustomerBillProcedure(con, customerbill);
//		}).flattenAsFlowable(this::processListCustomerBillResultSet).onErrorResumeNext(error -> {
//		return Flux.error(error);
//		}));
//
//	}
//
//	private CallableStatement listCustomerBillProcedure(Connection con, CustomerBillRequestDto customerbill)
//			throws DBException {
//
//		try {
//
//			logger.info("----------------- {} --------------",customerbill.toString());
//
//			String procedure = "{call APPS.TS_OAC_CL_CONSULTA_PKG.pr_estado_cuenta_openapi (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
//			Date dateDesde =null;
//			Date dateHasta =null;
//			CallableStatement cs = con.prepareCall(procedure);
//			if(customerbill.getStartDateTime()!=null) {
//			long epochMilliDesde = customerbill.getStartDateTime().toInstant().toEpochMilli();
//			dateDesde= new Date(epochMilliDesde);
//			}
//			if(customerbill.getEndDateTime()!=null) {
//			long epochMilliHasta = customerbill.getEndDateTime().toInstant().toEpochMilli();
//			dateHasta = new Date(epochMilliHasta);
//			}
//
//			log.info(procedure);
//			log.info(PARAMS_LOG_FORMAT,"pv_cod_aplicacion", customerbill.getCodApp());
//			log.info(PARAMS_LOG_FORMAT,"pv_usuario", customerbill.getId());
//			log.info(PARAMS_LOG_FORMAT,"pv_cod_fac", customerbill.getBillNo());
//			log.info(PARAMS_LOG_FORMAT,"pv_tipo_consulta", "");//customerbill.getTipoConsulta());
//			log.info(PARAMS_LOG_FORMAT,"pv_flag_solo_saldo", customerbill.getFlagSoloSaldo());
//			log.info(PARAMS_LOG_FORMAT,"pv_flag_solo_disputa", customerbill.getFlagSoloDisputa());
//			log.info(PARAMS_LOG_FORMAT,"pv_tipo_servicio", customerbill.getType());
//			log.info(PARAMS_LOG_FORMAT,"pv_cli_nro_cuenta", customerbill.getId());
//			log.info(PARAMS_LOG_FORMAT,"pv_nro_telefono", customerbill.getTelefono());
//			log.info(PARAMS_LOG_FORMAT,"pd_fecha_desde", dateDesde);
//			log.info(PARAMS_LOG_FORMAT,"pd_fecha_hasta", dateHasta);
//			log.info(PARAMS_LOG_FORMAT,"pn_tamano_pagina", Integer.parseInt(customerbill.getTamanioPagina()));
//			log.info(PARAMS_LOG_FORMAT,"pn_nro_pagina", Integer.parseInt(customerbill.getNumeroPagina()));
//
//
//			cs.setString("pv_cod_aplicacion", customerbill.getCodApp());
//			cs.setString("pv_usuario", customerbill.getId());
//			cs.setString("pv_cod_fac", customerbill.getBillNo());
//			cs.setString("pv_tipo_consulta","");// customerbill.getTipoConsulta());
//			cs.setString("pv_flag_solo_saldo", customerbill.getFlagSoloSaldo());
//			cs.setString("pv_flag_solo_disputa", customerbill.getFlagSoloDisputa());
//			cs.setString("pv_tipo_servicio", customerbill.getType());
//			cs.setString("pv_cli_nro_cuenta", customerbill.getId());
//			cs.setString("pv_nro_telefono", customerbill.getTelefono());
//			if(dateDesde!=null) {
//				cs.setDate("pd_fecha_desde", dateDesde);
//			}else {
//				cs.setNull("pd_fecha_desde", java.sql.Types.NULL );
//			}
//			if(dateHasta!=null) {
//				cs.setDate("pd_fecha_hasta", dateHasta);
//			}else {
//				cs.setNull("pd_fecha_hasta", java.sql.Types.NULL );
//			}
//
//			cs.setInt("pn_tamano_pagina", Integer.parseInt(customerbill.getTamanioPagina()));
//			cs.setInt("pn_nro_pagina", Integer.parseInt(customerbill.getNumeroPagina()));
//
//			cs.registerOutParameter(Constants.XT_ESTADO_CUENTA, OracleTypes.ARRAY, "APPS.TS_OAC_CL_EST_CTA_CA2_REC_TYPE" );
//			cs.registerOutParameter("xv_status", OracleTypes.VARCHAR);
//			cs.registerOutParameter("xv_message", OracleTypes.VARCHAR);
//
//			cs.execute();
//
//			String codError = cs.getString("xv_status");
//			String msjError = cs.getString("xv_message");
//
//			log.info(PARAMS_LOG_FORMAT,"xv_status",codError);
//			log.info(PARAMS_LOG_FORMAT,"xv_message",msjError);
//
//			if (!codError.equalsIgnoreCase("0")) {
//				throw new DBException(codError, msjError, null);
//			}
//
//			return cs;
//
//		} catch (SQLException sqlEx) {
//			throw new DBException(null, "Error General", sqlEx);
//		}
//
//	}
//
//	private List<CustomerBill> processListCustomerBillResultSet(CallableStatement cs) throws DBException {
//
//		List<CustomerBill> response = new ArrayList<>();
//
//		try {
//			if (cs.getObject(Constants.XT_ESTADO_CUENTA) != null) {
//				Array struct = (Array) cs.getObject(Constants.XT_ESTADO_CUENTA);
//				logger.info(struct.toString());
//				ResultSet rs = struct.getResultSet();
//
//				response = obtenerEstadoCuenta(rs);
//
//			}
//
//		} catch (SQLException ex) {
//
//			String codError = "";
//			String msjError = "";
//			ex.printStackTrace();
//			String excepcion = ex + "";
//			if (excepcion.contains(propertiesExternos.getErrorSqlTimeoutException())) {
//				codError = this.propertiesExternos.getCodigoGenericoErrorBdTimeOut();
//				msjError = this.propertiesExternos.getMensajeGenericoErrorBdTimeout()
//						.replace("$bd", this.propertiesExternos.getDbOac())
//						.replace("$sp", this.propertiesExternos.getSpconsultadeudaopenapi()) + ex.getMessage();
//			} else if (!codError.equalsIgnoreCase(propertiesExternos.getCodigoGenericoErrorbdInesperado())) {
//				codError = this.propertiesExternos.getCodigoGenericoErrorBdDisponilidad();
//				msjError = this.propertiesExternos.getMensajeGenericoErrorBdDisponibilidad()
//						.replace("$bd", this.propertiesExternos.getDbOac())
//						.replace("$sp", this.propertiesExternos.getSpconsultadeudaopenapi()) + ex.getMessage();
//			}
//
//			throw new DBException(codError, msjError, ex);
//
//		}
//
//		return response;
//	}
//
//	private List<CustomerBill> obtenerEstadoCuenta(ResultSet rs) throws SQLException{
//
//		List<CustomerBill> response = new ArrayList<>();
//
//		while (rs.next()) {
//			    //Struct row1 = (Struct) rs.getObject(1);
//	        Struct row = (Struct) rs.getObject(2);
//	        if (row != null) {
//	            Object[] cols = row.getAttributes();
//	            String describUbigeo= (String) cols[8];
//	            String cicloFacturacion= (String) cols[12];
//	            Array detalle_eecc = (Array)cols[16];
//	            ResultSet detalle_eecc_rs = detalle_eecc.getResultSet();
//
//	            response = obtenerDetalles(describUbigeo,cicloFacturacion,detalle_eecc_rs);
//
//	        }
//	    }
//
//		return response;
//	}
//
//	private List<CustomerBill> obtenerDetalles(String describUbigeo,String cicloFacturacion, ResultSet detalle_eecc_rs) throws SQLException{
//
//		List<CustomerBill> response = new ArrayList<>();
//		while (detalle_eecc_rs.next()) {
//        	Struct rowDetalles = (Struct) detalle_eecc_rs.getObject(2);
//        	if (rowDetalles != null) {
//        		CustomerBill customerbill = new CustomerBill();
//        		Money money= new Money();
//				Money money2= new Money();
//        		Object[] colsDetalleEecc = rowDetalles.getAttributes();
//        		String nro_documento = (String) colsDetalleEecc[1];
//        		customerbill.setBillNo(nro_documento);
//        		//logger.info(nro_documento);
//        		String tipo_moneda = (String) colsDetalleEecc[7];
//        		//logger.info(tipo_moneda);
//        		BigDecimal monto_documento_bd = (BigDecimal) colsDetalleEecc[8];
//        		Double monto_documento = monto_documento_bd.doubleValue();
//        		//logger.info(monto_documento.toString());
//        		money.setUnit(tipo_moneda);
//        		money.setValue(monto_documento);
//        		BigDecimal saldo_documento_bd = (BigDecimal) colsDetalleEecc[11];
//        		Double saldo_documento = saldo_documento_bd.doubleValue();
//        		//logger.info(saldo_documento.toString());
//        		money2.setUnit(tipo_moneda);
//        		money2.setValue(saldo_documento);
//        		Timestamp fecha_emision_t = (Timestamp) colsDetalleEecc[5];
//        		OffsetDateTime fecha_emision = OffsetDateTime.ofInstant(fecha_emision_t.toInstant(), ZoneId.of("UTC"));
//        		customerbill.setBillDate(fecha_emision);
//        		//logger.info(colsDetalleEecc[5].toString());
//        		Timestamp fecha_vencimiento_t = (Timestamp) colsDetalleEecc[6];
//        		OffsetDateTime fecha_vencimiento = OffsetDateTime.ofInstant(fecha_vencimiento_t.toInstant(), ZoneId.of("UTC"));
//        		customerbill.paymentDueDate(fecha_vencimiento);
//        		//logger.info(colsDetalleEecc[6].toString());
//        		customerbill.setAmountDue(money);
//        		customerbill.setRemainingAmount(money2);
//
//        		//characteristic
//        		customerbill.addCharacteristicItem(new Characteristic().name("describ_ubigeo").value(describUbigeo));
//        		customerbill.addCharacteristicItem(new Characteristic().name("ciclo_facturacion").value(cicloFacturacion));
//        		customerbill.addCharacteristicItem(new Characteristic().name("tipo_documento").value(colsDetalleEecc[7]!=null?(String)colsDetalleEecc[0]:""));
//        		customerbill.addCharacteristicItem(new Characteristic().name("descrip_documento").value(colsDetalleEecc[2]!=null?(String)colsDetalleEecc[2]:""));
//        		customerbill.addCharacteristicItem(new Characteristic().name("estado_documento").value(colsDetalleEecc[3]!=null?(String)colsDetalleEecc[3]:""));
//        		customerbill.addCharacteristicItem(new Characteristic().name("fecha_registro").value(formatDate(colsDetalleEecc[4])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("monto_documento").value(monto_documento.toString()));
//        		customerbill.addCharacteristicItem(new Characteristic().name("monto_fco").value(formatNumber(colsDetalleEecc[9])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("monto_finan").value(formatNumber(colsDetalleEecc[10])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("saldo_documento").value(formatNumber(colsDetalleEecc[11])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("saldo_fco").value(formatNumber(colsDetalleEecc[12])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("saldo_finan").value(formatNumber(colsDetalleEecc[13])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("monto_soles").value(formatNumber(colsDetalleEecc[14])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("monto_dolares").value(formatNumber(colsDetalleEecc[15])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("cargo").value(formatNumber(colsDetalleEecc[16])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("abono").value(formatNumber(colsDetalleEecc[17])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("saldo_cuenta").value(formatNumber(colsDetalleEecc[18])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("monto_reclamado").value(formatNumber(colsDetalleEecc[28])));
//        		customerbill.addCharacteristicItem(new Characteristic().name("telefono").value(colsDetalleEecc[29]!=null?(String)colsDetalleEecc[29]:""));
//        		customerbill.addCharacteristicItem(new Characteristic().name("descrip_extend").value(colsDetalleEecc[32]!=null?(String)colsDetalleEecc[32]:""));
//        		customerbill.addCharacteristicItem(new Characteristic().name("motivo_ajuste").value(colsDetalleEecc[34]!=null?(String)colsDetalleEecc[34]:""));
//        		customerbill.addCharacteristicItem(new Characteristic().name("descripcion_ajuste").value(colsDetalleEecc[35]!=null?(String)colsDetalleEecc[35]:""));
//        		customerbill.addCharacteristicItem(new Characteristic().name("id_onbase").value(colsDetalleEecc[36]!=null?(String)colsDetalleEecc[36]:""));
//
//
//        		response.add(customerbill);
//        	}
//        }
//		return response;
//	}
//
//	private String formatNumber(Object object) {
//		if(object==null) {return "";}
//		else {
//		return ((BigDecimal)object).toString();
//		}
//	}
//
//	private String formatDate(Object date) {
//		if(date==null) {
//			return "";
//		}
//		Timestamp f=(Timestamp)date;
//		OffsetDateTime odt=OffsetDateTime.ofInstant(f.toInstant(), ZoneId.systemDefault());
//		return odt.toString();
//
//	}
//}
