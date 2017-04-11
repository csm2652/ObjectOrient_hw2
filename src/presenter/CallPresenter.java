package presenter;

import model.Call;
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


public class CallPresenter {
    private View view;

    // clicked list index
    private int iClickedList = -1;

    // JSON
    private JSONArray callArray;
    private JSONObject jsonObject;

    private ArrayList<Call> calls = new ArrayList<>();
    private JList list;

    // Construct
    public CallPresenter(View view) {
        this.view = view;
        loadCallJSON();
    }

    /* Getter */
    public JList getList() {
        return list;
    }

    public void setiClickedList(int index) {
        iClickedList = index;
    }

    private void loadCallJSON() {
        try {
            // Get JSON DATA
            JSONParser jsonParser= new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(new FileReader("src\\data\\call.json"));
            callArray = (JSONArray) jsonObject.get("call");

            // Add Json to Array
            JSONObject jsonObjectToSave;
            for (int iCall = 0; iCall < callArray.size(); iCall++) {
                jsonObjectToSave = (JSONObject) callArray.get(iCall);
                calls.add(new Call(
                        jsonObjectToSave.get("type").toString(),
                        jsonObjectToSave.get("number").toString(),
                        jsonObjectToSave.get("time").toString()));
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
        Collections.reverse(calls);
    }

    public void touchAddCall() {
        if (iClickedList != -1) {
            long time = System.currentTimeMillis();

            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = dayTime.format(new Date(time));

            addRecentCall(new Call("send", calls.get(iClickedList).getNumber(), currentTime));
            iClickedList = -1;
        } else {
            System.out.println("none clicked");
        }
    }

    public void touchDelCall() {
        if (iClickedList != -1) {
            delRecentCall(calls.get(iClickedList));
        } else {
            System.out.println("none clicked");
        }
    }

    // Add data (Data: Recent Call)
    private void addRecentCall(Call call) {
        try {
            reverseArray();

            JSONObject addCallObj = new JSONObject();
            addCallObj.put("type", call.getType());
            addCallObj.put("number", call.getNumber());
            addCallObj.put("time", call.getTime());
            callArray.add(addCallObj);
            jsonObject.put("call", callArray);

            calls.add(call);

            reverseArray();

            saveCallJSON();

            list.clearSelection();

            view.deleteList();
            refreshRecentCall();
            refreshAcceptInView();
            //view.updateScreen();
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    // Del data (Data: Recent Call)
    private void delRecentCall(Call call) {
        try {
            reverseArray();

            JSONObject delCallObj = new JSONObject();
            delCallObj.put("type", call.getType());
            delCallObj.put("number", call.getNumber());
            delCallObj.put("time", call.getTime());
            callArray.remove(delCallObj);
            jsonObject.put("call", callArray);

            calls.remove(call);

            reverseArray();

            saveCallJSON();

            list.clearSelection();

            view.deleteList();
            refreshRecentCall();
            refreshAcceptInView();
            //view.updateScreen();
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    // Refresh List (Array to JList)
    // Accept Renderer, Add Pane
    public void refreshRecentCall() {
        list = new JList(calls.toArray());
        list.setVisibleRowCount(4);
    }
    public void refreshAcceptInView() {
        view.acceptRenderer();
    }

    private void saveCallJSON() {
        FileWriter fileWriter;
        try{
            fileWriter = new FileWriter("src\\data\\call.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
        }catch (Exception e){
            System.err.println("Error: " + e);
        }
    }

    public String getStringValue(Object value) {
        Call entry = (Call) value;
        return ("  " + entry.toStringNumber() + "  " + entry.getTime());
    }
    public String getTypeValue(Object value) {
        Call entry = (Call) value;
        return entry.getType();
    }

    public interface View {
        void deleteList();
        void acceptRenderer();
    }
}
