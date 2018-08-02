package za.co.mifinity.interview;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.mifinity.interview.beans.entity.User;
import za.co.mifinity.interview.beans.pojo.dto.UserDTO;
import za.co.mifinity.interview.exception.InvalidUserCreationException;
import za.co.mifinity.interview.exception.LoginException;
import za.co.mifinity.interview.repository.UserRepository;
import za.co.mifinity.interview.service.UserService;
import za.co.mifinity.interview.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
        @Bean
        public ModelMapper modelMapper() {
            return new ModelMapper();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    User user;

    @Before
    public void init() throws Exception {
        user = new User("username", "password");


    }


    @Test(expected = LoginException.class)
    public void noName() throws Exception{
        userService.login(new UserDTO("",""));
    }



    @Test
    public void validLogin() throws Exception{
        Mockito.when(userRepository.findOneByUserNameAndPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(user);
        UserDTO userDTO =userService.login(new UserDTO("username","password"));
        assert userDTO.getUserName().equalsIgnoreCase("username");
    }

    @Test
    public void inValidLogin() {
        try{
            Mockito.when(userRepository.findOneByUserNameAndPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(null);
            userService.login(new UserDTO("username","password"));
        }
        catch (Exception e){
            assert e.getMessage().equalsIgnoreCase("no user with that combination could be found");
        }
    }

    @Test
    public void noUserName() {
        try{
            userService.login(new UserDTO("","password"));
        }
        catch (Exception le){
            assert le.getMessage().equalsIgnoreCase("Username cannot be empty.");
        }
    }

    @Test
    public void noPassword() {
        try{
            userService.login(new UserDTO("username",""));
        }
        catch (Exception le){
            assert le.getMessage().equalsIgnoreCase("Password cannot be empty.");
        }
    }

    @Test
    public void registerNameExists() {
        try{
            Mockito.when(userRepository.findOneByUserName(Mockito.anyString())).thenReturn(new User("username","password"));
            userService.register("username","password","password");
        }
        catch (InvalidUserCreationException le){
            assert le.getMessage().equalsIgnoreCase("Username has already been taken");
        }
        catch (Exception e){
            assert false;
        }
    }

    @Test
    public void registerPasswordNoMatch() {
        try{
            userService.register("username","2","1");
        }
        catch (InvalidUserCreationException e){
            assert e.getMessage().equalsIgnoreCase("Your Passwords do not match");
        }
    }

    @Test
    public void registerSuccess() {
        try{
            Mockito.when(userRepository.findOneByUserName(Mockito.anyString())).thenReturn(null);
            Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(new User("username","password"));
            UserDTO userDTO= userService.register("username","password","password");
            userDTO.getUserName().equalsIgnoreCase("username");
        }
        catch (Exception e){
            assert false;
        }
    }




}
