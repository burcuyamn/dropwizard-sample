package dropwizard.rest.RESTfulExample.helloworld.dao.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.Consumes;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "saying")
@NamedQueries({
	@NamedQuery(name="saying.delete",query="DELETE FROM Saying s WHERE s.id = :id"),
	@NamedQuery(name="saying.update",query="UPDATE Saying s SET s.content =:content WHERE s.id = :id")
})

public class Saying implements Serializable {

	@Column(name="id")
	@Id
	private Integer id;
	
	@Column(name="content")
	private String content;
	
	public Saying() {
		
    }
	
	public Saying(int i, String content) {
        this.setId(i);
        this.setContent(content);
    }
	@JsonProperty
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	 @JsonProperty
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
