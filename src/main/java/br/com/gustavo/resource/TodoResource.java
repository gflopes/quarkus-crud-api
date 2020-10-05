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

import br.com.gustavo.entity.Todo;
import br.com.gustavo.repository.TodoRepository;
import br.com.gustavo.service.TodoService;

@Path("/todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @Inject
    TodoRepository repository;

    @Inject
    TodoService service;

    @GET
    public Response listAll() {
        return Response.ok(service.listaAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        return Response.ok(service.findById(id)).build();
    }

    @POST
    public Response insert(@Valid Todo todo) {
        if (todo == null) {
            return Response.status(400).entity("Informe os dados da tarefa").build();
        }
        service.insert(todo);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @PUT
    public Response update(@Valid Todo todo) {
        service.update(todo);
        return Response.ok().build();
    }

    @PATCH
    @Path("{id}/complete")
    public Response setComplete(@PathParam("id") String id) {
        service.setComplete(id);
        return Response.ok().build();
    }
}