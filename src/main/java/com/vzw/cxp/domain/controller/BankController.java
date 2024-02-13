package com.vzw.cxp.domain.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vzw.cxp.domain.constants.Constants;
import com.vzw.cxp.domain.dto.LoginDTO;
import com.vzw.cxp.domain.dto.LoginResponse;
import com.vzw.cxp.domain.entity.Account;
import com.vzw.cxp.domain.service.BankService;
@RestController
@RequestMapping(value = "/bank")
@CrossOrigin("http://localhost:3000/")
public class BankController {
	
	@Autowired
	BankService bankService;
	
	@PostMapping("/createAccount")
	public Account saveAccount(@RequestBody Account account, @RequestHeader Map<String, String> requestHeaders) throws IOException {
		return bankService.createAccount(account, requestHeaders);
	}
	
	@GetMapping("/getAccount/{id}")
	public Optional<Account> getDetails(@PathVariable("id") Long id) {
		return bankService.getAccountById(id);
	}
	
	@GetMapping("/getAllAccounts")
	public List<Account> getData(){
		return bankService.retrieveAccoutDetails();
	}
	
	@PutMapping("/deposit/{id}")
	public Account deposit(@RequestBody Account account, @PathVariable("id") Long id) {
		return bankService.deposit(account, id);
	}
	
	@PutMapping("/withdraw/{id}")
	public Account withdraw(@RequestBody Account account, @PathVariable("id") Long id) {
		return bankService.withdraw(account, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteAccount(@PathVariable("id") Long id) {
		bankService.deleteAccountById(id);
		return Constants.DeleteMsg;
	}
	
	@PostMapping(path = "/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO)
    {
        LoginResponse loginResponse = bankService.loginEmployee(loginDTO);
        return ResponseEntity.ok(loginResponse);
    }

}
