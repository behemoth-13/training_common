package by.training.spring.code;

import by.training.spring.model.Faculty;
import by.training.spring.model.Professor;
import by.training.spring.model.University;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {
    //Universities
    @Bean(name = "bntu")
    public University getBntu() {
        University u = new University();
        u.setName("The Belarusian National Technical University");
        List<Faculty> facs = new ArrayList<>();
        facs.add(getAtf());
        facs.add(getMef());
        u.setFaculties(facs);
        return u;
    }

    //Faculties
    @Bean(name = "atf")
    public Faculty getAtf() {
        Faculty f = new Faculty("Automotive and Tractor Faculty");
        List<Professor> prof = new ArrayList<>();
        prof.add(getPupkin());
        prof.add(getHolkin());
        f.setProfessors(prof);
        return f;
    }

    @Bean(name = "mef")
    public Faculty getMef() {
        Faculty f = new Faculty("Mechanical Engineering Faculty");
        List<Professor> prof = new ArrayList<>();
        prof.add(getIvanov());
        prof.add(getPetrov());
        prof.add(getSidorov());
        f.setProfessors(prof);
        return f;
    }

    //Professors
    @Bean(name = "pupkin")
    public Professor getPupkin() {
        return new Professor("Anton", "Pupkin");
    }

    @Bean(name = "holkin")
    public Professor getHolkin() {
        return new Professor("Valentin", "Holkin");
    }

    @Bean(name = "ivanov")
    public Professor getIvanov() {
        return new Professor("Ivan", "Ivanov");
    }

    @Bean(name = "petrov")
    public Professor getPetrov() {
        return new Professor("Petr", "Petrov");
    }

    @Bean(name = "sidorov")
    public Professor getSidorov() {
        return new Professor("Fedor", "Sidorov");
    }
}
