package SpringReactiveDemo.SpringReactiveDemo.config;

import SpringReactiveDemo.SpringReactiveDemo.repositories.TeacherRepository;
import SpringReactiveDemo.SpringReactiveDemo.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import SpringReactiveDemo.SpringReactiveDemo.repositories.StudentRepository;
import SpringReactiveDemo.SpringReactiveDemo.services.StudentService;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final StudentRepository studentRepository;

    private final StudentService studentService;

    private final TeacherRepository teacherRepository;

    private final TeacherService teacherService;

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        this.studentRepository
                .deleteAll()
                .thenMany(studentService.saveAll("Evan", "Ji", "Harry", "Ryan", "Rick", "Chuck"))
                .thenMany(studentService.getAllStudents())
                .subscribe(s -> System.out.println(s.getName()));
        this.teacherRepository
                .deleteAll()
                .thenMany(teacherService.saveAll("Laura", "Janine", "Al", "Burt", "Donna", "Mari"))
                .thenMany(teacherService.getAllTeachers())
                .subscribe(s -> System.out.println(s.getName()));
    }
}
