package hearc.ch.roleplay;

import java.util.HashMap;

/**
 * Created by axel.bentodas on 02.11.2017.
 */

public class HistoryNode {
    public  String strId = "";
    public String strText = "";
    public HashMap<String,String> accessibleNodes;

    public HistoryNode(String id, String text, HashMap<String,String> nodes)
    {
        strId = id;
        strText = text;
        accessibleNodes = nodes;
    }


}
