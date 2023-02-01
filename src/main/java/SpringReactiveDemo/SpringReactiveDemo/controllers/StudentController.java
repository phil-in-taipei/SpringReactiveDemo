package SpringReactiveDemo.SpringReactiveDemo.controllers;

import SpringReactiveDemo.SpringReactiveDemo.models.Student;
import SpringReactiveDemo.SpringReactiveDemo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class StudentController {

    @Autowired
    StudentService studentService;

    @Bean
    public RouterFunction<ServerResponse> getAllStudentsRoute() {
        return route(GET("/students-route"),
                req -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(studentService.getAllStudents(), Student.class));
    }
}
