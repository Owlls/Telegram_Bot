package sample.UsersData.Database;

import org.telegram.telegrambots.meta.api.objects.User;
import sample.GuiPart.Errors;
import sample.UsersData.Post;
import sample.UsersData.Reviews.ReView;
import sample.UsersData.UsersManager;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class ReviewDataBase extends DataBaseHandler implements Serializable {


    public void DeleteReView(int sellerId,int NumReVieW){
        String SQL = getStr_DeleteReView(sellerId,NumReVieW);
        AddToDb(SQL);
    }

    public void insertByerId(int userId,int sellerId,int NumReVieW){
        String SQL = getStr_insert_ByerId(userId,sellerId,NumReVieW);
        AddToDb(SQL);
        //Вносим время обращения
    }

    public int getReViewIndex(int sellerId){//Возвращает индекс в таблице
        String SQL = getStr_AllReViews(sellerId);
        try {
            ResultSet set = Connectdb_INFO().executeQuery(SQL);
            set.last();
            int num = set.getRow() + 1 ;
            return num;
        } catch (SQLException e) {
            Errors.AddError("6 Can`t get ReView for Seller");
            e.printStackTrace();
        }
        return -1;
    }


    public void insertDescription(int sellerId,int NumReVieW,String value){
        String SQL = getStr_Insert_InfoToReView(sellerId,"B_description",value,NumReVieW);
        AddToDb(SQL);
    }
    public void insertConformity(int sellerId,int NumReVieW,String value){
        String SQL = getStr_Insert_InfoToReView(sellerId,"B_conformity",value,NumReVieW);
        AddToDb(SQL);
    }
    public void insertService(int sellerId,int NumReVieW,String value){
        String SQL = getStr_Insert_InfoToReView(sellerId,"B_service",value,NumReVieW);
        AddToDb(SQL);
    }
    public void insertQuality(int sellerId,int NumReVieW,String value){
        String SQL = getStr_Insert_InfoToReView(sellerId,"B_quality",value,NumReVieW);
        AddToDb(SQL);
    }
    public void insertQ_p(int sellerId,int NumReVieW,String value){
        String SQL = getStr_Insert_InfoToReView(sellerId,"B_q_p",value,NumReVieW);
        AddToDb(SQL);
    }
    public void insertIndexV(int sellerId,int NumReVieW,Double value){
        String SQL = getStr_Insert_InfoToReView(sellerId,"IndexV", value,NumReVieW);
        AddToDb(SQL);
    }
    public void insertRecomendet(int sellerId,int NumReVieW,String value){
        String SQL = getStr_Insert_InfoToReView(sellerId,"B_recomended",value,NumReVieW);
        AddToDb(SQL);
    }
    public void insertBuyerFinish(int sellerId,int NumReVieW){
        String SQL = getStr_Byer_Finish(true,sellerId,NumReVieW);
        AddToDb(SQL);
    }
    public void insertFinishTime(int sellerId,int NumReVieW,Date date){
        String SQL = getStr_ReViewFinishTime(sellerId,date,NumReVieW);
        AddToDb(SQL);
    }
    public void insertRevId(int sellerId,int NumReVieW,int val){
        String SQL = getStr_setRevId(sellerId,NumReVieW,val);
        AddToDb(SQL);
    }

    public void insertNumOfReview(int sellerId, int num){ //Выставляет первый номер в таблице
        String SQL = getStr_InsertNumOfReView(sellerId,num);
        AddToDb(SQL);
    }

    public void insertFirstTime(int sellerId, Date date,int NumReView){//Вносим в базу данных время, когда пользователь обратился к боту
        String SQl = getStr_ReViewTime_ReqtoBot(sellerId,date,NumReView);
        AddToDb(SQl);
        //Вносим время обращения
    }

    public void insertTimeAskingForReView(int sellerId, Date date,int NumReView){
        //Вносим время когда пользователь попросил отзыв
        String SQl = getStr_ReViewTime_Req210(sellerId,date,NumReView);
        AddToDb(SQl);
    }

    public void insertWasReq210(boolean b,int sellerId,int NumReView){
        //Вносим булево значение о том, что пользователь попросил отзыа
        String SQl = getStr_Was_Req210(b,sellerId,NumReView);
        AddToDb(SQl);
    }

    public void insertTimeAskingForReViewSec(int sellerId, Date date,int NumReView){
        //Вносим время когда пользователь попросил отзыв ещё раз
        String SQl = getStr_ReViewTime_Req211(sellerId,date,NumReView);
        AddToDb(SQl);
    }

    public void insertTimeResponse(int sellerId, Date date,int NumReView){
        //Вносим время когда продавец оставил право отзыва
        String SQl = getStr_ReViewTime_ResSeller(sellerId,date,NumReView);
        AddToDb(SQl);
    }

    public void Res201(boolean b,int sellerId,int NumReView){
        //Вносим булево значение о том, что продавец оставил право отзыва с 201
        String SQl = getStr_Was_Res201(b,sellerId,NumReView);
        AddToDb(SQl);
    }

    public void Res202(boolean b,int sellerId,int NumReView){
        //Вносим булево значение о том, что продавец оставил право отзыва с 202
        String SQl = getStr_Was_Res202(b,sellerId,NumReView);
        AddToDb(SQl);
    }

    public boolean isExist(int userId){
        boolean is = false;
        String str = getStr_is_exsist_TableForReView(userId);
        try {
            ResultSet set = Connectdb_INFO().executeQuery(str);
            if(set.next()){
                is = true;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return is;
    }

    public void create_Table(int userId){
        String SQL = getStr_createTableForReViews(userId);
        AddToDb(SQL);
    }

    private void AddToDb(String SQL){// С помощью этого метода, я буду вносить даннаые
        try{
            Connectdb_INFO().executeUpdate(SQL);
            closeDb();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

   // ------------------------------------------------ RELOAD

    public ArrayList<ReView> getReView(int selleId, UsersManager manager){
        UsersManager manager1 = manager;
        ReView reView;
        ArrayList<ReView> ReViews = new ArrayList<ReView>();
        String SQL = getStr_GetAllReViews(selleId);
        try{
            ResultSet set  = Connectdb_INFO().executeQuery(SQL);
            int count = 1;
            while (set.next()){
                reView = setReViewParams(set,new ReView(this),manager1,selleId);
                count++;
                ReViews.add(reView);
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return ReViews;
    }
    private ReView setReViewParams(ResultSet set, ReView reView,UsersManager usersManager,int sellerId) throws SQLException {
        ResultSetMetaData rsmd = set.getMetaData();
        int numberColumns = rsmd.getColumnCount();
        reView.setNum_ofReView(set.getInt(1));
        if (usersManager.GettingUser(set.getInt(2)) != null){
            reView.setByer(usersManager.GettingUser(set.getInt(2)));
        } else {
            reView.setByerId(set.getInt(2));
            Errors.AddError(" 13 Error User is not in data Base !!! ");
        }
       // System.out.println(usersManager.GettingUser(set.getInt(2)) + "  dd d");
        reView.setSeller(usersManager.GettingSeller(sellerId));
        reView.setTime_request_toBot(set.getTimestamp(3));
        reView.setTime_request_first(set.getTimestamp(4));
        reView.setTime_request_second(set.getTimestamp(5));
        reView.setTime_Ofinish(set.getTimestamp(7));
        reView.setTime_seller_response(set.getTimestamp(8));
        if(set.getBoolean(9)){
            reView.setAskReView_wasClicked(true);
        }else {
            reView.setAskReView_wasClicked(false);
        }
        if(set.getBoolean(10)){
            reView.setRightforReView_Was201(true);
        } else if(set.getBoolean(11)){
            reView.setRightforReView_Was202(true);
        }
        if(set.getString(12) != null && set.getString(12) != ""){
            reView.setDescription(set.getString(12));
        }
        if(set.getBoolean(13)){
            reView.setConformity(true);
        }else {
            reView.setConformity(false);
        }
        reView.setService(set.getInt(14));
        reView.setQuality(set.getInt(15));
        reView.setQ_p(set.getInt(16));
        if(set.getBoolean(17)){
            reView.setRecomended(true);
        }else {
            reView.setRecomended(false);
        }
        if(set.getBoolean(18)){//Оконченная или нет, если нет
            reView.setBuyerGiveReV(true);
        }else {
            reView.setBuyerGiveReV(false);
        }
        reView.setIndex_q(set.getDouble(19));
        reView.setRevId(set.getInt(20));
        if(reView.isAskReView_wasClicked()){
            if(!reView.isBuyerGiveReV()){
                usersManager.LoadConnection(reView.getByer(), sellerId, reView);
            }
        }
        return reView;
    }

}
