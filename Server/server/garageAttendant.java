package server;

public class garageAttendant implements Runnable {

	static final Object isAvailable = new Object();
	static final Object isWaiting = new Object();

	public static long time = System.currentTimeMillis();
	int name;
	static boolean status = true;

	public garageAttendant(int i) {
		name = i;
		msg("Attendant " + name + " ready.");
	}

	public void run() {
		msg("Attendant " + name + " is running");
		
	}

	public void signalCommuter() {
		while (true) {
			synchronized (isWaiting) {
			try {
				isWaiting.wait();
				break;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		

	}

	public void waitForCommuter() {
		synchronized (isAvailable) {
			isAvailable.notify();
		}
	}

	public void msg(String m) {
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m);
	}

	public int getName() {
		return name;
	}

}
