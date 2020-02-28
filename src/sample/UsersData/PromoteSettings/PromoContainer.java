package sample.UsersData.PromoteSettings;

import java.io.Serializable;
import java.util.Date;

public class PromoContainer implements Serializable {
    private VProme_OneTime Ot_promo;
    private VProme_ManyTimes Mt_promo;

    String [][] F = {{"️"},{""},{""},{""},{""},{""}};
    String [][] S = {{" ️️"},{""},{""},{""},{""},{"חזרה"}};
    String [][] Df = {{""},{""},{""},{" "},{""},{""}};
    String [][] Ds = {{""},{" "},{"-"},{" "},{""},{""}};
    String  exTime_format = "--:--";
    String  exDate_format = "year-month-day";


    public VProme_OneTime getOt_promo() {
        return Ot_promo;
    }

    public VProme_ManyTimes getMt_promo() {
        return Mt_promo;
    }

    public PromoContainer(){
        Ot_promo = new VProme_OneTime();
    }
    public PromoContainer(PromoContainer promoContainer){
        if(promoContainer.type_is_Mt_promo()){
            this.Mt_promo = promoContainer.getMt_promo();
        } else if(promoContainer.type_is_Ot_promo()){
            this.Ot_promo = promoContainer.getOt_promo();
        }
    }

    public boolean type_is_Ot_promo(){
        if(Ot_promo != null && Mt_promo == null ){
            return true;
        }else {
            return false;
        }
    }

    public boolean type_is_Mt_promo(){
        if(Ot_promo == null && Mt_promo != null ){
            return true;
        }else {
            return false;
        }
    }

    public Date getOtFullDate(){
        return Ot_promo.FullTime();
    }

    public String getOt_promoTime(){
       return Ot_promo.getTime();
    }

    public String getOt_promoDate(){
        return Ot_promo.getDate();
    }

    public String getMt_promoTime(){
        return Mt_promo.getTime();
    }


    public int getMt_promoLoops(){
        return Mt_promo.getLoops();
    }

    public boolean IsMtHaveLoops(){
        return Mt_promo.isHaveLoopsNum();
    }

    public boolean IsMtHaveTime(){
       return Mt_promo.isHaveTime();
}

    //-----------------------------------------------------// Сеттеры -------------------------------------------------------------------------------------------------//

    public void Set_Mt_promo(){
        Ot_promo = null;
        if(Mt_promo == null){
        Mt_promo = new VProme_ManyTimes();
        }
    }

    public void Set_Ot_promo(){
        Mt_promo = null;
        if(Ot_promo == null){
        Ot_promo = new VProme_OneTime();
        }
    }

    //-----------------------------------------------------// Сеттеры -------------------------------------------------------------------------------------------------//

    public void setOt_promoTime(String s){
        if(Ot_promo != null){
            Ot_promo.setTime(s);
        }
    }

    public void setOt_promoDate(String s){
        if(Ot_promo != null){
            Ot_promo.setDate(s);

        }
    }

    public void setMt_promoTime(String s){
        if(Mt_promo != null){
            Mt_promo.setTime(s);
        }
    }

    public void setMt_promoLoop(String s){
        if(Mt_promo != null){
            Mt_promo.setLoops(s);
        }
    }
    //-----------------------------------------------------// Галочки для клавы -------------------------------------------------------------------------------------------------//
    public String[][] Get_TextKeybordMas(int pos){
        int position = pos;
        String [] [] result = F;
        if (Ot_promo != null && Mt_promo == null){
            if(Ot_promo.isHaveTime() && Ot_promo.isHaveDate()){
                //Стоят Дата и Время
                result = F;
                result[0][0] = Ot_promo.getTime();
                result[1][0] = Ot_promo.getDate();
            }else if(Ot_promo.isHaveTime() && !Ot_promo.isHaveDate()){
                //Стоит только время
                result = F;
                result[0][0] = Ot_promo.getTime();
                switch (position){
                    case 1106:{result[1][0] = exDate_format;  }break;

                }
            } else if (!Ot_promo.isHaveTime() && Ot_promo.isHaveDate()){
                //Стоит только дата
                result = F;
                result[1][0] = Ot_promo.getDate();
                switch (position){
                    case 1105:{result[0][0] = exTime_format;}break;
                }
            }else if (!Ot_promo.isHaveTime() && !Ot_promo.isHaveDate()){
                result = F;
                switch (position){
                    case 1105:{result[0][0] = exTime_format;}break;
                    case 1106:{result[1][0] = exDate_format;}break;
                }
            }

        }else if(Ot_promo == null && Mt_promo != null){
             if(Mt_promo.isHaveTime() && Mt_promo.isHaveLoopsNum()){
                 result = S;
                 result[0][0] = Mt_promo.getTime();
                 result[1][0] = String.valueOf(Mt_promo.getLoops());
             }else if(Mt_promo.isHaveTime() && !Mt_promo.isHaveLoopsNum()){
                 result = S;
                 result[0][0] = Mt_promo.getTime();
                 switch (position){
                     case 1109:{result[1][0] = "⏏️";}break;
                 }
             }else if(!Mt_promo.isHaveTime() && Mt_promo.isHaveLoopsNum()){
                 result = S;
                 result[1][0] = String.valueOf(Mt_promo.getLoops());
                 switch (position){
                     case 1105:{result[0][0] = exTime_format;}break;
                 }
             }else if(!Mt_promo.isHaveTime() && !Mt_promo.isHaveLoopsNum()){
                 result = S;
                 switch (position){
                     case 1105:{result[0][0] = exTime_format;}break;
                     case 1109:{result[1][0] = "⏏️";}break;
                 }
            }


        }
        return result;
    }

    public String[][] Get_DataKeybordMas(){
        String [] [] result = Df;
        if (Ot_promo != null && Mt_promo == null){
            result = Df;
        }else if(Ot_promo == null && Mt_promo != null){
            result = Ds;
        }
        return result;
    }

}




