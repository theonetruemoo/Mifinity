package za.co.mifinity.interview.service;

import za.co.mifinity.interview.beans.pojo.dto.CardDetailDTO;
import za.co.mifinity.interview.beans.pojo.dto.UserDTO;
import za.co.mifinity.interview.exception.InvalidCardCreationException;

import java.util.List;

public interface CardDetailService{

    /**
     * Returns a card based on card number
     * @param cardNumber
     * @return
     */
    CardDetailDTO findByCardNumber(String cardNumber);

    /**
     * Saves/updates a card
     * @param cardDetailDTO
     * @return
     * @throws InvalidCardCreationException
     */
    CardDetailDTO save(CardDetailDTO cardDetailDTO) throws InvalidCardCreationException;

    /**
     * returns a list of cards linked ot the user
     * @param userDTO
     * @return
     */
    List<CardDetailDTO> findAllCardsForUser(UserDTO userDTO);

    /**
     * finds all cards
     * @return
     */
    List<CardDetailDTO> findAll();

    /**
     * searches for a card that has the numbers entered
     * @param cardNumber
     * @param userDTO
     * @return
     */
    List<CardDetailDTO> findLikeCardNumber(String cardNumber, UserDTO userDTO);


}
