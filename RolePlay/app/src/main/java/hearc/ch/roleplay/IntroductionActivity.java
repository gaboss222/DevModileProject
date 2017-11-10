package hearc.ch.roleplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import hearc.ch.roleplay.hearc.ch.roleplay.perso.Player;


/**
 * Created by gabriel.griesser on 27.10.2017.
 */

public class IntroductionActivity extends AppCompatActivity implements View.OnClickListener
{

    TextView txtLORE;
    Button btnSuivant;
    EditText pseudo = null;
    String pseudonyme = null;
    static final String filename = "save.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_menu);

        btnSuivant = (Button)findViewById(R.id.btnSuivant);
        pseudo = (EditText) findViewById(R.id.editPseudo);
        txtLORE = (TextView)findViewById(R.id.txtLORE);
        btnSuivant.setOnClickListener(this);
    }

    private String getPseudo()
    {
        if(pseudo.getText().length() > 0)
        {
            pseudonyme = pseudo.getText().toString();
            return pseudonyme;
        }
        else
        {
            return null;
        }
    }

    private void setPseudo(String msg)
    {
        pseudo.setText(msg);
    }

    @Override
    public void onClick(View v)
    {

        if(getPseudo() != null)
        {
            Player p = new Player();
            if(p.add(pseudonyme))
            {
                String msg = readFromFile(this.getApplicationContext());
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("Pseudo", msg);
                startActivity(intent);
            }
            else
            {
                setPseudo("Pseudo déjà utilisé");
            }

        }
        else
        {
            setPseudo("Rentrez un pseudo");
        }

    }

    private String readFromFile(Context context) {

        String ret = "";

        try
        {
            InputStream inputStream = context.openFileInput(filename);

            if ( inputStream != null )
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null )
                {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void writeToFile(String data, Context context)
    {
        try
        {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        }
        catch(IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
