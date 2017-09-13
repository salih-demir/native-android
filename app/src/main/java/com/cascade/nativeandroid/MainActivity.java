package com.cascade.nativeandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private TextView textViewJavaResults;
    private TextView textViewCResults;
    private TextView textViewSeekBarDescription;

    private int forLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native int sumNumbersInC(int length);

    /**
     * A usual Java method
     */
    private int sumNumbersInJava(int length) {
        int sum = 0;
        for (int i = 0; i < length; ++i)
            sum++;
        return sum;
    }

    private void initializeViews() {
        textViewSeekBarDescription = findViewById(R.id.tv_seek_bar_description);
        textViewJavaResults = findViewById(R.id.tv_Java_results);
        textViewCResults = findViewById(R.id.tv_C_results);

        SeekBar seekBarForLength = findViewById(R.id.sb_for_length);
        seekBarForLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                forLength = i;
                textViewSeekBarDescription.setText("Select for loop length (" + String.valueOf(i) + ")");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarForLength.setProgress(10000);

        findViewById(R.id.b_calculate_sums).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateSums();
            }
        });
    }

    private void calculateSums() {
        long startTimeJava = System.nanoTime();
        int sumCalculatedByJava = sumNumbersInJava(forLength);
        long endTimeJava = System.nanoTime();
        String executeTimeJava = String.valueOf(TimeUnit.MILLISECONDS.convert(endTimeJava - startTimeJava, TimeUnit.NANOSECONDS)) + " ms";

        long startTimeC = System.nanoTime();
        int sumCalculatedByC = sumNumbersInC(forLength);
        long endTimeC = System.nanoTime();
        String executeTimeC = String.valueOf(TimeUnit.MILLISECONDS.convert(endTimeC - startTimeC, TimeUnit.NANOSECONDS)) + " ms";

        String JavaResults = "Java Results\n"
                + "for loop summation: " + String.valueOf(sumCalculatedByJava) + "\n"
                + "execute time: " + executeTimeJava;

        String CResults = "C Results\n"
                + "for loop summation: " + String.valueOf(sumCalculatedByC) + "\n"
                + "execute time: " + executeTimeC;

        textViewJavaResults.setText(JavaResults);
        textViewCResults.setText(CResults);
    }
}