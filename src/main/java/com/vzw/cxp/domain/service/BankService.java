package com.vzw.cxp.domain.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.vzw.cxp.domain.dto.LoginDTO;
import com.vzw.cxp.domain.dto.LoginResponse;
import com.vzw.cxp.domain.entity.Account;

public interface BankService {

	Account createAccount(Account account, Map<String, String> requestHeaders) throws IOException;

	//List<Account> retrieveAccoutDetails();

	List<Account> retrieveAccoutDetails();
	
	//Account deposit(Long id, Map<String, Double> amount);

	Account deposit(Account account, Long id);

	void deleteAccountById(Long id);

	Account withdraw(Account account, Long id);

	Optional<Account> getAccountById(Long id);

	LoginResponse loginEmployee(LoginDTO loginDTO);

}
