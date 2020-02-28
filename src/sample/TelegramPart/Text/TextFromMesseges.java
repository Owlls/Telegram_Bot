package sample.TelegramPart.Text;

import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFromMesseges {

    private static HashMap<String,Integer> messeges_from_users = new HashMap<String, Integer>();
    private static HashMap<String,Integer> querydata_from_users = new HashMap<String, Integer>();

    public TextFromMesseges(){
        messeges_from_users.put("/start",1);
        messeges_from_users.put("321",2);




        querydata_from_users.put("123",250);
        querydata_from_users.put("",1000);

    }

    public int userIdFromSellerMessage(String str){
        String [] mes = str.split("/");
        int result = Integer.parseInt(mes[1]);
        return result;
    }
    public int ComandFromSellerMessage(String str){
        String [] mes = str.split("/");
        String [] exam = {"GIVE201","BREAK201","GIVE202","BREAK202"};
        int Count = 0;
        for(String s: exam) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(mes[0]);
            if(matcher.find()){
                return Count;
            }
            Count++;
        }
        return -1;
    }

    public  ArrayList<String> getNewStrings(int value){ //Получает сообщение по номеру
        ArrayList<String> newStr = new ArrayList<String>();
        for(Map.Entry entry : messeges_from_users.entrySet()){
            if(entry.getValue().equals(value)){
                newStr.add((String)entry.getKey());
            }
        }
        return newStr;
    }

    public int pos_in_query(String mesage){
       if(querydata_from_users.containsKey(mesage)){
           return querydata_from_users.get(mesage);
       }else {
           return -1;
       }
    }

    public int pos_in_dialog(String mesage){ //Получает нужный номер сообщение
        if(messeges_from_users.containsKey(mesage)){
            return messeges_from_users.get(mesage);
        }else{
            return -1;}
    }
}
