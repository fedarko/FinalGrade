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
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
 
    private EditText q1;
    private EditText q2;
    private EditText q3;
    private EditText q4;
    private EditText midterm;
    private EditText fin;
        
    private TextView results;
    private TextView midtermDesc;
    private TextView q3Desc;
    private TextView q4Desc;
    private TextView finDesc;
    
    private boolean year_long = true;
    
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
        
        midtermDesc = (TextView) findViewById(R.id.midterm);
        q3Desc = (TextView) findViewById(R.id.q3);
        q4Desc = (TextView) findViewById(R.id.q4);
        finDesc = (TextView) findViewById(R.id.fin);
        results = (TextView) findViewById(R.id.resultsText);

        RadioGroup courseTypes = (RadioGroup) findViewById(R.id.courseLengthRadioButtons);
        OnCheckedChangeListener switchCourseLengths = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// Switch between semester and year-long course lengths
				
				if (checkedId == R.id.semesterRadioButton) {
					q3.setVisibility(View.GONE);
					q4.setVisibility(View.GONE);
					fin.setVisibility(View.GONE);
					q3Desc.setVisibility(View.GONE);
					q4Desc.setVisibility(View.GONE);
					finDesc.setVisibility(View.GONE);
					midtermDesc.setText("Final Exam");
					year_long = false;
				}
				else if (checkedId == R.id.yearRadioButton) {
					q3.setVisibility(View.VISIBLE);
					q4.setVisibility(View.VISIBLE);
					fin.setVisibility(View.VISIBLE);
					q3Desc.setVisibility(View.VISIBLE);
					q4Desc.setVisibility(View.VISIBLE);
					finDesc.setVisibility(View.VISIBLE);
					midtermDesc.setText("Midterm Exam");
					year_long = true;
				}
			}
        	
        };
        courseTypes.setOnCheckedChangeListener(switchCourseLengths);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }    

    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	
    		case R.id.menu_about:
    			Intent i = new Intent(getApplicationContext(), AboutActivity.class);
    			startActivity(i);
    	    	return true;  	
    	    	
    	    default:
    	    	return super.onOptionsItemSelected(item);
    	}    	
    }    

    public void genErrorMsg() {
    	// Shows the user an error message due to invalid input.
    	// If necessary, can be expanded later. (Extensibility woo!)
    	
    	results.setText("Invalid grade entered. Please enter in one letter grade for each section.");
    }

    public char qualityPointsToFinalGrade(double qualitypts) {
	    if(qualitypts >= 3.5) {
            return 'A';
	    }
	    else if(qualitypts >= 2.5) {
            return 'B';
	    }
	    else if(qualitypts >= 1.5) {
            return 'C';
	    }
	    else if(qualitypts >= 0.75) {
            return 'D';
	    }
	    else {
            return 'E';
	    }
    }
    
    public int getQualityPointValue(String grade) {
    	/* Returns the amount of quality points a grade is worth.
    	 * A=4, B=3, C=2, D=1, E=0
    	 * Ignores case. If input is invalid, returns -1.
    	 */
    	if(grade.equals("a")) {
            return 4;
        }
        else if(grade.equals("b")) {
            return 3;
        }
        else if(grade.equals("c")) {
            return 2;
        }
        else if(grade.equals("d")) {
            return 1;
        }
        else if(grade.equals("e")) {
            return 0;
        }
        else {
        	return -1;
        }
    }
    
    public void calculateFinalGrade(View view) {
    	if (year_long)
    		calculateYearLongFG();
    	else
    		calculateSemesterFG();
    }
    
    public void calculateSemesterFG() {
    	/* According to HCPSS:
    	 * "Final grades for semester courses will be calculated in the following manner:
    	 * 
    	 * Add the quality point numerical equivalents for each marking period (A=4, B=3,
    	 * C=2, D=1, E=0) and multiply by two. Add the quality point value of the exam grade.
    	 * Divide the combined total by five."
    	 */
    	double qualitypts = 0;
    	char finalLetterGrade;
    	String q1G, q2G, examG;
    	int v;
    	
    	q1G = q1.getText().toString().toLowerCase(Locale.getDefault());
    	q2G = q2.getText().toString().toLowerCase(Locale.getDefault());
    	examG = midterm.getText().toString().toLowerCase(Locale.getDefault());
    	
    	String[] quarterGrades = {q1G, q2G};
    	
    	for (String g: quarterGrades) {
    		v = getQualityPointValue(g);
    		if (v != -1) {
    			qualitypts += v;
    		}
    		else {
    			genErrorMsg();
    			return;
    		}
    	}
    	// Quarter grades have been processed and validated
    	qualitypts *= 2;
    	// Check and add exam grade
    	v = getQualityPointValue(examG);
    	if (v != -1) {
    		qualitypts += v;
    	}
    	else {
    		genErrorMsg();
			return;    		
    	}
    	qualitypts /= 5;
    	finalLetterGrade = qualityPointsToFinalGrade(qualitypts);
        results.setText(String.format("Final Grade: %.2f quality points / 4.00 possible = %c", qualitypts, finalLetterGrade));
    }
    
    public void calculateYearLongFG() {
    	/* According to HCPSS:
    	 * "Final grades for year-long courses will be calculated in the following manner:
    	 * 
    	 * Add the quality point numerical equivalents of each marking period grade. (A=4,
    	 * B=3, C=2, D=1, E=0)
    	 * 
    	 * Average the quality point value of the exam grades.
    	 * Add this total to the marking period total. Divide the combined total by five."
    	 */
        int i, v;
        double qualitypts = 0;
        double exam_avg = 0;
        char finalLetterGrade;
        
        // Format quarter grades into an easily processed format (a list of all-lowercase strings)
        EditText[] quarters = {q1, q2, q3, q4};
        List<String> quarterGrades = new ArrayList<String>();
        
        for(i = 0; i <= 3; i++) {
                quarterGrades.add(quarters[i].getText().toString().toLowerCase(Locale.getDefault()));
        }
        
        // Next, add points to the total quality point count for each quarter.
    	for (String g: quarterGrades) {
    		v = getQualityPointValue(g);
    		if (v != -1) {
    			qualitypts += v;
    		}
    		else {
    			genErrorMsg();
    			return;
    		}
    	}
        // Done adding points from quarters. Now add points from exams.
        String mg = midterm.getText().toString().toLowerCase(Locale.getDefault());
        String fg = fin.getText().toString().toLowerCase(Locale.getDefault());
        String[] exams = {mg, fg};
        
    	for (String g: exams) {
    		v = getQualityPointValue(g);
    		if (v != -1) {
    			exam_avg += v;
    		}
    		else {
    			genErrorMsg();
    			return;
    		}
    	}
		// Done adding exam points.
		// Finalize quality points and output final grade.
		exam_avg /= 2.0;
		qualitypts += exam_avg;
		
		qualitypts /= 5.0;
        finalLetterGrade = qualityPointsToFinalGrade(qualitypts);
        results.setText(String.format("Final Grade: %.2f quality points / 4.00 possible = %c", qualitypts, finalLetterGrade));
    }
}
