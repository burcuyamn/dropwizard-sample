package dropwizard.rest.RESTfulExample.helloworld.api;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Saying {
	 private long id;

	    @Length(max = 3)
	    private String content;

	    public Saying() {
	    }
	    public Saying(int i, String content) {
	        this.setId(i);
	        this.setContent(content);
	    }

	    @JsonProperty
	    public long getId() {
	        return id;
	    }

	    @JsonProperty
	    public String getContent() {
	        return content;
	    }
		public void setId(long id) {
			this.id = id;
		}
		public void setContent(String content) {
			this.content = content;
		}
	    
	}