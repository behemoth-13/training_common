package by.training;

import by.training.spring.code.Config;
import by.training.spring.model.Faculty;
import by.training.spring.model.Professor;
import by.training.spring.model.University;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PrintData {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        University bntu = (University) ctx.getBean("bntu");

        System.out.println("University name: " + bntu.getName());
        for (Faculty f : bntu.getFaculties()) {
            System.out.println("    Faculty name: " + f.getName());
            for (Professor p : f.getProfessors()) {
                System.out.println("        Professor: " + p.toString());
            }
        }
    }
}
