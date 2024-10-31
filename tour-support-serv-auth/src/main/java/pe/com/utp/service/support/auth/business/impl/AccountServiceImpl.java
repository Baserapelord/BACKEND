//package pe.com.claro.tmf.service.support.oac.business.impl;
//
//
//
//
//import pe.com.claro.tmf.service.support.oac.business.AccountService;
//import pe.com.claro.tmf.service.support.oac.repository.OACAccountRepository;
//
//
//
//@Service
//public class AccountServiceImpl implements AccountService{
//
//	private OACAccountRepository repository;
//
//	public AccountServiceImpl(OACAccountRepository repository) {
//		this.repository = repository;
//	}
//	@Override
//	public Flux<FinancialAccount> listFinancialAccount(FinancialAccountRequestDto financialAccountRequest) {
//		log.info("Inicio AccountServiceImpl.listFinancialAccount");
//		return repository.listFinancialAccount(financialAccountRequest);
//	}
//
//
//}
