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

    @EJB
    CourseBean cb;

    @PostConstruct
    public void populateDB() {

        System.out.println("##### Creating classes...");
        cb.create(1, "DAE");
        cb.create(2, "DAD");


        System.out.println("##### Creating students...");
        sb.create("s1", "Abel", "Abel", "abel@mail.com", 1);
        sb.create("s2", "Bernardo", "Bernardo", "bernardo@mail.com", 2);
        System.out.println("##### FINISHED!!");
    }
}
