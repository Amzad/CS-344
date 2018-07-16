import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class Shuttle extends Thread{
    
	private int id;
	private int tankCapacity;
	private int numCruise = 3;
	private boolean needAssistance = false;
	public Controller controller;
	public Station station;
	public Vector<Object> waitingShuttles = new Vector();
	Object group = new Object();
	Supervisor supervisor;

	public static long time = System.currentTimeMillis();
	
	public Shuttle(int id, Controller cont, Station stat, Supervisor superv){
		this.id = id;
		controller = cont;
		station = stat;
		supervisor = superv;
		randomGetCapacity(); // random tank capacity for fuel
		msg("Started");
	}
	
	public Shuttle() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		while(true){
		inAir();
		landToRecharge();
		goToTakeoff();
		break;
		}
	}
	
	private void inAir() { 
		try {
    		int time = randomInt(1,5) * 1000;//convert to milliseconds
    		msg("Cruising for " + time/1000 + " seconds");
    		Thread.sleep(time);
    	}
    	
    	catch (InterruptedException e) {
    		msg("Cruise interrupted!");
    	}	
	}

	private synchronized void landToRecharge() {
		group = new Object();
		updateTank();
		
		synchronized (group) {
			while(true) {
				try {
					station.joinQueue(this);
					msg(getShuttleName() + " joined queue");
					group.wait();
					this.refillTank();
					msg(getShuttleName() + " moving to takeoff area");
					station.queue.remove(0);
					supervisor.queue.add(this);
					break;
					//this.wait(100000);
					//station.joinQueue(this);
				} catch (InterruptedException e) {
					msg("Interrupted Error");
					e.printStackTrace();
				}
				
			}
			
	
		}

	}

	private synchronized void goToTakeoff() {
		
		
		if(Supervisor.permisssionToTakeOff()){
			waitingShuttles.remove(0);
		}
	}
	
	public void msg(String m) {
		System.out.println("[" + (System.currentTimeMillis() - time) + "] "+ getShuttleName() + ": " + m);
	}
	
	public void sleep () {
    	try {
    		int time = randomInt(3,10) * 1000;//convert to milliseconds
    		msg(getShuttleName() + " flying for " + time/1000 + " seconds");
    		Thread.sleep(time);
    	}
    	
    	catch (InterruptedException e) {
    		msg(getShuttleName() + " interrupted!");
    	}
    }
	
	
	public void updateTank() {
		tankCapacity = tankCapacity/2;
		
	}
	
	public synchronized void refillTank() {
		if(Station.getTank() > (tankCapacity*2)) {
			msg(getShuttleName() + " tank refilled");
			station.updateTank((Station.getTank() - (tankCapacity*2)));
			tankCapacity = tankCapacity*2;
		}
		else {
			msg("Tank empty");
			controller.refillTank();
			msg(getShuttleName() + " tank refilled");
			station.updateTank((Station.getTank() - (tankCapacity*2)));
			tankCapacity = tankCapacity*2;

		}
		
		
	}
	
	private void randomGetCapacity() {
		this.tankCapacity = randomInt(50,100);
		
	}
	
	public int randomInt(int min, int max) {//random integer formula
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public int getShuttleId() {//to get name
		return this.id;
	}

	public String getShuttleName() {//to get name
		return "Shuttle_" + this.id;
	}
	
	public void setNeedAssistance(boolean needAssistance) {
		this.needAssistance = needAssistance;
		Controller.refillTank();
	}


	
}
