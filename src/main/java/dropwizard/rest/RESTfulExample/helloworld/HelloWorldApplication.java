package dropwizard.rest.RESTfulExample.helloworld;

import dropwizard.rest.RESTfulExample.helloworld.dao.SayingDao;
import dropwizard.rest.RESTfulExample.helloworld.dao.pojo.Saying;
import dropwizard.rest.RESTfulExample.helloworld.resources.HelloWorldResource;
import dropwizard.rest.RESTfulExample.helloworld.resources.SayingResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration>{

	private final HibernateBundle<HelloWorldConfiguration> hibernate = new HibernateBundle<HelloWorldConfiguration>(Saying.class) {
	    @Override
	    public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
	        return configuration.getDataSourceFactory();
	    }
	};

	public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
    	  bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(HelloWorldConfiguration configuration,
                    Environment environment){

    	HelloWorldResource resource = new HelloWorldResource();
    	environment.jersey().register(resource);

    	 final SayingDao dao = new SayingDao(hibernate.getSessionFactory());
    	    environment.jersey().register(new SayingResource(dao));
    }
}