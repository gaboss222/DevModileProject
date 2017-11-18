package hearc.ch.roleplay;

import android.content.Context;
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

/**
 * Created by axel.bentodas on 02.11.2017.
 */

public class ReadFile
{
    public ReadFile(){}
    static final String FILENAME = "save.txt";


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

    public boolean fileExists(Context context)
    {
        File file = context.getFileStreamPath(FILENAME);
        if(file == null || !file.exists())
        {
            Log.e("File error", "File doesn't exists");
            return false;
        }
        return true;
    }

    public boolean isNameIsInFile(Context context, String pseudo)
    {
        if(fileExists(context))
        {
            String data = "";
            try
            {
                InputStream inputStream = context.openFileInput(FILENAME);
                if(inputStream!=null)
                {
                    //Préparation lecture
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveData = "";
                    StringBuilder stringBuilder = new StringBuilder();

                    //Lecture des données
                    while ((receiveData = bufferedReader.readLine()) != null)
                    {
                        stringBuilder.append(receiveData);
                        stringBuilder.append("\n");
                    }

                    inputStream.close();
                    data = stringBuilder.toString();

                    //Si le fichier contient le pseudo, CONTINUER ICI
                    if (data.contains(pseudo))
                    {
                        Log.i("File info", "File contains the pseudo " + pseudo);
                        Log.i("File data", data.toString());
                        return true;
                    }
                    //Sinon, on ecrit le pseudo dans le fichier
                    else
                    {
                        writeToFile(context, pseudo);
                        return false;
                    }
                }
            }
            catch(IOException e)
            {
                Log.e("Read error", "Error while read file");
            }
        }
        else
        {
            createFile(context, pseudo);
            return false;
        }
        return false;
    }

    public void createFile(Context context, String pseudo)
    {
        FileOutputStream outputStream = null;
        File file = new File(FILENAME);
        try
        {
            outputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            outputStream.write(pseudo.getBytes());

        }
        catch(IOException e)
        {
            e.printStackTrace();
            Log.e("File error", "Error while open file");
        }
        finally
        {
            try
            {
                outputStream.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void writeToFile(Context context, String data)
    {
        try
        {
            FileOutputStream outputStream = context.openFileOutput(FILENAME, context.MODE_APPEND);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            //REPRENDRE ICI, NOUVELLE LIGNE A CHAQUE PSEUDO

            outputStreamWriter.append(data);
            outputStreamWriter.append('\n');
            outputStreamWriter.close();
            outputStream.close();
        }
        catch (IOException e)
        {
            Log.e("File error", "Error while writing");
            e.printStackTrace();
        }
    }


}
