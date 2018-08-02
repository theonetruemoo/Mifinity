package za.co.mifinity.interview.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.mifinity.interview.beans.entity.User;
import za.co.mifinity.interview.beans.pojo.dto.UserDTO;
import za.co.mifinity.interview.exception.GenericException;
import za.co.mifinity.interview.exception.InvalidUserCreationException;
import za.co.mifinity.interview.exception.LoginException;
import za.co.mifinity.interview.repository.UserRepository;
import za.co.mifinity.interview.service.UserService;
import za.co.mifinity.interview.util.LoggerService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;


    public UserDTO convertToDto(User entityBean) {
        return modelMapper.map(entityBean, UserDTO.class);
    }

    public User convertToEntity(UserDTO dtoBean) {
        return modelMapper.map(dtoBean, User.class);
    }


    private static String hashPassword(String plainText) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(plainText.getBytes());
            char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash.toString();
    }

    @Override
    public UserDTO login(UserDTO userDTO) throws LoginException {
        try {
            genericUserChecks(userDTO.getUserName(), userDTO.getPassword());
            User user = userRepository.findOneByUserNameAndPassword(userDTO.getUserName(), hashPassword(userDTO.getPassword()));
            if (user == null) {
                throw new LoginException("no user with that combination could be found");
            }
            return convertToDto(user);
        }
        catch (GenericException ge){
            throw new LoginException(ge.getMessage());
        }
        catch (Exception ex) {
            LoggerService.logError(ex, logger);
        }
        throw new LoginException("no user with that combination could be found");
    }

    @Override
    public UserDTO register(String userName, String pass1, String pass2) throws InvalidUserCreationException {
        try {
            genericUserChecks(userName, pass1);
            if (!pass1.equalsIgnoreCase(pass2)) {
                throw new InvalidUserCreationException("Your Passwords do not match");
            }
            if (userRepository.findOneByUserName(userName) != null) {
                throw new InvalidUserCreationException("Username has already been taken");
            }
            return convertToDto(userRepository.save(new User(userName, hashPassword(pass1))));
        } catch (Exception e) {
            throw new InvalidUserCreationException(e.getMessage());
        }


    }


    private void genericUserChecks(String userName, String password) throws GenericException {
        if (userName == null || userName.isEmpty()) {
            throw new GenericException("Username cannot be empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new GenericException("Password cannot be empty.");
        }
    }

}
