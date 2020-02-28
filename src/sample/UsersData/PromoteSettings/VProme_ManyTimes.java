package sample.UsersData.PromoteSettings;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VProme_ManyTimes implements Serializable {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int Loops;
    private boolean haveTime = false;
    private boolean haveLoopsNum = false;
    private Calendar calendar;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public VProme_ManyTimes(){
        calendar = Calendar.getInstance();
    }


    public Date getTimeV(){
        if(haveTime){
            return calendar.getTime();
        }else {
            return null;
        }
    }
    public boolean setTime(String time){
        boolean Succes = false;
        try {
            calendar.setTime(timeFormat.parse(time));
            haveTime = true;
            Succes = true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Succes;
    }

    public boolean setLoops(String Loopnumber){
        try {
            Loops = Integer.parseInt(Loopnumber);
            haveLoopsNum = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return haveLoopsNum;
    }

    public boolean isHaveTime() {
        return haveTime;
    }

    public boolean isHaveLoopsNum() {
        return haveLoopsNum;
    }

    public String getTime(){
        String time = timeFormat.format(calendar.getTime());
        return time;
    }

    public int getLoops(){
        return Loops;
    }



    public void setDate(){
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            calendar.set(Calendar.YEAR, c.get(Calendar.YEAR) );
            calendar.set(Calendar.MONTH, c.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));

    }
    public void increaseDate(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(86400000 + new Date().getTime()));
        calendar.set(Calendar.YEAR, c.get(Calendar.YEAR) );
        calendar.set(Calendar.MONTH, c.get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));

    }
    public void degreaseLoops(){
        Loops--;

    }

}
