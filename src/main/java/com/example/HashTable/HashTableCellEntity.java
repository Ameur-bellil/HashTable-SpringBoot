package com.example.HashTable;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
public class HashTableCellEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String data;

    @ManyToOne
    @JoinColumn(name = "row_id")
    private HashTableRowEntity row;

    @ManyToOne
    @JoinColumn(name = "next_id")
    private HashTableCellEntity next;

}