package com.example.HashTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTableRepository extends JpaRepository<HashTableEntity,Integer>{


}
