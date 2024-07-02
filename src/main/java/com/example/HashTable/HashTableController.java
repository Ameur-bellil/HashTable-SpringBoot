package com.example.HashTable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/hashtable")
@CrossOrigin
public class HashTableController {
    final
    HashTableService hashTableService;

    public HashTableController(HashTableService hashTableService) {
        this.hashTableService = hashTableService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestParam String name) {
        hashTableService.add(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contains")
    public ResponseEntity<Boolean> contains(@RequestParam String name) {
        boolean result = hashTableService.contains(name);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Boolean> remove(@RequestParam String name) {
        boolean result = hashTableService.remove(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/size")
    public ResponseEntity<Integer> size() {
        int size = hashTableService.size();
        return ResponseEntity.ok(size);
    }

    @GetMapping("/print")
    public ResponseEntity<List<HashTableRowEntity>> print() {
        List<HashTableRowEntity> table = hashTableService.getTable();
        return ResponseEntity.ok(table);
    }
}
