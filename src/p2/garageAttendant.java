package p2;

public class garageAttendant implements Runnable {

	static final Object isAvailable = new Object();
	static int counter;

	public static long time = System.currentTimeMillis();
	int name;
	static boolean status = true;

	public garageAttendant(int i) {
		name = i;
		msg("Attendant " + name + " ready.");
	}

	public void run() {
		while (true) {
			synchronized (isAvailable) {
				isAvailable.notify();
			}
		}
	}

	public void msg(String m) {
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m);
	}

	public int getName() {
		return name;
	}

}
