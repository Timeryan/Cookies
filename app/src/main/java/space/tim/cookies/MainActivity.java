package space.tim.cookies;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences mSettings;
    LinearLayout LayoutUpgrate;
    LinearLayout layoutEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        addListenerOnButton();
        LayoutUpgrate =findViewById(R.id.layoutUpgrate);
        layoutEnd = findViewById(R.id.layoutEnd);
        count = findViewById(R.id.count);
        if(Integer.valueOf(count.getText().toString())>=1000000){
            layoutEnd.setVisibility(View.VISIBLE);
        }
    }


    Button btnHead;
    Button btnUpgrate ;
    Button btnUpgrate2;
    Button btnToaster;
    Button btnTostMan;
    TextView count;
    int cost = 1;
    int sec = 1100;




    public void addListenerOnButton() {
        btnHead = findViewById(R.id.btnHead);
        btnUpgrate =findViewById(R.id.btnUpgrate);
        btnUpgrate2 = findViewById(R.id.btnUpgrate2);
        btnToaster = findViewById(R.id.btnToaust);
        btnTostMan = findViewById(R.id.btnTostMan);
        final Animation animDown = AnimationUtils.loadAnimation( this,R.anim.go_down);
        final Animation animUp = AnimationUtils.loadAnimation( this,R.anim.go_up);
        final Animation animJump = AnimationUtils.loadAnimation(this,R.anim.jump);

        btnHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(LayoutUpgrate.getVisibility() == View.VISIBLE){
                    LayoutUpgrate.startAnimation(animUp);
                    LayoutUpgrate.setVisibility(View.INVISIBLE);}
                count = findViewById(R.id.count);
                int temp = Integer.valueOf(count.getText().toString());
                temp +=cost;
                count.setText(Integer.toString(temp));
                count.startAnimation(animJump);
            }
        });
        btnUpgrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutUpgrate =findViewById(R.id.layoutUpgrate);
                if(LayoutUpgrate.getVisibility() == View.INVISIBLE){
                LayoutUpgrate.startAnimation(animDown);
                LayoutUpgrate.setVisibility(View.VISIBLE);}
                else {
                    LayoutUpgrate.startAnimation(animUp);
                    LayoutUpgrate.setVisibility(View.INVISIBLE);}
            }
        });
        btnUpgrate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutUpgrate =findViewById(R.id.layoutUpgrate);
                if(LayoutUpgrate.getVisibility() == View.INVISIBLE){
                    LayoutUpgrate.startAnimation(animDown);
                    LayoutUpgrate.setVisibility(View.VISIBLE);}
                else {
                    LayoutUpgrate.startAnimation(animUp);
                    LayoutUpgrate.setVisibility(View.INVISIBLE);}
            }
        });
        btnToaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = findViewById(R.id.count);
                if(Double.valueOf(btnToaster.getText().toString())<= Double.valueOf(count.getText().toString()))
                {
                    count.setText(((Integer.toString((Integer.valueOf(count.getText().toString()))-(Integer.valueOf(btnToaster.getText().toString()))))));
                    btnToaster.setText(Integer.toString(Integer.valueOf(Long.toString(Math.round((Integer.valueOf(btnToaster.getText().toString()))*1.5)))));
                    cost++;

                }
            }
        });

        btnTostMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count.findViewById(R.id.count);
                if((Integer.valueOf(count.getText().toString()))>=500){
                    btnTostMan.setEnabled(false);
                    if(sec!= 100)sec-=100;
                    Man(sec);
                    count.setText(((Integer.toString((Integer.valueOf(count.getText().toString()))-500))));
                }
            }
        });

    }
    public void Man(int sec){
        count = findViewById(R.id.count);
            new CountDownTimer(480000, sec) {
            @Override
            public void onTick(long l) {
                count.setText(Integer.toString(Integer.valueOf(count.getText().toString()) + 1));
            }

            @Override
            public void onFinish() {
                btnTostMan.setEnabled(true);
            }
        }.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Запоминаем данные
        mSettings = this.getSharedPreferences("mySetiings",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        TextView count = findViewById(R.id.count);
        btnToaster = findViewById(R.id.btnToaust);

        editor.putString("Counter", count.getText().toString());
        editor.putInt("Cost",cost);
        editor.putString("TosterPrise", btnToaster.getText().toString());
        editor.putInt("Sec", sec);
        editor.apply();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        mSettings = this.getSharedPreferences("mySetiings",Context.MODE_PRIVATE);
        if(mSettings.contains("Counter")) {
        TextView count = findViewById(R.id.count);
            count.setText(mSettings.getString("Counter", ""));
        }
        if(mSettings.contains("Cost")) {
            cost = mSettings.getInt("Cost", 0);
        }
        if(mSettings.contains("TosterPrise")) {
            TextView count = findViewById(R.id.btnToaust);
            count.setText(mSettings.getString("TosterPrise", ""));
        }
        if(mSettings.contains("Sec")) {
            sec = mSettings.getInt("Sec", 0);
        }


    }
}