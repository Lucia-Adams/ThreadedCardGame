import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * The test class testCardGame.
 *
 * @Jessie McColm and Lucia Adams
 */
public class testCardGame
{
    /**
     * Default constructor for test class testCardGame
     */
    public testCardGame()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tests that a game with 1 player can finish and the last lines of the
     * output file read as we expect
     */
    @Test
    public void test1PlayerWinnable(){
        ArrayList<Card> cardList= new ArrayList<Card>();
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        cardList.add(new Card(2));
        cardList.add(new Card(82));
        cardList.add(new Card(2));
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        CardGame.runGame(cardList);

        try{
          File file = new File("player1_output.txt");
          FileInputStream fis = new FileInputStream(file);
          BufferedReader br = new BufferedReader(new InputStreamReader(fis));

          String currentLine1;
          String currentLine2;
          String currentLine3;

          String lastline1="";
          String lastline2="";
          String lastline3="";

          while( ((currentLine1 = br.readLine()) != null) && ((currentLine2 = br.readLine()) != null) && ((currentLine3 = br.readLine()) != null)){
            lastline1 = currentLine1;
            lastline2 = currentLine2;
            lastline3 = currentLine3;
          }

          br.close();

          boolean allLinesEqual=(lastline1.equals("player 1 wins") && lastline2.equals("player 1 exits") && lastline3.equals("player 1 final hand: 1 1 1 1"));
          assertTrue(allLinesEqual);
        } catch (IOException e){
            //if an error occurs, the test should fail
          e.printStackTrace();
          assertTrue(false);
        }

    }


    /**
     * Tests that a game with 2 players with one player winning immediately
     * ends and that player 1 writes 3 lines to their
     * output file with our expected output. Also tests last lines of
     * player 2s output is as we expect ie player 1 won
     */
    @Test
    public void test2PlayerImmediateWin(){
        ArrayList<Card> cardList= new ArrayList<Card>();
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        cardList.add(new Card(2));
        cardList.add(new Card(1));
        cardList.add(new Card(82));
        cardList.add(new Card(1));
        cardList.add(new Card(2));
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        cardList.add(new Card(2));
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        cardList.add(new Card(1));
        CardGame.runGame(cardList);
        boolean allLinesEqual = false;
        boolean allLinesEqual2 = false;
        try{
          File file = new File("player1_output.txt");
          FileInputStream fis = new FileInputStream(file);
          BufferedReader br = new BufferedReader(new InputStreamReader(fis));



          String lastline1=br.readLine();
          String lastline2=br.readLine();
          String lastline3=br.readLine();

          br.close();

           allLinesEqual=(lastline1.equals("player 1 wins") && lastline2.equals("player 1 exits") && lastline3.equals("player 1 final hand: 1 1 1 1"));

        } catch (IOException e){
            //if an error occurs, the test should fail
          e.printStackTrace();
          assertTrue(false);
        }

        // Tests last line of Player 2 here
        try{
          File file = new File("player2_output.txt");
          FileInputStream fis = new FileInputStream(file);
          BufferedReader br = new BufferedReader(new InputStreamReader(fis));

          String currentLine1;
          String currentLine2;
          String currentLine3;

          String lastlineA="";
          String lastlineB="";

          while( ((currentLine1 = br.readLine()) != null) && ((currentLine2 = br.readLine()) != null) && ((currentLine3 = br.readLine()) != null)){
            lastlineA = currentLine1;
            lastlineB = currentLine2;
          }

          br.close();

          allLinesEqual2=(lastlineA.equals("player 1 has informed player 2 that player 1 has won") && lastlineB.equals("player 2 exits") );

        } catch (IOException e){
            //if an error occurs, the test should fail
          e.printStackTrace();
          assertTrue(false);
        }

        assertTrue(allLinesEqual && allLinesEqual2);

    }



    /**
     * Tests that the dealCards method deals out known cards to
     * players in the expected order
     */
    @Test
    public void testDealCards(){
        Deck[] gameDecks = new Deck[2];
        Player[] gamePlayers = new Player[2];
        ArrayList<Card> cardList= new ArrayList<Card>();

        for (int i=0; i< 16; i++){
              cardList.add(new Card(i+1));
            }

        for (int i=0; i< 2; i++){
              gameDecks[i] = new Deck(i+1);
            }


        for (int i=0; i< 2; i++){
          gamePlayers[i] = new Player(i+1, gameDecks[(i+1)%2], gameDecks[i]);
        }

        CardGame.dealCards(cardList,gameDecks,gamePlayers);

        boolean testTrue=true;
        ArrayList<Card> player1cards=gamePlayers[0].getHand();
        if(!(player1cards.get(3)==cardList.get(0) && player1cards.get(2)==cardList.get(2) && player1cards.get(1)==cardList.get(4)
            && player1cards.get(0)==cardList.get(6))){
                testTrue=false;}

         ArrayList<Card> deck1cards=gameDecks[0].getCardList();
        if(!(deck1cards.get(0)==cardList.get(8) && deck1cards.get(1)==cardList.get(10) && deck1cards.get(2)==cardList.get(12)
            && deck1cards.get(3)==cardList.get(14))){
                testTrue=false;}

        assertTrue(testTrue);

    }

    /**
     * Tests that the dealCards method doesn't deal out known cards to players
     * in an unexpected order
     */
    @Test
    public void testDealCardsFalse(){
        Deck[] gameDecks = new Deck[2];
        Player[] gamePlayers = new Player[2];
        ArrayList<Card> cardList= new ArrayList<Card>();

        for (int i=0; i< 16; i++){
              cardList.add(new Card(i+1));
            }

        for (int i=0; i< 2; i++){
              gameDecks[i] = new Deck(i+1);
            }

        for (int i=0; i< 2; i++){
          gamePlayers[i] = new Player(i+1, gameDecks[(i+1)%2], gameDecks[i]);
        }

        CardGame.dealCards(cardList,gameDecks,gamePlayers);

        boolean testTrue=true;
        ArrayList<Card> player1cards=gamePlayers[0].getHand();
        if(!(player1cards.get(3)==cardList.get(3) && player1cards.get(2)==cardList.get(8) && player1cards.get(1)==cardList.get(2)
            && player1cards.get(0)==cardList.get(6))){
                testTrue=false;}

         ArrayList<Card> deck1cards=gameDecks[0].getCardList();
        if(!(deck1cards.get(0)==cardList.get(7) && deck1cards.get(2)==cardList.get(10) && deck1cards.get(2)==cardList.get(12)
            && deck1cards.get(3)==cardList.get(14))){
                testTrue=false;}

        assertFalse(testTrue);

    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        try{
            File fileToDelete = new File("player1_output.txt");
            fileToDelete.delete();

            fileToDelete = new File("player2_output.txt");
            fileToDelete.delete();

            fileToDelete = new File("deck1_output.txt");
            fileToDelete.delete();

            fileToDelete = new File("deck2_output.txt");
            fileToDelete.delete();


        }catch(Exception e) {

        }
    }
}
