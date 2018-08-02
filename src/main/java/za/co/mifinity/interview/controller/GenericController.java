package za.co.mifinity.interview.controller;

import za.co.mifinity.interview.beans.pojo.dto.UserDTO;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class GenericController {

    public void userToSession(UserDTO userDTO) {
        HttpSession httpSession = GenericController.getRequest().getSession();
        httpSession.setAttribute("thatGuy", userDTO);
    }

    public UserDTO getUserDetails() {
        HttpSession httpSession = GenericController.getRequest().getSession();
        return (UserDTO) httpSession.getAttribute("thatGuy");

    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
    }

    public void updateMessage(FacesMessage.Severity severity, String error) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, null, error));
    }
}
