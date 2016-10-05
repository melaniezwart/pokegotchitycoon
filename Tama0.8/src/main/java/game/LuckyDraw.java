package game;

import java.util.Random;

public class LuckyDraw {

	int gameState = 0; //1 is in game, 2 is in retry;
	int statusScorePlayer = 0;
	int statusScoreBank = 0;
	
	public LuckyDraw(Tama tama) {
		play(tama);
	}
	
	private void play(Tama tama){
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
		raiseHealth(tama);
	}
	
	private void playRound(){
		// Kaart van de bank
		Random rn = new Random();
		int bankScore = rn.nextInt(11) + 1;

		// Kaart speler
		int playerScore = rn.nextInt(11) + 1;

		System.out.print("\nJouw score is: " + playerScore + "\nDe score van de bank is: " + bankScore + "\n");
			if (playerScore > bankScore) {
				System.out.println("Gefeliciteerd, je hebt deze ronde gewonnen!");
				statusScorePlayer += 1;
			} else if (playerScore < bankScore) {
				System.out.println("Helaas, je hebt deze ronde verloren.");
				statusScoreBank += 1;
			} else if (playerScore == bankScore) {
				System.out.println("Jij en de bank hebben deze ronde gelijk gespeeld.");
			}
			
		System.out.println("Tussenstand: You: " + statusScorePlayer + " Bank: " + statusScoreBank);
	}
	
	private void raiseHealth(Tama tama){	//Adds happy and social exp to the current tama
		tama.happy += 5;
		if(tama.happy > 100) tama.happy = 100;
		tama.socialExp += 25;
		tama.levelUp();
	}
	
	protected void yes(Tama tama){
		if(gameState == 1) {
			playRound();
			statusScorePlayer = 0;
			statusScoreBank = 0;
		} else if(gameState == 2) play(tama);
	}
	
	protected void no(){
		if(gameState == 2) Game.state = 1;
	}

}
