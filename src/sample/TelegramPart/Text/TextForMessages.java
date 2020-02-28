package sample.TelegramPart.Text;

import sample.UsersData.Reviews.ReView;
import sample.UsersData.Users.AUser;
import sample.UsersData.Users.Seller;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextForMessages {
    private static HashMap<Integer,String> messeges_for_users = new HashMap<Integer,String>();
    private static HashMap<Integer,String> Qmesseges_for_users = new HashMap<Integer,String>();
    public TextForMessages(){
        init();
    }
    public static void init(){
        messeges_for_users.put(1," \n " );



    }

}
