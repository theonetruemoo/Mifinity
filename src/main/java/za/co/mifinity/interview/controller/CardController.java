package za.co.mifinity.interview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import za.co.mifinity.interview.beans.pojo.Role;
import za.co.mifinity.interview.beans.pojo.dto.CardDetailDTO;
import za.co.mifinity.interview.exception.InvalidCardCreationException;
import za.co.mifinity.interview.service.CardDetailService;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;


@ManagedBean
@RequestScoped
public class CardController extends GenericController implements Serializable {

    @Autowired
    private CardDetailService cardDetailService;
    private CardDetailDTO cardDetailDTO;
    private ArrayList<CardDetailDTO> cardDetailDTOS;
    private String searchString;


    @PostConstruct
    public void init() {
        cardDetailDTO = new CardDetailDTO();
        cardDetailDTOS = populateCards();
    }


    public CardDetailDTO getCardDetailDTO() {
        return cardDetailDTO;
    }

    public void save() {
        try {
            cardDetailDTO.setUser(super.getUserDetails());
            cardDetailService.save(cardDetailDTO);
            cardDetailDTOS = populateCards();
            cardDetailDTO = new CardDetailDTO();
            updateMessage(FacesMessage.SEVERITY_INFO, "Card Saved");
        } catch (InvalidCardCreationException e) {
            updateMessage(FacesMessage.SEVERITY_ERROR, e.getMessage());
        }
    }

    public void search() {
        cardDetailDTOS = new ArrayList<>(cardDetailService.findLikeCardNumber(searchString, super.getUserDetails()));
    }


    public ArrayList<CardDetailDTO> populateCards() {
        if (super.getUserDetails().getRole() == Role.ADMIN) {
            return new ArrayList<>(cardDetailService.findAll());
        }
        return new ArrayList<>(cardDetailService.findAllCardsForUser(super.getUserDetails()));
    }

    public void setCardDetailDTO(CardDetailDTO cardDetailDTO) {
        this.cardDetailDTO = cardDetailDTO;
    }

    public ArrayList<CardDetailDTO> getCardDetailDTOS() {
        return cardDetailDTOS;
    }

    public void setCardDetailDTOS(ArrayList<CardDetailDTO> cardDetailDTOS) {
        this.cardDetailDTOS = cardDetailDTOS;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
