package dropwizard;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.ClassRule;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.stubbing.Answer;

import dropwizard.rest.RESTfulExample.helloworld.dao.pojo.Saying;
import dropwizard.rest.RESTfulExample.helloworld.dao.SayingDao;
import dropwizard.rest.RESTfulExample.helloworld.resources.SayingResource;
import io.dropwizard.testing.junit.ResourceTestRule;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.http.HttpResponseMatchers.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SayingResourceTest {

//	@ClassRule
//	public static final ResourceTestRule resources = ResourceTestRule.builder().
//													addResource(new HelloWorldResource()).build();
//	Saying saying;
	
	 private static final SayingDao dao = mock(SayingDao.class);
	
	@ClassRule
	public static final ResourceTestRule resources = ResourceTestRule.builder().
													addResource(new SayingResource(dao)).build();
	@Test
	public void test1_saveSaying() {
		Response response = resources.getJerseyTest().target("/sayingDao")
							.request().post(Entity.entity(new Saying(1, "burcu"), MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		assertThat(response,hasStatusCode(201));
	}
	
	@Test
	public void test2_saveSameSaying() {
		when(dao.find(eq(1))).thenReturn(new Saying(1,"burcu"));
		Response response = resources.getJerseyTest().target("/sayingDao")
							.request().post(Entity.entity(new Saying(1, "burcu"), MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		assertThat(response,hasStatusCode(400));
	}
	
	@Test
	public void test3_listSaying() {
		List<Saying> sayingList = new ArrayList<Saying>();
		sayingList.add(new Saying(1,"burcu"));
		when(dao.allList()).thenReturn(sayingList);
		Response response= resources.getJerseyTest().target("/sayingDao/list").request().get();
		System.out.println(response.getStatus());
		assertThat(response,hasStatusCode(200));
		assertEquals(1,response.readEntity(List.class).size());
	}
	
	@Test
	public void test4_findSaying() {
		Response response= resources.getJerseyTest().target("/sayingDao").path("1").request().get();
		System.out.println(response.getStatus());
		assertThat(response,hasStatusCode(200));
	}
	
	@Test
	public void test5_notFindSaying() {
		Response response= resources.getJerseyTest().target("/sayingDao").path("2").request().get();
		System.out.println(response.getStatus());
		assertThat(response,hasStatusCode(404));
	}
	
	@Test
	public void test6_updateSaying() {
		when(dao.update(any(Saying.class))).thenReturn(1);
		Response response = resources.getJerseyTest().target("/sayingDao")
							.request().put(Entity.entity(new Saying(1, "deneme"), MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		assertThat(response,hasStatusCode(200));
	}
	
	@Test
	public void test7_updateControl() {
		when(dao.find(1)).thenReturn(new Saying(1,"deneme"));
		String response= resources.getJerseyTest().target("/sayingDao").path("1").request().get(Saying.class).getContent();
		assertEquals("deneme", response);
	}
	
	@Test
	public void test8_updateErrorSaying() {
		when(dao.update(any(Saying.class))).thenReturn(0);
		Response response = resources.getJerseyTest().target("/sayingDao")
							.request().put(Entity.entity(new Saying(2, "deneme"), MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		assertThat(response,hasStatusCode(404));
	}
	
	@Test
	public void test9_1_deleteSaying() {
		when(dao.delete(1)).thenReturn(1);
		Response response= resources.getJerseyTest().target("/sayingDao").path("1").request().delete();
		System.out.println(response.getStatus());
		assertThat(response,hasStatusCode(200));
	}
	
	@Test
	public void test9_2_deleteControl() {
		List<Saying> sayingList = new ArrayList<Saying>();
		when(dao.allList()).thenReturn(sayingList);
		int response= resources.getJerseyTest().target("/sayingDao/list").request().get(List.class).size();
		assertEquals(0,response);
	}
	
	@Test
	public void test9_3_deleteErrorSaying() {
		Response response= resources.getJerseyTest().target("/sayingDao").path("2").request().delete();
		System.out.println(response.getStatus());
		assertThat(response,hasStatusCode(404));
	}
}
