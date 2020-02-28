package sample.UsersData.Database;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import org.telegram.telegrambots.meta.api.objects.User;
import sample.GuiPart.Errors;
import sample.UsersData.Users.AUser;
import sample.UsersData.Users.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.UIManager.getString;

public class DataBaseHandler extends DataBaseW{
    /* Это класс должен представлять базу данных и удобную навигацию по ней */

    protected Connection connection;

    protected Connection sel_connection;

    protected Connection Info_connection;


    protected Statement connection_state;

    protected Statement sel_connection_state;

    protected Statement Info_connection_state;

    protected void closeDb(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(sel_connection != null){
            try {
                sel_connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(Info_connection != null){
            try {
                Info_connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(connection_state != null){
            try {
                connection_state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(sel_connection_state != null ){
            try {
                sel_connection_state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(Info_connection_state != null ){
            try {
                Info_connection_state.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    protected void closeConnection() throws SQLException{
        if(connection != null){
            connection.close();
        }
        if(sel_connection != null){
            sel_connection.close();
        }
        if(Info_connection != null){
            Info_connection.close();
        }

    }

    public void setCreditsToSeller(int userId, int value){
        String SQL = getStr_insertsCreditToSeller(userId,value);
        try {
            Connectdb().executeUpdate(SQL);
            closeDb();
        } catch (SQLException e) {
            System.out.println("Not work");
            e.printStackTrace();
        }
    }

    public void addComlaint(int userId,String comlaint){//Добавляем жалобы
        String sql = getStr_addComlaints(userId,comlaint);
        try {
            Connectdb().executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void increaseSuccesDealsNUm(int userId){//Увеличиваем количество успешных сделок у покупателя
        String sql = getStr_SetIncreaseSuccesDeals(userId);
        try {
            Connectdb().executeUpdate(sql);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void increaseComlaintNum(int userId){//Добавляем жалобы
        String sql = getStr_SetIncreaseComlaintsNum(userId);
        try {
            Connectdb().executeUpdate(sql);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void increaseReqToSeller(int userId){//Добавляем жалобы
        String sql = getStr_IncreaseReqToSeller(userId);
        try {
            Connectdb().executeUpdate(sql);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setWasLastTimeSeller(int userId,Date date){//Добавляем жалобы
        String sql = getStr_setLastTime(userId,date);
        try {
            Connectdb().executeUpdate(sql);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void ProtectedPoints(int userId,String username){//
        //*В этом методе я получаю все защитные юзернэймы пользователей
        // и если текущего нет - то вношу новые*/
        String SQL = getStr_selectProtectPoints(userId);
        StringBuilder strUsernames = new StringBuilder();
        try {
            strUsernames.append(Connectdb().executeQuery(SQL).getCursorName());//
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile(username);
        Matcher matcher = pattern.matcher(strUsernames);
        if(matcher.find()){
            //
        } else {
            strUsernames.append(" " + username + "\n");
            String sql = getStr_InsertUsernamesToUser(userId,strUsernames.toString());
            try {
                Connectdb().executeUpdate(sql);
                closeDb();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean IsReqToBotNull(int userId){
        boolean is = true;
        String str = findUser_allData_userId + userId + "`\";";
        try {
            ResultSet set = Connectdb().executeQuery(str);
            if(set.getInt("Requests_to_Bot") != 0){
                closeDb();
                return false;
            }
            closeDb();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return is;
    }

    public void insertPersonalSellerNumber(String pn,int sellerId){
        String SQL = getStr_setPersonalNumber(pn,sellerId);
        try {
            Connectdb().executeUpdate(SQL);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void BlockAuser(int userId){
        String SQL = getStr_BlockAUser(userId);
        try {
            Connectdb().executeUpdate(SQL);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DeleteAuser(int sellerId){
        String SQL = getStr_desist_seller(sellerId);
        String SQL2 = getStr_DeleteSeller(sellerId);
        try {
            Connectdb().executeUpdate(SQL);
            Connectdb().executeUpdate(SQL2);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void IncreaseReqToBot(int userId){
        String SQL = getStr_IncreaseReqToBot(userId);
        try {
            Connectdb().executeUpdate(SQL);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertNew_first_name(int id, String first_name) { //Вносим userId и first_name в базу данных

        //String result = first_name.replaceAll("\\p{Cntrl}", "");
        String result = first_name.replaceAll("\'", "");
        System.out.println("Fist NAME " + result);
        String insertTableSQL = getStr_insert_first_name(id,result);

        try {
            Connectdb().executeUpdate(insertTableSQL);
            closeDb();
            return true;
        } catch (SQLException e) {
            System.out.println("Not work");
            e.printStackTrace();
            Errors.AddError("14 User not inserting his Id and username to Db");
            return false;
        }
    }
    public boolean insertNew_UserId(int id) { //Вносим userId и first_name в базу данных
        String insertTableSQL = getStr_insert_userId(id);
        try {
            Connectdb().executeUpdate(insertTableSQL);
            closeDb();
            return true;
        } catch (SQLException e) {
            System.out.println("Not work");
            e.printStackTrace();
            Errors.AddError("15 User not inserting User first_name Id and username to Db");
            return false;
        }
    }

    public boolean insertNew_UserName(int id,String Username) { //Вносим Username
        String SQL = getStr_insert_UserName(id,Username);
        try {
            Connectdb().executeUpdate(SQL);
            closeDb();
            return true;
        } catch (SQLException e) {
            System.out.println("Not work");
            e.printStackTrace();
            return false;
        }
    }

    public void insertNew_LastName(int id,String last_name) { //Вносим Фамилию пользователя
        String SQL = getStr_insert_LastName(id,last_name);
        try {
            Connectdb().executeUpdate(SQL);
            closeDb();
        } catch (SQLException e) {
            System.out.println("Not work");
            e.printStackTrace();
        }
    }

    public AUser getUserData_with_userId(int userId){ //Ищем пользователя и получаем его данные по userId, и возвращает или AUser со всеми имеющимися данными, либо null
        AUser auser = new AUser();
        String Sql = findUser_allData_userId + userId;
        try{
            ResultSet rs = Connectdb().executeQuery(Sql);
            if(rs.next()){
                auser = setAUsers_params(rs,auser);
            } else {
                return null;
            }
            closeDb();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return auser;
    }//Возможно он не понадобится

    public boolean addPointsToSeller(int userId,int value){
        boolean res = false;
        String SQL = getStr_AddPointsToSeller(userId,value);
        try {
            Connectdb().executeUpdate(SQL);
            closeDb();
            res = true;
        } catch (SQLException e) {
            System.out.println("Not work");
            e.printStackTrace();
        }
        return res;
    }

    public boolean IsNewUser(int userId){
        String Sql = findUser_allData_userId + userId;
        try{
            ResultSet rs = Connectdb().executeQuery(Sql);
            if(rs.next()){
                closeDb();
               return false;
            }
            closeDb();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public void SetSellerInfo(Seller seller){  //Задаем пользователю определенные данные
        String SQL = getStr_SellerData(seller.getUserId());
        try{
            ResultSet set = Connectdb().executeQuery(SQL);
            set.next();
            if(set.getBoolean(2)){
                seller.setAgreement(set.getBoolean(2));
            }
            if(set.getInt(3) != 0){
                seller.setNum_ReqTo(set.getInt(3));
            }
            if(set.getDate(4) != null){
                seller.setTimeOfRegistration(set.getTimestamp(4));
            }
            if(set.getInt(5) != 0){
                seller.setPoints(set.getInt(5));
            }
            if(set.getInt(6) != 0){
                seller.setAllPoints(set.getInt(6));
            }
            if(set.getInt(7) != 0){
                seller.setCredit(set.getInt(7));
            }
            closeDb();
        } catch (SQLException e) {
            Errors.AddError("Cann`t to Connect DataBase with SellersInfo 102");
            System.out.println("Not work");
            e.printStackTrace();
        }
    }

    public ArrayList<AUser> getData(){ //Получаем данные из таблицы и записываем их в Арэй лист и возвращаем их
        AUser auser;
        ArrayList<AUser> list = new ArrayList<AUser>();
        String getTableSQL = "SELECT * FROM shik.users";
        try {
            ResultSet rs = Connectdb().executeQuery(getTableSQL);
            while(rs.next()){
                auser = setAUsers_params(rs,new AUser());
                list.add(auser);
            }
            closeDb();
            Errors.AddInfo("Users Info have load!");
        } catch (SQLException e) {
            Errors.AddError("Can`t Connect DataBase with User`s info 101");
            System.out.println("Not work");
            e.printStackTrace();
        }
         return list;
    }

    public void changeUser_to_Seller(int userId){
        String Sql = getStr_become_seller(userId);
        try{
            Connectdb().executeUpdate(Sql);
            closeDb();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void RegistSeller(int userId,java.util.Date date){//
        String insertTableSQL = getStr_createTableForSeller(userId);
        String insertSellerInfo = getStr_insertSellersInfo(userId,date);
        try {
            Connectdb().executeUpdate(insertSellerInfo); //При регистрации вносим втроку в таблицу, которая содержит инфу о пользователе
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
            Errors.AddError("Error in Registration new Seller 200");
        }
        try {
            Connect_sellersinfo_shema().executeUpdate(insertTableSQL); // Создаем таблицу для хранения информации о постах
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
            Errors.AddError("Error in Registration new Seller 200");
            closeDb();
        }

    }


    public boolean HasSeller_posts(int userId){
        boolean is = false;

        String str = getStr_is_exsist_TableForSeller(userId);
        try {
            ResultSet set = Connect_sellersinfo_shema().executeQuery(str);
            if(set.next()){
                is = true;
            }
            closeDb();
        } catch (SQLException e){
            e.printStackTrace();
            closeDb();
        }
        return is;
    }
    public boolean HasSeller_reviews(int selleriD){
        boolean is = false;
        String str = getStr_is_exsist_ReViewTable(selleriD);
        ResultSet set = null;
        try {
             set = Connect_sellersinfo_shema().executeQuery(str);
            if(set.next()){
                is = true;
                set.close();
            } else {
                set.close();
            }
            closeDb();
        } catch (SQLException e){
            Errors.AddError("5" + selleriD + " ReViews Cannt Find");
            e.printStackTrace();
            closeDb();
            try {
                if(set != null) {
                    set.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return is;
    }
    protected AUser setAUsers_params(ResultSet rs,AUser auser) throws SQLException { //Этот метод получает пустого пользователя и данные из бд. Он устанавливает пользователю нужные параметры
            auser.setUserId(rs.getInt(1)); //UserId
            if((rs.getString(2))!= null){
                auser.setUsername(rs.getString(2)); //UserName
            }
            auser.setFirst_name(rs.getString(3)); //FirstName
            if((rs.getString(4))!= null){
                auser.setLast_name(rs.getString(4)); //LastName
            }
            auser.setSeller(rs.getBoolean(5));  //setSeller
            auser.setReqToBot(rs.getInt(6));
            auser.setReqToSellers(rs.getInt(7));
            auser.setSucces_Deals(rs.getInt(8));
            if(rs.getString(9) != null && rs.getString(9) != ""){
                auser.setProtect_Points(rs.getString(9));
            }
            if(rs.getString(10) != null){
                StringBuilder str = new StringBuilder(rs.getString(10));
                auser.setComlains_fromSeller(str);
            }
            auser.setComlains_number(rs.getInt(11));
            auser.setDate(rs.getString(12));
            if(rs.getString(13) != null) {
                auser.setPersonalNum(rs.getString(13));
            }
            return auser;
    }

    protected Statement Connect_sellersinfo_shema() { //Подключаем шему для хранение информации клентов
        closeDb();
        String url = "jdbc:mysql://localhost/sellersinfo?serverTimezone=Europe/Moscow&useSSL=false";
        try {
            sel_connection = DriverManager.getConnection(url, dbUser, dbPass);
            sel_connection_state = sel_connection.createStatement();
            //System.out.println("Database connected!");
           // closeConnection();
            return sel_connection_state;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    protected Statement Connectdb() { //Вспомогательный метод
        closeDb();
        String url = "jdbc:mysql://localhost/shik?serverTimezone=Europe/Moscow&useSSL=false";
        try {
            connection = DriverManager.getConnection(url, dbUser, dbPass);
            connection_state = connection.createStatement();
            //System.out.println("Database connected!");
           // closeConnection();
            return connection_state;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    protected Statement Connectdb_INFO() { //Вспомогательный метод
        closeDb();
        String url = "jdbc:mysql://localhost/info?serverTimezone=Europe/Moscow&useSSL=false";
        try {
            Info_connection = DriverManager.getConnection(url, dbUser, dbPass);
            Info_connection_state = Info_connection.createStatement();
            //closeConnection();
            //System.out.println("Database connected!");
            return Info_connection_state;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
