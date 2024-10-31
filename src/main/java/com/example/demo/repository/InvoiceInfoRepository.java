package com.example.demo.repository;

import com.example.demo.model.InvoiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceInfoRepository extends JpaRepository<InvoiceInfo, Integer> {

}