import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * The test class testPack
 *
 * @Jessie McColm and Lucia Adams
 */
public class testPack
{
    /**
     * Default constructor for test class testPack
     */
    public testPack()
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
     * Tests that when a valid pack file is read in, the correct values are used
     * to make cards and construct the card list in the correct order with the
     * correct values
     */
    @Test
    public void readValidPackTest()
    {
        Pack testPack= new Pack();
        testPack.readPackFile("./packFiles/validPack.txt",2);
        ArrayList<Card> testCardList = testPack.getCardList();
        int[] cards = {1,4,88,99,5,65,76,43,86,34,56,3,7,5,56,33};
        ArrayList<Card> cardList = new ArrayList<Card>();
        for (int eachCard:cards){
            Card cardToAdd =new Card(eachCard);
            cardList.add(cardToAdd);
        }

        boolean areEqual=true;
        if (cardList.size()==testCardList.size()){
            for (int i =0; i< cardList.size(); i++){
                if (cardList.get(i).getCardValue() != testCardList.get(i).getCardValue()){
                    areEqual=false;
                }
            }

        }else{
            areEqual=false;
        }
        assertTrue(areEqual);
    }

    /**
     * Tests that when a invalid pack file is read in, the cardList is not
     * constucted (is empty)
     *
     */
    @Test
    public void testReadInvalidPack()
    {
        Pack testPack= new Pack();
        testPack.readPackFile("./packFiles/invalidCharPack.txt",2);
        ArrayList<Card> testCardList = testPack.getCardList();
        assertTrue(testCardList.size()==0);
    }

    /**
     * Tests that a valid pack file (i.e. is the correct length and only
     * contains positive integers) is marked as valid by the checkFileValidity method
     *
     */
    @Test
    public void validPackTest() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/validPack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 2);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertTrue (testPack.getValidity());
    }

    /**
     * Tests that a longer valid pack file (i.e. is the correct length and only
     * contains positive integers) is marked as valid by the
     * checkFileValidity method. This pack file is for 3 players instead of 2
     *
     */
    @Test
    public void longerValidPackTest() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/longerValidPack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 3);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertTrue (testPack.getValidity());
    }

    /**
     * Tests that a longer valid pack file (i.e. is the correct length and
     * only contains positive integers) is marked as valid by the
     * checkFileValidity method. This pack file is for 100 players instead of 2
     *
     */
    @Test
    public void longestValidPackTest() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/longestValidPack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 100);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertTrue (testPack.getValidity());
    }

    /**
     * Tests that a invalid pack file (is too short for 2 players) is marked as
     * invalid by the checkFileValidity method
     *
     */
    @Test
    public void testShortPack() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/sortPack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 2);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertFalse (testPack.getValidity());
    }

    /**
     * Tests that a invalid pack file (is too long for 2 players) is marked
     *  as invalid by the checkFileValidity method
     *
     */
    @Test
    public void testLongPack() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/longPack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 2);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertFalse (testPack.getValidity());
    }

    /**
     * Tests that a invalid pack file (contains invalid character(non integers))
     * is marked as invalid by the checkFileValidity method
     *
     */
    @Test
    public void testInvalidCharPack() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/invalidCharPack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 2);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertFalse (testPack.getValidity());
    }

    /**
     * Tests that a valid pack file that contains 0s is marked as valid by
     * the checkFileValidity method
     *
     */
    @Test
    public void testZeroPack() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/zeroPack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 2);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertTrue (testPack.getValidity());
    }

    /**
     * Tests that a invalid pack file (contains negative integers) is marked as
     * invalid by the checkFileValidity method
     *
     */
    @Test
    public void testNegativePack() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/negativePack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 2);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertFalse (testPack.getValidity());
    }

    /**
     * Tests that a invalid pack file (is empty) is marked as invalid by the
     * checkFileValidity method
     *
     */
    @Test
    public void testEmptyPack() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/emptyPack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 2);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertFalse (testPack.getValidity());
    }

    /**
     * Tests that a nonexistant pack file is marked as invalid by the
     * checkFileValidity method
     *
     */
    @Test
    public void testNonexistantPackFile() throws Exception
    {
        Pack testPack= new Pack();
        Class packClass = testPack.getClass();
        Field fileName = packClass.getDeclaredField("fileName");
        fileName.setAccessible(true);
        fileName.set(testPack, "./packFiles/emptyPack.txt");
        Field nPlayers = packClass.getDeclaredField("nPlayers");
        nPlayers.setAccessible(true);
        nPlayers.set(testPack, 2);
        Method checkFileValidity= packClass.getDeclaredMethod("checkFileValidity");
        checkFileValidity.setAccessible(true);
        checkFileValidity.invoke(testPack);


        assertFalse (testPack.getValidity());
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
}
