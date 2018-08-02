package za.co.mifinity.interview.beans.pojo.dto;

import java.time.LocalDate;

public class CardDetailDTO {

    private long id;
    private String cardNumber;
    private String name;
    private LocalDate expDate;
    private UserDTO user;
    //Form Fields
    private String expDateString;

    public CardDetailDTO() {
    }

    public CardDetailDTO(String cardNumber, String name, LocalDate expDate) {
        this.cardNumber = cardNumber;
        this.name = name;
        this.expDate = expDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getExpDateString() {
        return expDateString;
    }

    public void setExpDateString(String expDateString) {
        this.expDateString = expDateString;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CardDetailDTO{");
        sb.append("id=").append(id);
        sb.append(", cardNumber='").append(cardNumber).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", expDate='").append(expDate).append('\'');
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardDetailDTO)) return false;

        CardDetailDTO that = (CardDetailDTO) o;

        if (getId() != that.getId()) return false;
        if (getCardNumber() != null ? !getCardNumber().equals(that.getCardNumber()) : that.getCardNumber() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getExpDate() != null ? !getExpDate().equals(that.getExpDate()) : that.getExpDate() != null) return false;
        return getUser() != null ? getUser().equals(that.getUser()) : that.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getCardNumber() != null ? getCardNumber().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getExpDate() != null ? getExpDate().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }
}
