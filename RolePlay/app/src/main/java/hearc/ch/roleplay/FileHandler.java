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
                    else if (firstChar.equals("+"))
                    {

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
            text.append("There has been a problem with read of the file");
        }
        if(childNodes.isEmpty())
        {
            childNodes = null;
        }
        return new HistoryNode(text.toString(), childNodes);
    }

    public boolean isNameInFile(Context context, String pseudo)
    {
        String fileName = "SaveName.txt";
        try
       {
           BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
            String line = "";
            while((line = br.readLine()) != null){
                if(line.equals(pseudo))
                    return true;
            }

           br.close();
       }
       catch (IOException e){}

       try
       {
           FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_APPEND);
           fos.write(pseudo.getBytes());
           fos.flush();
           fos.close();
       }
       catch (IOException e){}
       return false;
    }

    public void writePlayer(Context context, String pseudo)
    {
        try
        {
            FileOutputStream fos = context.openFileOutput(pseudo+".txt",Context.MODE_PRIVATE);
            fos.write(("Life;"+Player.life).getBytes());
            fos.write(("Power;"+Player.endurance).getBytes());
            fos.write(("Chap;" + Player.actualNodes).getBytes());

            fos.flush();
            fos.close();

        }
        catch (IOException e)
        {
            Log.e("File error", "Error while writing");
            e.printStackTrace();
        }
    }


}
