import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * Object acting as deck of cards. Consists of list of cards it stores
 * and methods to add and remove cards
 *
 * Methods: getDeckID, getCardList, addCard, removeCard, isDeckEmpty
 * isTooBig, isDeckFull, endGame
 *
 * @Jessie McColm and Lucia Adams
 */
public class Deck {

    private int deckID;
    // start of list is the top of deck, end of list is bottom
    private ArrayList<Card> cardList = new ArrayList<Card>();
    private String deckFile;

    /**
     * Constructor for objects of class Deck
     *
     */
    public Deck(int ID){
        deckID = ID;
        deckFile = String.format("deck%d_output.txt", deckID);
    }

    /**
     * Getter method for the unique DeckID
     *
     * @param  None
     * @return int deckID
     */
    public int getDeckID(){
      return deckID;
    }

    /**
     * Getter method for the Deck's card list (an ArrayList of card objects)
     *
     * @param  None
     * @return cardList
     */
    public ArrayList<Card> getCardList(){
      return cardList;
    }

    /**
     * Method to add Card object to the Deck list (to bottom of deck)
     *
     * @param  cardName  Card object to add
     * @return  None
     */
    public synchronized void addCard(Card cardName){
      cardList.add(cardName);
    }

    /**
     * Method to remove Card object from the Deck list (from top of deck)
     *
     * @param  None
     * @return cardToReturn Card object removed from deck
     */
    public synchronized Card removeCard(){
      Card cardToReturn= null;

      if (cardList.size() != 0){
        cardToReturn=cardList.remove(0);
      }

      return cardToReturn;
    }

    /**
     * Method to check whether the deck is empty (true if empty)
     *
     * @param None
     * @return Boolean isEmpty
     */
    public boolean isDeckEmpty(){

       boolean isEmpty;

       if (cardList.size() == 0){isEmpty = true;
       } else { isEmpty = false;}

       return isEmpty;
    }

    /**
     * Method to check if the list of cards in the deck exceeds 4
     * (true if too big)
     *
     * @param  None
     * @return Boolean isTooBig
     */
    public Boolean isTooBig(){
      boolean isTooBig = false;

      if (cardList.size() > 4){
        isTooBig = true;
      }

      return isTooBig;
    }

    /**
     * Method to check whether the deck is full (true if full)
     *
     * @param  None
     * @return Boolean isFull
     */
    public boolean isDeckFull(){

      boolean isFull;

      if (cardList.size() >= 4){isFull = true;
      } else { isFull = false;}

      return isFull;
    }

    /**
     * Method to output deck content to file named after deckID
     * at the end of the game
     *
     * @param  None
     * @return  None
     */
    public void endGame(){

        String textFileString;
        ArrayList<String> cardListContent = new ArrayList<String>();

        for (Card cardItem : cardList){
          cardListContent.add(cardItem.toString());
        }

        textFileString = String.format("deck %d contents: ",deckID);
        textFileString += String.join(" ", cardListContent);

        try {
          FileWriter fileToWrite = new FileWriter(deckFile);
          BufferedWriter output = new BufferedWriter(fileToWrite);
          output.write(textFileString);
          output.close();

        } catch (IOException e) {
          e.printStackTrace();
        }
    }
}
