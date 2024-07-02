package com.example.HashTable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

    @Data
    @Entity
    public class HashTableEntity {
        @Id
        @GeneratedValue
        private int id;
        private int size;
        private String name;

}
