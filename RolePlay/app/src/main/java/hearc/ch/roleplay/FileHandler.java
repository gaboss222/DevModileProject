package hearc.ch.roleplay;

import android.content.Context;
import android.media.Image;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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
    private int attributeChanged = 0;
    private Context context;


    //The constructor
    public FileHandler(Context _context){
        this.context = _context;
    }

    //This function read an history node file and with it, create and return the corresponding HistoryNode object
    public HistoryNode readNode(String fileName)
    {
        HashMap<String,String> childNodes = new HashMap<>();
        attributeChanged = 0;
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
                            attributeChanged = 1;
                        }
                        else if(line.contains("Life"))
                        {
                            Player.life += Integer.parseInt(line.substring(life.length() + 1, line.length()));
                            attributeChanged = 2;
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
            Log.e("FileHandlerError", e.getMessage());
            text.append("There has been a problem with read of the file");
        }
        if(childNodes.isEmpty())
        {
            childNodes = null;
        }
        return new HistoryNode(fileName, text.toString(), childNodes);
    }

    public int isAttributeChanged()
    {
        return attributeChanged;
    }

    //This function is use to know if a pseudo is already used in the Save file
    public boolean isNameInFile(String pseudo)
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

    //Save the player advancement, call when the game is closed
    public void savePlayer()
    {
        try
        {
            File folder = new File(context.getFilesDir(), "");
            String nameFile = Player.pseudo+".txt";
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

    //Return every saved files, to display them into LoadActivity
    public File[] getSaves()
    {
        File saveFolder = new File(context.getFilesDir(),"");
        return saveFolder.listFiles();
    }

    //Load the strPseudo Player advancement
    public void loadSave(String strPseudo)
    {
        try {
            File file = new File(strPseudo);
            BufferedReader br = new BufferedReader(new InputStreamReader(context.openFileInput(strPseudo)));
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
