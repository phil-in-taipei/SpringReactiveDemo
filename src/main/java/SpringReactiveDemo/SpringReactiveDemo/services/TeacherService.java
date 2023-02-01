package SpringReactiveDemo.SpringReactiveDemo.services;

import SpringReactiveDemo.SpringReactiveDemo.models.Teacher;
import SpringReactiveDemo.SpringReactiveDemo.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    private final TransactionalOperator transactionalOperator;

    public Mono<Teacher> saveTeacher(Teacher teacher) {
        return transactionalOperator.transactional(teacherRepository.save(teacher));
    }

    public Flux<Teacher> saveAll(String... names) {
        final Flux<Teacher> reservationFlux = Flux.fromArray(names)
                .map(name -> new Teacher(null, name))
                .doOnNext(r -> Assert.isTrue(isNameValid(r), "the first letter must be uppercase"))
                .flatMap(this.teacherRepository::save);

        return transactionalOperator.transactional(reservationFlux);
    }

    public Flux<Teacher> getAllTeachers() {
        return transactionalOperator.transactional(teacherRepository.findAll());
    }

    public Mono<Teacher> getTeacher(Integer id) {
        return transactionalOperator.transactional(teacherRepository.findById(id));
    }

    public Mono<Teacher> updateTeacher(Integer id, Mono<Teacher> updatedTeacher) {
        return getTeacher(id)
                .flatMap(teacher -> updatedTeacher.flatMap(teacher1 -> {
                    teacher.setName(teacher1.getName());
                    return saveTeacher(teacher);
                }));
    }

    public Mono<Void> deleteTeacher(Teacher teacher) {
        return transactionalOperator.transactional(teacherRepository.delete(teacher));
    }

    private boolean isNameValid(Teacher teacher) {
        String name = teacher.getName();
        return name != null && Character.isUpperCase(name.charAt(0));
    }
}
