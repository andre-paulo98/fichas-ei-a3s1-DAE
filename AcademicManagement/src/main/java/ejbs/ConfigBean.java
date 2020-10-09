package ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton(name = "ConfigEJB")
@Startup
public class ConfigBean {
	@EJB
	StudentBean studentBean;
	@EJB
	CourseBean courseBean;
	@EJB
	SubjectBean subjectBean;

	@PostConstruct
	public void populateDB() {

		System.out.println("##### Creating courses...");
		courseBean.create(1, "EI");
		courseBean.create(2, "PSI");

		System.out.println("##### Creating subjects...");
		subjectBean.create(1, "DAE", courseBean.findCourse(1), 2020, 3);
		subjectBean.create(2, "DAD", courseBean.findCourse(1), 2020, 3);
		subjectBean.create(3, "IS", courseBean.findCourse(1), 2020, 3);

		subjectBean.create(4, "DAPLIC", courseBean.findCourse(2), 2020, 2);
		subjectBean.create(5, "PWEBC", courseBean.findCourse(2), 2020, 1);

		System.out.println("##### Creating students...");
		studentBean.create("s1", "1", "Eduardo Paixão", "eduardo@mail.com", 1);		// EI
		studentBean.create("s2", "1", "Natália Taveira", "natalia@mail.com", 1);		// EI
		studentBean.create("s3", "1", "Rúben Bouças", "ruben@mail.com", 2);			   // PSI
		studentBean.create("s4", "1", "Fred Fonseca", "fred@mail.com", 1);			// EI
		studentBean.create("s5", "1", "Manel Castelhano", "manel@mail.com", 2);		   // PSI
		studentBean.create("s6", "1", "Vilma Vieira", "vilma@mail.com", 2);			   // PSI
		studentBean.create("s7", "1", "Sancho Leão", "sancho@mail.com", 2);			   // PSI
		studentBean.create("s8", "1", "Rui Vasconcelos", "rui@mail.com", 1);			// EI
		studentBean.create("s9", "1", "Lorenzo Domingos", "lorenzo@mail.com", 1);	// EI
		studentBean.create("s10", "1", "Carlos Sesimbra", "carlos@mail.com", 1);		// EI
		studentBean.create("s11", "1", "Nelson Fonseca", "nelson@mail.com", 1);		// EI

		System.out.println("##### Enroling Students...");
		studentBean.enrollStudentInSubject("s1", 1);
		studentBean.enrollStudentInSubject("s1", 2);
		studentBean.enrollStudentInSubject("s1", 3);

		studentBean.enrollStudentInSubject("s2", 1);
		studentBean.enrollStudentInSubject("s2", 2);
		studentBean.enrollStudentInSubject("s2", 3);

		studentBean.enrollStudentInSubject("s4", 1);
		studentBean.enrollStudentInSubject("s4", 2);

		studentBean.enrollStudentInSubject("s7", 4);
		studentBean.enrollStudentInSubject("s7", 5);

		studentBean.enrollStudentInSubject("s6", 5);

		System.out.println("##### FINISHED!!");
	}
}
