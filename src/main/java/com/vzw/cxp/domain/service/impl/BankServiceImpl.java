package com.vzw.cxp.domain.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vzw.cxp.domain.constants.Constants;
import com.vzw.cxp.domain.dto.LoginDTO;
import com.vzw.cxp.domain.dto.LoginResponse;
import com.vzw.cxp.domain.entity.Account;
import com.vzw.cxp.domain.exception.UserNotFoundException;
import com.vzw.cxp.domain.repository.BankRepository;
import com.vzw.cxp.domain.service.BankService;
@Service
public class BankServiceImpl implements BankService{
	
	@Autowired
	private BankRepository bankRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public Account createAccount(Account account, Map<String, String> requestHeaders) throws IOException {
		Map<String, String> map = new HashMap<>();
		map.putAll(requestHeaders);
			if(null != account && null != requestHeaders && account.getAccountName() != null && !account.getAccountName().equals("")) {
				Random random = new Random();
				long sid = random.nextLong(999999+111111);
				account.setId(sid);
				String pwd = passwordEncoder.encode(account.getPassword());
				account.setPassword(pwd);
				return bankRepository.save(account);
			}else {
				 throw new IOException(Constants.ERRORMSG);  
			}
		
	}

	public List<Account> retrieveAccoutDetails() {
		// TODO Auto-generated method stub
		return (List<Account>)bankRepository.findAll();
	}

	@Override
	public Account deposit(Account account, Long id) {
		// TODO Auto-generated method stub
		Account accountDb = bankRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		
		double total = accountDb.getBalance() + account.getBalance();
		accountDb.setBalance(total);
		return bankRepository.save(accountDb);
	}
	
	@Override
	public Account withdraw(Account account, Long id) {
Account accountDb = bankRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		
		double total = accountDb.getBalance() - account.getBalance();
		accountDb.setBalance(total);
		return bankRepository.save(accountDb);
	}

	@Override
	public void deleteAccountById(Long id) {
		// TODO Auto-generated method stub
		bankRepository.deleteById(id);
		
	}

	@Override
	public Optional<Account> getAccountById(Long id) {
		return bankRepository.findById(id);
	}

	@Override
	public LoginResponse loginEmployee(LoginDTO loginDTO) {
		String msg = "";
        Account account  = bankRepository.findByEmail(loginDTO.getEmail());
        if (account != null) {
            String password = loginDTO.getPassword();
            String encodedPassword = account.getPassword();
            Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
            if (isPwdRight) {
                Optional<Account> employee = bankRepository.findOneByEmailAndPassword(loginDTO.getEmail(), encodedPassword);
                if (employee.isPresent()) {
                    return new LoginResponse("Login Success", true);
                } else {
                    return new LoginResponse("Login Failed", false);
                }
            } else {

                return new LoginResponse("password Not Match", false);
            }
        }else {
            return new LoginResponse("Email not exits", false);
        }
	}
	
}
