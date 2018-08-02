package za.co.mifinity.interview.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import za.co.mifinity.interview.beans.entity.CardDetail;
import za.co.mifinity.interview.beans.entity.User;

import java.util.List;

public interface CardDetailRepository extends CrudRepository<CardDetail, Long> {

    CardDetail findByCardNumber(String cardNumber);

    List<CardDetail> findByUser(User user);

    @Query("SELECT cd FROM CardDetail cd WHERE cd.cardNumber LIKE CONCAT('%',:cardNumber,'%')")
    List<CardDetail> findLikeCardNumberAdmin(@Param("cardNumber") String cardNumber);

    @Query("SELECT cd FROM CardDetail cd WHERE cd.cardNumber LIKE CONCAT('%',:cardNumber,'%') and cd.user.id=:userId")
    List<CardDetail> findLikeCardNumber(@Param("cardNumber") String cardNumber, @Param("userId") long userId);


}
