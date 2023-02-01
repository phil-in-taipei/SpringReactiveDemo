package SpringReactiveDemo.SpringReactiveDemo.controllers;

import SpringReactiveDemo.SpringReactiveDemo.models.Teacher;
import SpringReactiveDemo.SpringReactiveDemo.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.http.HttpStatus;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    public Mono<ServerResponse> getAllTeachers(ServerRequest request) {
        Flux<Teacher> teachers = teacherService.getAllTeachers();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(teachers, Teacher.class);
    }

    public Mono<ServerResponse> getTeacher(ServerRequest request) {
        Integer teacherId = Integer.parseInt(request.pathVariable("id"));
        return teacherService.getTeacher(teacherId)
                .flatMap(student -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(student), Teacher.class))
                .switchIfEmpty(ServerResponse.notFound()
                        .build());
    }

    public Mono<ServerResponse> addNewTeacher(ServerRequest request) {
        Mono<Teacher> teacherMono = request.bodyToMono(Teacher.class);
        Mono<Teacher> newTeacher = teacherMono.flatMap(teacherService::saveTeacher);
        return ServerResponse.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newTeacher, Teacher.class);
    }

    public Mono<ServerResponse> updateTeacher(ServerRequest request) {
        String teacherId = request.pathVariable("id");
        Mono<Teacher> teacherMono = request.bodyToMono(Teacher.class);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(teacherService.updateTeacher(Integer.valueOf(teacherId), teacherMono), Teacher.class);
    }

    public Mono<ServerResponse> deleteTeacher(ServerRequest request) {
        String teacherId = request.pathVariable("id");
        return teacherService.getTeacher(Integer.valueOf(teacherId))
                .flatMap(teacher -> teacherService
                        .deleteTeacher(teacher)
                        .then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
