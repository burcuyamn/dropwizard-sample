package dropwizard.rest.RESTfulExample.helloworld.resources;

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
import dropwizard.rest.RESTfulExample.helloworld.dao.SayingDao;
import dropwizard.rest.RESTfulExample.helloworld.dao.pojo.Saying;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/sayingDao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SayingResource {

	SayingDao sayingDao;
	
	public SayingResource(SayingDao sayingDao) {
		this.sayingDao = sayingDao;
	}
	
	@POST
	@Timed
	@UnitOfWork
	public Response save(Saying saying) {
		if (sayingDao.find(saying.getId())==null ) {
			sayingDao.save(saying);
			return Response.status(201).build();
		}
		else {
			return Response.status(400).build();
		}
	}
	
    @GET
    @UnitOfWork
    @Path("/list")
    public Response allList() {
    	return Response.status(200).entity(sayingDao.allList()).build();
    }
    
    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response find(@PathParam("id") Integer id){
    	if (sayingDao.find(id)==null ) {
    		return Response.status(404).build();
    	}
    	else {
    		return Response.status(200).entity(sayingDao.find(id)).build();
		}
    }
    
    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response delete(@PathParam("id") int id) {
    	Integer returnValue=sayingDao.delete(id);
    	if(returnValue==0) {
    		return Response.status(404).build();
    	}
    	else {
    		return Response.status(200).build();
		}
    }
    
    @PUT
	@Timed
	@UnitOfWork
	public Response update(Saying saying) {
    	Integer returnValue=sayingDao.update(saying);
    	if(returnValue==0) {
    		return Response.status(404).build();
    	}
    	else {
    		return Response.status(200).build();
		}
    }
    	
}
