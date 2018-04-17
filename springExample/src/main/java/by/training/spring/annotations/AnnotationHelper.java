package by.training.spring.annotations;

import by.training.spring.model.Faculty;
import by.training.spring.model.Professor;
import by.training.spring.model.University;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("annotation")
public class AnnotationHelper{

    public University getUniversity() {

        Faculty atf = new Faculty("Automotive and Tractor Faculty");
        List<Professor> atfProf = new ArrayList<>();
        atfProf.add(new Professor("Anton", "Pupkin"));
        atfProf.add(new Professor("Valentin", "Holkin"));
        atf.setProfessors(atfProf);

        Faculty mef = new Faculty("Mechanical Engineering Faculty");
        List<Professor> mefProf = new ArrayList<>();
        mefProf.add(new Professor("Ivan", "Ivanov"));
        mefProf.add(new Professor("Petr", "Petrov"));
        mefProf.add(new Professor("Fedor", "Sidorov"));
        mef.setProfessors(mefProf);

        University u = new University();
        u.setName("The Belarusian National Technical University");
        List<Faculty> facs = new ArrayList<>();
        facs.add(atf);
        facs.add(mef);
        u.setFaculties(facs);
        return u;
    }
}