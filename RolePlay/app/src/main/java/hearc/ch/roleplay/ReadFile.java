package hearc.ch.roleplay;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import hearc.ch.roleplay.HistoryNode;

/**
 * Created by axel.bentodas on 02.11.2017.
 */

public class ReadFile {
    public ReadFile(){}


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
}
