package uk.ac.leedsbeckett.albertarkaa.studentportal.util;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.albertarkaa.studentportal.model.CourseModel;
import uk.ac.leedsbeckett.albertarkaa.studentportal.repository.CourseRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CourseDataInitializer implements ApplicationRunner {

    private final CourseRepository courseRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            CourseModel course = CourseModel.builder()
                    .courseCode("SESC50" + i)
                    .courseName(new Faker().lorem().sentence(5))
                    .courseDescription(new Faker().lorem().sentence(25))
                    .fee(new Faker().number().randomDouble(2, 50, 150))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            courseRepository.save(course);
        }
    }
}