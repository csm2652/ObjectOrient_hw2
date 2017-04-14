package presenter;

import model.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddModifyPersonPresenter {
    private static View view;
    private static boolean isModify;

    private JSONArray personArray;
    private JSONObject jsonObject;

    private ArrayList<Person> persons = new ArrayList<>();
    private ArrayList<Person> personsSearched = new ArrayList<>();

    public AddModifyPersonPresenter(View view) {
        this.view = view;
    }

    public void savePerson(String name, String number, String group, String email) {
        if (isModify) {
            // 수정할시 원래꺼 지우고 새로 넣기
        } else {
            // 수정이 아닐시 그냥 넣기
        }
        loadJSON();
        addPerson(new Person(name.trim(), number.trim(), group.trim(), email.trim()));
        saveJSON();
        MainPresenter.switchScreen(false);
    }
    private void loadJSON() {
        try {
            // Get JSON DATA
            JSONParser jsonParser= new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(new FileReader("src\\data\\person.json"));
            personArray = (JSONArray) jsonObject.get("person");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
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

        } catch (Exception e) {
            System.out.println("fail");
        }
    }
    private void saveJSON() {
        FileWriter fileWriter;
        try{
            fileWriter = new FileWriter("src\\data\\person.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
        }catch (Exception e){
            System.err.println("Error: " + e);
        }
    }

    public static void refreshAdd() {
        isModify = false;
        view.setTextNoArgument();
    }

    public static void refreshModify(String name, String number, String group, String email) {
        isModify = true;
        view.setTextArgument(name, number, group, email);
    }

    public interface View {
        void setTextNoArgument();
        void setTextArgument(String name, String number, String group, String email);
    }
}
