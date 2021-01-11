package com.example.banka2service.repository;


import com.example.banka2service.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository  extends JpaRepository<Bank, Long> {
    List<Bank> findAll();
    Bank findBankById(Long id);
}