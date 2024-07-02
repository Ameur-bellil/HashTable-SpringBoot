package com.example.HashTable;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HashTableService {

    private final HashTableRowRepository rowRepository;
    private final HashTableCellRepository cellRepository;
    private final int TABLE_SIZE = 5;

    @Autowired
    public HashTableService(HashTableRowRepository rowRepository, HashTableCellRepository cellRepository) {
        this.rowRepository = rowRepository;
        this.cellRepository = cellRepository;
    }
    @Transactional
    public void add(String data) {
        int hash = calculateHash(data);
        HashTableRowEntity row = getOrCreateRow(hash);

        if (containsInRow(row, data)) {
            System.out.println( data + " already exists in the hash table.");
            return;
        }

        HashTableCellEntity newCell = new HashTableCellEntity();
        newCell.setData(data);
        newCell.setRow(row);

        if (row.getFirstCell() == null) {
            row.setFirstCell(newCell);
        } else {
            HashTableCellEntity lastCell = findLastCell(row);
            assert lastCell != null;
            lastCell.setNext(newCell);
        }

        row.getCells().add(newCell);


        cellRepository.save(newCell);
        rowRepository.save(row);

        System.out.println("Added cell :" + data);
        System.out.print("Current state of row " + hash + ":  ");
        printRow(row);
    }

    @Transactional
    public boolean remove(String data) {
        int hash = calculateHash(data);
        Optional<HashTableRowEntity> rowOptional = rowRepository.findByIndex(hash);
        if (rowOptional.isPresent()) {
            HashTableRowEntity row = rowOptional.get();
            HashTableCellEntity currentCell = row.getFirstCell();
            HashTableCellEntity previousCell = null;

            while (currentCell != null) {
                if (currentCell.getData().equals(data)) {
                    if (previousCell == null) {
                        row.setFirstCell(currentCell.getNext());
                    } else {
                        previousCell.setNext(currentCell.getNext());
                    }
                    row.getCells().remove(currentCell);
                    cellRepository.delete(currentCell);
                    rowRepository.save(row);

                    System.out.println("Deleted cell : " + data);
                    System.out.print("Current state of row " + hash + ":  ");
                    printRow(row);

                    return true;
                }
                previousCell = currentCell;
                currentCell = currentCell.getNext();
            }
        }
        return false;
    }

    public boolean contains(String data) {
        int hash = calculateHash(data);
        Optional<HashTableRowEntity> rowOptional = rowRepository.findByIndex(hash);
        if (rowOptional.isPresent()) {
            HashTableCellEntity cell = rowOptional.get().getFirstCell();
            while (cell != null) {
                if (cell.getData().equals(data)) {
                    return true;
                }
                cell = cell.getNext();
            }
        }
        return false;
    }


    public int calculateHash(String data) {
        int hash = 0;
        for (int c : data.toCharArray()) {
            hash = (hash * 31 + c) % TABLE_SIZE;
        }

        return hash;
    }


    private HashTableRowEntity getOrCreateRow(int index) {
        return rowRepository.findByIndex(index).orElseGet(() -> {
            HashTableRowEntity newRow = new HashTableRowEntity();
            newRow.setIndex(index);
            rowRepository.save(newRow);
            return newRow;
        });
    }

    private boolean containsInRow(HashTableRowEntity row, String data) {
        HashTableCellEntity cell = row.getFirstCell();
        while (cell != null) {
            if (cell.getData().equals(data)) {
                return true;
            }
            cell = cell.getNext();
        }
        return false;
    }

    private HashTableCellEntity findLastCell(HashTableRowEntity row) {
        HashTableCellEntity cell = row.getFirstCell();
        if (cell == null) {
            return null;
        }
        while (cell.getNext() != null) {
            cell = cell.getNext();
        }
        return cell;
    }
    private void printRow(HashTableRowEntity row) {
        HashTableCellEntity cell = row.getFirstCell();
        while (cell != null) {
            System.out.print(cell.getData() + " ");
            cell = cell.getNext();
        }
        System.out.println();
    }
    public int size() {
        return (int) cellRepository.count();
    }


    public List<HashTableRowEntity> getTable()  {
        return rowRepository.findAll();
    }

    public void print() {
        for (int i = 0; i < TABLE_SIZE; i++) {
            Optional<HashTableRowEntity> rowOptional = rowRepository.findByIndex(i);
            if (rowOptional.isPresent()) {
                HashTableRowEntity row = rowOptional.get();
                System.out.print("Row " + i + ": ");
                HashTableCellEntity cell = row.getFirstCell();
                while (cell != null) {
                    System.out.print(cell.getData() + " ");
                    cell = cell.getNext();
                }
                System.out.println();
            } else {
                System.out.println("Row " + i + ": ");
            }
        }
    }
}