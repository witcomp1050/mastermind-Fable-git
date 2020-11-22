package edu.wit.comp1050;

import java.io.FileInputStream;
import java.util.*;

public class Utils {
    public static Properties importProperties() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("edu/wit/comp1050/mmind.properties"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return prop;
    }
    public static <K,V> K getFirstKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return(entry.getKey());
            }
        }
        return null;
    }

    public static void print(Object a) {System.out.print(a);}
    public static void println(Object b) {System.out.println(b);}
}
