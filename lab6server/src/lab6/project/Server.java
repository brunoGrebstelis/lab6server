package lab6.project;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


import obj.Coordinates;
import obj.Location;
import obj.Objects;
import obj.Person;
import obj.PrintOut;

public class Server {
	
	//private DataOperations doc = new DataOperations("person_data.xml");
	private String envvariable = "GoToLab5XmlFile";
	private DataOperations doc = new DataOperations(System.getenv(envvariable));
	private IDoperations check =  new IDoperations(doc.getShowPersons2());
	private Remove rem = new Remove(doc.getShowPersons2());
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectOutputStream outStream =null;
	private ObjectInputStream inStream = null;
	private Objects msgRes, msg;
	private String input;
	private String send;

	public void server() throws Exception {
		System.out.println("Server is activated!");
		serverSocket = new ServerSocket(4671);
		socket = serverSocket.accept();
		System.out.println("Server: Connected");
		
		outStream = new ObjectOutputStream(socket.getOutputStream());
		inStream = new ObjectInputStream(socket.getInputStream());
		
		do {
			msgRes = (Objects)inStream.readObject();
			this.input=msgRes.message;
			System.out.println("From client: "+input);
			
			switch(input) {
			    
			  case "printOut":
				outStream.reset();
				outStream.writeObject(new PrintOut(doc.getShowPersons2()));
			    break;
			   
			  case "insert":
				Insert in = new Insert(doc.getShowPersons2());
				IDoperations getID =  new IDoperations(doc.getShowPersons2());
				String s=String.valueOf(getID.setID());
				outStream.writeObject(new Objects(s));
				Person msgResPerson = (Person)inStream.readObject();
				Coordinates newCoordinates = new Coordinates((msgResPerson.getXcoordinate()), (msgResPerson.getYcoordinate()));
				Location newLocation = new Location((msgResPerson.getXlocation()), (msgResPerson.getYlocation()), (msgResPerson.getZlocation()));
				in.insert(msgResPerson.getName(),newCoordinates,msgResPerson.getHeight(),msgResPerson.getID(),
						msgResPerson.getPassportID(),msgResPerson.getEyeColor(),newLocation,msgResPerson.FgetBirthday());
				outStream.writeObject(new Objects(msgResPerson.getName()+ " is added to the list!"));
				break;
				
			  case "remove":
				do {
					msgRes = (Objects)inStream.readObject();
					if(!check.testIfIDExist(Long.valueOf(msgRes.message))){
						msg = new Objects("1");
						outStream.writeObject(msg);
						} else {
						msg = new Objects("2");
						outStream.writeObject(msg);}
				}while(!check.testIfIDExist(Long.valueOf(msgRes.message)));
				
				outStream.writeObject(new Objects(rem.removeID(Long.valueOf(msgRes.message))));
				break;
				
			  case "remove_greater":
					do {
						msgRes = (Objects)inStream.readObject();
						if(!check.testIfKeyExist(Long.valueOf(msgRes.message))){send = "1";} else {send = "2";}
						outStream.writeObject(new Objects(send));
					}while(!check.testIfKeyExist(Long.valueOf(msgRes.message)));
					rem.remove_greater(Long.valueOf(msgRes.message));
					outStream.writeObject(new Objects("Elements key>= "+Long.valueOf(msgRes.message)+ " where deleted"));  
				break;
				
			  case "clear":
				  rem = new Remove(doc.getShowPersons2());
				  outStream.reset();
				  outStream.writeObject(new Objects(rem.clear()));
			    break;
			    
			  case "replace_if_greater":
				Long checkID=0L;
				Long checkIDNew=0L;
				do {
					msgRes = (Objects)inStream.readObject();
					checkID=Long.valueOf(msgRes.message);
					msgRes = (Objects)inStream.readObject();
					checkIDNew=Long.valueOf(msgRes.message);
					if(check.testIfIDExist(checkID)&&!check.testIfIDExist(checkIDNew)){send="2";} else {send="1";}
					outStream.writeObject(new Objects(send));
				}while(send.equals("1"));
				outStream.writeObject(new Objects(rem.removeID(checkID)));
				break;
				
			  case "remove_all_by_birthday":
				msgRes = (Objects)inStream.readObject(); 
				System.out.println(msgRes.message);
				outStream.writeObject(new Objects(rem.remove_all_by_birthday(msgRes.message)));
				break;		 
			}
			
			
			
		}while(!msgRes.message.equals("exit"));
		System.out.println("Server: Disconnected");
		serverSocket.close();
		Save s = new Save(doc.getShowPersons2(),doc.getEnvvariable());
		s.wylts();	
	}
	
	
	
}
