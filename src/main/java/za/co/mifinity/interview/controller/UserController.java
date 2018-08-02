package za.co.mifinity.interview.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.mifinity.interview.beans.pojo.dto.UserDTO;
import za.co.mifinity.interview.exception.InvalidUserCreationException;
import za.co.mifinity.interview.exception.LoginException;
import za.co.mifinity.interview.service.UserService;
import za.co.mifinity.interview.util.LoggerService;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
public class UserController extends GenericController implements Serializable {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    private UserDTO userDTO;
    private String regUserName;
    private String regPassword;
    private String confirmRegPassword;

    {
        userDTO = new UserDTO();
    }

    public void login() {
        try {
            userDTO = userService.login(userDTO);
            super.userToSession(userDTO);
            FacesContext.getCurrentInstance().getExternalContext().redirect("cardDetailsPage.xhtml");

        } catch (LoginException le) {
            updateMessage(FacesMessage.SEVERITY_ERROR, le.getMessage());
        } catch (Exception e) {
            LoggerService.logError(e, logger);
        }
    }


    public void register() {

        try {
            userDTO = userService.register(regUserName, regPassword, confirmRegPassword);
        } catch (InvalidUserCreationException iuce) {
            updateMessage(FacesMessage.SEVERITY_ERROR, iuce.getMessage());
        }
        updateMessage(FacesMessage.SEVERITY_INFO, "Saved, please hit login");

    }


    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getRegUserName() {
        return regUserName;
    }

    public void setRegUserName(String regUserName) {
        this.regUserName = regUserName;
    }

    public String getRegPassword() {
        return regPassword;
    }

    public void setRegPassword(String regPassword) {
        this.regPassword = regPassword;
    }

    public String getConfirmRegPassword() {
        return confirmRegPassword;
    }

    public void setConfirmRegPassword(String confirmRegPassword) {
        this.confirmRegPassword = confirmRegPassword;
    }
}
