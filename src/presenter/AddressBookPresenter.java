package presenter;

import model.Call;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


public class AddressBookPresenter {
    private View view;

    // clicked list index
    private int iClickedList = -1;

    // JSON
    private JSONArray personArray;
    private JSONObject jsonObject;

    private ArrayList<Person> persons = new ArrayList<>();
    private JList list;
    private JList listSearched;

    // Construct
    public AddressBookPresenter(View view) {
        this.view = view;
        loadCallJSON();
    }

    /* Getter */
    public JList getList() {
        return list;
    }
    public JList getListSearched() {
        return listSearched;
    }

    public void setiClickedList(int index) {
        iClickedList = index;
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
            reverseArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // reverArray
    private void reverseArray() {
        Collections.reverse(persons);
    }

    // Button clicked
    public void touchAddPerson() {
        iClickedList = iClickedList;
    }

    public void touchDelPerson() {

    }

    public void touchModPerson() {

    }



    // Add data (Data: Recent Call)
    private void addPerson(Person person) {
        try {
            reverseArray();

            JSONObject addPersonObj = new JSONObject();
            addPersonObj.put("name", person.getName());
            addPersonObj.put("number", person.getNumber());
            addPersonObj.put("group", person.getGroup());
            addPersonObj.put("email", person.getEmail());
            personArray.add(addPersonObj);
            jsonObject.put("person", personArray);

            persons.add(person);

            reverseArray();

            saveCallJSON();

            list.clearSelection();

            view.deleteList();
            refreshPersonList();
            refreshAcceptInView();
            //view.updateScreen();
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    // Del data (Data: Recent Call)
    private void delPerson(Person person) {
        try {
            reverseArray();

            JSONObject delPersonObj = new JSONObject();
            delPersonObj.put("name", person.getName());
            delPersonObj.put("number", person.getNumber());
            delPersonObj.put("group", person.getGroup());
            delPersonObj.put("email", person.getEmail());
            personArray.add(delPersonObj);
            jsonObject.put("person", personArray);

            persons.remove(person);

            reverseArray();

            saveCallJSON();

            list.clearSelection();

            view.deleteList();
            refreshPersonList();
            refreshAcceptInView();
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
    public void refreshAcceptInView() {
        view.acceptRenderer();
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
        if (search.equals("")) {
            list.setVisibleRowCount(4);
            view.deleteList();
            refreshAcceptInView();
        } else {
            listSearched = new JList();



            listSearched.setVisibleRowCount(4);
            view.deleteList();
            view.acceptSearched();
        }
    }


    public interface View {
        void deleteList();
        void acceptRenderer();
        void acceptSearched();
    }
}
