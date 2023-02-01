package SpringReactiveDemo.SpringReactiveDemo.config;

import SpringReactiveDemo.SpringReactiveDemo.controllers.StudentController;
import SpringReactiveDemo.SpringReactiveDemo.controllers.TeacherController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.http.HttpStatus;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class RouteConfig {

    @Bean
    RouterFunction<ServerResponse> studentRoutes(StudentController studentController) {
        return route(
                GET("/students")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::getAllStudents)
                .andRoute(GET("/students/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::getStudent)
                .andRoute(POST("/students")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::addNewStudent)
                .andRoute(PUT("/students/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::updateStudent)
                .andRoute(DELETE("/students/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), studentController::deleteStudent);
    }

    @Bean
    RouterFunction<ServerResponse> teacherRoutes(TeacherController teacherController) {
        return route(
                GET("/teachers")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), teacherController::getAllTeachers)
                .andRoute(GET("/teachers/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), teacherController::getTeacher)
                .andRoute(POST("/teachers")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), teacherController::addNewTeacher)
                .andRoute(PUT("/teachers/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), teacherController::updateTeacher)
                .andRoute(DELETE("/teachers/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), teacherController::deleteTeacher);
    }
}
