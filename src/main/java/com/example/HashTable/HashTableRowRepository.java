package com.example.HashTable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashTableRowRepository extends JpaRepository<HashTableRowEntity,Integer> {
    @Override
    List<HashTableRowEntity> findAll();
    Optional<HashTableRowEntity> findByIndex(int index);

}

