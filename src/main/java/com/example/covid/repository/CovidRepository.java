package com.example.covid.repository;

import com.example.covid.model.Covid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidRepository extends JpaRepository<Covid, Long> {
}
