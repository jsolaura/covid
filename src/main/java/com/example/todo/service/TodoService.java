package com.example.todo.service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.model.TodoEntity;
import com.example.todo.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoEntity add(TodoDTO todoDTO) {
        TodoEntity todoEntity = TodoEntity.builder()
                .title(todoDTO.getTitle())
                .order(todoDTO.getOrder())
                .completed(todoDTO.getCompleted())
                .build();
        return this.todoRepository.save(todoEntity);
    }

    public TodoEntity searchById(Long id) {
        return this.todoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<TodoEntity> searchAll() {
        return this.todoRepository.findAll();
    }

    public TodoEntity updateById(Long id, TodoDTO todoDTO) {
        TodoEntity todoEntity = this.searchById(id);

        if (todoDTO.getTitle() != null) {
            todoEntity.setTitle(todoDTO.getTitle());
        }
        if (todoDTO.getOrder() != null) {
            todoEntity.setOrder(todoDTO.getOrder());
        }
        if (todoDTO.getCompleted() != null) {
            todoEntity.setCompleted(todoDTO.getCompleted());
        }

        return this.todoRepository.save(todoEntity);
    }

    public void deleteById(Long id) {
        this.todoRepository.deleteById(id);
    }

    public void deleteAll() {
        this.todoRepository.deleteAll();
    }

    public List<TodoDTO> getTodoList() {
        List<TodoEntity> todoEntities = todoRepository.findAll();
        List<TodoDTO> todoDTOList = new ArrayList<>();

        for (TodoEntity todoEntity : todoEntities) {
            TodoDTO todoDTO = TodoDTO.builder()
                    .id(todoEntity.getId())
                    .title(todoEntity.getTitle())
                    .order(todoEntity.getOrder())
                    .completed(todoEntity.getCompleted())
                    .build();
            todoDTOList.add(todoDTO);
        }
        return todoDTOList;
    }

}
