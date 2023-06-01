package com.example.computershop.repository;

import com.example.computershop.model.BaseComputerPart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseComputerPartRepository extends JpaRepository<BaseComputerPart, Long> {
}
