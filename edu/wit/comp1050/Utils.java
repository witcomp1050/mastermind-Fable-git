package edu.wit.comp1050;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    public static void print(Object a) {System.out.print(a);}
    public static void println(Object b) {System.out.println(b);}
}
