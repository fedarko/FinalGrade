/* Copyright 2013 Marcus Fedarko
 * Contact Email: marcus.fedarko@gmail.com
 * 
 * This file is part of FinalGrade.
 * 
 *     FinalGrade is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  FinalGrade is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FinalGrade.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.mfedarko.finalgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        double qualitypts = 0;
        double exam_avg = 0;
        boolean invalidInput = false;
        String finalLetterGrade;
        
        // Format quarter grades into an easily processed format (a list of all-lowercase strings)
        EditText[] quarters = {q1, q2, q3, q4};
        List<String> quarterGrades = new ArrayList<String>();
        
        for(i = 0; i <= 3; i++) {
                quarterGrades.add(quarters[i].getText().toString().toLowerCase(Locale.getDefault()));
        }
        
        // Next, add points to the total quality point count for each quarter.
        for(i = 0; i <= 3; i++) {
                String g = quarterGrades.get(i);
                if(g.equals("a")) {
                        qualitypts += 4;
                }
                else if(g.equals("b")) {
                        qualitypts += 3;
                }
                else if(g.equals("c")) {
                        qualitypts += 2;
                }
                else if(g.equals("d")) {
                        qualitypts += 1;
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
                String mg = midterm.getText().toString().toLowerCase(Locale.getDefault());
                String fg = fin.getText().toString().toLowerCase(Locale.getDefault());
                String[] exams = {mg, fg};
                
                for(i = 0; i < 2; i++) {
                        if(exams[i].equals("a")) {
                                exam_avg += 4;
                        }
                        else if(exams[i].equals("b")) {
                                exam_avg += 3;
                        }
                        else if(exams[i].equals("c")) {
                                exam_avg += 2;
                        }
                        else if(exams[i].equals("d")) {
                                exam_avg += 1;
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
                		exam_avg /= 2.0;
                		qualitypts += exam_avg;
                		
                		qualitypts /= 5.0;
                        if(qualitypts >= 3.5) {
                                finalLetterGrade = "A";
                        }
                        else if(qualitypts >= 2.5) {
                                finalLetterGrade = "B";
                        }
                        else if(qualitypts >= 1.5) {
                                finalLetterGrade = "C";
                        }
                        else if(qualitypts >= 0.75) {
                                finalLetterGrade = "D";
                        }
                        else {
                                finalLetterGrade = "E";
                        }
                        results.setText(String.format("Final Grade: %.2f quality points / 4.00 possible = %s", qualitypts, finalLetterGrade));
                }
        }
    }
}
