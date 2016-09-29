package game;

public class RandomEvent {

	protected RandomEvent(String eventType){
		switch(eventType){
		case "sick":
			getSick();
			break;
		case "rebel":
			getRebel();
			break;
		case "rng":
			rngEvent();
			break;
		}
	}
	
	protected void getSick(){
		
	}
	
	protected void getRebel(){
		
	}
	
	protected void rngEvent(){
		
	}
}
