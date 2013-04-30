package com.mfedarko.finalgrade;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
 
    private EditText q1;
    private EditText q2;
    private EditText q3;
    private EditText q4;
    private EditText midterm;
    private EditText fin;
        
    private TextView results;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        q1 = (EditText) findViewById(R.id.q1EnterText);
        q2 = (EditText) findViewById(R.id.q2EnterText);
        q3 = (EditText) findViewById(R.id.q3EnterText);
        q4 = (EditText) findViewById(R.id.q4EnterText);
        midterm = (EditText) findViewById(R.id.midtermEnterText);
        fin = (EditText) findViewById(R.id.finEnterText);
        
        results = (TextView) findViewById(R.id.resultsText);

        // Make the hyperlinks in the credits TextView clickable
        TextView credits = (TextView) findViewById(R.id.credits);
        credits.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    public void calculateFinalGrade(View view) {
        int i;
        int qualitypts = 0;
        boolean invalidInput = false;
        String finalLetterGrade;
        
        // Format quarter grades into an easily processed format (a list of all-lowercase strings)
        EditText[] quarters = {q1, q2, q3, q4};
        List<String> quarterGrades = new ArrayList<String>();
        
        for(i = 0; i <= 3; i++) {
                quarterGrades.add(quarters[i].getText().toString().toLowerCase());
        }
        
        // Next, add points to the total quality point count for each quarter.
        for(i = 0; i <= 3; i++) {
                String g = quarterGrades.get(i);
                if(g.equals("a")) {
                        qualitypts += 8;
                }
                else if(g.equals("b")) {
                        qualitypts += 6;
                }
                else if(g.equals("c")) {
                        qualitypts += 4;
                }
                else if(g.equals("d")) {
                        qualitypts += 2;
                }
                else {
                        // An E = +/- 0 quality points, so we just ignore it.
                        if(!(g.equals("e"))) {
                                results.setText("Invalid grade entered. Please enter in one letter grade for each section.");
                                invalidInput = true;
                                break;
                        }
                }
        }
        if(!invalidInput) {
                // Done adding points from quarters. Now add points from exams.
                String mg = midterm.getText().toString().toLowerCase();
                String fg = fin.getText().toString().toLowerCase();
                String[] exams = {mg, fg};
                
                for(i = 0; i < 2; i++) {
                        if(exams[i].equals("a")) {
                                qualitypts += 4;
                        }
                        else if(exams[i].equals("b")) {
                                qualitypts += 3;
                        }
                        else if(exams[i].equals("c")) {
                                qualitypts += 2;
                        }
                        else if(exams[i].equals("d")) {
                                qualitypts += 1;
                        }
                        else {
                                if(!(exams[i].equals("e"))) {
                                        results.setText("Invalid grade entered. Please enter in one letter grade for each section.");
                                        invalidInput = true;
                                        break;
                                }
                        }
                }
                if(!invalidInput) {
                        if(qualitypts >= 35) {
                                finalLetterGrade = "A";
                        }
                        else if(qualitypts >= 30) {
                                finalLetterGrade = "B";
                        }
                        else if(qualitypts >= 25) {
                                finalLetterGrade = "C";
                        }
                        else if(qualitypts >= 20) {
                                finalLetterGrade = "D";
                        }
                        else {
                                finalLetterGrade = "E";
                        }
                        results.setText(String.format("Final Grade: %d quality points / 40 possible = %s", qualitypts, finalLetterGrade));
                }
        }
    }
}
