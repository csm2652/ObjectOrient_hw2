package model;

public class Person {
    private String sName;
    private String sNumber;
    private String sGroup;
    private String sEmail;

    public Person(String name, String number, String group, String email) {
        sName = name;
        sNumber = number;
        sGroup = group;
        sEmail = email;
    }

    public String getName() { return sName; }
    public String getNumber() { return sNumber; }
    public String getGroup() { return sGroup; }
    public String getEmail() { return sEmail; }


    public String toStringPerson() {
        return sName + "  " + sNumber;
    }
}
