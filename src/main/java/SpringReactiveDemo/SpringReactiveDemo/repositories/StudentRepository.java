package SpringReactiveDemo.SpringReactiveDemo.repositories;

import SpringReactiveDemo.SpringReactiveDemo.models.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface StudentRepository extends ReactiveCrudRepository<Student, Integer> {
}
