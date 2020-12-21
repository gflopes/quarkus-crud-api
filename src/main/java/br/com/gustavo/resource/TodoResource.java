package br.com.gustavo.resource;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

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

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.jboss.logging.Logger;

import br.com.gustavo.entity.Todo;
import br.com.gustavo.repository.TodoRepository;
import br.com.gustavo.service.TodoService;

@Path("/todo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    private static final Logger LOGGER = Logger.getLogger(TodoResource.class);

    @Inject
    TodoRepository repository;

    @Inject
    TodoService service;

    private AtomicLong counter = new AtomicLong(0);

    @GET
    @Retry(maxRetries = 3)
    public Response listAll() {
        final Long invocationNumber = counter.getAndIncrement();
        listAllFail(String.format("TodoResource#listAll() invocation #%d failed", invocationNumber));
        return Response.ok(service.listAll()).build();
    }

    @GET
    @Path("/{id}")
    @CircuitBreaker(requestVolumeThreshold = 4)
    public Response get(@PathParam("id") String id) {
        getIdFail();
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

    private void listAllFail(String failureLogMessage) {
        if (new Random().nextBoolean()) {
            LOGGER.error(failureLogMessage);
            throw new RuntimeException("Resource failure.");
        }
    }

    private void getIdFail() {
        final Long invocationNumber = counter.getAndIncrement();
        if (invocationNumber % 4 > 1) {
            throw new RuntimeException("Service failed.");
        }
    }
}