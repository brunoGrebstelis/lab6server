package lab6.project;

public class MainLab6 {

	public static void main(String[] args) throws Exception {
		ServerScoketThread socket = new ServerScoketThread("socket");
		socket.start();
		ServerKeyboardThread keyboard = new ServerKeyboardThread("keyboard", socket.getCollecton());
		keyboard.start();
	}

}
