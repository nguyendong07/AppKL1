package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    static final int READ_BLOCK_SIZE = 100;
    private LineChart mChart;
    private Thread thread;
    private boolean plotData = true;

    EditText textmsg;
    private  static final  String TAG = "MainActivity";
    private SensorManager sensorManager;
    private Sensor sensor;
    private Sensor accelerometer;
    private static Object obj;
    private static double timeInMillis;
    ArrayList<Double> listString = new ArrayList<Double>();

    ArrayList<Double> XList = new ArrayList<Double>();
    ArrayList<Double> YList = new ArrayList<Double>();
    ArrayList<Double> ZList = new ArrayList<Double>();
    ArrayList<Double> TList = new ArrayList<Double>();

    double[] xArray;
    double[] yArray;
    double[] zArray;
    double[] tArray;

    TextView xValue;
    TextView yValue;
    TextView zValue;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        xValue = (TextView) findViewById(R.id.xValue);
//        yValue = (TextView) findViewById(R.id.yValue);
//        zValue = (TextView) findViewById(R.id.zValue);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        for(int i=0; i<sensors.size(); i++){
            Log.d(TAG, "onCreate: Sensor "+ i + ": " + sensors.get(i).toString());
        }

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.getDescription().setEnabled(true);
        mChart.getDescription().setText("Real Time Accelerometer");
        mChart.setTouchEnabled(false);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setPinchZoom(false);
        mChart.setBackgroundColor(Color.WHITE);
        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);
        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(20f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.setDrawBorders(false);

        feedMultiple();
//        try {
//             obj = readModel.serialize("mlp.model");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        button = findViewById(R.id.button2);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int i = 2;
//                xArray = new double[]{1.0, 2.0, 3.0, 4.0};
//                yArray = new double[]{1.0, 2.0, 3.0, 4.0};
//                zArray = new double[]{1.0, 2.0, 3.0, 4.0};
//                tArray = new double[]{1.0, 2.0, 3.0, 4.0};
//                listString = ComputedFeatures.getAllFeature(xArray,yArray,zArray,tArray,i);
//                System.out.println(xArray[1]);
//                After calculate feature and predict, clear all array list for the next predict
//                XList.clear();
//                YList.clear();
//                ZList.clear();
//                TList.clear();
//            }
//        });
        Log.d(TAG, "OnCreate : Register accelerometer Listener");
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(2f);
        set.setColor(Color.MAGENTA);
//        set.setHighlightEnabled(false);
//        set.setDrawValues(false);
//        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        return set;
    }

    private void addEntry(SensorEvent event) {

        LineData data = mChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), event.values[0] + 5), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();

            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(150);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());

        }
    }

    private void feedMultiple() {

        if (thread != null){
            thread.interrupt();
        }

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true){
                    plotData = true;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (thread != null) {
            thread.interrupt();
        }
        sensorManager.unregisterListener(this);

    }
    
    public double[] ArrLs2Arr(ArrayList<Double> ArrayList) {
           int n = ArrayList.size();
           double[] arr = new double[n];
           for (int i = 0; i < n; i++) {
               arr[i] = ArrayList.get(i);
           }
           return  arr;
    }

    public void setData(int count, int range) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//        xValue.setText("xValue: "+ sensorEvent.values[0]);
//        yValue.setText("yValue: "+ sensorEvent.values[1]);
//        zValue.setText("zValue: "+ sensorEvent.values[2]);
        if(plotData){
            addEntry(sensorEvent);
            plotData = false;
        }
        timeInMillis = System.currentTimeMillis() + (sensorEvent.timestamp - System.nanoTime()) / 1000000L;
        XList.add((double) sensorEvent.values[0]);
        YList.add((double) sensorEvent.values[1]);
        ZList.add((double) sensorEvent.values[2]);
        TList.add(timeInMillis);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(MainActivity.this);
        thread.interrupt();
        super.onDestroy();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // write text to file
    public void WriteBtn(View v) {
        // add-write text into file
        try {
            FileOutputStream fileout=openFileOutput("mytextfile.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(textmsg.getText().toString());
            outputWriter.close();

            //display file saved message
            Toast.makeText(getBaseContext(), "File saved successfully!",
                    Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public void ReadBtn(View v) {
        //reading text from file
        try {
            FileInputStream fileIn=openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            char[] inputBuffer= new char[READ_BLOCK_SIZE];
            String s="";
            int charRead;
            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            textmsg.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}