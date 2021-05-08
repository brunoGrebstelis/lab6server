package lab6.project;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import obj.Person;


public class Remove {
	
	private LinkedHashMap<Integer, Person> showPersons2;
	
	public Remove(LinkedHashMap<Integer, Person> showPersons2){
		this.showPersons2 =showPersons2;
	}
	
	public String removeID(long id) {
		String removedName = null;
		int rem = 0;
		for (Entry<Integer, Person> m : showPersons2.entrySet()) {
			Person a = m.getValue();
			if (a.getID() == id) {
				rem = m.getKey();
				removedName= a.getName();
			}
		}
		showPersons2.remove(rem);
		return(removedName + " was removed");
	}
	
	public String clear() {
		showPersons2.clear();
		return "List is empty";
	}
	
	public void remove_greater(long key) {	
		ArrayList<Integer> keyToRem= new ArrayList<Integer>();	
		for (Entry<Integer, Person> m : showPersons2.entrySet()) {
			if (key <= m.getKey()) {
				keyToRem.add(m.getKey());
			}
		}		
		for(int i = 0; i<keyToRem.size();i++) {
			showPersons2.remove(keyToRem.get(i));
		}
	}
	
	public String remove_all_by_birthday(String birthsday) {
		int res=-1;
		String retMes = "Nobody has birthsday at that date";
		for (Entry<Integer, Person> m : showPersons2.entrySet()) {
			Person a = m.getValue();
			if (a.FgetBirthday().toString().equals(birthsday)) {
				res = m.getKey();
				retMes = (a.getName() + " was removed");
			}
		}
		System.out.println(res);
		showPersons2.remove(res);
		
		return retMes;
	}

}
