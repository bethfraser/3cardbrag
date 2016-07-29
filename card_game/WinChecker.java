package card_game;

import card_game.*;
import java.util.*;

public class WinChecker {

  private Player player1;
  private Player player2;
  private ArrayList<Player> winners = new ArrayList<Player>();
  private String winType = "High Card";

  public void setPlayers(Player player1, Player player2){
    this.player1 = player1;
    this.player2 = player2;
  }

  public Player checkForWin(){
    winners.clear();

    checkForPrial(player1);
    checkForPrial(player2);

    if (winners.size() > 0) {
      if (winners.size() == 1) {
        return winners.get(0);
      }
      else {
        // If both players have a Prial, the player whose Prial is of the highest number wins. This version has Ace as low. Could add in to have 3 as the top number. 
        if (player1.getHand()[0].getNumber() > player2.getHand()[0].getNumber()){
          return player1;
        }
        return player2;
      }
    }

    checkForRunningFlush(player1);
    checkForRunningFlush(player2);

    if(winners.size() > 0){
      if (winners.size() == 1){
        return winners.get(0);
      }
      else {

        // If both players have a running flush, the player with the highest run (shown by the highest card of the run) wins. If both runs are the same, player who refused to bet higher wins (if no betting, just first player by random luck).
        return highestCard(player1, player2);
      }
    }

    checkForRun(player1);
    checkForRun(player2);

    if(winners.size() > 0){
      if (winners.size() == 1){
        return winners.get(0);
      }
      else {
        // If both players have a standard run (no flush), the one with the highest card in wins. If both runs are the same, player who refused to bet higher wins (if no betting, just first player by random luck).
        return highestCard(player1, player2);
      }
    }

    checkForFlush(player1);
    checkForFlush(player2);

    if (winners.size() > 0) {
      if (winners.size() == 1) {
        return winners.get(0);
      }
      else {
        return highestCardAll(player1, player2);
      }
    }

    checkForPair(player1);
    checkForPair(player2);

    if (winners.size() > 0) {
      if (winners.size() == 1) {
        return winners.get(0);
      }
      else {
        return highestPair(player1, player2);
      }
    }

    return highestCardAll(player1, player2);
  }


  public boolean checkForPrial(Player player){
    Card[] hand = player.getHand();

    if (hand[0].getNumber() == hand[1].getNumber() && hand[1].getNumber() == hand[2].getNumber()){
      winners.add(player);
      this.winType = "Prial";
      return true;
    }
    return false;
  }

  public boolean checkForFlush(Player player){
    Card[] hand = player.getHand();

    if(hand[0].getSuit() == hand[1].getSuit() && hand[1].getSuit() == hand[2].getSuit()){
      winners.add(player);
      this.winType = "Flush";      
      return true;
    }
    return false;
  }

  public boolean checkForRun(Player player){
    Card[] hand = player.getHand();
    ArrayList<Integer> cardNumbers = makeCardNumbersArray(hand);
    int max = findMaxCard(hand);

    if (cardNumbers.contains(max - 1)){
      if (cardNumbers.contains(max - 2)){
        winners.add(player);
        this.winType = "Run";
        return true;
      }
    }
    return false;
  }

  public boolean checkForRunningFlush(Player player){
    boolean run = checkForRun(player);
    winners.remove(player);
    boolean flush = checkForFlush(player);
    winners.remove(player);

    // change of winType needs to happen regardless of if both conditions are met, in case other player has one and it's been overwritten by checks above. 
    this.winType = "Running Flush";

    if(run && flush){
      winners.add(player);
      return true;
    }
    return false;
  }

  public int findMaxCard(Card[] hand){
    ArrayList<Integer> cardNumbers = makeCardNumbersArray(hand);
    int max = (int) Collections.max(cardNumbers);
    return max;
  }

  public ArrayList<Integer> makeCardNumbersArray(Card[] hand){
    ArrayList<Integer> cardNumbers = new ArrayList<Integer>();
    cardNumbers.add(hand[0].getNumber());
    cardNumbers.add(hand[1].getNumber());
    cardNumbers.add(hand[2].getNumber());
    return cardNumbers;
  }

  public Player highestCard(Player player1, Player player2){
    int player1Max = findMaxCard(player1.getHand());
    int player2Max = findMaxCard(player2.getHand());

    if (player1Max > player2Max){
      return player1;
    }
    else if (player2Max > player1Max){
      return player2;
    }
    // if there is no diff in highest card of running flush, return player who refused to bet any higher - always enter this player as player1. 
    return player1;
  }

  public Player highestCardAll(Player player1, Player player2){

    ArrayList<Integer> player1Numbers = makeCardNumbersArray(player1.getHand());
    ArrayList<Integer> player2Numbers = makeCardNumbersArray(player2.getHand());

    Collections.sort(player1Numbers);
    Collections.sort(player2Numbers);

    for (int i = 2; i > -1; i--){
      if (player1Numbers.get(i) > player2Numbers.get(i)){
        return player1;
      }
      else if (player2Numbers.get(i) > player1Numbers.get(i)){
        return player2;
      }
    }

    return player1;
  }

  public boolean checkForPair(Player player){
    Card[] hand = player.getHand();

    if (hand[0].getNumber() == hand[1].getNumber() || hand[1].getNumber() == hand[2].getNumber() || hand[0].getNumber() == hand[2].getNumber()){
      winners.add(player);
      this.winType = "Pair";     
      return true;
    }
    return false;
  }

  public Player highestPair(Player player1, Player player2){
    // working on it

    // get the pair
    ArrayList<Integer> player1Numbers = makeCardNumbersArray(player1.getHand());
    ArrayList<Integer> player2Numbers = makeCardNumbersArray(player2.getHand());

    Collections.sort(player1Numbers);
    Collections.sort(player2Numbers);

    // When 3 cards including a pair are sorted, the middle card is always going to be part of the pair. 

    Integer player1Pair = player1Numbers.get(1);
    player1Numbers.remove(player1Pair);
    player1Numbers.remove(player1Pair);
    Integer player1Spare = player1Numbers.get(0);

    Integer player2Pair = player2Numbers.get(1);
    player2Numbers.remove(player2Pair);
    player2Numbers.remove(player2Pair);
    Integer player2Spare = player2Numbers.get(0);

    // compare pair cards
    if (player1Pair > player2Pair){
      return player1;
    }
    else if (player2Pair > player1Pair){
      return player2;
    }
    else if (player2Spare > player1Spare){
      return player2;
    }
    return player1;
  }

  public String printHands(){
    return player1.printHand() + " -- " + player2.printHand();
  }

  public String getWinType(){
    return winType;
  }

}