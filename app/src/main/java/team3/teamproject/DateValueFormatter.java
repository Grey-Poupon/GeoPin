package team3.teamproject;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

//Controlls the date controller

public class DateValueFormatter implements IAxisValueFormatter{
    private ArrayList<String> dates;
    private ArrayList<Integer> indices;

    public DateValueFormatter() {
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return this.dates.get((int) value);
    }

    public String test() {
        return "TESTING TESTING TESTING";
    }
    public void setIndices(ArrayList<Integer> indices) {
        this.indices = indices;
    }
    public void setDates(ArrayList<String> dates) {
        this.dates = dates;
    }
}
