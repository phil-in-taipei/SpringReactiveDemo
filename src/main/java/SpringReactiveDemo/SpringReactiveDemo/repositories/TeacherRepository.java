package SpringReactiveDemo.SpringReactiveDemo.repositories;
import SpringReactiveDemo.SpringReactiveDemo.models.Teacher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TeacherRepository extends ReactiveCrudRepository<Teacher, Integer> {

}
