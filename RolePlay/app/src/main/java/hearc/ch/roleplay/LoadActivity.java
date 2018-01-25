package hearc.ch.roleplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import hearc.ch.roleplay.R;

/**
 * Created by gabriel.griesser on 24.11.2017.
 */

public class LoadActivity extends AppCompatActivity
{
    private ListView lsSave;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final FileHandler fileHandler = new FileHandler(this.getApplicationContext());
        setContentView(R.layout.load_menu);
        lsSave = (ListView)findViewById(R.id.listSave);
        lsSave.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String strName = lsSave.getItemAtPosition(i).toString();
                fileHandler.loadSave(strName);
                Intent intent = new Intent(LoadActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
        ArrayList<String> saveFiles = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, saveFiles);
        lsSave.setAdapter(adapter);

        File[] fileSave = fileHandler.getSaves();
        for(File file : fileSave) {
            String name = file.getName();
            saveFiles.add(name);
        }
        adapter.notifyDataSetChanged();

    }


}
