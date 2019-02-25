package dropwizard.rest.RESTfulExample.helloworld.resources;

import java.util.ArrayList;
import java.util.List;
import dropwizard.rest.RESTfulExample.helloworld.api.Saying;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.codahale.metrics.annotation.Timed;

@Path("/saying")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

	List<Saying> sayingList = new ArrayList<Saying>();

	@POST
	@Timed
	public Response save(Saying saying) {
		for (Saying say : sayingList) {
			if (say.getId() == saying.getId()) {
				return Response.status(400).build();
			}
		}
		sayingList.add(saying);
		return Response.status(201).build();
	}

	@GET
	@Path("/list")
	public Response allList() {
		return Response.status(200).entity(sayingList).build();
	}

	@GET
	@Path("{id}")
	public Response find(@PathParam("id") int id) {
		for (Saying saying : sayingList) {
			if (saying.getId() == id) {
				return Response.status(200).entity(saying).build();
			}
		}
		return Response.status(404).build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		for (Saying saying : sayingList) {
			if (saying.getId() == id) {
				sayingList.remove(saying);
				return Response.status(200).build();
			}
		}
		return Response.status(404).build();
	}

	@PUT
	@Timed
	public Response update(Saying saying) {
		for (int i = 0; i < sayingList.size(); i++) {
			if (sayingList.get(i).getId() == saying.getId()) {
				sayingList.set(i, saying);
				return Response.status(200).build();
			}
		}
		return Response.status(404).build();
	}
}
