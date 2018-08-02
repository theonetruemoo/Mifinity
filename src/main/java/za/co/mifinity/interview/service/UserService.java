package za.co.mifinity.interview.service;

import za.co.mifinity.interview.beans.entity.User;
import za.co.mifinity.interview.beans.pojo.dto.UserDTO;
import za.co.mifinity.interview.exception.InvalidUserCreationException;
import za.co.mifinity.interview.exception.LoginException;


public interface UserService {

    /**
     * Logs the user in
     * @param userDTO
     * @return
     * @throws LoginException
     */
    UserDTO login(UserDTO userDTO) throws LoginException;

    /**
     * Registers a new user at Level of "USER"
     * @param userName
     * @param pass1
     * @param pass2
     * @return
     * @throws InvalidUserCreationException
     */
    UserDTO register(String userName, String pass1, String pass2) throws InvalidUserCreationException;

    /**
     * converts from entity to DTO
     * @param entityBean
     * @return
     */
    UserDTO convertToDto(User entityBean);

    /**
     * converts from Dto to Entity
     * @param userDTO
     * @return
     */
    User convertToEntity(UserDTO userDTO);
}
