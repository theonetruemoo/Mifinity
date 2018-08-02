package za.co.mifinity.interview;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import za.co.mifinity.interview.service.CardDetailService;
import za.co.mifinity.interview.service.UserService;
import za.co.mifinity.interview.service.impl.CardDetailServiceImpl;
import za.co.mifinity.interview.service.impl.UserServiceImpl;

@SpringBootApplication
public class MifinityInterviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MifinityInterviewApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public CardDetailService cardDetailService() {
        return new CardDetailServiceImpl();
    }
}
