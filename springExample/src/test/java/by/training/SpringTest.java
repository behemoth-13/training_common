package by.training;

import static org.junit.Assert.assertEquals;

import by.training.spring.annotations.AnnotationHelper;
import by.training.spring.code.Config;
import by.training.spring.model.Faculty;
import by.training.spring.model.Professor;
import by.training.spring.model.University;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SpringTest {

    @Test
    public void code() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);

        University bntu = (University) ctx.getBean("bntu");
        testUniversity(bntu);
    }

    @Test
    public void annotations() {
        ApplicationContext ctx =
                new AnnotationConfigApplicationContext("by.training.spring.annotations");
        AnnotationHelper helper = (AnnotationHelper) ctx.getBean("annotation");
        University bntu = helper.getUniversity();
        testUniversity(bntu);
    }

    @Test
    public void xml() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        University bntu = (University) ctx.getBean("bntu");
        testUniversity(bntu);
    }

    private void testUniversity(University university) {
        assertEquals("The Belarusian National Technical University", university.getName());
        List<Faculty> facs = university.getFaculties();

        Faculty atf = facs.get(0);
        List<Professor> atfProf = atf.getProfessors();
        assertEquals("Automotive and Tractor Faculty", atf.getName());
        assertEquals("Anton Pupkin", atfProf.get(0).toString());
        assertEquals("Valentin Holkin", atfProf.get(1).toString());

        Faculty mef = facs.get(1);
        List<Professor> mefProf = mef.getProfessors();
        assertEquals("Mechanical Engineering Faculty", mef.getName());
        assertEquals("Ivan Ivanov", mefProf.get(0).toString());
        assertEquals("Petr Petrov", mefProf.get(1).toString());
        assertEquals("Fedor Sidorov", mefProf.get(2).toString());
    }
}
