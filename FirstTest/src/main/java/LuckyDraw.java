
import java.util.Random;

public class LuckyDraw {

	int gameState = 0; //1 is in game, 2 is in retry;
	int statusScorePlayer = 0;
	int statusScoreBank = 0;
	
	public LuckyDraw() {
		play();
	}
	
	private void play(){
		gameState = 1;
		for (int i = 0; i < 5 && statusScoreBank < 3 && statusScorePlayer < 3; i++) {
			playRound();
		}
		
		if (statusScoreBank > statusScorePlayer) {
			System.out.println("Helaas, de bank heeft 3 van de 5 spellen gewonnen, you lose!");
		} else if (statusScoreBank < statusScorePlayer) {
			System.out.println("Jij hebt 3 van de 5 spellen gewonnen, gefeliciteerd!");
		} else if (statusScoreBank == statusScorePlayer) {
			System.out.println("Jij en de bank hebben dit spel gelijk gespeeld!");
		}
		gameState = 2;
		raiseHealth();
	}
	
	private void playRound(){
		// Kaart van de bank
		Random rn = new Random();
		int bankScore = rn.nextInt(11) + 1;

		// Kaart speler
		int playerScore = rn.nextInt(11) + 1;

		System.out.print("\nJouw score is: " + playerScore + "\nDe score van de bank is: " + bankScore + "\n");
			if (playerScore > bankScore) {
				System.out.println("\nGefeliciteerd, je hebt deze ronde gewonnen!\n");
				statusScorePlayer += 1;
			} else if (playerScore < bankScore) {
				System.out.println("\nHelaas, je hebt deze ronde verloren.\n");
				statusScoreBank += 1;
			} else if (playerScore == bankScore) {
				System.out.println("\nJij en de bank hebben deze ronde gelijk gespeeld.\n");
			}
			
		System.out.println("Tussenstand:\n You: " + statusScorePlayer + "\n Bank: " + statusScoreBank + "\n");
	}
	
	private void raiseHealth(){	//Adds happy and social exp to the current tama
		Game.a.happy += 5;
		if(Game.a.happy > 100) Game.a.happy = 100;
		Game.a.socialExp += 25;
		Game.a.levelUp();
	}
	
	protected void yes(){
		if(gameState == 1) {
			playRound();
			statusScorePlayer = 0;
			statusScoreBank = 0;
		} else if(gameState == 2) play();
	}
	
	protected void no(){
		if(gameState == 2) Game.state = 1;
	}

}
