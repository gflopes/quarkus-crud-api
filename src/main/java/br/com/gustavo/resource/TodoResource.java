package br.com.gustavo.resource;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.types.ObjectId;

import br.com.gustavo.entity.Todo;
import br.com.gustavo.repository.TodoRepository;

@Path("/todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @Inject
    TodoRepository repository;

    @GET
    public Response listAll() {
        return Response.ok(repository.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        return Response.ok(repository.findById(new ObjectId(id))).build();
    }

    @POST
    public Response insert(@Valid Todo todo) {
        if (todo == null) {
            return Response.status(400).entity("Informe os dados da tarefa").build();
        }
        repository.persist(todo);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(String id) {
        repository.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Response update(@Valid Todo todo) {
        repository.update(todo);
        return Response.ok().build();
    }

    @PATCH
    @Path("{id}/complete")
    public Response setComplete(@PathParam("id") String id) {
        Todo todo = repository.findById(new ObjectId(id));
        todo.setCompleted(true);
        repository.update(todo);
        return Response.ok().build();
    }
}