//package pe.com.claro.tmf.service.support.oac.web;
//
//import java.time.OffsetDateTime;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import pe.com.claro.tmf.service.support.oac.business.CustomerBillService;
//import reactor.core.publisher.Flux;
//
//@RestController
//@RequestMapping("/customer")
//public class CustomerBillController {
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	CustomerBillService customerBillService;
//
//	@GetMapping
//	public ResponseEntity<Flux<CustomerBill>> listCustomerBill(
//			@ApiParam(value = "A secure token chain that indicates that it is an authorized request.") @RequestHeader(value = "Authorization", required = false) String authorization,
//			@ApiParam(value = "NB ID used to match the request with its corresponding SB asynchronous notification. The format should be like below:   ASPID+yyyyMMddHHmmss+sequence(5 digits)   Note:+ means concat   The ASPID is the M2M indentifer, and its length is maximum 21 characters   yyyyMMddHHmmss is the UTC time format.   Sequence is an increased sequence, and its length is 5 digits.the sequence should be started with 00001 and end with 99999.   Example: TP000002322017022409365100021") @RequestHeader(value = "correlatorId", required = false) String correlatorId,
//			@ApiParam(value = "Transaction identity for each request") @RequestHeader(value = "messageId", required = false) String messageId,
//			@ApiParam(value = "Time and date the request is sent") @RequestHeader(value = "requestDate", required = false) OffsetDateTime requestDate,
//			@ApiParam(value = "Transaction origin") @RequestHeader(value = "originSystem", required = false) OffsetDateTime originSystem,
//			@ApiParam(value = "destination of the transaction") @RequestHeader(value = "targetSystem", required = false) OffsetDateTime targetSystem,
//			@ApiParam(value = "Indicates the country to route. ISO 1366 – 3 character Example: MEX") @RequestHeader(value = "country", required = false) String country,
//			@ApiParam(value = "Unique-Identifier for this <123>") @Valid @RequestParam(value = "id", required = false) String billingAccountId,
//			@ApiParam(value = "Type of product") @Valid @RequestParam(value = "type", required = false) String productType,
//			@ApiParam(value = "Bill reference known by the customer or the party and displayed on the bill. Could be different from the id") @Valid @RequestParam(value = "billNo", required = false) String billNo,
//			@ApiParam(value = "Start of the time period, using IETC-RFC-3339 format. If you define a start, you must also define an end") @Valid @RequestParam(value = "startDateTime", required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) OffsetDateTime billingPeriodStartDateTime,
//			@ApiParam(value = "End of the time period, using IETC-RFC-3339 format") @Valid @RequestParam(value = "endDateTime", required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) OffsetDateTime billingPeriodEndDateTime,
//			String codApp, String usuario, String tamanioPagina, String numeroPagina, String tipoConsulta,
//			String flagSoloSaldo, String flagSoloDisputa, String telefono) {
//
//		CustomerBillRequestDto customerbill = new CustomerBillRequestDto();
//
//		customerbill.setId(billingAccountId);
//		customerbill.setType(productType);
//		customerbill.setBillNo(billNo);
//		customerbill.setStartDateTime(billingPeriodStartDateTime);
//		customerbill.setEndDateTime(billingPeriodEndDateTime);
//		customerbill.setCodapp(codApp);
//		customerbill.setUsuario(usuario);
//		customerbill.setTamanioPagina(tamanioPagina);
//		customerbill.setNumeroPagina(numeroPagina);
//		customerbill.setTipoConsulta(tipoConsulta);
//		customerbill.setFlagSoloSaldo(flagSoloSaldo);
//		customerbill.setFlagSoloDisputa(flagSoloDisputa);
//		customerbill.setTelefono(telefono);
//		logger.info("----------------- {} --------------",customerbill.toString());
//
//		return new ResponseEntity<Flux<CustomerBill>>(customerBillService.listCustomerBill(customerbill), HttpStatus.OK);
//
//	}
//
//}
