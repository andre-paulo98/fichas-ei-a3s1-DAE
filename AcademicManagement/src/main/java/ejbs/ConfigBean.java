package ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton(name = "ConfigEJB")
@Startup
public class ConfigBean {
    @EJB
    StudentBean sb;

    @PostConstruct
    public void populateDB() {
        System.out.println("##### Creating students...");
        sb.create("s1", "Abel", "Abel", "abel@mail.com");
        sb.create("s2", "Bernardo", "Bernardo", "bernardo@mail.com");
        System.out.println("##### FINISHED!!");
    }
}
