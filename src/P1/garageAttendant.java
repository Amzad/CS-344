package P1;

public class garageAttendant implements Runnable {

	static final Object isAvailable = new Object();
	static int counter;
	

	public static long time = System.currentTimeMillis();
	int name;
	static int count = 0;
	static boolean status = true;

	public garageAttendant(int i, int count) {
		name = i;
		counter = count;
	}

	public void run() {
		msg("Attendant ready");
		while (true) {
			if (count == counter) break;
			waitForCommuter();
		}
	}

	public void waitForCommuter() {
		while (count < counter) {
			synchronized (isAvailable) {
				isAvailable.notify();
				break;
				
			}		
		}
	}

	public void msg(String m) {
		System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m);
	}

	public int getName() {
		return name;
	}
	
	public static synchronized void increaseCounter() {
		count++;
	}
}
