package hearc.ch.roleplay.hearc.ch.roleplay.perso;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import hearc.ch.roleplay.HistoryNode;

/**
 * Created by gabriel.griesser on 27.10.2017.
 */

public class Player
{
    static public String pseudo;
    static public int endurance = 50;
    static public int life = 65;
    static public String actualNodes = "";
    static public int nbNode = 0;
    static private ArrayList<String> list = new ArrayList<String>();
    private static final String FILENAME=null;


    public static boolean add(String pseudo)
    {
        if(list.contains(pseudo))
        {
            return false;
        }
        else
        {
            list.add(pseudo);
            return true;
        }
    }

    public static  ArrayList<String> getlist() {
        list.add("Salut");
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            Log.i("Member: ", list.get(i));
        }
        return list;
    }

}
