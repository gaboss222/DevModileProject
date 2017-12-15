package hearc.ch.roleplay;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import hearc.ch.roleplay.HistoryNode;
import hearc.ch.roleplay.hearc.ch.roleplay.perso.Player;

/**
 * Created by axel.bentodas on 02.11.2017.
 */

public class FileHandler
{
    public FileHandler(){}


    public HistoryNode readNode(String fileName, Context context)
    {
        HashMap<String,String> childNodes = new HashMap<>();
        StringBuilder text = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName),"UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.length() > 0) {
                    String firstChar = line.substring(0, 1);
                    if (firstChar.equals("/")) {
                        String[] arr = line.split("/");
                        childNodes.put(arr[1], arr[2]);
                    }
                    else
                    {
                        text.append(line);
                        text.append('\n');
                    }
                }
            }
            br.close() ;
        }catch (IOException e) {
            text.append("Some shit append");
        }
        if(childNodes.isEmpty())
        {
            childNodes = null;
        }
        return new HistoryNode(text.toString(), childNodes);
    }

    public boolean fileExists(Context context, String pseudo)
    {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "Save");
        if(!folder.exists())
            folder.mkdir();
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Save/"+pseudo+".txt");
        if(file == null || !file.exists())
        {
            Log.e("File error", "File doesn't exists");
            return false;
        }
        return true;
    }

    public boolean isNameIsInFile(Context context, String pseudo)
    {
        if(fileExists(context, pseudo))
            return true;
        else
        {
            createFile(context, pseudo);
            return false;
        }
    }

    public void createFile(Context context, String pseudo) {
        BufferedWriter bw = null;
        File file = new File(pseudo+".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writePlayer(Context context)
    {
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(Environment.getExternalStorageDirectory() + File.separator +"/Save/"+Player.pseudo+".txt", Context.MODE_PRIVATE)));

            bw.write("Life;"+Player.life);
            bw.newLine();
            bw.write("Power;"+Player.endurance);
            bw.newLine();
            bw.write("Chap;");
            for(String  h : Player.hNodes)
                bw.write(h+";");

        }
        catch (IOException e)
        {
            Log.e("File error", "Error while writing");
            e.printStackTrace();
        }
        finally {
            try {
                bw.flush();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
