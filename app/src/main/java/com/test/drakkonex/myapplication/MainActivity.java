package com.test.drakkonex.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public View button1, button2;
    public int scount = 0, bcount = 0,tocount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.strike_button);
        button1.setOnClickListener(this);
        incScount();

        button2 = findViewById(R.id.ball_button);
        button2.setOnClickListener(this);
        incBcount();

        SharedPreferences settings = getSharedPreferences("Preferred", 0);
        tocount = settings.getInt("Total Outs",tocount);
        incTOCount();

    }
    public void incScount(){
        TextView t = (TextView)findViewById(R.id.sscore);
        t.setText(Integer.toString(scount));
    }
    public void incBcount(){
        TextView s = (TextView)findViewById(R.id.bscore);
        s.setText(Integer.toString(bcount));
    }
    public void incTOCount(){
        TextView r = (TextView)findViewById(R.id.tscore);
        r.setText(Integer.toString(tocount));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                scount = 0;
                bcount = 0;
                tocount = 0;
                incTOCount();
                incBcount();
                incScount();
                return true;
            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v){
       switch (v.getId()){
            case R.id.strike_button:
                if(scount == 2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("3 Strikes!");
                    builder.setMessage("You're out!");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Next!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            scount=0;
                            incScount();

                            bcount=0;
                            incBcount();

                            tocount++;
                            incTOCount();

                            SharedPreferences settings = getSharedPreferences("Preferred", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putInt("Total Outs",tocount);
                            editor.commit();
                        }
                    });
                    builder.show();
                }
                else{
                    scount++;
                }
                break;
           case R.id.ball_button:
               if (bcount ==3){
                   AlertDialog.Builder builder = new AlertDialog.Builder(this);
                   builder.setTitle("4 Balls!");
                   builder.setMessage("You walk!");
                   builder.setCancelable(false);
                   builder.setPositiveButton("Next!", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           scount=0;
                           incScount();

                           bcount=0;
                           incBcount();
                       }
                   });
                   builder.show();

               }
               else{
                    bcount++;
               }
               break;

        }
        incBcount();
        incScount();
        incTOCount();
    }

}
