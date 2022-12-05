package shop.mtcoding.finalproject.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;

public class DevInit extends DummyEntity {
    @Profile("dev")
    @Bean
    public CommandLineRunner dataSetting(UserRepository userRepository) {

        return (args) -> {
            User ssar = userRepository.save(newUser("ssar"));
        };
    }
}
