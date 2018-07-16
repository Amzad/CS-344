import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Controller extends Thread {

	private int numRecharge = 3;
	private int numShuttles;
	private static int reserviorTank=200;
	public static long time = System.currentTimeMillis();
	private boolean isHelping = false;
	Object waitingTank = new Object();
	Station station;
	boolean recharge = false;

	public Controller(int numShuttles, Station stat) {
		setNumShuttles(numShuttles);
		station = stat;
		//msg("Started");
	}
	@Override
	public void run() {
		
		while(true){
			//spentRandomTime(2000);
			try {
				Thread.sleep(6000);
				
				openStation();
				//waitingTank.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}	
	}
	
	public void setNumShuttles(int n) {
		numShuttles = n;
	}
	
	public void openStation() {
	//	msg("Station open");
		station.openStation(numRecharge);
	}

	public synchronized void waitingforAction() {
		synchronized (waitingTank) {
			while (true) {
				try {
					msg("waiting for command");
					waitingTank.wait();
					msg("I have been notified to refill the tank");
					if (recharge == true) {
						refillTank();
						recharge = false;
					}
					else {
						msg("Closing Station");
					}
					
					// station.joinQueue(this);
				} catch (InterruptedException e) {
					msg("Interrupted Error");
					e.printStackTrace();
				}
				finally {
					//waitingforAction();
				}

			}

		}

	}
	
	
	
	private synchronized void helpRecharge() {
		for(int i=0; i<4; i++){
			//Station.
			
		}
	}

	private void spentRandomTime(int max) {//sleep random time
		try {
			Thread.yield();
			Thread.sleep(randomInt(0, max * 10));
		} catch (InterruptedException e) {//should not come  to this step 
			msg("Error: spentRandomTime was interrupted by unknown reason.");
			e.printStackTrace();
		}
	}
	
	public int randomInt(int min, int max) {//random integer formula
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+getControllerName()+": "+m);
	}
	public boolean isHelping() {
		return isHelping;
	}
	public boolean setHelping(boolean isHelping) {
		this.isHelping = isHelping;
		return isHelping;
	}
	public static String getControllerName() {//to get name
		return "Controller";
	}
	
	public static void refillTank(){
		msg("Tank refilled");
		reserviorTank=200;
	}
}
