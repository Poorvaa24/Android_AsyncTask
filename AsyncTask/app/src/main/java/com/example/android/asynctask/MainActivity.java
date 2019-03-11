package com.example.android.asynctask;

import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText no_of_reading;
    private Button generate_btn;
    private TextView txt_temp;
    private TextView txt_humid;
    private TextView txt_activity;
    private TextView txt_output;
    private ProgressBar my_progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_temp = (TextView)findViewById(R.id.txt_temp_val);
        txt_humid = (TextView)findViewById(R.id.txt_humid_val);
        txt_activity = (TextView)findViewById(R.id.txt_activity_val);
        no_of_reading = (EditText)findViewById(R.id.input_reading);
        my_progressbar = (ProgressBar)findViewById(R.id.progressBar);
        txt_output = (TextView)findViewById(R.id.txt_output);
        txt_output.setMovementMethod(new ScrollingMovementMethod());


        generate_btn = (Button)findViewById(R.id.generate);
        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reading = no_of_reading.getText().toString();
                if (TextUtils.isEmpty(reading)) {
                    Toast t = Toast.makeText(MainActivity.this, "Please enter the no of readings", Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    MyAsyncTask runner = new MyAsyncTask();

                    Log.v("**********************", reading);
                    int n = Integer.parseInt(reading);
                    runner.execute(n);
                }
            }
        });

    }

    private class MyAsyncTask extends AsyncTask<Integer,MySensor,Void> {
        // ProgressDialog progressDialog;
        private Random myRandom = new Random();

        @Override
        protected Void doInBackground(Integer... params)
        {
            int n = params[0];

            for(int i = 1; i <= n ; i++)
            {
                int rnd_temp = myRandom.nextInt(100 - 25 + 1) + 25;
                int rnd_humid = myRandom.nextInt(100 - 40 + 1) + 40;
                int rnd_activity = myRandom.nextInt(500 - 1 + 1) + 1;

                MySensor sensor = new MySensor(rnd_temp, rnd_humid, rnd_activity, i);
                publishProgress(sensor);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

           my_progressbar.setVisibility(View.GONE);

        }

        @Override
        protected void onPreExecute() {

            my_progressbar.setVisibility(View.VISIBLE);
            my_progressbar.setProgress(0);
            txt_output.setText("");

            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(MySensor... param) {


            int temp = param[0].m_temperature;
            int humid = param[0].m_humidity;
            int activity = param[0].m_activity;
            int count = param[0].m_count;


            txt_temp.setText(" "+temp+"F");

            txt_humid.setText(" "+humid+"%");

            txt_activity.setText(" "+activity);

            String reading_value = no_of_reading.getText().toString();
            int total_reading = Integer.parseInt(reading_value);




            String old_output = txt_output.getText().toString();
            String new_output = "Output "+ count +":"+"\n"+"Temperature: "+temp+"F"+"\n"+ "Humidity :"+humid+"%"+"\n"+ "Activity: "+activity;

            txt_output.setText(old_output +"\n" +new_output +"\n");

            my_progressbar.setProgress(count*100/total_reading);



        }
    }

    public void finishActivity(View v) {
        MainActivity.this.finish();
        super.onStop();
    }
}
