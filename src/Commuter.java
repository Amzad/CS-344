
import java.util.Random;
import java.util.Vector;

public class Commuter implements Runnable {

	int name; // Train ID
	int paymentMethod; // 0 for EZPass and 1 for Cash
	int commuterCount; // Counter for total commuter to end thread upon program completion.
	String trainName;

	public static Vector<Commuter> ezpassLane = new Vector<Commuter>();
	public static Vector<Commuter> cashLane = new Vector<Commuter>();

	public static long time = System.currentTimeMillis(); // Program run time

	public Commuter(int id) {
		name = id;
		paymentMethod = id % 2; // Set Ezpass or cash randomly based on even or odd.
		if ((id % 2) == 0) {
			trainName = "A";
		}
		if ((id % 2) == 1) {
			trainName = "B";
		}
	}

	public void run() {
		commuteToNYC(); // Commute to NYC with random sleep time.
		joinLane(); // Join appropriate lane based on ezpass or cash.
		payToll(); // Use vector to keep place in appropriate lane and pay.
		parkAtGarage(); // Park at the garage.
		waitForTrain();
	}

	private void commuteToNYC() {
		msg("Commuter " + name + " is commuting to New York City.");
		sleep(random(5, 10)); // Sleep for a random time. time is between 5 and 10 seconds.
	}

	private void joinLane() {
		// If the default payment method is ezpass
		if (paymentMethod == 0) {
			synchronized (ezpassLane) {
				ezpassLane.addElement(this);
			}
			msg(name + " has joined the ezpass lane.");
			sleep(2);
			// If your not the first person on line, you wait again.
			while (true) {
				if (!ezpassLane.elementAt(0).equals(this)) {
					try {
						synchronized (ezpassLane) {
							ezpassLane.wait();
						}
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				} else {
					break;
				}
			}
		}
		// If the default payment method is cash.
		if (paymentMethod == 1) {
			synchronized (cashLane) {
				cashLane.addElement(this);
			}
			msg(name + " has joined the cash lane.");
			sleep(2);
			while (true) {
				if (!cashLane.elementAt(0).equals(this)) {
					try {
						synchronized (cashLane) {
							cashLane.wait();
						}
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
				} else {
					break;
				}
			}
		}
	}

	private void payToll() {
		// If payment is ezpass
		if (paymentMethod == 0) {
			try {
				synchronized (ezpassLane) {
					if (ezpassLane.elementAt(0).equals(this)) {
						ezpassLane.removeElementAt(0);
						if (ezpassLane.size() != 0)
							ezpassLane.notifyAll();
					} else {
						ezpassLane.wait();
					}
				}
				msg("Commuter " + name + " paid ezpass toll.");
				// Continue commuting to NYC
				sleep(6);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// If payment is cash
		if (paymentMethod == 1) {
			try {
				synchronized (cashLane) {
					if (cashLane.elementAt(0).equals(this)) { // If the commuter is firsts in line, pay toll and move
																// on.
						cashLane.removeElementAt(0);
						if (cashLane.size() != 0)
							cashLane.notifyAll();
					} else {
						cashLane.wait();
					}

				}
				msg("Commuter " + name + " paid cash toll.");
				// Continue commuting to NYC
				sleep(6);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void parkAtGarage() {
		synchronized (garageAttendant.isAvailable) {
			msg("Commuter " + name + " has arrived at the parking garage.");
			msg("Commuter " + name + " is waiting for an attendant.");
			while (true) {
				try {
					garageAttendant.isAvailable.wait();
					msg("Attendant has parked Commuter's " + name + " car.");
					garageAttendant.increaseCounter();
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// }
		}

	}

	private void waitForTrain() {
		msg("Commuter " + name + " waiting for Train " + trainName);
		// Train A
		if (trainName.equals("A")) {
			{
				while (true) {
					try {
						synchronized (subwayTrain.trainA) {
							subwayTrain.trainA.wait();
							if (subwayTrain.isSpaceA()) {
								msg("Commuter " + name + " has entered Train A");
								subwayTrain.capacityA.incrementAndGet();
								break;
							}
							if (subwayTrain.isSpaceA() == false) {
								msg("Train is full. Commuter " + name + " waiting for next train.");
							}
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
						continue;
					}
				}
				try {
					synchronized (subwayTrain.trainAPassengers) {
						msg("Commuter " + name + " is waiting inside Train A");
						subwayTrain.trainAPassengers.wait();
						msg("Commuter " + name + " has arrived at the last stop and is walking on his/her way to work.");
						subwayTrain.increaseCount();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		// Train B
		if (trainName.equals("B")) {
			{
				while (true) {
					try {
						synchronized (subwayTrain.trainB) {
							subwayTrain.trainB.wait();
							if (subwayTrain.isSpaceB()) {
								msg("Commuter " + name + " has entered Train B");
								subwayTrain.capacityB.incrementAndGet();
								break;
							}
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
						continue;
					}
				}
				try {
					synchronized (subwayTrain.trainBPassengers) {
						msg("Commuter " + name + " is waiting inside Train B");
						subwayTrain.trainBPassengers.wait();
						msg("Commuter " + name + " has arrived at the last stop and is walking on his/her way to work.");
						subwayTrain.increaseCount();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	// Return random int between min and max.
	private int random(int min, int max) {
		int value = new Random().nextInt((max - min) + 1) + min;
		return value;
	}

	// Sleep for x seconds.
	private void sleep(int x) {
		try {
			Thread.sleep(x * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void msg(String m) {
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m);
	}

	private int getName() {
		return name;
	}

}
