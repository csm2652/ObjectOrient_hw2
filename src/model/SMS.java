package model;

public class SMS {
    private final String sType;
    private final String sNumber;
    private final String sContent;
    private final String sTime;

    public SMS(String type, String number, String content, String time) {
        sType = type;
        sNumber = number;
        sContent = content;
        sTime = time;
    }

    public String getType() { return sType; }
    public String getNumber() { return sNumber; }
    public String getContent() { return sContent; }
    public String getTime() { return sTime; }


    public String toStringContent() {

        if (sContent.length() > 6) {
            return sContent.substring(0, 6) + "...";
        }

        return sContent;
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
}
