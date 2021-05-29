package lab6.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import obj.Person;

public class Save {
	private LinkedHashMap<Integer, Person> showPersons2;
	private String envvariable;
	private String com;
	private BufferedReader reader;

	Save(LinkedHashMap<Integer, Person> showPersons2, String envvariable) {
		this.showPersons2 = showPersons2;
		this.envvariable = envvariable;
	}

	public void wylts() throws IOException {
		System.out.println("Would you like to save changes you made?(yes/no)");
		reader = new BufferedReader(new InputStreamReader(System.in));
		com = reader.readLine();
		if (com.equals("yes")) {
			save();
			System.out.println("Changes you made have been saved");
			System.exit(0);
		} else {
			System.out.println("Changes you made have not been saved");
			System.exit(0);
		}
	}

	public void save() throws FileNotFoundException {

		PrintWriter writer = new PrintWriter(envvariable);
		writer.print("");
		writer.close();

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(envvariable, true)));
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
			out.println("<information>");
			for (Entry<Integer, Person> m : showPersons2.entrySet()) {
				Person a = m.getValue();

				out.println("	<Person>");
				out.println("		<id>" + a.getID() + "</id>");
				out.println("		<name>" + a.getName() + "</name>");
				out.println("		<coordinates>");
				out.println("			<x>" + a.getXcoordinate() + "</x>");
				out.println("			<y>" + a.getYcoordinate() + "</y>");
				out.println("		</coordinates>");
				out.println("		<creationDate>" + a.getCreationDate() + "</creationDate>");
				out.println("		<height>" + a.getHeight() + "</height>");
				out.println("		<birthsday>" + a.FgetBirthday() + "</birthsday>");
				out.println("		<passportID>" + a.getPassportID() + "</passportID>");
				out.println("		<eaycolor>" + a.getEyeColor() + "</eaycolor>");
				out.println("		<location>");
				out.println("			<x>" + a.getXlocation() + "</x>");
				out.println("			<y>" + a.getYlocation() + "</y>");
				out.println("			<z>" + a.getZlocation() + "</z>");
				out.println("		</location>");
				out.println("	</Person>");
			}
			out.println("</information>");

		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
