package lab6.project;

import java.io.File;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import obj.Color;
import obj.Coordinates;
import obj.Location;
import obj.Person;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DataOperations {

	private String envvariable;
	private File xmlFile;
	private LinkedHashMap<Integer, Person> showPersons2 = new LinkedHashMap<Integer, Person>();

	DataOperations(String envvariable) {

		if (envvariable == null) {
			System.out.println("Enter valid enviroment variable  - 'GoToLab5XmlFile'");
			System.exit(1);
		}

		this.xmlFile = new File(envvariable);
		this.envvariable = envvariable;

		if (!xmlFile.exists()) {
			System.out.println("The file you entered doesnt exist!");
			System.exit(1);
		}

		this.fill();
	}

	public void fill() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			NodeList nodeList = doc.getElementsByTagName("Person");
			for (int itr = 0; itr < nodeList.getLength(); itr++) {
				Element node = (Element) nodeList.item(itr);
				Element coords = (Element) node.getElementsByTagName("coordinates").item(0);
				Element loc = (Element) node.getElementsByTagName("location").item(0);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Person pers = new Person(Long.parseLong(node.getElementsByTagName("id").item(0).getTextContent()),
							node.getElementsByTagName("name").item(0).getTextContent(),
							new Coordinates(
									Double.parseDouble(coords.getElementsByTagName("x").item(0).getTextContent()),
									Float.parseFloat(coords.getElementsByTagName("y").item(0).getTextContent())),
							Double.parseDouble(node.getElementsByTagName("height").item(0).getTextContent()),
							node.getElementsByTagName("passportID").item(0).getTextContent(),
							Color.valueOf(node.getElementsByTagName("eaycolor").item(0).getTextContent()),
							new Location(Integer.parseInt(loc.getElementsByTagName("x").item(0).getTextContent()),
									Long.parseLong(loc.getElementsByTagName("y").item(0).getTextContent()),
									Long.parseLong(loc.getElementsByTagName("z").item(0).getTextContent())));

					pers.setCreationDate(
							ZonedDateTime.parse(node.getElementsByTagName("creationDate").item(0).getTextContent()));
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					pers.FsetDateTimeBirthString(LocalDate
							.parse(node.getElementsByTagName("birthsday").item(0).getTextContent(), formatter));

					showPersons2.put(itr, pers);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LinkedHashMap<Integer, Person> getShowPersons2() {
		return showPersons2;
	}

	public String getEnvvariable() {
		return envvariable;
	}
}