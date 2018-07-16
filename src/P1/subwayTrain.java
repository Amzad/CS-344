package P1;

import java.util.Vector;

public class subwayTrain implements Runnable {
	int name;
	int capacity;
	int transitInterval = 10;
	
	static final Object trainA = new Object();
	static final Object trainB = new Object();
	
	public subwayTrain(int i, int size) {
		name = i;
		this.capacity = size;
	}
	
	public Object getTrain() {
		if (name == 0) {
			return trainA; 
		}
		else {
			return trainB;
		}
	}
	
	public void run() {
		while (true) {
			nextStop();
		}
	}
	
	public void nextStop() {
		
	}
}
