package hearc.ch.roleplay;

import java.util.HashMap;

/**
 * Created by axel.bentodas on 02.11.2017.
 */

public class HistoryNode {
    public String strText = "";
    public HashMap<String,String> accessibleNodes;

    public HistoryNode(String text, HashMap<String,String> nodes)
    {
        strText = text;
        accessibleNodes = nodes;
    }


}
