//package pe.com.claro.tmf.service.support.oac.util;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.stereotype.Component;

/**
 * Clase que contiene los valores del properties de acuerdo a su key.
 * 
 * @author Jose jara.
 */
//@Component
//@RefreshScope
//public class PropertiesExternos {

//	@Value("${db.oac}")
//	private String dbOac;
//
//	@Value("${db.apps.owner}")
//	private String dbAppsOwner;
//
//	@Value("${sp.apps.consultadeudaopenapi}")
//	private String spconsultadeudaopenapi;
//
//	@Value("${db.error.SqlTimeoutException}")
//	private String errorSqlTimeoutException;
//
//	@Value("${codigo.generico.error.bd.timeout}")
//	private String codigoGenericoErrorBdTimeOut;
//
//	@Value("${codigo.generico.error.bd.disponibilidad}")
//	private String codigoGenericoErrorBdDisponilidad;
//
//	@Value("${mensaje.generico.error.bd.timeout}")
//	private String mensajeGenericoErrorBdTimeout;
//
//	@Value("${mensaje.generico.error.bd.disponibilidad}")
//	private String mensajeGenericoErrorBdDisponibilidad;
//
//	@Value("${relatedParty.role}")
//	private String relatedPartyRole;
//
//	@Value("${characteristic.name}")
//	private String characteristicName;
//
//	@Value("${codigo.generico.error.bd.inesperado}")
//	private String codigoGenericoErrorbdInesperado;
//
//	@Value("${codigo.generico.error.idf01}")
//	private String codigoGenericoErrorIdf01;
//
//	@Value("${mensaje.generico.error.idf01}")
//	private String mensajeGenericoErrorIdf01;
//
//	@Value("${application.properties.codapp:TMFORUM}")
//	private String codApp;
//
//
//	@Value("${application.properties.usrapp:USRTMF}")
//	private String usrApp;
//
//	public String getErrorSqlTimeoutException() {
//		return errorSqlTimeoutException;
//	}
//
//	public void setErrorSqlTimeoutException(String errorSqlTimeoutException) {
//		this.errorSqlTimeoutException = errorSqlTimeoutException;
//	}
//
//	public String getCodigoGenericoErrorBdDisponilidad() {
//		return codigoGenericoErrorBdDisponilidad;
//	}
//
//	public void setCodigoGenericoErrorBdDisponilidad(String codigoGenericoErrorBdDisponilidad) {
//		this.codigoGenericoErrorBdDisponilidad = codigoGenericoErrorBdDisponilidad;
//	}
//
//	public String getMensajeGenericoErrorBdDisponibilidad() {
//		return mensajeGenericoErrorBdDisponibilidad;
//	}
//
//	public void setMensajeGenericoErrorBdDisponibilidad(String mensajeGenericoErrorBdDisponibilidad) {
//		this.mensajeGenericoErrorBdDisponibilidad = mensajeGenericoErrorBdDisponibilidad;
//	}
//
//
//	public String getCodigoGenericoErrorBdTimeOut() {
//		return codigoGenericoErrorBdTimeOut;
//	}
//
//	public void setCodigoGenericoErrorBdTimeOut(String codigoGenericoErrorBdTimeOut) {
//		this.codigoGenericoErrorBdTimeOut = codigoGenericoErrorBdTimeOut;
//	}
//
//	public String getMensajeGenericoErrorBdTimeout() {
//		return mensajeGenericoErrorBdTimeout;
//	}
//
//	public void setMensajeGenericoErrorBdTimeout(String mensajeGenericoErrorBdTimeout) {
//		this.mensajeGenericoErrorBdTimeout = mensajeGenericoErrorBdTimeout;
//	}
//
//	public String getRelatedPartyRole() {
//		return relatedPartyRole;
//	}
//
//	public void setRelatedPartyRole(String relatedPartyRole) {
//		this.relatedPartyRole = relatedPartyRole;
//	}
//
//	public String getCharacteristicName() {
//		return characteristicName;
//	}
//
//	public void setCharacteristicName(String characteristicName) {
//		this.characteristicName = characteristicName;
//	}
//
//	public String getCodigoGenericoErrorbdInesperado() {
//		return codigoGenericoErrorbdInesperado;
//	}
//
//	public void setCodigoGenericoErrorbdInesperado(String codigoGenericoErrorbdInesperado) {
//		this.codigoGenericoErrorbdInesperado = codigoGenericoErrorbdInesperado;
//	}
//
//	public String getCodigoGenericoErrorIdf01() {
//		return codigoGenericoErrorIdf01;
//	}
//
//	public void setCodigoGenericoErrorIdf01(String codigoGenericoErrorIdf01) {
//		this.codigoGenericoErrorIdf01 = codigoGenericoErrorIdf01;
//	}
//
//	public String getMensajeGenericoErrorIdf01() {
//		return mensajeGenericoErrorIdf01;
//	}
//
//	public void setMensajeGenericoErrorIdf01(String mensajeGenericoErrorIdf01) {
//		this.mensajeGenericoErrorIdf01 = mensajeGenericoErrorIdf01;
//	}
//
//	public String getDbOac() {
//		return dbOac;
//	}
//
//	public void setDbOac(String dbOac) {
//		this.dbOac = dbOac;
//	}
//
//	public String getDbAppsOwner() {
//		return dbAppsOwner;
//	}
//
//	public void setDbAppsOwner(String dbAppsOwner) {
//		this.dbAppsOwner = dbAppsOwner;
//	}
//
//	public String getSpconsultadeudaopenapi() {
//		return spconsultadeudaopenapi;
//	}
//
//	public void setSpconsultadeudaopenapi(String spconsultadeudaopenapi) {
//		this.spconsultadeudaopenapi = spconsultadeudaopenapi;
//	}
//
//
//	public String getCodApp() {
//		return codApp;
//	}
//
//	public void setCodApp(String codApp) {
//		this.codApp = codApp;
//	}
//
//	public String getUsrApp() {
//		return usrApp;
//	}
//
//	public void setUsrApp(String usrApp) {
//		this.usrApp = usrApp;
//	}
//
//}
