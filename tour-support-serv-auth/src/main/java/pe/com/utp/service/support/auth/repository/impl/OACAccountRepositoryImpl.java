//package pe.com.claro.tmf.service.support.oac.repository.impl;
//
//import java.math.BigDecimal;
//import java.sql.Array;
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Struct;
//import java.time.OffsetDateTime;
//import java.time.ZoneOffset;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//
//import lombok.extern.slf4j.Slf4j;
//import oracle.jdbc.OracleTypes;
//import pe.com.claro.maverick.core.starter.oracle.manager.MaverickDatabase;
//import pe.com.claro.tmf.service.support.oac.exception.DBException;
//import pe.com.claro.tmf.service.support.oac.repository.OACAccountRepository;
//import pe.com.claro.tmf.service.support.oac.util.PropertiesExternos;
//import reactor.adapter.rxjava.RxJava2Adapter;
//import reactor.core.publisher.Flux;
//
//@Slf4j
//@Service
//public class OACAccountRepositoryImpl implements OACAccountRepository {
//
//	private final MaverickDatabase testDB;
//
//	@Autowired
//	private PropertiesExternos propertiesExternos;
//
//	private final String PARAMS_LOG_FORMAT="{} : {}";
//
//	public OACAccountRepositoryImpl(@Autowired @Qualifier(value = "oac") MaverickDatabase testDB) {
//		this.testDB = testDB;
//	}
//
//	@Override
//	public Flux<FinancialAccount> listFinancialAccount(FinancialAccountRequestDto financialAccountRequest) {
//		return RxJava2Adapter.flowableToFlux(testDB.builder().apply(con -> {
//		return this.listFinancialAccountProcedure(con, financialAccountRequest);
//		}).flattenAsFlowable(this::processListFinancialAccountResultSet).onErrorResumeNext(error -> {
//		return Flux.error(error);
//		}));
//	}
//
//	private CallableStatement listFinancialAccountProcedure(Connection con, FinancialAccountRequestDto financialAccountRequest)
//			throws DBException {
//
//		try {
//
//			String procedure = "{call APPS.TS_OAC_CL_CONSULTA_DEUDA_PKG.pr_consulta_deuda_docid_ws (?,?,?,?,?,?,?,?)}";
//
//			CallableStatement cs = con.prepareCall(procedure);
//
//			log.info(procedure);
//			log.info(PARAMS_LOG_FORMAT,"pv_trx_id_ws", financialAccountRequest.getTrx_id_ws());
//			log.info(PARAMS_LOG_FORMAT,"pv_cod_aplicacion", propertiesExternos.getCodApp());
//			log.info(PARAMS_LOG_FORMAT,"pv_usuario_aplic", propertiesExternos.getUsrApp());
//			log.info(PARAMS_LOG_FORMAT,"pv_cli_tipo_doc_ident", financialAccountRequest.getRelatedPartyLegalIdType());
//			log.info(PARAMS_LOG_FORMAT,"pv_cli_nro_doc_ident", financialAccountRequest.getRelatedPartyLegalIdIdentificationId());
//
//
//
//			cs.setString("pv_trx_id_ws", financialAccountRequest.getTrx_id_ws());
//			cs.setString("pv_cod_aplicacion", propertiesExternos.getCodApp());
//			cs.setString("pv_usuario_aplic", propertiesExternos.getUsrApp());
//			cs.setString("pv_cli_tipo_doc_ident", financialAccountRequest.getRelatedPartyLegalIdType());
//			cs.setString("pv_cli_nro_doc_ident", financialAccountRequest.getRelatedPartyLegalIdIdentificationId());
//
//
//			cs.registerOutParameter("xt_deuda", OracleTypes.ARRAY, "APPS.TS_OAC_CL_QRY_DEU_CAB_REC_TYPE");
//			//cs.registerOutParameter("xt_deuda", OracleTypes.STRUCT, "APPS.TS_OAC_CL_QRY_DEU_CAB_REC_TYPE");
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
//
//	private List<FinancialAccount> processListFinancialAccountResultSet(CallableStatement cs) throws DBException {
//
//		List<FinancialAccount> response = new ArrayList<>();
//
//		try {
//			if (cs.getObject("xt_deuda") != null) {
//
//				Array arr =  (Array) cs.getObject("xt_deuda");
//				log.info("arr: " + arr.toString());
//
//				ResultSet rs = arr.getResultSet();
//
//				while(rs.next()) {
//					Object rowIndex = rs.getObject(1);
//					log.info(rowIndex + ": ");
//					Struct row = (Struct) rs.getObject(2);
//					if ( row != null ) {
//						Object[] cols  = row.getAttributes();
//						if (  cols[0] != null ) {
//
//
//
//
//
//						FinancialAccount faItem = new FinancialAccount();
//						RelatedParty relatedPartyItem = new RelatedParty();
//						//AccountBalance accountBalanceItem = new AccountBalance();
//
//						Characteristic characteristicItem1 = new Characteristic();
//						characteristicItem1.setName("debtAge");
//						characteristicItem1.setValue(cols[10] != null ? cols[10].toString() : "");
//
//						Characteristic characteristicItem2 = new Characteristic();
//						characteristicItem2.setName("totalServices");
//						characteristicItem2.setValue(cols[11] != null ? cols[11].toString() : "");
//
//						Account account = new Account();
//						account.setAccountBalance(new ArrayList<AccountBalance>());
//						//account.getAccountBalance().add(new AccountBalance());
//
//						account.setCharacteristic(new ArrayList<Characteristic>());
//						//account.getCharacteristic().add(new Characteristic());
//
//						faItem.setAccountBalance(new ArrayList<AccountBalance>());
//						faItem.setRelatedParty(new ArrayList<RelatedParty>());
//						faItem.setAccountRelationship(new ArrayList<AccountRelationship>());
//						faItem.setCharacteristic(new ArrayList<Characteristic>());
//
//						AccountBalance accountBalance3 = new AccountBalance();
//						accountBalance3.setBalanceType("mobileDebt");
//						accountBalance3.setAmount(new ArrayList<Money>());
//						BigDecimal pigDecimal = (BigDecimal) cols[3];
//		        		accountBalance3.getAmount().add(new Money());
//		        		accountBalance3.getAmount().get(0).setValue(pigDecimal.floatValue());
//		        		accountBalance3.getAmount().get(0).setUnit("PEN");
//						faItem.getAccountBalance().add(accountBalance3);
//
//						AccountBalance accountBalance4 = new AccountBalance();
//						accountBalance4.setBalanceType("fixedDebt");
//						accountBalance4.setAmount(new ArrayList<Money>());
//						pigDecimal = (BigDecimal) cols[4];
//		        		accountBalance4.getAmount().add(new Money());
//		        		accountBalance4.getAmount().get(0).setValue(pigDecimal.floatValue());
//		        		accountBalance4.getAmount().get(0).setUnit("PEN");
//						faItem.getAccountBalance().add(accountBalance4);
//
//						AccountBalance accountBalance5 = new AccountBalance();
//						accountBalance5.setBalanceType("expiredMobileDebt");
//						accountBalance5.setAmount(new ArrayList<Money>());
//						pigDecimal = (BigDecimal) cols[5];
//		        		accountBalance5.getAmount().add(new Money());
//		        		accountBalance5.getAmount().get(0).setValue(pigDecimal.floatValue());
//		        		accountBalance5.getAmount().get(0).setUnit("PEN");
//						faItem.getAccountBalance().add(accountBalance5);
//
//						AccountBalance accountBalance6 = new AccountBalance();
//						accountBalance6.setBalanceType("expiredFixedDebt");
//						accountBalance6.setAmount(new ArrayList<Money>());
//						pigDecimal = (BigDecimal) cols[6];
//		        		accountBalance6.getAmount().add(new Money());
//		        		accountBalance6.getAmount().get(0).setValue(pigDecimal.floatValue());
//		        		accountBalance6.getAmount().get(0).setUnit("PEN");
//						faItem.getAccountBalance().add(accountBalance6);
//
//						AccountBalance accountBalance7 = new AccountBalance();
//						accountBalance7.setBalanceType("writtenOffMobileDebt");
//						accountBalance7.setAmount(new ArrayList<Money>());
//						pigDecimal = (BigDecimal) cols[7];
//		        		accountBalance7.getAmount().add(new Money());
//		        		accountBalance7.getAmount().get(0).setValue(pigDecimal.floatValue());
//		        		accountBalance7.getAmount().get(0).setUnit("PEN");
//						faItem.getAccountBalance().add(accountBalance7);
//
//						AccountBalance accountBalance8 = new AccountBalance();
//						accountBalance8.setBalanceType("writtenOffFixedDebt");
//						accountBalance8.setAmount(new ArrayList<Money>());
//						pigDecimal = (BigDecimal) cols[8];
//		        		accountBalance8.getAmount().add(new Money());
//		        		accountBalance8.getAmount().get(0).setValue(pigDecimal.floatValue());
//		        		accountBalance8.getAmount().get(0).setUnit("PEN");
//						faItem.getAccountBalance().add(accountBalance8);
//
//
//						relatedPartyItem.setIndividual(new Individual());
//
//						relatedPartyItem.setName( cols[0] != null ? cols[0].toString() : "");
//						relatedPartyItem.getIndividual().setGivenName( cols[0] != null ? cols[0].toString() : "");
//						relatedPartyItem.getIndividual().setFamilyName(cols[1] != null ? cols[1].toString() : "");
//						relatedPartyItem.getIndividual().setSecondLastName(cols[2] != null ? cols[2].toString() : "");
//						relatedPartyItem.setRole("customer");
//						relatedPartyItem.setIdentifierType("RUC");
//
//						relatedPartyItem.setId( cols[9] != null ? cols[9].toString() : "" );
//
//						Array detalle = (Array) cols[12];
//						ResultSet detalle_rs = detalle.getResultSet();
//						while ( detalle_rs.next() ) {
//							Struct rowDetalles = (Struct) detalle_rs.getObject(2);
//							if (rowDetalles != null) {
//								AccountRelationship accountRelationshipItem = new AccountRelationship();
//								accountRelationshipItem.setAccount(new Account());
//								accountRelationshipItem.getAccount().setAccountBalance(new ArrayList<AccountBalance>());
//
//								Object[] colsDetalle = rowDetalles.getAttributes();
//
//								if ("FIJA".equals(colsDetalle[0].toString())) {
//									accountRelationshipItem.getAccount().setName("fixedAccount");
//								} else if (colsDetalle[0]!=null){
//									accountRelationshipItem.getAccount().setName("mobileAccount");
//								} else {
//									accountRelationshipItem.getAccount().setName(null);
//								}
//
//								accountRelationshipItem.getAccount().setId((String) colsDetalle[1]);
//
//								if ("A".equals((String) colsDetalle[2].toString())) {
//									accountRelationshipItem.getAccount().setState("Activo");
//								} else if ("D".equals((String) colsDetalle[2].toString())) {
//									accountRelationshipItem.getAccount().setState("Desactivado");
//								} else if ("I".equals((String) colsDetalle[2].toString())) {
//									accountRelationshipItem.getAccount().setState("Inactivo");
//								} else {
//									accountRelationshipItem.getAccount().setState(null);
//								}
//
//
//								//accountRelationshipItem.getAccount().setState((String) colsDetalle[2]);
//
//								AccountBalance accountBalanceItem3 = new AccountBalance();
//								accountBalanceItem3.setBalanceType("averageBilled");
//								accountBalanceItem3.setAmount(new ArrayList<Money>());
//								pigDecimal = (BigDecimal) colsDetalle[3];
//								accountBalanceItem3.getAmount().add(new Money());
//								accountBalanceItem3.getAmount().get(0).setUnit("PEN");
//								accountBalanceItem3.getAmount().get(0).setValue(pigDecimal.floatValue());
//				        		//account.getAccountBalance().add(accountBalanceItem3);
//				        		accountRelationshipItem.getAccount().getAccountBalance().add(accountBalanceItem3);
//
//								AccountBalance accountBalanceItem4 = new AccountBalance();
//								accountBalanceItem4.setBalanceType("currentDebt");
//								accountBalanceItem4.setAmount(new ArrayList<Money>());
//								pigDecimal = (BigDecimal) colsDetalle[4];
//								accountBalanceItem4.getAmount().add(new Money());
//								accountBalanceItem4.getAmount().get(0).setUnit("PEN");
//								accountBalanceItem4.getAmount().get(0).setValue(pigDecimal.floatValue());
//								accountRelationshipItem.getAccount().getAccountBalance().add(accountBalanceItem4);
//
//								AccountBalance accountBalanceItem5 = new AccountBalance();
//								accountBalanceItem5.setBalanceType("expiredDebt");
//								accountBalanceItem5.setAmount(new ArrayList<Money>());
//								pigDecimal = (BigDecimal) colsDetalle[5];
//								accountBalanceItem5.getAmount().add(new Money());
//								accountBalanceItem5.getAmount().get(0).setUnit("PEN");
//								accountBalanceItem5.getAmount().get(0).setValue(pigDecimal.floatValue());
//								accountRelationshipItem.getAccount().getAccountBalance().add(accountBalanceItem5);
//
//								AccountBalance accountBalanceItem6 = new AccountBalance();
//								accountBalanceItem6.setBalanceType("writtenOffDebt");
//								accountBalanceItem6.setAmount(new ArrayList<Money>());
//								pigDecimal = (BigDecimal) colsDetalle[6];
//								accountBalanceItem6.getAmount().add(new Money());
//								accountBalanceItem6.getAmount().get(0).setUnit("PEN");
//								accountBalanceItem6.getAmount().get(0).setValue(pigDecimal.floatValue());
//								accountRelationshipItem.getAccount().getAccountBalance().add(accountBalanceItem6);
//
//
//								Characteristic cantidadServicios = new Characteristic();
//								cantidadServicios.setName("serviceQuantity");
//								cantidadServicios.setValue(colsDetalle[7].toString());
//
//								Characteristic centralRiesgo = new Characteristic();
//								centralRiesgo.setName("sendedRiskCentral");
//								centralRiesgo.setValue((String) colsDetalle[8]);
//
//								accountRelationshipItem.getAccount().setCharacteristic(new ArrayList<Characteristic>());
//								accountRelationshipItem.getAccount().getCharacteristic().add(cantidadServicios);
//								accountRelationshipItem.getAccount().getCharacteristic().add(centralRiesgo);
//
//								//accountRelationshipItem.setAccount(account);
//								faItem.getAccountRelationship().add(accountRelationshipItem);
//							}
//						}
//						faItem.getRelatedParty().add(relatedPartyItem);
//
//						faItem.getCharacteristic().add(characteristicItem1);
//						faItem.getCharacteristic().add(characteristicItem2);
//
//						response.add(faItem);
//					}
//					}
//				}
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
//		}
//		return response;
//	}
//
//
//
//	private OffsetDateTime dealOffsetTime(java.sql.Date date) {
//		if (date == null) {
//			return null;
//		}
//		return new Date(date.getTime()).toInstant().atOffset(ZoneOffset.UTC);
//	}
//
//}
