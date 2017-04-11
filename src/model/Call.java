package model;

public class Call {
    // sType : "recv, send"
    private final String sType;
    private final String sNumber;
    private final String sTime;

    public Call(String type, String number, String time) {
        sType = type;
        sNumber = number;
        sTime = time;
    }

    public String getType() {
        return sType;
    }
    public String getNumber() {
        return sNumber;
    }
    public String toStringNumber() {
        String returnString = "";
        String subStrString;

        if (sNumber.length() > 3) {
            subStrString = sNumber.substring(0, 3);
        } else {
            return sNumber;
        }

        returnString += subStrString + "-";

        if (sNumber.length() > 7) {
            subStrString = sNumber.substring(3, 7);
        } else {
            return sNumber;
        }

        returnString += subStrString + "-" + sNumber.substring(7);

        return returnString;
    }
    public String getTime() {
        return sTime;
    }
}
