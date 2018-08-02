package za.co.mifinity.interview.repository;

import org.springframework.data.repository.CrudRepository;
import za.co.mifinity.interview.beans.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

    User findOneByUserNameAndPassword(String userName, String password) throws Exception;

    User findOneByUserName(String userName) throws Exception;

}
