package org.lessons.hellospring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.lessons.hellospring.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/students")
public class StudentController {

/*  @GetMapping
  public String studentList(Model model, @RequestParam(name = "search", required = false) String searchString){
    String studentString;
    // controllo se il parametro di ricerca è presente
    if(searchString != null){
      // ritorno la lista filtrata
      studentString = getStudentsString(searchString);
    } else {
      // ritorno la lista completa
      studentString = getStudentsString();
    }
    model.addAttribute("students", studentString);
    return "students";
  }*/
// versione con Optional
  @GetMapping
  public String studentList(Model model, @RequestParam("search") Optional<String> searchString){
    String studentString;
    List<Student> studentList;
    // controllo se il parametro di ricerca è presente
    if(searchString.isPresent()){
      // ritorno la lista filtrata
      studentString = getStudentsString(searchString.get());
      studentList = getStudents(searchString.get());
    } else {
      // ritorno la lista completa
      studentString = getStudentsString();
      studentList = getStudents();
    }
    model.addAttribute("students", studentString);
    model.addAttribute("studentList", studentList);
    return "students";
  }

  // uso della redirect
  @GetMapping("/list")
  public String redirectList(){
    return "redirect:/students";
  }

  // pagina di dettaglio dello studente con id preso dal path
  @GetMapping("/{id}")
  public String studentDetail(@PathVariable Integer id, Model model){
    // recupero lo Student con l'id preso dal path
    //String name = "unknown";
    Student currentStudent = null;
    for(Student student: getStudents()){
      if(student.getId() == id){
        // name = student.getName();
        currentStudent = student;
      }
    }
    // aggiungo il nome dello studente al model
    model.addAttribute("student", currentStudent);
    return "detail";
  }

  /* METODI DI UTILITY*/
  // metodo che crea una lista di studenti e la restituisce
  private List<Student> getStudents(){
    List<Student> students = new ArrayList<>();
    students.add(new Student(1, "Daniel", "daniel@email.com"));
    students.add(new Student(2, "Lucia", "lucia@email.com"));
    students.add(new Student(3, "Thomas", "thomas@email.com"));
    students.add(new Student(4, "Carlo", "carlo@email.com"));
    students.add(new Student(5, "Luca", "luca@email.com"));
    return students;
  }

  private List<Student> getStudents(String search){
    List<Student> students = getStudents();
    List<Student> result = new ArrayList<>();
    for(Student student: students){
      // se il nome contiene la parola di ricerca lo aggiungo alla lista risultato
      if(student.getName().toLowerCase().contains(search.toLowerCase())){
        result.add(student);
      }
    }
    return result;
  }
  // metodo che prende la lista di studenti e la converte in stringa
  private String getStudentsString(){
    List<Student> studentList = getStudents();
    return concatList(studentList);
  }
  private String getStudentsString(String search){
    List<Student> studentList = getStudents(search);
    return concatList(studentList);
  }
  private static String concatList(List<Student> studentList) {
    String concat = "";
    for(Student student : studentList){
      concat += student.getName() + ", ";
    }
    if(concat.length() > 0)
      return concat.substring(0, concat.length() - 2);
    else return "";
  }


}
