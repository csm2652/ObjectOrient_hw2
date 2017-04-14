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

   /* public static Vector <String> searching(String input, Vector <String> Lib)
    {
        Vector <String> Temp = new Vector<String>(10);
        for(int i=0; i< Lib.size();i++)
        {
            System.out.println("dd");
            if(Lib.get(i).substring(1,input.length()) == input)
            {
                Temp.add(count++,Lib.get(i));
                System.out.println("hh");
            }
        }
        return Temp;
    }
*/

    public static void main(String[] args){
        int count=0;
        String input = "0106";
        String tmp = "0";

        Vector<String> Library = new Vector<String>(10);
        Vector<String> Tmp = new Vector<String>(10);
        Library.add("01048173738");
        Library.add("01069485992");
        Library.add("01069134543");

        for(int i=0; i< Library.size();i++)
            {
               System.out.println("dd");
               if((input).equals(Library.get(i).substring(0,input.length())))
               {
                   Tmp.add(count++,Library.get(i));
                   System.out.println("hh");
               }
            }
        System.out.println( count + ", 전화번호: " + Library.get(1));
        for(int j =0; j < count; j++)
        {
            System.out.println("asd");
            System.out.println( j + ", 전화번호: " + Tmp.get(j));
        }
        }
}
