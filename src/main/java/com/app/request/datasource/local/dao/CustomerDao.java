package com.app.request.datasource.local.dao;

import com.app.request.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, String> {
}
