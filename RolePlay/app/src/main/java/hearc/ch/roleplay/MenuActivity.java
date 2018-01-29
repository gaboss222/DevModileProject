package hearc.ch.roleplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btnNewGame;
    Button btnLoadGame;
    Button btnQuitGame;

    //onCreate, initialize component
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        btnNewGame = (Button)findViewById(R.id.btnNewGame);
        btnLoadGame = (Button)findViewById(R.id.btnLoadGame);
        btnQuitGame = (Button)findViewById(R.id.btnQuitGame);


        btnQuitGame.setOnClickListener(clickListenerBtnQuitGame);
        btnNewGame.setOnClickListener(clickListenerBtnNewGame);
        btnLoadGame.setOnClickListener(clickListenerBtnLoadGame);

    }

    //New introduction (begin a new game)
    private View.OnClickListener clickListenerBtnNewGame = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext(), IntroductionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    };

    //New loadActivity (load existing save)
    private View.OnClickListener clickListenerBtnLoadGame = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    };

    //Quit game
    private View.OnClickListener clickListenerBtnQuitGame = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            finish();
            System.exit(0);
        }
    };
}
