package com.example.HashTable;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Entity
@Data
public class HashTableRowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int index;

    @Setter
    @ManyToOne
    @JoinColumn(name = "first_cell_id")
    HashTableCellEntity firstCell;
    @OneToMany(mappedBy = "row", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<HashTableCellEntity> cells = new LinkedList<>();

    public void setId(int id) {
        this.id = id;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setCells(LinkedList<HashTableCellEntity> cells) {
        this.cells = cells;
    }
}