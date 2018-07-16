import java.util.Vector;

public class Main {
	
	public static Vector<Shuttle> shuttles = new Vector<Shuttle>();

	public static void main(String[] args) {
		int numShuttles = Integer.parseInt(args[0]);
		
		
		
		Station station = new Station(numShuttles);
		
		station.start();
		
		Supervisor supervisor = new Supervisor();
		supervisor.start();
		
		Controller controller = new Controller(numShuttles, station);
		controller.start();

		
		for (int i = 0; i < numShuttles; i++) {
			shuttles.add(new Shuttle((i + 1), controller, station, supervisor));
			shuttles.get(i).start();
		}
		
		
		
		

		
	}

}
