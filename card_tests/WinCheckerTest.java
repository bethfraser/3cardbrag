import card_game.*;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class WinCheckerTest {

  Player player1;
  Player player2;
  WinChecker winChecker;
  Card card1;
  Card card2;
  Card card3;
  Card card4;
  Card card5;
  Card card6;
  Card card7;
  Card card8;
  Card card9;

  @Before
  public void before(){
    player1 = new Player("Beth");
    player2 = new Player("Rick");

    card1 = new Card(2, Suit.Clubs);
    card2 = new Card(3, Suit.Clubs);
    card3 = new Card(4, Suit.Clubs);

    card4 = new Card(3, Suit.Clubs);
    card5 = new Card(3, Suit.Spades);
    card6 = new Card(3, Suit.Diamonds);

    card7 = new Card(6, Suit.Clubs);
    card8 = new Card(6, Suit.Spades);
    card9 = new Card(6, Suit.Hearts);

    player1.setHand(card1, card2, card3);
    player2.setHand(card4, card5, card6);
    winChecker = new WinChecker();
    winChecker.setPlayers(player1, player2);
  }

  @Test
  public void hasTwoHands(){
    assertEquals("Two of Clubs, Three of Clubs, Four of Clubs -- Three of Clubs, Three of Spades, Three of Diamonds", winChecker.printHands());
  }

  @Test
  public void canRecognisePrial(){
    assertEquals(true, winChecker.checkForPrial(player2));
  }

  @Test
  public void canRecogniseNoPrial(){
    assertEquals(false, winChecker.checkForPrial(player1));
  }
  
  @Test
  public void canRecogniseFlush(){
    assertEquals(true, winChecker.checkForFlush(player1));
  }

  @Test
  public void canRecogniseNoFlush(){
    assertEquals(false, winChecker.checkForFlush(player2));
  }

  @Test 
  public void canRecogniseRun(){
    assertEquals(true, winChecker.checkForRun(player1));
  }

  @Test 
  public void canRecogniseNoRun(){
    assertEquals(false, winChecker.checkForRun(player2));
  }

  @Test
  public void canRecogniseRunningFlush(){
    assertEquals(true, winChecker.checkForRunningFlush(player1));
  }

  @Test
  public void canRecogniseNoRunningFlush(){
    player2.setHand(card1, card3, card6);
    winChecker.setPlayers(player1, player2);
    assertEquals(false, winChecker.checkForRunningFlush(player2));
  }

  @Test
  public void canFindMaxCard(){
    assertEquals(4, winChecker.findMaxCard(player1.getHand()));
  }

  @Test
  public void canMakeNumbersArray(){
    ArrayList<Integer> testArray = new ArrayList<Integer>();
    testArray.add(2);
    testArray.add(3);
    testArray.add(4);
    assertEquals(testArray, winChecker.makeCardNumbersArray(player1.getHand()));
  }

  @Test
  public void canFindHighestSingleCard(){
    assertEquals(player1, winChecker.highestCard(player1, player2));
  } 

  @Test
  public void canFindHighestMultipleCards(){
    player2.setHand(card4, card5, card3);
    winChecker.setPlayers(player1, player2);
    assertEquals(player2, winChecker.highestCardAll(player1, player2));
  }

  @Test
  public void canRecognisePair(){
    player2.setHand(card5, card6, card8);
    winChecker.setPlayers(player1, player2);
    assertEquals(true, winChecker.checkForPair(player2));
  }

  @Test
  public void canRecogniseNoPair(){
    assertEquals(false, winChecker.checkForPair(player1));
  }

  @Test
  public void canRecogniseHighestPair(){
    player2.setHand(card8, card9, card1);
    player1.setHand(card5, card6, card8);
    winChecker.setPlayers(player1, player2);
    assertEquals(player2, winChecker.highestPair(player1, player2));
  }

  @Test
  public void canRecogniseHighestSpare(){
    player2.setHand(card8, card9, card2);
    player1.setHand(card8, card9, card1);
    winChecker.setPlayers(player1, player2);
    assertEquals(player2, winChecker.highestPair(player1, player2));
  }

  @Test
  public void canCheckWinner_OnePrial(){
    assertEquals(player2, winChecker.checkForWin());
    assertEquals("Prial", winChecker.getWinType());
  }

  @Test
  public void canCheckWinner_TwoPrials(){
    player1.setHand(card7, card8, card9);
    winChecker.setPlayers(player1, player2);
    assertEquals(player1, winChecker.checkForWin());
  }

  @Test
  public void canCheckWinner_OneRunningFlush(){
    player2.setHand(card4, card7, card2);
    winChecker.setPlayers(player1, player2);
    assertEquals(player1, winChecker.checkForWin());
    assertEquals("Running Flush", winChecker.getWinType());
  }

  @Test
  public void canCheckWinner_TwoRunningFlushes(){
    Card cardA = new Card(3, Suit.Hearts);
    Card cardB = new Card(4, Suit.Hearts);
    Card cardC = new Card(5, Suit.Hearts);

    player2.setHand(cardA, cardB, cardC);
    winChecker.setPlayers(player1, player2);
    assertEquals(player2, winChecker.checkForWin());
  }

}