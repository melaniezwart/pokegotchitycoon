package game;

import java.util.Scanner;
import java.util.Random;

public class LuckyDraw {

	public LuckyDraw() {

		int statusScorePlayer = 0;
		int statusScoreBank = 0;

		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		// score:
		for (int i = 0; i < 5 && statusScoreBank < 3 && statusScorePlayer < 3; i++) {

			// Kaart van de bank
			Random rn = new Random();
			int bankScore = rn.nextInt(11) + 1;

			// Kaart speler
			int playerScore = rn.nextInt(11) + 1;

			// ____________________________________________________________________________
			System.out.println("Trek een kaart door 'ja' te typen:");
			String answer = input.next();

			if (answer.equals("ja")) {
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
				raiseHealth();

			} else {
				System.out.print("\nBedankt voor het spelen.");
			}

			System.out.println("Tussenstand:\n You: " + statusScorePlayer + "\n Bank: " + statusScoreBank + "\n");

		}
		if (statusScoreBank > statusScorePlayer) {
			System.out.println("Helaas, de bank heeft 3 van de 5 spellen gewonnen, you lose!");
		} else if (statusScoreBank < statusScorePlayer) {
			System.out.println("Jij hebt 3 van de 5 spellen gewonnen, gefeliciteerd!");
		} else if (statusScoreBank == statusScorePlayer) {
			System.out.println("Jij en de bank hebben dit spel gelijk gespeeld!");
		}
		return;
	}
	private void raiseHealth(){	//Adds happy and social exp to the current tama
		Game.a.happy += 5;
		if(Game.a.happy > 100) Game.a.happy = 100;
		Game.a.socialExp += 25;
		Game.a.levelUp();
	}

}
