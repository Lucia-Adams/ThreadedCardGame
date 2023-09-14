import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The test class testPlayer.
 *
 * @Jessie McColm and Lucia Adams
 */
public class testPlayer
{
    /**
     * Default constructor for test class testPlayer
     */
    public testPlayer()
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
     * Tests that when you create a player with a specific ID,
     * getPlayerID will return that ID
     */
    @Test
    public void testGetPlayerID() {
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        assertTrue(testPlayer.getPlayerID()==1);
    }

    /**
     * Tests that when you create a player with a specific ID, getPlayerID will
     * not return something that isn't that ID
     */
    @Test
    public void testFalseGetPlayerID() {
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        assertFalse(testPlayer.getPlayerID()==2);
    }


    /**
     * Tests that when you create a player, you can add a card to their
     * cardList, and getHand() will return that card
     */
    @Test
    public void testAddCard(){
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        Card testCard=new Card(1);
        testPlayer.addCard(testCard);
        assertEquals(testPlayer.getHand().get(0),testCard);
    }

    /**
     * Tests that when you create a player, you can add a card to their cardList,
     * and getHand() will not return somthing that isn't that card
     */
    @Test
    public void testFalseAddCard(){
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        Card testCard=new Card(1);
        Card testCard2=new Card(1);
        testPlayer.addCard(testCard);
        assertNotEquals(testPlayer.getHand().get(0),testCard2);
    }

    /**
     * Tests that when you add 4 matching cards to a Player
     * (so that they are in a state where they should win the game)
     * calling checkVictory will return true
     */
    @Test
    public void testCheckVictoryWhenWinner() {
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        assertTrue(testPlayer.checkVictory());


    }

