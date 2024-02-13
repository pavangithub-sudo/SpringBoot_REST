package com.vzw.cxp.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vzw.cxp.domain.entity.Account;

@Repository
public interface BankRepository extends JpaRepository<Account, Long>{

	Account save(double total);

	Account findByEmail(String email);

	Optional<Account> findOneByEmailAndPassword(String email, String encodedPassword);

}
