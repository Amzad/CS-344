package P1;

public class Main {
	
	public static void main(String[] args) {
		
		int numCommuter = 15;
		int numAttendant = 2;
		int trainCapacity = 10;
		
		for (int i = 1; i <= trainCapacity; i++) {
			subwayTrain c = new subwayTrain(i, trainCapacity);
			Thread t = new Thread(c);
			t.start();
		}
		
		for (int i = 1; i <= numAttendant; i++) {
			garageAttendant c = new garageAttendant(i, numCommuter);
			Thread t = new Thread(c);
			t.start();
		}
		
		
		for (int i = 1; i <= numCommuter; i++) {
			Commuter c = new Commuter(i);
			Thread t = new Thread(c);
			t.start();
		}
		
		
		
		
	}

}
