import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * The test class testDeck.
 *
 * @Jessie McColm and Lucia Adams
 */
public class testDeck
{
    /**
     * Default constructor for test class testDeck
     */
    public testDeck()
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
     * Tests that when a deck is created with a specific ID, getDeckID()
     * returns the expected value
     *
     */
    @Test
    public void testGetDeckID(){
        Deck testDeck = new Deck(1);
        assertEquals(1,testDeck.getDeckID());

    }

    /**
     * Tests that you can add a card to a deck and when you call getCardList it
     * contains that card object
     *
     */
    @Test
    public void testAddCard(){
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        assertEquals(testCard, testDeck.getCardList().get(0));

    }

    /**
     * Tests that you can add a card to deck and when you call removeCard()
     * it returns that card object
     *
     */
    @Test
    public void testRemoveCard() {
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        assertEquals(testCard, testDeck.removeCard());

    }

    /**
     * Tests that you can add card, to deck and when you call removeCard()
     * it returns those card objects in the expected order (first Card to be added
     * is also the first card to be removed
     */
    @Test
    public void testRemoveCardOrdering(){
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        Card testCard2 = new Card(2);
        Card testCard3 = new Card(1);
        testDeck.addCard(testCard);
        testDeck.addCard(testCard2);
        testDeck.addCard(testCard3);
        assertEquals(testCard, testDeck.removeCard());

    }

    /**
     * Tests that the order of how the Cards are added to and stored in cardList
     * is as expected (first to be added = first in list)
     */
    @Test
    public void testGetCardListOrdering(){
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        Card testCard2 = new Card(2);
        Card testCard3 = new Card(1);
        testDeck.addCard(testCard);
        testDeck.addCard(testCard2);
        testDeck.addCard(testCard3);
        assertTrue(testDeck.getCardList().get(0)==testCard &&
        testDeck.getCardList().get(1)==testCard2 && testDeck.getCardList().get(2)==testCard3);
    }

    /**
     * Tests that the order of how the Cards are added to and stored in
     * cardList is not something we don't expect (first to be added != last in list)
     */
    @Test
    public void testGetCardListOrderingIsNot(){
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        Card testCard2 = new Card(2);
        Card testCard3 = new Card(1);
        testDeck.addCard(testCard);
        testDeck.addCard(testCard2);
        testDeck.addCard(testCard3);
        assertFalse(testDeck.getCardList().get(2)==testCard);
    }

    /**
     * Tests that the output to the deck file when endGame() is called is
     * what we expect when we set up a deck with cards of specific values in it
     */
    @Test
    public void testEndGame() throws Exception{
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        Card testCard2 = new Card(2);
        Card testCard3 = new Card(1);
        testDeck.addCard(testCard);
        testDeck.addCard(testCard2);
        testDeck.addCard(testCard3);
        testDeck.endGame();
        try{
            File file = new File("deck1_output.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line=br.readLine();
            br.close();
            assertTrue(line.equals("deck 1 contents: 1 2 1"));
          } catch (IOException e){
            e.printStackTrace();
            assertTrue(false);
          }
    }

    /**
     * Tests that when you call isDeckEmpty on a deck that isn't
     * empty (has at least one card in it) it returns false
     */
    @Test
    public void testIsNonEmptyDeckEmpty() {
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        assertFalse(testDeck.isDeckEmpty());

    }

    /**
     * Tests that when you call isDeckEmpty on a deck that is empty
     * (no cards in it) it returns true
     */
    @Test
    public void testIsEmptyDeckEmpty() {
        Deck testDeck = new Deck(1);
        assertTrue(testDeck.isDeckEmpty());

    }

    /**
     * Tests that when you call isDeckFull on a deck that isn't full
     * (has less than 4 cards in it) it returns false
     */
    @Test
    public void testIsNonFullDeckFull() throws Exception{
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        assertFalse(testDeck.isDeckFull());

    }

    /**
     * Tests that when you call isDeckFull on a deck that is full
     * (has at least 4 cards in it) it returns true
     */
    @Test
    public void testIsFullDeckFull() throws Exception{
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        Card testCard2 = new Card(1);
        testDeck.addCard(testCard2);
        Card testCard3 = new Card(1);
        testDeck.addCard(testCard3);
        Card testCard4 = new Card(1);
        testDeck.addCard(testCard4);
        assertTrue(testDeck.isDeckFull());

    }

    /**
     * Tests that when you call isDeckTooBig on a deck that is too big
     * (has more than 4 cards in it) it returns true
     */
    @Test
    public void testBigDeckIsTooBig() throws Exception{
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        Card testCard2 = new Card(1);
        testDeck.addCard(testCard2);
        Card testCard3 = new Card(1);
        testDeck.addCard(testCard3);
        Card testCard4 = new Card(1);
        testDeck.addCard(testCard4);
        Card testCard5 = new Card(2);
        testDeck.addCard(testCard5);
        assertTrue(testDeck.isTooBig());

    }

    /**
     * Tests that when you call isDeckTooBig on a deck that is not too big
     * (does not have more than 4 cards in it) it returns true
     */
    @Test
    public void testSmallDeckIsTooBig() throws Exception{
        Deck testDeck = new Deck(1);
        Card testCard = new Card(1);
        testDeck.addCard(testCard);
        Card testCard2 = new Card(1);
        testDeck.addCard(testCard2);
        Card testCard3 = new Card(1);
        testDeck.addCard(testCard3);
        Card testCard4 = new Card(1);
        testDeck.addCard(testCard4);
        assertFalse(testDeck.isTooBig());

    }


    /**
     * Tears down the test fixture. Need to delete any deck output files created
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        try{
            File fileToDelete = new File("deck1_output.txt");
            fileToDelete.delete();
        }catch(Exception e) {

        }
    }
}
