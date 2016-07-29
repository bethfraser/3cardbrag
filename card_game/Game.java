package card_game;
import card_game.*;

import java.util.*;

public class Game {

  private ArrayList<Player> players = new ArrayList<Player>();
  private static ArrayList<Card> deck = new ArrayList<Card>();
  private WinChecker winChecker = new WinChecker();
  private String winType = "None";

  public void buildDeck(){
    for (int i=1; i<14; i++) {
      Card cardClubs = new Card(i, Suit.Clubs);
      Card cardSpades = new Card(i, Suit.Spades);
      Card cardHearts = new Card(i, Suit.Hearts);
      Card cardDiamonds = new Card(i, Suit.Diamonds);
      deck.add(cardClubs);
      deck.add(cardSpades);
      deck.add(cardHearts);
      deck.add(cardDiamonds);
    }
    shuffleDeck();
  }

  public Game(){
    buildDeck();
  }

  public void shuffleDeck(){
    Collections.shuffle(deck);
  }

  public ArrayList<Card> getDeck(){
    return this.deck;
  }

  public void addPlayer(Player player){
    players.add(player);
  }

  public int playerCount(){
    return players.size();
  }

  public void deal(){
    for (Player player : this.players){
      player.setHand(deck.get(0), deck.get(1), deck.get(2));
      deck.remove(0);
      deck.remove(0);
      deck.remove(0);
    } 
  }

  public Player findWinner(Player player1, Player player2){
    winChecker.setPlayers(player1, player2);
    Player winner = winChecker.checkForWin();
    this.winType = winChecker.getWinType();
    return winner;
  }

  public String getWinType(){
    return this.winType;
  }




}