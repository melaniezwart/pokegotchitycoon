import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/status")
@ApplicationScoped
public class GameRest {
	
	@Inject
	private Tama a;
	
	//double hunger = a.hunger;
	//double hygiene = a.hygiene;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/hunger")
	public int getHunger() {
		int h = (int)a.getHunger();
		// System.out.println("hunger is " + h);
		return (int)h;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/happy")
	public int getHappy(){
		int hap = (int)a.getHappy();
		// System.out.println("happy is" + hap);
		return (int)hap;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/hygiene")
	public int getHygiene(){
		int hyg = (int)a.getHygiene();
		// System.out.println("hygiene is " + hyg);
		return (int)hyg;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/sleep")
	public int getSleep(){
		int s = (int)a.getSleep();
		// System.out.println("Sleep is " + s);
		return (int)s;
	}
}
