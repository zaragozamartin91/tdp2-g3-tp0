package com.tdp2.ghsz.tp0;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2.ghsz.tp0.util.DoubleRounder;

public class ForecastArrayAdapter extends ArrayAdapter<Forecast> {
    private final Context context;
    private final Forecast[] values;

    public ForecastArrayAdapter(Context context, Forecast[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView dateText = rowView.findViewById(R.id.date_label);
        TextView dayText = rowView.findViewById(R.id.day_label);
        TextView nightText = rowView.findViewById(R.id.night_label);
        ImageView dayIcon = rowView.findViewById(R.id.day_icon);
        ImageView nightIcon = rowView.findViewById(R.id.night_icon);

        Forecast forecast = values[position];

        switch (position) {
            case 0:
                dateText.setText("Hoy");
                break;
            case 1:
                dateText.setText("Mañana");
                break;
            default:
                org.joda.time.LocalDate localDate = org.joda.time.LocalDate.now().plusDays(position);
                String text = getNameOfDay(localDate.getDayOfWeek()) + ", "
                        + "  " + localDate.getDayOfMonth() + "/"
                        + localDate.getMonthOfYear();
                /* + "/" + localDate.getYear();*/
                dateText.setText(text);
        }

        double daytemp = DoubleRounder.round(forecast.getDay().temp, 1);
        dayText.setText(daytemp + "°C");
        double nighttemp = DoubleRounder.round(forecast.getNight().temp, 1);
        nightText.setText(nighttemp + "°C");
        // Change the icon for Windows and iPhone

        String dayWeather = "i" + forecast.getDay().weather.toLowerCase();
//        String dayWeather = "ic_wi_day_" + forecast.day.weather.toLowerCase();
        int dayIconId = context.getResources().getIdentifier(dayWeather, "drawable", context.getApplicationInfo().packageName);
        dayIconId = dayIconId == 0 ? R.drawable.ic_wi_day_sunny : dayIconId;
        dayIcon.setImageResource(dayIconId);

        String nightWeather = "i" + forecast.getNight().weather.toLowerCase();
//        String nightWeather = "ic_wi_moon_" + forecast.night.weather.toLowerCase();
        int nightIconId = context.getResources().getIdentifier(nightWeather, "drawable", context.getApplicationInfo().packageName);
        nightIconId = nightIconId == 0 ? R.drawable.ic_wi_moon_alt_new : nightIconId;
        nightIcon.setImageResource(nightIconId);


        return rowView;
    }
    private String getNameOfDay(int dayOfWeek){
        String[] days = new String[] { "Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado" };
        return days[dayOfWeek];
    }
}