package search;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * Created by csm26 on 2017-04-12.
 */
public class SearchByPhoneNumber {
    public int comp(Vector<String> lib, String searching){
        int i, j;
        String tmp;
        for(int k =0; k<lib.size();k++){
            tmp = lib.get(k);
        for(i = j = 0; i < tmp.length() ; i++, j++){
            if(j == searching.length()){
                return i - j;
            }
            while(lib.get(i) != ptr[j] && j > 0){
                j = fail[j - 1] + 1;
            }
            if(str[i] != ptr[j]){
                j = -1;
            }
        }
        }
        return -1;
    }

    public static void main(String[] args){
    Vector<String> Library = new Vector<String>(10);
    Library.addElement("01048173738");
    Library.addElement("01069485992");
    Library.get(0);
    }
}
