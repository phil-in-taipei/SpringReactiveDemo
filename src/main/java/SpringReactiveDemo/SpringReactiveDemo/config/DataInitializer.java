package SpringReactiveDemo.SpringReactiveDemo.config;

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

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        this.studentRepository
                .deleteAll()
                .thenMany(studentService.saveAll("Evan", "Ji", "Harry", "Ryan", "Rick", "Chuck"))
                .thenMany(studentService.getAllStudents())
                .subscribe(s -> System.out.println(s.getName()));
    }
}
