package presenter;

import model.SMS;
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

public class SMSPresenter {
    private View view;

    // clicked list index
    private int iClickedList = -1;

    // JSON
    private JSONArray smsArray;
    private JSONObject jsonObject;

    // SMS List
    private ArrayList<SMS> smss = new ArrayList<>();
    private JList list;

    // Construct
    public SMSPresenter(View view) {
        this.view = view;
        loadCallJSON();
    }

    /* Getter */
    public JList getList() {
        return list;
    }

    /* Setter */
    public void setiClickedList(int index) {
        iClickedList = index;
    }

    private void loadCallJSON() {
        try {
            // Get JSON DATA
            JSONParser jsonParser= new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(new FileReader("src\\data\\sms.json"));
            smsArray = (JSONArray) jsonObject.get("sms");

            // Add Json to Array
            JSONObject jsonObjectToSave;
            for (int iSMS = 0; iSMS < smsArray.size(); iSMS++) {
                jsonObjectToSave = (JSONObject) smsArray.get(iSMS);
                smss.add(new SMS(
                        jsonObjectToSave.get("type").toString(),
                        jsonObjectToSave.get("number").toString(),
                        jsonObjectToSave.get("content").toString(),
                        jsonObjectToSave.get("time").toString()));
                // charset = iso-8859-1
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

    // reverseArray
    private void reverseArray() {
        Collections.reverse(smss);
    }

    public void touchAddSMS() {
        if (iClickedList != -1) {

            long time = System.currentTimeMillis();

            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = dayTime.format(new Date(time));

            addRecentSMS(new SMS("send", smss.get(iClickedList).getNumber(), "한글", currentTime));
            iClickedList = -1;
        } else {
            System.out.println("none clicked");
        }
    }

    public void touchDelSMS() {
        if (iClickedList != -1) {
            delRecentSMS(smss.get(iClickedList));
        } else {
            System.out.println("none clicked");
        }
    }

    // Add data (Data: Recent SMS)
    private void addRecentSMS(SMS sms) {
        try {
            reverseArray();

            JSONObject addSMSObj = new JSONObject();
            addSMSObj.put("type", sms.getType());
            addSMSObj.put("number", sms.getNumber());
            addSMSObj.put("content", sms.getContent());
            addSMSObj.put("time", sms.getTime());
            smsArray.add(addSMSObj);
            jsonObject.put("sms", smsArray);

            smss.add(sms);

            reverseArray();

            saveSMSJSON();

            list.clearSelection();

            view.deleteList();
            refreshRecentSMS();
            refreshAcceptInView();
        } catch (Exception e) {
            System.out.println("fail");
        }
    }




    // Del data (Data: Recent SMS)
    private void delRecentSMS(SMS sms) {
        try {
            reverseArray();

            JSONObject delCallObj = new JSONObject();
            delCallObj.put("type", sms.getType());
            delCallObj.put("number", sms.getNumber());
            delCallObj.put("content", sms.getContent());
            delCallObj.put("time", sms.getTime());
            smsArray.remove(delCallObj);
            jsonObject.put("call", smsArray);

            smss.remove(sms);

            reverseArray();

            saveSMSJSON();

            list.clearSelection();

            view.deleteList();
            refreshRecentSMS();
            refreshAcceptInView();
        } catch (Exception e) {
            System.out.println("fail");
        }
    }

    // Refresh List (Array to JList)
    // Accept Renderer, Add Pane
    public void refreshRecentSMS() {
        list = new JList(smss.toArray());
        list.setVisibleRowCount(4);
    }
    public void refreshAcceptInView() {
        view.acceptRenderer();
    }

    private void saveSMSJSON() {
        FileWriter fileWriter;
        try{
            fileWriter = new FileWriter("src\\data\\sms.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
        }catch (Exception e){
            System.err.println("Error: " + e);
        }
    }

    public String getStringValue(Object value) {
        SMS entry = (SMS) value;
        return ("  " + entry.toStringNumber() + "  "  + entry.toStringContent() + " "+ entry.getTime());
    }
    public String getTypeValue(Object value) {
        SMS entry = (SMS) value;
        return entry.getType();
    }

    public interface View {
        void deleteList();
        void acceptRenderer();
    }
}
