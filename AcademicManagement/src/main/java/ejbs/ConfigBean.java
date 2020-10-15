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
	@EJB
	TeacherBean teacherBean;
	@EJB
	AdministratorBean administratorBean;

	@PostConstruct
	public void populateDB() {

		System.out.println("##### Creating courses...");
		courseBean.create(1, "EI");
		courseBean.create(2, "PSI");

		System.out.println("##### Creating subjects...");
		subjectBean.create(1, "DAE", courseBean.getCourse(1), "2020/2021", 3);
		subjectBean.create(2, "DAD", courseBean.getCourse(1), "2020/2021", 3);
		subjectBean.create(3, "IS", courseBean.getCourse(1), "2020/2021", 3);

		subjectBean.create(4, "DAPLIC", courseBean.getCourse(2), "2020/2021", 2);
		subjectBean.create(5, "PWEBC", courseBean.getCourse(2), "2020/2021", 1);
		subjectBean.create(6, "PWEBCCCCC", courseBean.getCourse(2), "2020/2021", 1);

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
		subjectBean.enrollStudentInSubject(1, "s1");
		subjectBean.enrollStudentInSubject(2, "s1");
		subjectBean.enrollStudentInSubject(3, "s1");

		subjectBean.enrollStudentInSubject(1, "s2");
		subjectBean.enrollStudentInSubject(2, "s2");
		subjectBean.enrollStudentInSubject(3, "s2");

		subjectBean.enrollStudentInSubject(1, "s4");
		subjectBean.enrollStudentInSubject(2, "s4");

		subjectBean.enrollStudentInSubject(4, "s7");
		subjectBean.enrollStudentInSubject(5, "s7");

		subjectBean.enrollStudentInSubject(5, "s6");


		System.out.println("##### Creating teachers...");
		teacherBean.create("t1", "1", "Andreia Torres", "andreia@mail.com", "A1");
		teacherBean.create("t2", "1", "Samuel Rebotim", "samuel@mail.com", "A2");
		teacherBean.create("t3", "1", "Orlando Milheiriço", "orlando@mail.com", "A3");
		teacherBean.create("t4", "1", "Edgar Roriz", "edgar@mail.com", "A4");


		System.out.println("##### Creating admins...");
		administratorBean.create("a1", "1", "super Admin1", "a1@mail.com");
		administratorBean.create("a2", "2", "super Admin2", "a2@mail.com");


		System.out.println("##### FINISHED!!");
	}
}
