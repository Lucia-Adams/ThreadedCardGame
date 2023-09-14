/**
 * Object acting as a card in the game, storing a value of its face
 *
 * Methods: getCardValue, toString
 *
 * @Jessie McColm and Lucia Adams
 */
public class Card {

    private int cardValue;

    /**
     * Constructor for objects of class Card
     *
     */
    public Card(int value){
        cardValue = value;
    }

    /**
     * Getter for the value of the card
     *
     * @param  None
     * @return  int cardValue
     */
    public int getCardValue(){
        return cardValue;
    }

    /**
     * To String method that returns String of value on card
     *
     * @param  None
     * @return  String cardValue
     */
    @Override
    public String toString(){
      return String.valueOf(cardValue);
    }

}
