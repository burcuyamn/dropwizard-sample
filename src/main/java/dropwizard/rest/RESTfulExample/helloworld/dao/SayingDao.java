package dropwizard.rest.RESTfulExample.helloworld.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import dropwizard.rest.RESTfulExample.helloworld.dao.pojo.Saying;
import io.dropwizard.hibernate.AbstractDAO;

public class SayingDao extends AbstractDAO<Saying>{

	public SayingDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public void save(Saying saying) {
		currentSession().save(saying);
	}
	
	public List<Saying> allList(){
		return currentSession().createCriteria(Saying.class).list();
	}
	
	public Saying find(int id) {
		return (Saying) currentSession().get(Saying.class, id);
	}
	
	public int delete(Integer id) {
		Query query= currentSession().getNamedQuery("saying.delete");
		query.setParameter("id", id);
		return query.executeUpdate();
    }

	public int update(Saying saying) {
		Query query= currentSession().getNamedQuery("saying.update");
		query.setParameter("id", saying.getId());
		query.setParameter("content", saying.getContent());
		return query.executeUpdate();
	}

}
