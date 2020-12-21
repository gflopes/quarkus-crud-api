package br.com.gustavo.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import org.bson.types.ObjectId;

import br.com.gustavo.entity.Todo;
import br.com.gustavo.repository.TodoRepository;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class TodoService {
 
    @Inject
    TodoRepository todoRepository;

    public Todo findById(final String id) {
        Optional<Todo> optional = todoRepository.findByIdOptional(new ObjectId(id));
        return optional.orElseThrow(() -> new NotFoundException());
    }

    public void delete(final String id) {
        todoRepository.deleteById(new ObjectId(id));
    }

    public void insert(final Todo todo) {
        todoRepository.persist(todo);
    }

    public void update(final Todo todo) {
        todoRepository.update(todo);
    }

    public List<Todo> listAll() {
        return todoRepository.listAll(Sort.ascending("name"));
    }

    public void setComplete(String id) {
        Todo todo = findById(id);
        todo.setCompleted(true);
        todoRepository.update(todo);
    }
}
