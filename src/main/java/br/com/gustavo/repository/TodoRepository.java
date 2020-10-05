package br.com.gustavo.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.gustavo.entity.Todo;
import io.quarkus.mongodb.panache.PanacheMongoRepository;

@ApplicationScoped
public class TodoRepository implements PanacheMongoRepository<Todo> {

}