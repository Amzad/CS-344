package p1;

public class garageAttendant implements Runnable {

	static final Object isAvailable = new Object();
	static final Object isWaiting = new Object();
	static int counter;
	

	public static long time = System.currentTimeMillis();
	int name;
	static int count = 0;
	static boolean status = true;

	public garageAttendant(int i, int count) {
		name = i;
		counter = count;
		msg("Attendant " + name + " ready.");
	}

	public void run() {
		while (true) {
			if (count == counter) break;
			waitForCommuter();
		}
	}

	private void waitForCommuter() {
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
