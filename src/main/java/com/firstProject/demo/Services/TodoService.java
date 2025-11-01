package com.firstProject.demo.Services;


import com.firstProject.demo.Models.TodoModel;
import com.firstProject.demo.Repository.TodoRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    public void saveTodo(TodoModel todoModel) {
        todoRepository.save(todoModel);
    }

    public List<TodoModel> getAllTodos() {
        return todoRepository.findAll();
    }

    public Optional<TodoModel> findById(ObjectId id) {
        return todoRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        todoRepository.deleteById(id);
    }


    public TodoModel updateById(ObjectId todoId, Map<String, Object> updates) {
        TodoModel existing = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));

        if (updates.containsKey("task")) {
            String newTask = (String) updates.get("task");
            if (newTask != null && !newTask.trim().isEmpty()) {
                existing.setTask(newTask);
            }
        }
        if (updates.containsKey("isCompleted")) {
            existing.setIsCompleted((Boolean) updates.get("isCompleted"));
        }

        existing.setUpdatedAt(LocalDateTime.now());
        return todoRepository.save(existing);
    }


}
