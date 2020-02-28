package sample.TelegramPart.Text;

import com.mysql.fabric.xmlrpc.base.Array;
import org.checkerframework.checker.units.qual.A;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.UsersData.Reviews.ReView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class TextForKeybors {

    private HashMap<Integer, KeyBoardData> data = new HashMap<Integer, KeyBoardData>();
    ArrayList <String> price = new ArrayList<String>();
    ArrayList <String> location = new ArrayList<String>();


     String [][] pos_1Text = {{""},{""}};
     int [] pos_1Num = {1,1};

     String [][] pos_2Text = {{"",""},{""}};
     int [] pos_2Num = {2,1};


    String [][] pos_5000Text = {{""},{""},{""},{"uD83D\uDDE8"}};
    String [] [] pos_5000Data ={{""},{""},{null},{null}};
    String [][] pos_5000_URLData = {{null},{null},{""},{""}};
    int [] pos_5000Num = {1,1,1,1};

    String [][] pos_10Text = {{""},{""},{""}};
    int [] pos_10Num = {1,1,1};
    String [][] pos_13Text = {{""},{""},{""}};
    int [] pos_13Num = {1,1,1};





    public TextForKeybors(){
        location.add("אזור פעילות");
        location.add("+");
        price.add("מהיר");
        price.add("+");
        data.put(1,new KeyBoardData(pos_1Text,pos_1Num));
        data.put(1,new KeyBoardData(pos_5000Text,pos_5000Num,pos_5000Data,pos_5000_URLData));


    }



    public KeyBoardData getKeyboard_preReview(int pos, int numReviews,int sellerId){
        pos ++;
        String [] Fist_text_row = {"0","⏮","⏪",String.valueOf(pos),"⏩","⏭",String.valueOf(numReviews)};
        String [] Fist_Data_row = {"s6061","s6062","s6063","sss","s6064","s6065","s6066"};
        String [] Fifth_text_row = {"חזרה"};
        String [] Fifth_Data_row = {"s6007"};
        int [] num = {7,1};
        String [][] Text_result = {Fist_text_row,Fifth_text_row};
        String [][] Data_result = {Fist_Data_row, Fifth_Data_row};
        KeyBoardData data = new KeyBoardData(Text_result,num,Data_result);
        return data;
    }

    public KeyBoardData getKeyboard_preView(int pos,int numPosts,int sellerId){
        pos ++;
        String [] Fist_text_row = {"0","⏮","⏪",String.valueOf(pos),"⏩","⏭",String.valueOf(numPosts)};
        String [] Fist_Data_row = {"s5061","s5062","s5063","sss","s5064","s5065","s5066"};
        String [] Second_Text_row = {""};
        String [] Second_Url_row = {String.format(": ", sellerId)};
        String [] Therd_Text_row = {""};
        String [] Therd_Url_row = {String.format(" ", sellerId)};
        String [] Four_Text_row = {""};
        String [] Four_Data_row = {""};
        String [] Fifth_text_row = {""};
        String [] Fifth_Data_row = {""};
        int [] num = {7,1,1,1,1};
        String [][] Text_result = {Fist_text_row,Second_Text_row,Therd_Text_row,Four_Text_row,Fifth_text_row};
        String [][] Data_result = {Fist_Data_row, {null}, {null}, Four_Data_row, Fifth_Data_row};
        String [][] Url_result = {{null},Second_Url_row,Therd_Url_row,{null},{null}};
        KeyBoardData data = new KeyBoardData(Text_result,num,Data_result,Url_result);
        return data;
    }

    public KeyBoardData  getKeyboard_Fist_filtres(int pos,boolean [] mas , String [] text){
        String [][] Btn_text = data.get(pos).getText_for_rows();
        int [][] Btn_data = {{5002},{5003},{5004},{5005},{5006},{5007},{5008},{5009}};
        int [] num = data.get(pos).getNumber_buttons();
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> data_result = new ArrayList<ArrayList<String>>();
        ArrayList<String> texton_savebtn =  new ArrayList<String>();//Предпоследняя кнопка
        texton_savebtn.add(Btn_text[6][0]);
        ArrayList<String> texton_exitbtn =  new ArrayList<String>();//Последняя кнопка
        texton_exitbtn.add(Btn_text[7][0]);
        ArrayList<String> datain_savebtn =  new ArrayList<String>();//Предпоследняя кнопка
        datain_savebtn.add("s" + Btn_data[6][0]);
        ArrayList<String> datain_exitbtn =  new ArrayList<String>();//Последняя кнопка
        datain_exitbtn.add("s" + Btn_data[7][0]);
        /* Сначала выставляем первые пять кнопок*/
        for(int i = 0; i < mas.length; i++){ //Перебираем массив, который содержит информацию о том, устанновлен лИ Филтр
            ArrayList<String> data_row = new ArrayList<>();
            ArrayList<String> row = new ArrayList<String>();
            if(mas[i]){// Если значения фильтра заданно - печатаем на кнопке его
                if(text[i] != null){//Проверяем если со строкой которая внесенна все ок
                    row.add(text[i]);
                    row.add("❌");
                    data_row.add("s" + Btn_data[i][0]);
                    data_row.add("s" + (Btn_data[i][0] + 10));
                } else {
                    row.add(Btn_text[i][0]);
                    row.add("❌");
                    data_row.add("s" + Btn_data[i][0]);
                    data_row.add("s" + (Btn_data[i][0] + 10));
                }
                num[i] = 2;//Должно быть две кнопки в одной строке
            }else {
                row.add(Btn_text[i][0]);
                data_row.add("s" + Btn_data[i][0]);
            }
            result.add(row);
            data_result.add(data_row);
        }
        data_result.add(datain_savebtn);
        data_result.add(datain_exitbtn);
        result.add(texton_savebtn);
        result.add(texton_exitbtn);
        KeyBoardData d  = new KeyBoardData(result,num,data_result);
        return d;
    }

    public KeyBoardData getKeyBoard_Second_Filtres(int pos, boolean [] mas, String [] text){
        String [][] Btn_text = data.get(pos).getText_for_rows();
        int [][] Btn_data = {{5022},{5023},{5024},{5025},{5026},{5027},{5028},{5029}};
        int [] num = data.get(pos).getNumber_buttons();
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> data_result = new ArrayList<ArrayList<String>>();
        ArrayList<String> texton_exitbtn =  new ArrayList<String>();//ПредПоследняя кнопка
        ArrayList<String> datain_exitbtn =  new ArrayList<String>();//ПредПоследняя кнопка
        texton_exitbtn.add(Btn_text[6][0]);
        datain_exitbtn.add("s" + Btn_data[6][0]);
        ArrayList<String> texton_savebtn =  new ArrayList<String>();//последняя кнопка
        ArrayList<String> datain_savebtn =  new ArrayList<String>();//последняя кнопка
        texton_savebtn.add(Btn_text[7][0]);
        datain_savebtn.add("s" + Btn_data[7][0]);
        for(int i = 0; i < mas.length; i++){
            ArrayList<String> data_row = new ArrayList<String>();
            ArrayList<String> row = new ArrayList<String>();
            if(mas[i]){
                if(text[i] != null){
                    row.add(text[i]);
                    row.add("❌");
                    data_row.add("s" + Btn_data[i][0]);
                    data_row.add("s" + (Btn_data[i][0] + 10));
                } else {
                    row.add(Btn_text[i][0]);
                    row.add("❌");
                    data_row.add("s" + Btn_data[i][0]);
                    data_row.add("s" + (Btn_data[i][0] + 10));
                }
                num[i] = 2;
            } else {
                row.add(Btn_text[i][0]);
                data_row.add("s" + Btn_data[i][0]);
            }
            result.add(row);
            data_result.add(data_row);
        }
        data_result.add(datain_exitbtn);
        data_result.add(datain_savebtn);
        result.add(texton_exitbtn);
        result.add(texton_savebtn);
        KeyBoardData d  = new KeyBoardData(result,num,data_result);
        return d;
    }
    /*
    public KeyBoardData getSimpleBackButton(){
        KeyBoardData keyBoardData = new KeyBoardData(,);
        return keyBoardData;
    }
    public KeyBoardData getSimpleFinishReView(){
        KeyBoardData keyBoardData = new KeyBoardData(FinishReView,FinishReViewN);
        return keyBoardData;
    }
    public KeyBoardData getChosenYN(){
        KeyBoardData keyBoardData = new KeyBoardData(pos_ChoseBoolText,pos_ChoseBoolNum);
        return keyBoardData;
    }
    public KeyBoardData getChosenNum(){
        KeyBoardData keyBoardData = new KeyBoardData(pos_ChoseNumText,pos_ChoseNumNum);
        return keyBoardData;
    }
    public KeyBoardData get_Byer213(){
        KeyBoardData keyboard = new KeyBoardData(AskReview_213_Text,AskReview_213_Num,AskReview_213_Data);
        return  keyboard;
    }
    /*
    public KeyBoardData get_Byer213(int userId, ReView review,int ClickOn){
        KeyBoardData keyboard = data.get(ClickOn);
        String [][] Text = keyboard.getText_for_rows();
        if(review.isDescription()){
            Text[0][0] = Text[0][0] + "✅";
        }
        if(review.isConformity()){
            Text[1][0] = Text[1][0]  + "✅";
        }
        if(review.isQ_P()){
            Text[2][0] = Text[2][0]  + "✅";
        }
        if(review.isQuality()){
            Text[3][0] = Text[3][0]  + "✅";
        }
        if(review.isRecomended()){
            Text[4][0] = Text[4][0]  + "✅";
        }
        return keyboard;
    }*/
    public KeyBoardData get_Seller511(ReView review,int ClickOn){
        KeyBoardData keyboard = data.get(ClickOn);
        String [][] Text = keyboard.getText_for_rows();
        if(review.isDealWasSucces()){
            Text[0][0] = Text[0][0] + "✅";
        }
        if(review.isAddProtectPoint()){
            Text[1][0] = Text[1][0]  + "✅";
        }
        if(review.isComlains()){
            Text[3][0] = Text[3][0]  + "✅";
        }
        return keyboard;
    }
    public KeyBoardData get_Seller201(int userId){
        String [][] AskReview_201_Text = {{"לתת זכות תגובה"},{"ביטול"}};
        String [][] AskReview_201_Data = {{"GIVE201" + String.valueOf( "/" + userId)},{"BREAK201" + "/" + userId}};
        int [] AskReview_201_Num = {1,1};
        KeyBoardData keyboard = new KeyBoardData(AskReview_201_Text,AskReview_201_Num,AskReview_201_Data);
        return keyboard;
    }
    public KeyBoardData get_Seller202(int userId){
        String [][] AskReview_202_Text = {{"לתת זכות תגובה"},{"העסקה לא התקיימה"}};
        String [][] AskReview_202_Data = {{"GIVE202" + String.valueOf("/" + userId)},{"BREAK202" + "/" + userId}};
        int [] AskReview_202_Num = {1,1};
        KeyBoardData keyboard = new KeyBoardData(AskReview_202_Text,AskReview_202_Num,AskReview_202_Data);
        return keyboard;
    }



    public  KeyBoardData get_keyboard_Data(int position){

        return data.get(position);
    }

    public KeyBoardData makeTimeMenuKeyBoard(String[][] text, String[][] data){
        String Text[][] = text;
        String Data[][] = data;
        int num [] = {1,1,1,1,1,1};
        KeyBoardData TMK = new KeyBoardData(Text, num, Data);
        return TMK;
    }

    public KeyBoardData makeInfoAboutPost(ArrayList<ArrayList<String>> text_for_buttons,ArrayList<ArrayList<String>> data_for_rows){
        int num[] = new int[text_for_buttons.size()];
        for(int i = 0; i < (num.length-1); i++){
            num[i] = 2;
        }
        num[num.length -1] = 1;
        num[num.length -2] = 1;
        KeyBoardData data = new KeyBoardData(text_for_buttons, num, data_for_rows);
        return data;
    }




    public KeyBoardData make_Text_KeyBoard(boolean [] mas,int[]info, int position){ //Сюда мы получаем масив-галочек и количество двух строчек
        int [] numbers;
        int numberButtons = (info[0] + 5);
        int num_PriceButtons = info[1];
        int num_LocButtons = info[2];
        int [] imt = new int[numberButtons];
        for (int i = 0; i < imt.length; i++){
            if(i >= 1 && i < num_LocButtons + num_PriceButtons + 1){
                imt[i] = 2;
            } else {
                imt[i] = 1;
            }
        }
        ArrayList<String> row = new ArrayList<String>();
        ArrayList<String> Newrow = new ArrayList<String>();
        ArrayList<String> data_row = new ArrayList<String>();
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> data_result = new ArrayList<ArrayList<String>>();
        String Text[][] = data.get(position).getText_for_rows(); //Это нужная нам клавиатура (с галочкой) или без
        String Data[][] = data.get(position).getData_for_ibuttons();
        for(int i = 0; i < Text.length; i++) {
            for (int j = 0; j < Text[i].length; j++) {
                row.add(Text[i][j]);
                data_row.add(Data[i][j]);

            }
            if(i == 1) {//Тот случай, когда i = тому номеру где находится кнопка "Цена"
                Newrow.addAll(row);
                for(int j = 0; j < num_PriceButtons; j++ ){
                    if (mas[(i+j)] == true) { //Проверяем, добавлять галочку или нет

                        Newrow.add(0,(Newrow.remove(0) + "✅"));
                        result.add(Newrow);
                        data_result.add(data_row);
                    } else { //Не добавляем
                        result.add(row);
                        data_result.add(data_row);
                    }
                }
                Newrow = new ArrayList<String>();
                row = new ArrayList<String>();
                data_row = new ArrayList<String>();
            } else if(i == 2){
                Newrow.addAll(row);
                for(int j = 0; j < num_LocButtons; j++ ){
                    if (mas[(i + j + (num_PriceButtons - 1))] == true) { //Проверяем, добавлять галочку или нет
                        Newrow.add(0,(Newrow.remove(0) + "✅"));
                        result.add(Newrow);
                        data_result.add(data_row);
                    } else { //Не добавляем
                        result.add(row);
                        data_result.add(data_row);
                    }
                }
                Newrow = new ArrayList<String>();
                row = new ArrayList<String>();
                data_row = new ArrayList<String>();
            } else if(i == 0){
                if (mas[(i)] == true) { //Проверяем, добавлять галочку или нет
                    row.add((row.remove(0) + "✅"));
                    result.add(row);
                    data_result.add(data_row);
                } else { //Не добавляем
                    result.add(row);
                    data_result.add(data_row);

                }
                row = new ArrayList<String>();
                data_row = new ArrayList<String>();
            }
            else if(i > 2){
                if (mas[(i + (num_PriceButtons + num_LocButtons - 2))] == true) { //Проверяем, добавлять галочку или нет
                    row.add((row.remove(0) + "✅"));
                    result.add(row);
                    data_result.add(data_row);
                } else { //Не добавляем
                    result.add(row);
                    data_result.add(data_row);

                }
                row = new ArrayList<String>();
                data_row = new ArrayList<String>();
            }
        }

        //numbers = imt.stream().mapToInt(i -> i).toArray();
        KeyBoardData data1 = new KeyBoardData(result, imt, data_result);
        return data1;
    }

    public KeyBoardData make_Menu_KeyBoard(boolean [] mas, int position){
        ArrayList<String> row = new ArrayList<String>();
        ArrayList<String> data_row = new ArrayList<String>();
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> data_result = new ArrayList<ArrayList<String>>();
        String Text[][] = data.get(position).getText_for_rows(); //Это нужная нам клавиатура (с галочкой) или без
        String Data[][] = data.get(position).getData_for_ibuttons();

        for(int i = 0; i < Text.length; i++){
           for(int j = 0; j < Text[i].length; j++) {
               row.add(Text[i][j]);
               data_row.add(Data[i][j]);
           }
           if(mas[i]){
               row.add((row.remove(0) + "✅"));
               result.add(row);
               data_result.add(data_row);
               row = new ArrayList<String>();
               data_row = new ArrayList<String>();
           } else {
               result.add(row);
               data_result.add(data_row);
               row = new ArrayList<String>();
               data_row = new ArrayList<String>();
           }

        }

        KeyBoardData data2 =  new KeyBoardData(result,data.get(position).getNumber_buttons(),data_result);
        return data2;
    }
}


