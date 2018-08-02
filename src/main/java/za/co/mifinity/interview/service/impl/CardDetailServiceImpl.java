package za.co.mifinity.interview.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.mifinity.interview.beans.entity.CardDetail;
import za.co.mifinity.interview.beans.pojo.Role;
import za.co.mifinity.interview.beans.pojo.dto.CardDetailDTO;
import za.co.mifinity.interview.beans.pojo.dto.UserDTO;
import za.co.mifinity.interview.exception.InvalidCardCreationException;
import za.co.mifinity.interview.repository.CardDetailRepository;
import za.co.mifinity.interview.service.CardDetailService;
import za.co.mifinity.interview.service.UserService;
import za.co.mifinity.interview.util.DateUtil;
import za.co.mifinity.interview.util.LoggerService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CardDetailServiceImpl implements CardDetailService {

    private final static Logger logger = LoggerFactory.getLogger(CardDetailServiceImpl.class);

    @Autowired
    private CardDetailRepository cardDetailRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;


    @Override
    public CardDetailDTO findByCardNumber(String cardNumber) {
        cardDetailRepository.findByCardNumber(cardNumber);

        return null;
    }

    @Override
    public CardDetailDTO save(CardDetailDTO cardDetailDTO) throws InvalidCardCreationException {
        try {
            cardDetailDTO = validateCardDetails(cardDetailDTO);
            CardDetail cardDetail = cardDetailRepository.save(convertToEntity(cardDetailDTO));
            return convertToDto(cardDetail);
        } catch (InvalidCardCreationException ivcce) {
            throw ivcce;
        } catch (Exception e) {
            LoggerService.logError(e, logger);
            throw new InvalidCardCreationException("There was a problem with Registering your card");
        }
    }

    @Override
    public List<CardDetailDTO> findAllCardsForUser(UserDTO userDTO) {
        return convertToDtoList(cardDetailRepository.findByUser(userService.convertToEntity(userDTO)));

    }

    @Override
    public List<CardDetailDTO> findAll() {
        return convertToDtoList(cardDetailRepository.findAll());
    }

    @Override
    public List<CardDetailDTO> findLikeCardNumber(String cardNumber, UserDTO userDTO) {
        if (userDTO.getRole() == Role.ADMIN) {
            return convertToDtoList(cardDetailRepository.findLikeCardNumberAdmin(cardNumber));
        }
        return convertToDtoList(cardDetailRepository.findLikeCardNumber(cardNumber, userDTO.getId()));
    }

    private CardDetailDTO validateCardDetails(CardDetailDTO cardDetailDTO) throws InvalidCardCreationException {

        int cardNumberLength = 16;
        String currentMillenium = "20";

        if (!cardDetailDTO.getCardNumber().matches("[0-9]+")) {
            throw new InvalidCardCreationException("Only numbers allowed in the Card number.");
        }

        if (cardDetailDTO.getCardNumber().length() != cardNumberLength) {
            throw new InvalidCardCreationException("Card number must be " + cardNumberLength + " numbers long.");
        }

        if (cardDetailDTO.getName() == null || cardDetailDTO.getName().length() == 0) {
            throw new InvalidCardCreationException("Please enter a name for the card.");
        }
        try {
            LocalDate expDate = LocalDate.of(Integer.parseInt(currentMillenium + cardDetailDTO.getExpDateString().substring(0, 2)), Integer.parseInt(cardDetailDTO.getExpDateString().substring(3, 5)), 1);
            if (expDate.isBefore(LocalDate.now())) {
                throw new InvalidCardCreationException("That card has already expired.");
            }
            CardDetail existingCard = cardDetailRepository.findByCardNumber(cardDetailDTO.getCardNumber());
            if (existingCard != null) {
                if (existingCard.getUser().getId() != cardDetailDTO.getUser().getId() && cardDetailDTO.getUser().getRole() != Role.ADMIN) {
                    throw new InvalidCardCreationException("That Card is already registered with another user");
                } else if (cardDetailDTO.getUser().getRole() != Role.ADMIN) {
                    cardDetailDTO.setUser(userService.convertToDto(existingCard.getUser()));
                }
                cardDetailDTO.setId(existingCard.getId());
            }
            cardDetailDTO.setExpDate(expDate);
        } catch (InvalidCardCreationException icce) {
            throw icce;
        } catch (Exception e) {
            LoggerService.logError(e, logger);
            throw new InvalidCardCreationException("Please enter a valid expiry date for the card");
        }
        return cardDetailDTO;
    }

    private List<CardDetailDTO> convertToDtoList(Iterable<CardDetail> cardDetails) {
        List<CardDetailDTO> cardDetailDTOS = new ArrayList<>();
        for (CardDetail cardDetail : cardDetails) {
            cardDetailDTOS.add(convertToDto(cardDetail));
        }
        return cardDetailDTOS;
    }

    private CardDetailDTO convertToDto(CardDetail entityBean) {
        CardDetailDTO cardDetailDTO = modelMapper.map(entityBean, CardDetailDTO.class);
        cardDetailDTO.setExpDateString(DateUtil.CONVERT_EXP_DATE(cardDetailDTO.getExpDate()));
        return cardDetailDTO;
    }

    private CardDetail convertToEntity(CardDetailDTO dtoBean) {
        return modelMapper.map(dtoBean, CardDetail.class);
    }


}
