import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
/**
 * Runnable object acting as a player within the game.
 *
 * Methods: getPlayerID, getHand, getHasWon, writeToPlayerFile, addCard,
 * checkVictory, win, loss, kill, endGame, pickAndDrop, run
 *
 * @Jessie McColm and Lucia Adams
 */
public class Player implements Runnable{

    private int playerID;
    private ArrayList<Card> hand= new ArrayList<Card>();
    private Deck dropDeck;
    private Deck pickDeck;
    private String playerFile;
    private Boolean hasWon;
    private volatile Boolean alive;

    /**
     * Constructor for objects of class Player
     */
    public Player(int playID, Deck dropper, Deck picker){

        playerID = playID;
        dropDeck = dropper;
        pickDeck = picker;
        alive = true;
        hasWon = false;
        playerFile = String.format("player%d_output.txt", playerID);


        try{
          FileWriter fileToWrite = new FileWriter(playerFile);
          BufferedWriter output = new BufferedWriter(fileToWrite);
          output.write("");
          output.close();
        } catch(Exception e){}
    }

    /**
     * Getter method for playerID
     *
     * @param  None
     * @return int playerID
     */
    public int getPlayerID(){
      return playerID;
    }

    /**
     * Getter method for the players hand (an ArrayList of Cards)
     *
     * @param None
     * @return ArrayList<Card> hand
     */
    public ArrayList<Card> getHand()
    {
        return hand;
    }

    /**
     * Getter method for hasWon
     * hasWon used to track if Player has had a winning hand
     *
     * @param None
     * @return Boolean hasWon
     */
    public Boolean getHasWon(){
      return this.hasWon;

    }

    /**
     * Writes stringToWrite to player output file
     * stringToWrite is the desired string
     *
     * @param  stringToWrite string to write to file
     * @return  None
     */
    private void writeToPlayerFile(String stringToWrite){

      try {
        // true in FileWritter sets to append mode
        FileWriter fileToWrite = new FileWriter(playerFile, true);
        BufferedWriter output = new BufferedWriter(fileToWrite);
        output.write(stringToWrite);
        output.close();

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    /**
     * Method to add Card object to the hand
     *
     * @param  cardName  Card object to add
     * @return None
     */
    public void addCard(Card cardName){
      hand.add(0, cardName);
    }

    /**
     * Checks if all cards in player hand have the same value
     * Returns whether they have won or not and also sets the status
     * alive of the player to this boolean.
     *
     * @param  None
     * @return  Boolean hasWon
     */
    public boolean checkVictory(){

      boolean winner = true;
      int firstCard;

      if (hand.size() == 4){
        firstCard = hand.get(0).getCardValue();

        for (Card eachCard : hand){
          if (eachCard.getCardValue() != firstCard){
            winner = false;
            break;
          }
        }
      } else {
        winner = false;
      }

      hasWon = winner;
      if (hasWon){
        this.alive = false;
      }

      return hasWon;
    }


    /**
     * Output to screen the player has won and write this to player file
     * Then calls endGame method
     *
     * @param  None
     * @return  None
     */
    public void win(){

      String winMessage = String.format("player %d wins", playerID);
      System.out.print(winMessage + "\n");
      endGame(winMessage);

    }

    /**
     * Write that the player has lost to another player in player file
     * Then calls endGame method
     *
     * @param  winnerID playerID of the player than has won
     * @return  None
     */
    public void loss(int winnerID){

      String lossMessage = String.format(
      "player %d has informed player %d that player %d has won", winnerID, playerID, winnerID);

      endGame(lossMessage);

    }

    /**
     * Setter to set the alive attribute of Player to false
     * Used to kill the thread via polling
     *
     * @param  None
     * @return  None
     */
    public void kill(){
      alive = false;
    }

    /**
     * Write to player file that they have exited and write their hand
     *
     * @param  endMessage  message detailing a win or loss
     * @return  None
     */
    private void endGame(String endMessage){

      String textFileString;
      ArrayList<String> handContent = new ArrayList<String>();

      for (Card cardItem : hand){
        handContent.add(cardItem.toString());
      }

      textFileString = endMessage + "\n" + String.format("player %d exits\n", playerID) ;
      textFileString += String.format("player %d final hand: ",playerID);
      textFileString += String.join(" ", handContent);

      writeToPlayerFile(textFileString);

    }

    /**
     * Method to pick up a card from the corresponding pick deck
     * and drop a card to the drop deck
     * The Player favours cards with the same value as its ID,
     * otherwise drops any random card
     *
     * @param  None
     * @return  None
     */
    private synchronized void pickAndDrop(){

      String textFileString;

      if (pickDeck.isDeckFull() && !dropDeck.isTooBig()){

        Card pickedCard = pickDeck.removeCard();


        ArrayList<Card> possibleDrops = new ArrayList<Card>();

        for (Card eachCard : hand){
          if (eachCard.getCardValue() != playerID){
            possibleDrops.add(eachCard);
          }
        }

        int dropNum;
        if (possibleDrops.size() > 1){
          Random random = new Random();
          int randNum = random.nextInt(possibleDrops.size()-1);
          dropNum = randNum;
        } else {
          dropNum = 0;
        }
        Card cardToDrop = possibleDrops.get(dropNum);

        hand.remove(cardToDrop);
        dropDeck.addCard(cardToDrop);

        hand.add(pickedCard);

        textFileString = String.format("player %d draws a %d from deck %d\n",
        playerID, pickedCard.getCardValue(),pickDeck.getDeckID());
        textFileString += String.format("player %d discards a %d to deck %d\n",
        playerID, cardToDrop.getCardValue(), dropDeck.getDeckID());

        ArrayList<String> handContent = new ArrayList<String>();

        for (Card cardItem : hand){
          handContent.add(cardItem.toString());
        }

        textFileString += String.format("player %d current hand: ",playerID);
        textFileString += String.join(" ", handContent) + "\n";

        writeToPlayerFile(textFileString);

     }
    }

    /**
     * Method to make the player pick up and drop cards while the
     * player attribute alive is true.
     * Checks for victory throughout which will exit the loop on
     * finding a winning hand
     *
     * @param  None
     * @return  None
     */
    public void run(){

      while (alive){
        if (hand.size() == 4){
          if (checkVictory()){
            break;
          }
          this.pickAndDrop();
        }
      }

    }
}
