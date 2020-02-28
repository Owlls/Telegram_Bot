package sample.UsersData.Users;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import sample.GuiPart.WorkClass.PosList;
import sample.TelegramPart.Finder.Finder;
import sample.UsersData.UsersManager;

import javax.print.attribute.SetOfIntegerSyntax;
import java.io.Serializable;
import java.sql.Array;
import java.sql.Date;
import java.util.*;

public class AUser implements Serializable {



    private transient int back_Counter = 2;
    private transient boolean back_Count = false;
    private int userId;
    private int idMesToSeller;
    private String personalNum;
    private String first_name;
    private String last_name;
    private String Username;
    private transient Finder finderObj;
    private boolean isSeller = false;
    private transient int ReqToBot = 0; // Обращения к Боту
    private transient int ReqToSellers = 0; // Обращения к Продавцам
    private int Succes_Deals = 0; // Успешные сделки
    private String Protect_Points; // Количество очков доверия, которые продвавец, может раздавать, псоле покупки
    private StringBuilder Comlains_fromSeller; // Жалобы - все жалобы в одной строке
    private int Comlains_number = 0;// Количество жалоб на пользователя
    private transient String date;
    protected transient PosList<Integer> position_in_dialog = new <Integer>PosList();
    protected transient PosList<Integer> position_in_quary = new <Integer>PosList();
    protected transient PosList<Integer> Qmessages = new PosList<Integer>();
    //protected HashMap<>

    //----------------------- Это предоставляет информацию о истории покупок пользователя, для продавца

    public int getReqToSellers() {
        return ReqToSellers;
    }

    public int getSucces_Deals() {
        return Succes_Deals;
    }

    public String getProtect_Points() {
        return Protect_Points;
    }

    public StringBuilder getComlains_fromSeller() {
        return Comlains_fromSeller;
    }

    public int getComlains_number() {
        return Comlains_number;
    }
    //----------------------


    public int getReqToBot() {
        return ReqToBot;
    }

    public void setReqToBot(int reqToBot) {
        ReqToBot = reqToBot;
    }

    public void setReqToSellers(int reqToSellers) {
        ReqToSellers = reqToSellers;
    }

    public void setSucces_Deals(int succes_Deals) {
        Succes_Deals = succes_Deals;
    }

    public void setProtect_Points(String protect_Points) {
        Protect_Points = protect_Points;
    }

    public void setComlains_fromSeller(StringBuilder comlains_fromSeller) {
        Comlains_fromSeller = comlains_fromSeller;
    }

    public void setComlains_number(int comlains_number) {
        Comlains_number = comlains_number;
    }

    //---
    public void adDReqToBot(int reqToBot) {
        ReqToBot = ReqToBot + reqToBot;
    }

    public void adDReqToSellers(int reqToSellers) {
        ReqToSellers = ReqToSellers + reqToSellers;
    }

    public void adDSucces_Deals(int succes_Deals) {
        Succes_Deals = Succes_Deals + succes_Deals;
    }

    public void adDProtect_Points(String protect_Points) {
        Protect_Points = Protect_Points + "\n" + protect_Points;
    }

    public void adDComlains_fromSeller(String comlains_fromSeller) {
        Comlains_fromSeller.append("\n" +comlains_fromSeller);
    }

    public void adDComlains_number(int comlains_number) {
        Comlains_number = Comlains_number + comlains_number;
    }

    ///---------------------------/////


    protected AUser(AUser aUser){           //Конструктор на случай, если пользователь уже существует и загружается из базы данных
        this.userId = aUser.getUserId();
        this.first_name = aUser.getFirst_name();
        this.last_name = aUser.getLast_name();
        this.Username = aUser.getUsername();
        this.isSeller = true;
        this.ReqToBot = aUser.ReqToBot;
        this.Succes_Deals = aUser.Succes_Deals;
        this.ReqToSellers = aUser.ReqToSellers;
        this.Protect_Points = aUser.Protect_Points;
        this.Comlains_fromSeller = aUser.Comlains_fromSeller;
        this.Comlains_number = aUser.Comlains_number;
        this.date = aUser.date;
        this.position_in_dialog = aUser.position_in_dialog;
        this.position_in_quary = aUser.position_in_quary;
        this.Qmessages = aUser.Qmessages;
        this.personalNum = aUser.personalNum;
    }

    public AUser(){

    }

    public AUser(User user){  //Конструктор на случай, если пользователь новенький
        position_in_dialog.add(1);
        this.first_name = user.getFirstName();
        this.userId = user.getId();
        if( user.getUserName() != null) {
            this.Username = user.getUserName();
        }
        if( user.getLastName() != null) {
            this.last_name = user.getLastName();
        }
    }

    public void createFinder(UsersManager manager){
        finderObj = new Finder(manager);
    }
    public Finder getFinder(){
        return finderObj;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPosition_in_dialog() {
        if(position_in_dialog.size() >= 1) {
            int position = position_in_dialog.stream().skip(position_in_dialog.size() - 1).findFirst().get();
            return position;
        }
            return 0;
    }
    public int getPreposition_in_dialog() throws java.lang.IllegalArgumentException {
        if (position_in_dialog.size() >= 2){
        int position  = position_in_dialog.stream().skip(position_in_dialog.size() - back_Counter).findFirst().get();
        back_Count = true;
        back_Counter++;
        return position;
        }else if(position_in_dialog.size() == 1){
            return position_in_dialog.stream().skip(0).findFirst().get();
        }
        return 0;
    }
    public void setPosition_in_dialog(int position) {
        position_in_dialog.add(position);
        if(back_Counter > 2 && back_Count == false){
            back_Counter = 2;
        }
        if(back_Count == true){
            back_Count = false;
        }
    }

    public void clear_position(){
        position_in_dialog.clear();
    }
    public int getIdMesToSeller() {
        return idMesToSeller;
    }

    public void setIdMesToSeller(int idMesToSeller) {
        this.idMesToSeller = idMesToSeller;
    }

    public int getQPosition_in_dialog() {
        if(position_in_quary.size() >= 1) {
            int position = position_in_quary.stream().skip(position_in_quary.size() - 1).findFirst().get();
            return position;
        }
        return 0;
    }

    public int getQPreposition_in_dialog() throws java.lang.IllegalArgumentException {
        if(position_in_quary.size() >= 2) {
            int position = position_in_quary.stream().skip(position_in_quary.size() - back_Counter).findFirst().get();
            back_Count = true;
            back_Counter++;
            return position;
        }else if(position_in_quary.size() == 1 || (position_in_quary.size() - back_Counter == 1)){
            return getPosition_in_dialog();
        }
            return 0;
    }

    public void setQPosition_in_dialog(int Qposition) {
       position_in_quary.add(Qposition);
       if (back_Counter > 2 && back_Count == false) {
           back_Counter = 2;
       }
       if (back_Count == true) {
           back_Count = false;
       }
    }

    public void setQPosition_in_dialog_(int Qposition) {
        position_in_quary.add(Qposition);

    }

    public int getPreMessage(){
        int id = Qmessages.stream().skip(Qmessages.size() - 1).findFirst().get();
        return id;
    }

    public void Add_Qmessage(int message){
        Qmessages.add(message);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public boolean getIsSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getUsername() {
        return Username;
    }


    public void setPersonalNum(String personalNum) {
        this.personalNum = personalNum;
    }
    public String getPersonalNum() {
        return personalNum;
    }
}
