package com.example.todo.controller;


import com.example.todo.dto.TodoDTO;
import com.example.todo.model.TodoEntity;
import com.example.todo.service.TodoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoDTO> create(@RequestBody TodoDTO todoDTO) {

        if (ObjectUtils.isEmpty(todoDTO.getTitle())) {
            return ResponseEntity.badRequest().build();
        }

        if (ObjectUtils.isEmpty(todoDTO.getOrder())) {
            todoDTO.setOrder(0L);
        }

        if (ObjectUtils.isEmpty(todoDTO.getCompleted())) {
            todoDTO.setCompleted(false);
        }

        TodoEntity result = this.todoService.add(todoDTO);

        return ResponseEntity.ok(new TodoDTO(result));
    }

    @GetMapping("{id}")
    public ResponseEntity<TodoDTO> readOne(@PathVariable Long id) {

        TodoEntity result = this.todoService.searchById(id);

        return ResponseEntity.ok(new TodoDTO(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoDTO>> readAll() {

        List<TodoEntity> resultList = this.todoService.searchAll();

        List<TodoDTO> response = resultList
                .stream()
                .map(result -> new TodoDTO(result))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<TodoDTO> update(@PathVariable Long id, @RequestBody TodoDTO todoDTO) {

        TodoEntity result = this.todoService.updateById(id, todoDTO);

        return ResponseEntity.ok(new TodoDTO(result));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {

        this.todoService.deleteById(id);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {

        this.todoService.deleteAll();

        return ResponseEntity.ok().build();
    }
}
