//package pe.com.claro.tmf.service.support.oac.web;
//
//import java.time.OffsetDateTime;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import io.swagger.annotations.ApiParam;
//import lombok.extern.slf4j.Slf4j;
//import pe.com.claro.tmf.service.support.oac.business.AccountService;
//import reactor.core.publisher.Flux;
//
//@RestController
//@RequestMapping("/account")
//@Slf4j
//public class AccountController {
//
//	@Autowired
//	AccountService accountService;
//
//	@GetMapping
//	public ResponseEntity<Flux<FinancialAccount>> listFinancialAccount(
//			@ApiParam(value = "A secure token chain that indicates that it is an authorized request.") @RequestHeader(value = "Authorization", required = false) String authorization,
//			@ApiParam(value = "NB ID used to match the request with its corresponding SB asynchronous notification. The format should be like below:   ASPID+yyyyMMddHHmmss+sequence(5 digits)   Note:+ means concat   The ASPID is the M2M indentifer, and its length is maximum 21 characters   yyyyMMddHHmmss is the UTC time format.   Sequence is an increased sequence, and its length is 5 digits.the sequence should be started with 00001 and end with 99999.   Example: TP000002322017022409365100021") @RequestHeader(value = "correlatorId", required = false) String correlatorId,
//			@ApiParam(value = "Transaction identity for each request") @RequestHeader(value = "messageId", required = false) String messageId,
//			@ApiParam(value = "Time and date the request is sent") @RequestHeader(value = "requestDate", required = false) OffsetDateTime requestDate,
//			@ApiParam(value = "Transaction origin") @RequestHeader(value = "originSystem", required = false) String originSystem,
//			@ApiParam(value = "destination of the transaction") @RequestHeader(value = "targetSystem", required = false) String targetSystem,
//			@ApiParam(value = "Indicates the country to route. ISO 1366 â€“ 3 character Example: MEX") @RequestHeader(value = "country", required = false) String country,
//			@ApiParam(value = "IP from the device since the invocation originated.") @RequestHeader(value = "ipClient", required = false) String ipClient,
//			@ApiParam(value = "Identifies the channel that invokes the service.") @RequestHeader(value = "channel", required = false) String channel,
//			@ApiParam(value = "Application Id.") @RequestHeader(value = "applicationId", required = false) String applicationId,
//			@ApiParam(value = "Identifies the sub-channel that invokes the service.") @RequestHeader(value = "subChannel", required = false) String subChannel,
//			@ApiParam(value = "Id of the device from which the invocation originated.") @RequestHeader(value = "device", required = false) String device,
//			@ApiParam(value = "Identifier of user") @Valid @RequestParam(value = "relatedParty.id", required = false) String relatedPartyId,
//			@ApiParam(value = "identifier Type of relatedParty") @Valid @RequestParam(value = "relatedParty.identifierType", required = false) String relatedPartyIdentifierType,
//			@ApiParam(value = "role of relatedParty") @Valid @RequestParam(value = "relatedParty.role", required = false) String relatedPartyRole,
//			@ApiParam(value = "legal type of user") @Valid @RequestParam(value = "relatedParty.legalId.type", required = false) String relatedPartyLegalIdType,
//			@ApiParam(value = "legal identifier of user") @Valid @RequestParam(value = "relatedParty.legalId.identificationId", required = false) String relatedPartyLegalIdIdentificationId) {
//
//		log.info("Inicio AccountController.listFinancialAccount");
//
//		FinancialAccountRequestDto financialAccountRequest = new FinancialAccountRequestDto();
//
//		financialAccountRequest.setRelatedPartyId(relatedPartyId);
//		financialAccountRequest.setRelatedPartyIdentifierType(relatedPartyIdentifierType);
//		financialAccountRequest.setRelatedPartyRole(relatedPartyRole);
//		financialAccountRequest.setRelatedPartyLegalIdIdentificationId(relatedPartyLegalIdIdentificationId);
//		financialAccountRequest.setRelatedPartyLegalIdType(relatedPartyLegalIdType);
//
//		try {
//			// return new
//			// ResponseEntity<Flux<FinancialAccount>>(accountService.listFinancialAccount(financialAccountRequest),
//			// HttpStatus.OK);
//			return new ResponseEntity<>(accountService.listFinancialAccount(financialAccountRequest), HttpStatus.OK);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//	}
//}
