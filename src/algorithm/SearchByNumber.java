package algorithm;

public class SearchByNumber {

    public static boolean search(String all, String part) {
        if (matchNumber(part, all)) {
            return  true;
        }
        return false;
    }

    public static boolean matchNumber(String part, String all){
        if((part).equals(all.substring(0, part.length())))
        {
            return true;
        }
        return false;
    }

}
