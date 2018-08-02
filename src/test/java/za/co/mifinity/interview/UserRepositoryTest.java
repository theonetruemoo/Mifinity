package za.co.mifinity.interview;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import za.co.mifinity.interview.beans.entity.User;
import za.co.mifinity.interview.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private User getUser() {
        if (user == null) {
            user = new User("user", "password");
            entityManager.persist(user);
            entityManager.flush();
        }
        return user;
    }
    @Test
    public void findByUsername() throws Exception {
        User entity = userRepository.findOneByUserName(getUser().getUserName());
        assert(entity.getUserName().equalsIgnoreCase(getUser().getUserName()));
    }

    @Test
    public void findByUsernameFail() throws Exception {
        User entity = userRepository.findOneByUserName("fail");
        assert(entity ==null);
    }

    @Test
    public void login() throws Exception {
        User entity = userRepository.findOneByUserNameAndPassword(getUser().getUserName(),getUser().getPassword());
        assert(entity.getUserName().equalsIgnoreCase(getUser().getUserName()));
    }

    @Test
    public void loginFail() throws Exception {
        User entity = userRepository.findOneByUserNameAndPassword(getUser().getUserName(),"");
        assert(entity == null);
    }
}
