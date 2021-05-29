package lab6.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

import obj.Person;

public class ServerKeyboardThread implements Runnable {

	private Thread t;
	private String threadName;
	private BufferedReader reader;
	private LinkedHashMap<Integer, Person> showPersons2;

	ServerKeyboardThread(String name, LinkedHashMap<Integer, Person> showPersons2) {
		this.threadName = name;
		this.showPersons2 = showPersons2;
	}

	public void run() {
		while (true) {
			try {
				reader = new BufferedReader(new InputStreamReader(System.in));
				if (reader.readLine().equals("save")) {
					System.out.println("The current collecton is saved!");
					Save s = new Save(showPersons2, "person_data.xml");
					s.save();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

}
