package com.example.todo.dto;

import com.example.todo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    private Long id;
    private String title;
    private Long order;
    private Boolean completed;
    private String url;

    public TodoDTO(TodoEntity todoEntity) {
        this.id = todoEntity.getId();
        this.title = todoEntity.getTitle();
        this.order = todoEntity.getOrder();
        this.completed = todoEntity.isCompleted();

        this.url = "http://localhost:8080/" + this.id;
    }
}
