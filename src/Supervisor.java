import java.util.Vector;

public class Supervisor extends Thread {

	Vector<Shuttle> queue = new Vector<Shuttle>();
	
	public Supervisor() {
		
	}
	
	@Override
	public void run() {
		
		
	}

	public static boolean permisssionToTakeOff() {
		return false;
		// TODO Auto-generated method stub
		
	}

}
