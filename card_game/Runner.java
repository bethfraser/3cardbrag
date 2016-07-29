import card_game.*;

public class Runner {

  public static void main(String[] args){
    Game game = new Game();
    Player player1 = new Player("Beth");
    Player player2 = new Player("Rick");
    game.addPlayer(player1);
    game.addPlayer(player2);

    game.deal();

    System.out.println(player1.getName() + ": " + player1.printHand());
    System.out.println(player2.getName() + ": " + player2.printHand());

    Player winner = game.findWinner(player1, player2);

    System.out.println("The winner is: " + winner.getName() + " with a " + game.getWinType());


  }

}