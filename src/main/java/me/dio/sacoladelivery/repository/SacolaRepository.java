package me.dio.sacoladelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.sacoladelivery.model.Sacola;

@Repository
public interface SacolaRepository extends JpaRepository<Sacola, Long>{
    
}
