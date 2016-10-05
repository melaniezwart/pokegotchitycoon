
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

@ManagedBean(name = "luckydraw")
public class LuckyDraw {
	
	@Inject
	private Tama a;
	@Inject
	private Game game;

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
	
	private void raiseHealth(){	//Adds happy and social exp to the current tama
		a.happy += 5;
		if(a.happy > 100) a.happy = 100;
		a.socialExp += 25;
		a.levelUp();
	}
	
	protected void yes(){
		if(gameState == 1) {
			playRound();
			statusScorePlayer = 0;
			statusScoreBank = 0;
		} else if(gameState == 2) play();
	}
	
	protected void no(){
		if(gameState == 2) game.state = 1;
	}

}
