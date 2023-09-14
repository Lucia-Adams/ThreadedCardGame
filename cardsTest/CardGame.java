import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Main class that runs the card game
 *
 * Methods: main, runGame, setUpCards, dealCards
 *
 * @Jessie McColm and Lucia Adams
 */
public class CardGame {

  public static void main(String[] args){

    ArrayList<Card> cardList = setUpCards();
    runGame(cardList);

  }

  /**
   * Method to run and stop the player threads
   *
   * @param  cardList list of cards in the game
   * @return  None
   */
  public static void runGame(ArrayList<Card> cardList){

    int numOfPlayers = cardList.size()/8;
    Player winner = null;
    ArrayList<Player> losers;

    Deck[] gameDecks = new Deck[numOfPlayers];
    Player[] gamePlayers = new Player[numOfPlayers];
    Thread[] gameThreads = new Thread[numOfPlayers];

    // loop through to make the decks
    for (int i=0; i< numOfPlayers; i++){
      gameDecks[i] = new Deck(i+1);
    }

    //loop through to make the players
    // with their corresponding pick and drop decks
    for (int i=0; i< numOfPlayers; i++){
      gamePlayers[i] = new Player(i+1, gameDecks[(i+1)%numOfPlayers], gameDecks[i]);
    }

    dealCards(cardList, gameDecks, gamePlayers);

    for (int i=0; i< numOfPlayers; i++){
      Thread playerThread = new Thread (gamePlayers[i]);
      gameThreads[i] = playerThread;
      playerThread.start();
    }

    // if thread wins it stops itself by setting alive to false
    // checks if something has won and if it has then it kills the others
    boolean loop = true;
    while (loop){
      for (int i=0; i< numOfPlayers; i++){
        if (gamePlayers[i].getHasWon()){
           winner = gamePlayers[i];
           loop = false;
           break;
        }
      }
    }

    for (int j=0; j< numOfPlayers; j++){
      gamePlayers[j].kill();
    }

    // go through list with winner removed so we can call loss
    losers = new ArrayList<>(Arrays.asList(gamePlayers));
    losers.remove(winner);

    int winnerID = winner.getPlayerID();

    for (Player eachPlayer : losers){
      eachPlayer.loss(winnerID);
    }

    // call win on the winner
    winner.win();

    // go through decks and call their endgame method
    for (Deck eachDeck: gameDecks){
      eachDeck.endGame();
    }

  }

  /**
   * Method to take in user input of number of players and deck file
   *
   * @param  None
   * @return  cardList list of cards in the game
   */
  public static ArrayList<Card> setUpCards(){

    Scanner readInput = new Scanner(System.in);
    boolean invalidInput = true;
    //has to be set to 0 as java doesn't like lack of instantiation - will always be changed if
    //we break out of the first while loop
    int nOfPlayers = 0;

    // Enter the number of players
    while (invalidInput){
      System.out.println("Please enter the number of players:");
      if (readInput.hasNextInt()){
        nOfPlayers = readInput.nextInt();
        readInput.nextLine(); // as only reads int. Needs to clear over rest of line
        if (nOfPlayers > 0){
          invalidInput = false;
        } else {
          System.out.println("Invalid integer input - must be integer above 0");
        }
      } else {
        System.out.println("Invalid string input - must be an integer");
        readInput.nextLine();
        // clears line from scanner as otherwise keeps reading the same line
      }
    }

    invalidInput = true;
    Pack cardPack = new Pack();

    // Enter the pack file name
    while (invalidInput){
      System.out.println("Please enter location of pack to load:");
      String filename = readInput.nextLine();
      cardPack.readPackFile(filename, nOfPlayers);
      invalidInput = !(cardPack.getValidity());

    }

    ArrayList<Card> cardList = new ArrayList<Card>();
    cardList = cardPack.getCardList();
    readInput.close();
    return cardList;

  }

  /**
   * Method to deal the cards out to players and decks
   *
   * @param  cardsInGame list of cards in the game
   * @param  decksInGame list of decks in the game
   * @param  playersInGame list of players in the game
   * @return  None
   */
  public static void dealCards(ArrayList<Card> cardsInGame, Deck[] decksInGame,
  Player[] playersInGame){

    int cardCounter = 0;

    for (int i=0; i<4 ; i++){
      for (Player eachPlayer : playersInGame){
        eachPlayer.addCard(cardsInGame.get(cardCounter));
        cardCounter++;
      }
    }

    for (int i=0; i<4 ; i++){
      for (Deck eachDeck : decksInGame){
        eachDeck.addCard(cardsInGame.get(cardCounter));
        cardCounter++;
      }
    }
  }


}
