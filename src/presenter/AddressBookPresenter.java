package presenter;

import algorithm.SearchByName;
import model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddressBookPresenter {
    private View view;

    // clicked list index
    private int iClickedList = -1;

    // JSON
    private JSONArray personArray;
    private JSONObject jsonObject;

    private static ArrayList<Person> persons = new ArrayList<>();
    private ArrayList<Person> personsSearched = new ArrayList<>();

    private JList list;
    private JList listSearched;

    private boolean isSearched;
    private String currentTxtSearched;

    // Construct
    public AddressBookPresenter(View view) {
        this.view = view;
        isSearched = false;
        loadCallJSON();
    }

    /* Getter */
    public static ArrayList<Person> getPersons() { return persons; }
    public JList getList() {
        return list;
    }
    public JList getListSearched() {
        return listSearched;
    }

    public void setiClickedList(int index) {
        iClickedList = index;
    }

    public void refreshPresent() {
        persons.clear();
        personsSearched.clear();

        loadCallJSON();
        list.clearSelection();
        view.deleteList();
        refreshPersonList();

        refreshList(isSearched);
        if (!isSearched) view.acceptRenderer();
        else view.acceptSearched();
    }

    private void loadCallJSON() {
        try {
            // Get JSON DATA
            JSONParser jsonParser= new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(new FileReader("src\\data\\person.json"));
            personArray = (JSONArray) jsonObject.get("person");

            // Add Json to Array
            JSONObject jsonObjectToSave;
            for (int iPerson = 0; iPerson < personArray.size(); iPerson++) {
                jsonObjectToSave = (JSONObject) personArray.get(iPerson);
                persons.add(new Person(
                        jsonObjectToSave.get("name").toString(),
                        jsonObjectToSave.get("number").toString(),
                        jsonObjectToSave.get("group").toString(),
                        jsonObjectToSave.get("email").toString()));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    // Button clicked
    public void touchAddPerson() {
        AddModifyPersonPresenter.refreshAdd();
        MainPresenter.switchScreen(true);

        //addPerson(new Person("최승민","01078898996","",""));
    }

    public void touchDelPerson() {
        if (iClickedList != -1) {
            if (isSearched == false) {
                delPerson(persons.get(iClickedList));
            } else {
                delPerson(personsSearched.get(iClickedList));
            }
            iClickedList = -1;
        } else {
            System.out.println("none clicked");
        }
    }

    public void touchModPerson() {
        if (iClickedList != -1) {
            Person modPerson;
            if (isSearched == false) {
                modPerson = persons.get(iClickedList);
            } else {
                modPerson = personsSearched.get(iClickedList);
            }

            AddModifyPersonPresenter.refreshModify(modPerson.getName(), modPerson.getNumber(),
                    modPerson.getGroup(), modPerson.getEmail());
            MainPresenter.switchScreen(true);
        } else {
            System.out.println("non clicked");
        }

    }


    /*
    // Add data (Data: Person)
    private void addPerson(Person person) {
        try {

            JSONObject addPersonObj = new JSONObject();
            addPersonObj.put("name", person.getName());
            addPersonObj.put("number", person.getNumber());
            addPersonObj.put("group", person.getGroup());
            addPersonObj.put("email", person.getEmail());
            personArray.add(addPersonObj);
            jsonObject.put("person", personArray);

            persons.add(person);

            saveCallJSON();

            list.clearSelection();

            view.deleteList();
            refreshPersonList();

            refreshList(isSearched);
            if (isSearched == false) view.acceptRenderer();
            else view.acceptSearched();
            //view.updateScreen();
        } catch (Exception e) {
            System.out.println("fail");
        }
    }*/

    // Del data (Data: Recent Call)
    private void delPerson(Person person) {
        try {

            JSONObject delPersonObj = new JSONObject();
            delPersonObj.put("name", person.getName());
            delPersonObj.put("number", person.getNumber());
            delPersonObj.put("group", person.getGroup());
            delPersonObj.put("email", person.getEmail());
            personArray.remove(delPersonObj);
            jsonObject.put("person", personArray);

            persons.remove(person);

            saveCallJSON();

            list.clearSelection();

            view.deleteList();
            refreshPersonList();

            refreshList(isSearched);
            if (isSearched == false) view.acceptRenderer();
            else view.acceptSearched();

            //view.updateScreen();
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    // Refresh List (Array to JList)
    // Accept Renderer, Add Pane
    public void refreshPersonList() {
        list = new JList(persons.toArray());
        list.setVisibleRowCount(4);
    }

    public void call(int index) {
        CallPresenter.callAtAdderssBook(persons.get(index).getNumber());
    }
    public void callSearched(int index) {
        CallPresenter.callAtAdderssBook(personsSearched.get(index).getNumber());
    }

    private void saveCallJSON() {
        FileWriter fileWriter;
        try{
            fileWriter = new FileWriter("src\\data\\person.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
        }catch (Exception e){
            System.err.println("Error: " + e);
        }
    }

    public String getStringNameNumber(Object value) {
        Person entry = (Person) value;
        return ("  " + entry.toStringPerson());
    }

    public void changedTxtSearch(String search) {
        currentTxtSearched = search;
        if (search.equals("")) {
            isSearched = false;
            refreshList(isSearched);
            view.acceptRenderer();
        } else {
            isSearched = true;
            refreshList(isSearched);
            view.acceptSearched();
        }
    }

    private void refreshList(boolean isSearched) {
        if(isSearched == false) {
            list.setVisibleRowCount(4);
            view.deleteList();
            //view.acceptRenderer();
        } else {
            personsSearched.clear();
            for (int iSearch = 0; iSearch < persons.size(); iSearch++) {
                if (SearchByName.search(persons.get(iSearch).getName(), currentTxtSearched))
                    personsSearched.add(persons.get(iSearch));
            }
            for (int iSearch = 0; iSearch < persons.size(); iSearch++) {
                if (SearchByName.search(persons.get(iSearch).getNumber(), currentTxtSearched))
                    personsSearched.add(persons.get(iSearch));
            }

            listSearched = new JList(personsSearched.toArray());

            listSearched.setVisibleRowCount(4);
            view.deleteList();
            //view.acceptSearched();
        }
    }


    public interface View {
        void deleteList();
        void acceptRenderer();
        void acceptSearched();
    }
}
