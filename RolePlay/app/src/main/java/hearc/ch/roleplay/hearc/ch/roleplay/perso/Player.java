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

/**
 * Created by gabriel.griesser on 27.10.2017.
 */

public class Player
{
    private String pseudo;
    private int endurance;
    private ArrayList<String> list = new ArrayList<String>();
    private static final String FILENAME=null;

    /*
    TODO Ajouter méthode pour récupérer le fichier
     */
    public Player(String pseudo)
    {
        this.pseudo = pseudo;
    }

}
