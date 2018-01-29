package hearc.ch.roleplay;

import java.util.HashMap;

/**
 * Created by axel.bentodas on 02.10.2017.
 */

//This class represent a node of the scenario
public class HistoryNode {
    public  String strId = "";
    public String strText = "";                     //Contain the narrative text
    public HashMap<String,String> accessibleNodes;  //Reference the next node

    public HistoryNode(String id, String text, HashMap<String,String> nodes)
    {
        strId = id;
        strText = text;
        accessibleNodes = nodes;
    }


}
