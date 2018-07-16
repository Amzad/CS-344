import java.util.Vector;

public class Station extends Thread {
	
	
	public boolean isOpen = false;
	private static int reserviorTank=200;
	public static long time = System.currentTimeMillis();
	int numRecharge;

	
	Vector<Shuttle> queue = new Vector<Shuttle>();

	public Station(int numShuttles) {

		msg("started");
	}
	
	
	public void run() {
		while (true) {
			if (isOpen == true) {
				process();
			}
		}
		
		
	}
	
	public synchronized void process() {
		
		msg("Station open");
		try {
			int length = 3;
			if (queue.size() < 3) {
				length = queue.size();
			}
			for (int i = 0; i < length; i++) {

				Object group = queue.get(i).group;
				synchronized (group) {
					
					synchronized (this) {
						//if (queue.get(0).refillTank() == true) {
						//	group.notify();
						//}

						
						group.notify();
						// group.notify();
						// queue.remove(0);
					}

				}
			}
			msg("Station closed");

		}

		catch (ArrayIndexOutOfBoundsException e) {

		}
		isOpen = false;

	}
	public void msg(String m) {
		System.out.println("[" + (System.currentTimeMillis() - time) + "] "+ getStationName() + ": " + m);
	}
	
	public String getStationName() {
		return "Shuttle_Station";
	}
	
	public void openStation(int numRecharge) {
		this.numRecharge = numRecharge;
		//isOpen = true;
		process();
	}
	
	public synchronized void updateTank(int tank) {
		reserviorTank = tank;
	}
	
	public static int getTank() {
		return reserviorTank;
	}
	
	public void joinQueue(Shuttle s) {
		queue.add(s);
	}
	
	
	public Vector<Shuttle> getQueue() {
		return queue;
	}
}
