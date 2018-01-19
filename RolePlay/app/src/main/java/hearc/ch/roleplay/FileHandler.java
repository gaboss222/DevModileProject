package hearc.ch.roleplay;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

    private String endurance = "Endurance";
    private String life = "Life";

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
                        if(line.contains("Endurance"))
                        {
                            Player.endurance += Integer.parseInt(line.substring(endurance.length() + 1, line.length()));
                        }
                        else if(line.contains("Life"))
                        {
                            Player.life += Integer.parseInt(line.substring(life.length() + 1, line.length()));
                        }
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

    public void savePlayer(Context context, String pseudo)
    {
        try
        {
            File folder = new File(context.getFilesDir(), "Save");
            if(!folder.exists())
                folder.mkdir();
            String nameFile = pseudo+".txt";

            File saveFile = new File(folder,nameFile);
            FileWriter writer = new FileWriter(saveFile);
            writer.append("Life;"+Player.life+"\n");
            writer.append("Power;"+Player.endurance+"\n");
            writer.append("NbChap;"+Player.nbNode+"\n");
            writer.append("Chap;" + Player.actualNodes+"\n");

            writer.flush();
            writer.close();

        }
        catch (IOException e)
        {
            Log.e("File error", "Error while writing");
            e.printStackTrace();
        }
    }

    public File[] getSaves(Context context)
    {
        File saveFolder = new File(context.getFilesDir(),"Save");
        return saveFolder.listFiles();
    }

    public void loadSave(Context context, String strName)
    {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput("./Save/"+strName)));
            String line ="";
            while ((line = br.readLine()) != null)
            {
                String[] component = line.split(";");
                switch(component[0])
                {
                    case "Life":
                        Player.life = Integer.parseInt(component[1]);
                        break;
                    case "Power":
                        Player.endurance = Integer.parseInt(component[1]);
                        break;
                    case "NbChap":
                        Player.nbNode = Integer.parseInt(component[1]);
                    case "Chap":
                        Player.actualNodes = component[1];
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
