package vuonuomit.freakingmath.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import vuonuomit.freakingmath.R;

public class WelcomeActivity extends Activity {

    Button btn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        overridePendingTransition(R.anim.visi_in, R.anim.slide_out);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.TWO");
                startActivity(intent);
            }
        });
    }
}
