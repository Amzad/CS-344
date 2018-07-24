package p1;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class subwayTrain implements Runnable {
	String name;
	static int capacityMax; // Maximum capacity of the train
	int transitInterval = 10; //
	static AtomicInteger capacityA = new AtomicInteger(); // current capacity of the train a.
	static AtomicInteger capacityB = new AtomicInteger(); // current capacity of the train b.
	int numCommuter;
	static AtomicInteger pCount = new AtomicInteger(); 
	
	static final Object trainA = new Object();
	static final Object trainB = new Object();
	
	static final Object trainAPassengers = new Object();
	static final Object trainBPassengers = new Object();

	public static long time = System.currentTimeMillis();
	
	public subwayTrain(String i, int size, int numCommuter) {
		name = i;
		capacityMax = size;
		this.numCommuter = numCommuter;
		pCount.set(0);
		generatePassengers(name);
	}
	
	public Object getTrain() {
		if (name.equals("A")) {
			return trainA; 
		}
		else {
			return trainB;
		}
	}
	
	public void run() {
		while (true) {
			if (pCount.get() >= numCommuter) {
				break;
			}
			resetTrain();
			inTransit();
			atStop();
			inTransit();
			lastStop();
			
		}
		msg("Trains ending");
	}
	
	private void atStop() {
		msg("Train " + name + " has arrived at the train station.");
		if (name.equals("A")) {
			synchronized(trainA) {
				trainA.notifyAll();
			}
			sleep(10); // Waits 10 seconds at the station.
			msg("Train " + name + " is leaving train station.");
		}
		if (name.equals("B")) {
			synchronized(trainB) {
				trainB.notifyAll();
			}
			sleep(10); // Waits 10 seconds at the station.
			msg("Train " + name + " is leaving train station.");
		}
		
	}
	
	public static synchronized boolean isSpaceA() {
		synchronized(capacityA) {
			if (capacityA.get() < capacityMax) {
				return true;
			}
		}	
		return false;
	}
	
	public static synchronized boolean isSpaceB() {
		synchronized(capacityB) {
			if (capacityB.get() < capacityMax) {
				return true;
			}
		}	
		return false;
	}
	
	private void inTransit() {
		msg("Train " + name + " is in transit to the next stop.");
		sleep(transitInterval); // Trains have almost a fixed time from one station to the next.
	}
	
	public static synchronized void increaseCount() {
		pCount.incrementAndGet();
	}
	
	public void getCount() {
		pCount.get();
	}
	
	
	public void msg(String m) {
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m);
	}
	
	public String getName() {
		return name;
	}
	
	private void generatePassengers(String x) {
		int max = capacityMax;
		int min = 0;
		int value = new Random().nextInt((max - min) + 1) + min;
		if (x.equals("A")) {
			capacityA.set(value);
			msg("Train " + name + " contains " + capacityA + " passengers.");
		} else {
			capacityB.set(value);
			msg("Train " + name + " contains " + capacityB + " passengers.");
		}
		
		
	}
	
	private void resetTrain() {
		generatePassengers(getName());
		
	}
	
	private void lastStop() {
		msg("Train " + name + " has arrived at the last stop.");
		if (name.equals("A")) {
			synchronized(trainAPassengers) {
				trainAPassengers.notifyAll();
			}
			sleep(10); // Waits 10 seconds at the station.
			msg("Train " + name + ": All passengers have left the train. Resetting train.");
		}
		if (name.equals("B")) {
			synchronized(trainBPassengers) {
				trainBPassengers.notifyAll();
			}
			sleep(10); // Waits 10 seconds at the station.
			msg("Train " + name + ": All passengers have left the train. Resetting train.");
		}
		
		
	}
	
	private void sleep(int x) {
		try {
			Thread.sleep(x * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
