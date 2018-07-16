package P1;

import java.util.Vector;

public class Commuter implements Runnable {

	int name;
	int paymentMethod;
	int lane;
	//Vector<Commuter> vector;
	// 0 for Ezpass, 1 for Cash

	public static Vector<Commuter> ezpassLane = new Vector<Commuter>();
	public static Vector<Commuter> cashLane = new Vector<Commuter>();
	public static long time = System.currentTimeMillis();

	public Commuter(int id) {
		name = id;
		paymentMethod = id % 2;
	}

	public void run() {
		commuteToNYC(); // Commute to NYC with random sleep time.
		joinLane(); // Join appropriate lane based on ezpass or cash.
		payToll(); // Use vector to keep place in appropriate lane and pay.
		parkAtGarage(); // Park at the garage.
	}

	public void commuteToNYC() {
		msg("Commuter " + name + " is commuting to New York City.");
		sleep(5);
	}

	public void sleep(int x) {
		try {
			Thread.sleep((long) Math.ceil(Math.random() * x * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void joinLane() {
		if (paymentMethod == 0) {
			synchronized (ezpassLane) {
				ezpassLane.addElement(this);
			}
			msg(name + " has joined the ezpass lane.");
			sleep(2);
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

	public void payToll() {
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
				sleep(6);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (paymentMethod == 1) {
			try {
				synchronized (cashLane) {
					if (cashLane.elementAt(0).equals(this)) {
						cashLane.removeElementAt(0);
						if (cashLane.size() != 0)
							cashLane.notifyAll();
					} else {
						cashLane.wait();
					}

				}
				msg("Commuter " + name + " paid cash toll.");
				sleep(6);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void parkAtGarage() {
		synchronized(garageAttendant.isAvailable) {
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
			//}
		}
		
	}

	public void msg(String m) {
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m);
	}

	public int getName() {
		return name;
	}

}
