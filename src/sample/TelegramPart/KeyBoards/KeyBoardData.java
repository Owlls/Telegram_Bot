package sample.TelegramPart.KeyBoards;

import java.util.ArrayList;

public class KeyBoardData {


    private String [][] text_for_rows;
    private int [] number_buttons;
    private String [][] Urldata_for_ibuttons;
    private String [][] data_for_ibuttons;
    private String [][] switch_Data;

    public boolean IsHave_SwitchData(){
        if(switch_Data == null){
            return false;
        }else {
            return true;
        }
    }
    public boolean IsHave_URLData(){
        if(Urldata_for_ibuttons == null){
            return false;
        }else {
            return true;
        }
    }

    public String[][] getUrldata_for_ibuttons() {
        return Urldata_for_ibuttons;
    }

    public void setUrldata_for_ibuttons(String[][] urldata_for_ibuttons) {
        Urldata_for_ibuttons = urldata_for_ibuttons;
    }

    public String[][] getSwitch_Data() {
        return switch_Data;
    }

    public String[][] getText_for_rows() {
        return text_for_rows;
    }

    public int[] getNumber_buttons() {
        return number_buttons;
    }

    public String[][] getData_for_ibuttons() {
        return data_for_ibuttons;
    }


    public KeyBoardData(String [][] text_for_rows, int[]number_buttons){ //Коструктор 2-массива Только текст
        this.text_for_rows = text_for_rows;
        this.number_buttons = number_buttons;
    }

    public KeyBoardData(String [][] text_for_rows, int[]number_buttons, String [][] data){
        this.data_for_ibuttons = data;
        this.text_for_rows = text_for_rows;
        this.number_buttons = number_buttons;
    }//Коструктор 3-массива

    public KeyBoardData(ArrayList<ArrayList<String>>  text_for_row, int [] number_buttons, ArrayList<ArrayList<String>> data){
        this.data_for_ibuttons  = new String[data.size()][2];
        this.text_for_rows = new String[text_for_row.size()][2];
        this.number_buttons = number_buttons;
        //this.number_buttons = number_buttons;
        for(int i = 0; i < text_for_row.size(); i++){
            for (int j = 0; j < text_for_row.get(i).size(); j ++){
                text_for_rows[i][j] = text_for_row.get(i).get(j);
                data_for_ibuttons[i][j] = (data.get(i).get(j));
            }
        }
    } //Коструктор принимающий списки, а не массивы 3-параметра

    public KeyBoardData(String [][] text_for_rows, int[]number_buttons, String [][] data, String[][] URL_Data){//Коструктор 4-массива Текст,Дата,Ссылки, расп-кнопок
        this.data_for_ibuttons = data;
        this.text_for_rows = text_for_rows;
        this.number_buttons = number_buttons;
        this.Urldata_for_ibuttons = URL_Data;
    }
    public KeyBoardData(String [][] text_for_rows, int[]number_buttons, String [][] data, String[][] URL_Data,String[][] Swithc_Data){//Коструктор 5-массивов Текст,Вызов бота,Дата,Ссылки, расп-кнопок
        this.data_for_ibuttons = data;
        this.text_for_rows = text_for_rows;
        this.number_buttons = number_buttons;
        this.Urldata_for_ibuttons = URL_Data;
        this.switch_Data = Swithc_Data;
    }

}
