import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class TestCard.
 *
 * @Jessie McColm and Lucia Adams
 */
public class TestCard
{
    /**
     * Default constructor for test class TestCard
     */
    public TestCard()
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
     * Tests that when a Card with a specific value is created the "getCardValue"
     * function returns the expected value
     *
     */
    @Test
    public void testGetCardValue() {
        Card testCard=new Card(1);
        assertTrue(testCard.getCardValue()==1);
    }

    /**
     * Tests that when a Card with a specific value is created the "getCardValue"
     * function does not return a different value
     *
     */
    @Test
    public void testGetCardValueIsNot() {
        Card testCard=new Card(1);
        assertFalse(testCard.getCardValue()==-1);
    }


    /**
     * Tests that when a Card with a specific value is created the "toString"
     * function returns the expected value (a string version of the Card's value)
     *
     */
    @Test
    public void testCardToString() {
        Card testCard=new Card(1);
        assertTrue(testCard.toString().equals("1"));
    }

    /**
     * Tests that when a Card with a specific value is created the "toString"
     * function doesn't return a different value to what is expected
     * (a string version of the Card's value)
     */
    @Test
    public void testCardToStringIsNot() {
        Card testCard=new Card(1);
        assertFalse(testCard.toString().equals("5"));
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
