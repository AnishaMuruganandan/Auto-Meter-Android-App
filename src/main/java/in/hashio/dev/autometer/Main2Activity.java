package in.hashio.dev.autometer;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;

public class Main2Activity extends AppCompatActivity {
double day_charges=0,night_charges=0,distance;
String d_charge,n_charge;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final RelativeLayout relativeLayout;
        Bundle b = getIntent().getExtras();
        final TextView distance1 = (TextView) findViewById(R.id.textView);
        final ImageView i3=(ImageView) findViewById(R.id.i3);
        distance1.setTextColor(Color.RED);
        String dist=b.getCharSequence("distance").toString();

        distance=Double.parseDouble(dist);
        //distance1.setText(distance);
        if(distance<=1.8) {
            day_charges = 25;
            night_charges=day_charges+(day_charges*0.5);
        }
        else if(distance>1.8) {
            day_charges = 25 + (distance - 1.8) * 12;
            night_charges=day_charges+(day_charges*0.5);
        }
        DecimalFormat df = new DecimalFormat("#.00");
        d_charge= df.format(day_charges);
        n_charge=df.format(night_charges);

        distance1.setText(d_charge);

        relativeLayout=(RelativeLayout)findViewById(R.id.RelatiaveLayout);

//        String name = "sun";
//        int id = getResources().getIdentifier(name, "drawable", getPackageName());
//        Drawable drawable = getResources().getDrawable(id);
//        relativeLayout.setBackground(drawable);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                  //  Toast.makeText(Main2Activity.this, "on", Toast.LENGTH_SHORT).show();
                    i3.setImageResource(R.drawable.sunny);
                    distance1.setText(d_charge);

                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                    i3.setImageResource(R.drawable.moon);
                    distance1.setText(n_charge);
                   // Toast.makeText(Main2Activity.this, "off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
