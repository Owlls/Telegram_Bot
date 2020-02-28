package sample.UsersData.PromoteSettings;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VProme_OneTime implements Serializable {
    private Calendar calendar;
    private boolean haveTime = false;
    private boolean haveDate = false;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("kk:mm");

    public VProme_OneTime(){
        calendar = Calendar.getInstance();
    }

    public boolean setTime(String time){
        boolean Succes = false;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(timeFormat.parse(time));
            calendar.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) );
            calendar.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
            haveTime = true;
            Succes = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Succes;
    }

    public boolean setDate(String date){
        boolean Succes = false;
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(dateFormat.parse(date));
            calendar.set(Calendar.YEAR, c.get(Calendar.YEAR) );
            calendar.set(Calendar.MONTH, c.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
            haveDate = true;
            Succes = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Succes;
    }

    public String getTime(){
        String time = timeFormat.format(calendar.getTime());
        return time;
    }

    public String getDate(){
        String date = dateFormat.format(calendar.getTime());
        return date;
    }

    public Date FullTime(){
        return calendar.getTime();
    }
    public boolean isHaveTime() {
        return haveTime;
    }

    public boolean isHaveDate() {
        return haveDate;
    }
}
