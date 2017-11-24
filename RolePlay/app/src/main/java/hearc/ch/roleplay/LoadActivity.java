package hearc.ch.roleplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import hearc.ch.roleplay.R;

/**
 * Created by gabriel.griesser on 24.11.2017.
 */

public class LoadActivity extends AppCompatActivity
{
    private EditText editLoadPseudo;
    private Button btnLoadPseudo;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_menu);
        editLoadPseudo = (EditText)findViewById(R.id.editLoadPseudo);
        btnLoadPseudo = (Button)findViewById(R.id.btnLoadPseudo);

        btnLoadPseudo.setOnClickListener(clickListenerBtnLoadPseudo);
    }

    private View.OnClickListener clickListenerBtnLoadPseudo = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            //Charger pseudo
        }
    };
}