    /**
     * Tests that when you add 4 non matching cards to a Player
     * (so that they are in a state where they shouldn't win the game)
     * calling checkVictory will return false
     */
    @Test
    public void testCheckVictoryWhenNotWinner() {
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(2));
        assertFalse(testPlayer.checkVictory());
    }

    /**
     * Tests that a player picks up the correct card from the correct pack,
     * that a player drops the correct card to the correct pack,
     * and that the player's hand after this is what we expect.
     * Tests this by checking that the player output file is what we expect.
     * An exception needs to be throwable due to the use of reflection,
     * but it shouldn't occur because we know a method with that name exists
     */
    @Test
    public void testPickAndDrop() throws Exception{
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck2, testDeck1);
        Class playerClass = testPlayer.getClass();
        Method pickAndDrop= playerClass.getDeclaredMethod("pickAndDrop");
        pickAndDrop.setAccessible(true);

        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(2));

        testDeck1.addCard(new Card(1));
        testDeck1.addCard(new Card(2));
        testDeck1.addCard(new Card(3));
        testDeck1.addCard(new Card(4));

        testDeck2.addCard(new Card(5));
        testDeck2.addCard(new Card(6));
        testDeck2.addCard(new Card(7));
        testDeck2.addCard(new Card(8));



        pickAndDrop.invoke(testPlayer);
        try{
            File file = new File("player1_output.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line1=br.readLine();
            String line2=br.readLine();
            String line3=br.readLine();
            br.close();

            boolean allLinesEqual=(line1.equals("player 1 draws a 1 from deck 1") &&  line2.equals("player 1 discards a 2 to deck 2")
            && line3.equals("player 1 current hand: 1 1 1 1"));
            assertTrue(allLinesEqual);
          } catch (IOException e){
              //if an error occurs, the test should fail
            e.printStackTrace();
            assertTrue(false);
          }

    }

    /**
     * Tests that a player picks up the correct card from the correct pack,
     * that a player drops one of the possible correct cards to the correct pack,
     * and that the player's hand after this is one of the 2 hands possible.
     * Tests this by checking that the player output file is what we expect.
     * An exception needs to be throwable due to the use of reflection,
     * but it shouldn't occur because we know a method with that name exists
     */
    @Test
    public void testPickAndDropWithRandomness() throws Exception{
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck2, testDeck1);
        Class playerClass = testPlayer.getClass();
        Method pickAndDrop= playerClass.getDeclaredMethod("pickAndDrop");
        pickAndDrop.setAccessible(true);

        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(2));

        testDeck1.addCard(new Card(4));
        testDeck1.addCard(new Card(3));
        testDeck1.addCard(new Card(2));
        testDeck1.addCard(new Card(1));


        testDeck2.addCard(new Card(5));
        testDeck2.addCard(new Card(6));
        testDeck2.addCard(new Card(7));
        testDeck2.addCard(new Card(8));



        pickAndDrop.invoke(testPlayer);
        try{
            File file = new File("player1_output.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line1=br.readLine();
            String line2=br.readLine();
            String line3=br.readLine();
            br.close();


            boolean allLinesEqual=(line1.equals("player 1 draws a 4 from deck 1") &&   (line2.equals("player 1 discards a 2 to deck 2") ||
            line2.equals("player 1 discards a 4 to deck 2") ) && (line3.equals("player 1 current hand: 1 1 1 4") ||
            line3.equals("player 1 current hand: 1 1 1 2")));

            assertTrue(allLinesEqual);
          } catch (IOException e){
            e.printStackTrace();
            //if an error occurs, the test should fail
            assertTrue(false);
          }

    }

    /**
     * Tests that a player does not drop a card that matches their playerID.
     * Tests this by checking that the player output file is what we expect.
     * An exception needs to be throwable due to the use of reflection,
     * but it shouldn't occur because we know a method with that name exists
     */
    @Test
    public void testPickAndDropWithRandomnessDoesNotDropMatchingCards() throws Exception{
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck2, testDeck1);
        Class playerClass = testPlayer.getClass();
        Method pickAndDrop= playerClass.getDeclaredMethod("pickAndDrop");
        pickAndDrop.setAccessible(true);

        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(2));

        testDeck1.addCard(new Card(4));
        testDeck1.addCard(new Card(3));
        testDeck1.addCard(new Card(2));
        testDeck1.addCard(new Card(1));

        testDeck2.addCard(new Card(5));
        testDeck2.addCard(new Card(6));
        testDeck2.addCard(new Card(7));
        testDeck2.addCard(new Card(8));



        pickAndDrop.invoke(testPlayer);
        try{
            File file = new File("player1_output.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line1=br.readLine();
            String line2=br.readLine();
            br.close();
            assertFalse(line2.equals("player 1 discards a 1 to deck 2"));
          } catch (IOException e){
              //if an error occurs, the test should fail
            e.printStackTrace();
            assertTrue(false);
          }

    }


    /**
     * Tests that the win() method writes the expected output to the expected
     * player output file. We set up a player with specific cards and expect these
     * to be correctly detailed in the output. This method also tests
     * endGame, as endGame is called in win()
     * If an error occurs in the file reading stage, the test fails
     */
    @Test
    public void testWin(){
        //this method also tests that a player object writes to the expected files
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(2));
        testPlayer.win();
        try{
            File file = new File("player1_output.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line1=br.readLine();
            String line2=br.readLine();
            String line3=br.readLine();
            br.close();


            boolean allLinesEqual=(line1.equals("player 1 wins") && line2.equals("player 1 exits") && line3.equals("player 1 final hand: 2 1 1 1"));
            assertTrue(allLinesEqual);
          } catch (IOException e){
              //if an error occurs, the test should fail
            e.printStackTrace();
            assertTrue(false);
          }
    }

    /**
     * Tests that the loss() method writes the expected output to the expected
     * player output file. We set up a player with specific cards and expect these
     * to be correctly detailed in the output. We also specify a winning player,
     * and also expect to see this reflected in the output. This method also
     * tests endGame, as endGame is called in loss()
     * If an error occurs in the file reading stage, the test fails
     */
    @Test
    public void testLoss(){
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(1));
        testPlayer.addCard(new Card(2));
        testPlayer.loss(3);
        try{
            File file = new File("player1_output.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line1=br.readLine();
            String line2=br.readLine();
            String line3=br.readLine();
            br.close();


            boolean allLinesEqual=(line1.equals("player 3 has informed player 1 that player 3 has won") && line2.equals("player 1 exits") && line3.equals("player 1 final hand: 2 1 1 1"));
            assertTrue(allLinesEqual);
          } catch (IOException e){
              //if an error occurs, the test should fail
            e.printStackTrace();
            assertTrue(false);
          }
    }

    /**
     * Tests that when you create a player, you can add cards to their cardList,
     * and getHand() will return the cards in the expected order
     */
    @Test
    public void testGetHand(){
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        Card testCard=new Card(1);
        Card testCard2=new Card(2);
        Card testCard3=new Card(1);
        Card testCard4=new Card(4);
        testPlayer.addCard(testCard);
        testPlayer.addCard(testCard2);
        testPlayer.addCard(testCard3);
        testPlayer.addCard(testCard4);
        boolean testTruth=true;

        if(testPlayer.getHand().get(3)!=testCard || testPlayer.getHand().get(2)!=testCard2
        || testPlayer.getHand().get(1)!=testCard3 || testPlayer.getHand().get(0)!=testCard4){
            testTruth=false;
        };
        assertTrue(testTruth);
    }


    /**
     * Tests that when you add 3 matching cards to a Player
     * (so that they are in a state where they shouldn't win the game)
     * calling checkVictory will return false
     */
    @Test
    public void testCheckVictoryWhenHasTooFewCards(){
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        Card testCard=new Card(1);
        Card testCard2=new Card(1);
        Card testCard3=new Card(1);
        testPlayer.addCard(testCard);
        testPlayer.addCard(testCard2);
        testPlayer.addCard(testCard3);
        assertFalse(testPlayer.checkVictory());
    }

    /**
     * Tests that when you add 5 matching cards to a Player (so that they are
     * in a state where they shouldn't win the game) calling checkVictory will
     * return false
     */
    @Test
    public void testCheckVictoryWhenHasTooManyCards(){
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        Card testCard=new Card(1);
        Card testCard2=new Card(1);
        Card testCard3=new Card(1);
        Card testCard4=new Card(1);
        Card testCard5=new Card(1);
        testPlayer.addCard(testCard);
        testPlayer.addCard(testCard2);
        testPlayer.addCard(testCard3);
        testPlayer.addCard(testCard4);
        testPlayer.addCard(testCard5);
        assertFalse(testPlayer.checkVictory());
    }

    /**
     * Tests that when you call kill() on a player the run() method is able
     * to return and the execution can pass to the line of code making the
     * test pass
     */
    @Test
    public void testKill(){
        Deck testDeck1 = new Deck(1);
        Deck testDeck2 = new Deck(2);
        Player testPlayer = new Player(1,testDeck1, testDeck2);
        testPlayer.kill();
        testPlayer.run();
        //tests if run stops executing
        assertTrue(true);
    }


    /**
     * Tears down the test fixture. Deletes player1_output file so it
     * can be writted to and used in different test
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        try{
            File fileToDelete = new File("player1_output.txt");
            fileToDelete.delete();
        }catch(Exception e) {

        }
    }
}
