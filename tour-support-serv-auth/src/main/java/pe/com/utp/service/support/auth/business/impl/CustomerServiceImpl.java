//package pe.com.claro.tmf.service.support.oac.business.impl;
//
//import org.springframework.stereotype.Service;
//
//import lombok.extern.slf4j.Slf4j;
//import pe.com.claro.tmf.service.support.oac.business.CustomerBillService;
//import pe.com.claro.tmf.service.support.oac.repository.OACCustomerBillRepository;
//import reactor.core.publisher.Flux;
//
//@Slf4j
//@Service
//public class CustomerServiceImpl implements CustomerBillService{
//
//	private OACCustomerBillRepository repository;
//
//	public CustomerServiceImpl(OACCustomerBillRepository repository) {
//		this.repository = repository;
//	}
//	@Override
//	public Flux<CustomerBill> listCustomerBill(CustomerBillRequestDto customerbill) {
//		return repository.listCustomerBill(customerbill);
//	}
//
//
//}
