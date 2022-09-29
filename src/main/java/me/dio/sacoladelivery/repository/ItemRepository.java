package me.dio.sacoladelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.sacoladelivery.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
}
