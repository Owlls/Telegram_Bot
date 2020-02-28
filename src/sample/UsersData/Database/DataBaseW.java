package sample.UsersData.Database;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class DataBaseW {

    public DataBaseW(){
        Alphabet.put(1,"A");
        Alphabet.put(2,"B");
        Alphabet.put(3,"C");
        Alphabet.put(4,"D");
        Alphabet.put(5,"F");
        Alphabet.put(6,"G");
        Alphabet.put(7,"H");
        Alphabet.put(8,"I");
        Alphabet.put(9,"J");
        Alphabet.put(10,"K");
        Alphabet.put(11,"L");
        Alphabet.put(12,"M");
        Alphabet.put(13,"O");
        Alphabet.put(14,"P");
        Alphabet.put(15,"Q");
        Alphabet.put(16,"R");
        Alphabet.put(17,"S");
        Alphabet.put(18,"T");
        Alphabet.put(19,"X");
        Alphabet.put(20,"W");
        Alphabet.put(21,"Z");
    }

    protected static HashMap<Integer,String> Alphabet = new HashMap<Integer,String>();

    protected final String USER_TABLE = "";
    protected final String CHAT_ID = "";
    protected final String USER_NAME = "";
    protected final String USER_FIRSTNAME = "";
    protected String dbHost = "";
    protected String dbPort = "";
    protected String dbUser = "";
    protected String dbPass = "";
    protected String dbName = "";


    protected final String findUser_allData_userId = "SELECT * FROM .users  WHERE idUsers =";
    protected final String insert_userId_first_name = "INSERT INTO .users (idUsers,first_name) VALUES";
    protected final String add_usersname = "UPDATE .users SET Username = ";
    protected final String add_lastname = "";
    protected void getStr_username(){
        String str = String.format("");
    }
   /*
   *
   *    ALTER TABLE `sellersinfo`.`166743740` ADD COLUMN `Price_B` VARCHAR(45) NULL AFTER `Price_A`;
   *                     1                       2                                3
   *   DELETE FROM ``.`users` WHERE (`idUsers` = '166743740');
   *   UPDATE `sellersinfo`.%s SET `FreePromo` = NULL, `Time` = NULL, `Date` = NULL, `TimeMt` = NULL,`NumLoops = NULL WHERE (`NumberPost` = '%s');
   *
   *
   * CREATE TABLE `info`.`sellerid` (
  `NumOfReview` INT NOT NULL AUTO_INCREMENT,
  `ByerId` INT(15) NULL,
  `TimeReq_toBot` DATETIME NULL,
  `TimeReq_210` DATETIME NULL,
  `TimeReq_211` DATETIME NULL,
  `TimeReq_212` DATETIME NULL,
  `TimeRes_fromSeller` DATETIME NULL,
  `Was_Req210` TINYINT NULL,
  `Was_Res201` TINYINT NULL,UPDATE ``.`users` SET `PersonalNumber` = '354345' WHERE (`idUsers` = '80260842');
  `Was_Res202` TINYINT NULL,
  PRIMARY KEY (`NumOfReview`));
   * */
   protected String getStr_setPersonalNumber(String pn,int userId){
       String str = String.format("UPDATE ``.`users` SET `PersonalNumber` = '%s' WHERE (`idUsers` = '%s');",pn,userId);
       return str;
   }

   protected String getStr_BlockAUser(int userId){
       String str = String.format("INSERT INTO ``.`blacklist` (`userId`) VALUES ('%s');",userId);
       return str;
   }
    protected String getStr_DeleteSeller(int sellerId){
        String str = String.format("DELETE FROM ``.`sellersinfo` WHERE (`idUsers` = '%s');",sellerId);
        return str;
    }
    protected String getStr_desist_seller(int sellerId){
        String str = String.format("UPDATE .users SET IsSeller = '0' WHERE (`idUsers` = '%s');",sellerId);
        return str;
    }
    protected String getStr_selectProtectPoints(int userId){
        String str = String.format("SELECT `Protect_Points` FROM ``.`users` WHERE (`idUsers` = '%s');",userId);
        return str;
    }
    protected String getStr_IncreaseReqToBot(int userId){
        String str = String.format("UPDATE ``.`users` SET `Requests_to_Bot` = `Requests_to_Bot` + '1' WHERE (`idUsers` = '%s');",userId);
        return str;
    }

    protected String getStr_setLastTime(int userId,Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("UPDATE ``.`users` SET `WasLastTime` =  '%s' WHERE (`idUsers` = '%s');",df.format(date),userId);
        return str;

    }
    protected String getStr_IncreaseReqToSeller(int userId){
        String str = String.format("UPDATE ``.`users` SET `Requests_to_Seller` = `Requests_to_Seller` + '1' WHERE (`idUsers` = '%s');",userId);
        return str;
    }
    protected String getStr_InsertUsernameToUser(int userId,String username){
        String str = String.format("UPDATE ``.`users` SET `Protect_Points` = `Protect_Points` + '%s' WHERE (`idUsers` = '%s');",username,userId);
        return str;
    }
    protected String getStr_InsertUsernamesToUser(int userId,String usernames){
        String str = String.format("UPDATE ``.`users` SET `Protect_Points` =  '%s' WHERE (`idUsers` = '%s');",usernames,userId);
        return str;
    }
    protected String getStr_addComlaints(int userId,String Comlaint){
        String str = String.format("UPDATE ``.`users` SET  `Comlaints_Column` = `Comlaints_Column` + '%s' WHERE (`idUsers` = '%s');",Comlaint,userId);
        return str;
    }
    protected String getStr_SetIncreaseReqToBot_1(int userId){
        String str = String.format("UPDATE ``.`users` SET `Requests_to_Bot` = '1'  WHERE (`idUsers` = '%s');",userId);
        return str;
    }
    protected String getStr_SetIncreaseSuccesDeals(int userId){
        String str = String.format("UPDATE ``.`users` SET `Succes_Deals` = `Succes_Deals` + '1'   WHERE (`idUsers` = '%s');",userId);
        return str;
    }
    protected String getStr_SetIncreaseComlaintsNum(int userId){
        String str = String.format("UPDATE ``.`users` SET `Comlaints_Column` = `Comlaints_Column` + '1'   WHERE (`idUsers` = '%s');",userId);
        return str;
    }
    protected String getStr_SellerData(int userId){
        String str = String.format("SELECT * FROM .sellersinfo WHERE idUsers = %s",userId);
        return str;
    }

    protected String getStr_DeletePost(int userId,int numberPost){ //,int numberPost
        String str = String.format("DELETE FROM `sellersinfo`.`%s` WHERE (`NumberPost` = '%s');", userId, numberPost);
        return str;
    }
    protected String getStr_AddPointsToSeller(int userId,int Points){
        String str = String.format("UPDATE ``.`sellersinfo` SET `Points` = '%s' WHERE (`idUsers` = '%s');",Points,userId);
        return str;
    }

    protected String getStr_DeleteAllTimeSettings(int userId,int numberPost){
        String str = String.format("UPDATE `sellersinfo`.%s SET `FreePromo` = NULL, `Time` = NULL, `Date` = NULL, `TimeMt` = NULL,`NumLoops = NULL WHERE (`NumberPost` = '%s');", userId, numberPost);
        return str;
    }
    protected String getStr_DeleteOtTimeSettings(int userId,int numberPost){
        String str = String.format("UPDATE `sellersinfo`.`%s` SET  `Time` = NULL, `Date` = NULL WHERE (`NumberPost` = '%s');", userId, numberPost);
        return str;
    }
    protected String getStr_DeleteMtTimeSettings(int userId,int numberPost){
        String str = String.format("UPDATE `sellersinfo`.`%s` SET  `TimeMt` = NULL,`NumLoops = NULL WHERE (`NumberPost` = '%s');", userId, numberPost);
        return str;
    }
    protected String getStr_DeleteFreeTimeSettings(int userId,int numberPost){
        String str = String.format("UPDATE `sellersinfo`.`%s` SET  `FreePromo` = NULL WHERE (`NumberPost` = '%s');", userId, numberPost);
        return str;
    }


    protected String getStr_Insert_NewColoumn(int userId,String Coloumn_name,int numberOfColumn){ //,int numberPost
            String str = null;
            String ColumnF;
            String ColumnS;
            if(numberOfColumn > 1) {
                ColumnF = (Coloumn_name + Alphabet.get(numberOfColumn - 1));
                ColumnS = (Coloumn_name + Alphabet.get(numberOfColumn));
                str = String.format("ALTER TABLE `sellersinfo`.`%s` ADD COLUMN `%s` VARCHAR(45) NULL AFTER `%s`;", userId, ColumnS, ColumnF);
            }
            return str;
        }

    protected String getStr_Insert_InfoToPost(int userId,String column,String value,int numberPost){
        String str = String.format("UPDATE `sellersinfo`.`%s` SET `%s` = '%s' WHERE (`NumberPost` = '%s');",userId,column,value,numberPost);
        return str;
    }

    protected String getStr_Insert_IntegerInfoToPost(int userId,String column,int value,int numberPost){
        String str = String.format("UPDATE `sellersinfo`.`%s` SET `%s` = '%s' WHERE (`NumberPost` = '%s');",userId,column,value,numberPost);
        return str;
    }

    protected String getStr_Insert_FPostV(int userId,String column,String value){
        String str = String.format("INSERT INTO `sellersinfo`.`%s` (`%s`) VALUES ('%s');",userId,column,value);
        return str;
    }

    protected String getStr_Set_Number_of_Post(int userId,int number){
        String str = String.format("INSERT INTO `sellersinfo`.`%s` (`NumberPost`) VALUES ('%s');",userId,number);
        return str;
    }
/*
    protected String getStr_insert_userId_first_name(int userId,String fist_name,int position){
        String str = String.format("INSERT INTO .users (idUsers,first_name,IsSeller) VALUES (%s,\'%s\',false);",userId,fist_name);
        return str;
    }*/
    protected String getStr_insert_first_name(int userId,String fist_name){
        String str = String.format("UPDATE ``.`users` SET `first_name` = '%s' WHERE (`idUsers` = '%s');",fist_name,userId);
        return str;
    }
    protected String getStr_insert_userId(int userId){
        String str = String.format("INSERT INTO ``.`users` (`idUsers`,`IsSeller`) VALUES ('%s',false);",userId);
        return str;
    }


    protected String getStr_insert_UserName(int userId,String Username){ //Дописать метод, так чтобы он возвращал строку для внесения в таблицу юзернейма пользователя
        String str = String.format("UPDATE ``.`users` SET `Username` = '%s' WHERE (`idUsers` = '%s');",Username,userId);
        return str;
    }

    protected String getStr_insert_LastName(int userId,String last_name){ //Поправить метод, так чтобы он возвращал строку, которая будет вносит Фамилию пользователя
        String str = String.format("UPDATE ``.`users` SET `last_name` = '%s' WHERE (`idUsers` = '%s');",last_name,userId);
        return str;
    }

    protected String getStr_become_seller(int userId){
        String str = String.format("UPDATE .users SET IsSeller = '1' WHERE (`idUsers` = '%s');",userId);
        return str;
    }


    protected String getStr_insertSellersInfo(int userId, Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("INSERT INTO ``.`sellersinfo` (`idUsers`, `Agreement`, `Num_ReqTo`, `TimeOfRegistration`, `Points`, `AllPoints`) VALUES ('%s', '0', '0', '%s', '0', '0');",userId,df.format(date));
        return str; //       `TimeMt` TIME NULL
    }

    protected String getStr_insertsCreditToSeller(int userId,int value){
        String str = String.format("UPDATE ``.`sellersinfo` SET `Credit` = '%s' WHERE (`idUsers` = '%s')", value,userId);
        return str;
    }

    protected String getStr_createTableForSeller(int sellerId){
        String str = String.format("CREATE TABLE `sellersinfo`.`%s` (\n" +
                "  `NumberPost` INT NULL,\n" +
                "  `FreePromo` TINYINT NULL,\n" +
                "  `Time` TIME NULL,\n" +
                "  `Date` DATE NULL,\n" +
                "  `TimeMt` TIME NULL ,\n" +
                "  `NumLoops` INT NULL,\n" +
                "  `MyAgreement` TINYINT(4) NULL,\n" +
                "  `TextArbitary` VARCHAR(3000) NULL,\n" +
                "  `Category_A` VARCHAR(45) NULL,\n" +
                "  `Category_B` VARCHAR(45) NULL, \n" +
                "  `Location_A` VARCHAR(100) NULL,\n" +
                "  `Price_A` VARCHAR(45) NULL, \n" +
                "   UNIQUE (`NumberPost`));",sellerId);
        return str;
    }
    protected String getStr_createTableForReViews(int sellerId){
        String str = String.format("CREATE TABLE `info`.`%s` (\n" +
                "  `NumOfReview` INT(11) NULL,\n" +
                "  `ByerId` INT(15) NULL DEFAULT NULL,\n" +
                "  `TimeReq_toBot` DATETIME NULL DEFAULT NULL,\n" +
                "  `TimeReq_210` DATETIME NULL DEFAULT NULL,\n" +
                "  `TimeReq_211` DATETIME NULL DEFAULT NULL,\n" +
                "  `TimeReq_212` DATETIME NULL DEFAULT NULL,\n" +
                "  `FinishTime` DATETIME NULL DEFAULT NULL,\n" +
                "  `TimeRes_fromSeller` DATETIME NULL DEFAULT NULL,\n" +
                "  `Was_Req210` TINYINT(4) NULL DEFAULT NULL,\n" +
                "  `Was_Res201` TINYINT NULL DEFAULT NULL,\n" +
                "  `Was_Res202` TINYINT NULL DEFAULT NULL,\n" +
                "  `B_description` VARCHAR(1000) NULL DEFAULT NULL,\n" +
                "  `B_conformity` TINYINT NULL DEFAULT NULL,\n" +
                "  `B_service` INT NULL DEFAULT NULL,\n" +
                "  `B_quality` INT NULL DEFAULT NULL,\n" +
                "  `B_q_p` INT NULL DEFAULT NULL,\n" +
                "  `B_recomended` TINYINT NULL DEFAULT NULL, \n" +
                "  `Byer_Finish` TINYINT NULL DEFAULT NULL, \n" +
                "  `IndexV` DOUBLE NULL NULL DEFAULT NULL, \n" +
                "  `RevId` INT  NULL NULL DEFAULT NULL, \n" +
                "  UNIQUE INDEX `NumOfReview_UNIQUE` (`NumOfReview` ASC));\n",sellerId);
        return str;
    }

    protected String getStr_setRevId(int sellerId,int NumReVieW,int val){
        String str = String.format("UPDATE `info`.`%s` SET `RevId` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,val,NumReVieW);
        return str;
    }

    protected String getStr_DeleteReView(int sellerId,int NumReVieW){ //Дописать метод, так чтобы он возвращал строку для внесения в таблицу юзернейма пользователя
        String str = String.format("DELETE FROM  `info`.`%s` WHERE (`NumOfReview` = '%s');",sellerId,NumReVieW);
        return str;
    }

    protected String getStr_insert_ByerId(int userId,int sellerId,int NumReVieW){ //Дописать метод, так чтобы он возвращал строку для внесения в таблицу юзернейма пользователя
        String str = String.format("UPDATE `info`.`%s` SET `ByerId` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,userId,NumReVieW);
        return str;
    }
    protected String getStr_Insert_InfoToReView(int sellerId,String column,String value,int NumReView){
        String str = String.format("UPDATE `info`.`%s` SET `%s` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,column,value,NumReView);
        return str;
    }
    protected String getStr_Insert_InfoToReView(int sellerId,String column,Double value,int NumReView){
        String str = String.format("UPDATE `info`.`%s` SET `%s` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,column,value,NumReView);
        return str;
    }
    //----------------------------------------------Выставляем время в отзыве
    protected String getStr_ReViewTime_Req210(int sellerId, Date date,int NumReView){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("UPDATE `info`.`%s` SET `TimeReq_210` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,df.format(date),NumReView);
        return str;
    }
    protected String getStr_ReViewTime_Req211(int sellerId, Date date,int NumReView){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("UPDATE `info`.`%s` SET `TimeReq_211` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,df.format(date),NumReView);
        return str;
    }
    protected String getStr_ReViewTime_Req212(int sellerId, Date date,int NumReView){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("UPDATE `info`.`%s` SET `TimeReq_212` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,df.format(date),NumReView);
        return str;
    }
    protected String getStr_ReViewTime_ResSeller(int sellerId, Date date,int NumReView){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("UPDATE `info`.`%s` SET `TimeRes_fromSeller` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,df.format(date),NumReView);
        return str;
    }

    //-----------------------------------------------------------------------------------------------------------

    protected String getStr_Was_Req210(boolean b,int sellerId,int NumReVieW){ //Дописать метод, так чтобы он возвращал строку для внесения в таблицу юзернейма пользователя
        int myInt = b ? 1 : 0;
        String str = String.format("UPDATE `info`.`%s` SET `Was_Req210` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,myInt,NumReVieW);
        return str;
    }
    protected String getStr_Was_Res201(boolean b,int sellerId,int NumReVieW){ //Дописать метод, так чтобы он возвращал строку для внесения в таблицу юзернейма пользователя
        int myInt = b ? 1 : 0;
        String str = String.format("UPDATE `info`.`%s` SET `Was_Res201` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,myInt,NumReVieW);
        return str;
    }
    protected String getStr_Was_Res202(boolean b,int sellerId,int NumReVieW){ //Дописать метод, так чтобы он возвращал строку для внесения в таблицу юзернейма пользователя
        int myInt = b ? 1 : 0;
        String str = String.format("UPDATE `info`.`%s` SET `Was_Res202` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,myInt,NumReVieW);
        return str;
    }
    protected String getStr_Byer_Finish(boolean b,int sellerId,int NumReVieW){ //Дописать метод, так чтобы он возвращал строку для внесения в таблицу юзернейма пользователя
        int myInt = b ? 1 : 0;
        String str = String.format("UPDATE `info`.`%s` SET `Byer_Finish` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,myInt,NumReVieW);
        return str;
    }
    protected String getStr_ReViewFinishTime(int sellerId, Date date,int NumReView){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("UPDATE `info`.`%s` SET `FinishTime` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,df.format(date),NumReView);
        return str; //       `TimeMt` TIME NULL
    }

    protected String getStr_ReViewTime_ReqtoBot(int sellerId, Date date,int NumReView){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("UPDATE `info`.`%s` SET `TimeReq_toBot` = '%s' WHERE (`NumOfReview` = '%s');",sellerId,df.format(date),NumReView);
        return str; //       `TimeMt` TIME NULL
    }
    protected String getStr_ReViewTime_ReqRight1(int userId, Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("",userId,df.format(date));
        return str; //       `TimeMt` TIME NULL
    }
    protected String getStr_ReViewTime_ReqRight2(int userId, Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("",userId,df.format(date));
        return str; //       `TimeMt` TIME NULL
    }
    protected String getStr_ReViewTime_ReqRight3(int userId, Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("",userId,df.format(date));
        return str; //       `TimeMt` TIME NULL
    }
    protected String getStr_ReViewTime_ResSeller(int userId, Date date){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = String.format("",userId,df.format(date));
        return str; //       `TimeMt` TIME NULL
    }
    protected String getStr_AllReViews(int sellerId){
        String str = String.format("SELECT * FROM `info`.`%s` ",sellerId);
        return str;
    }
    protected String getStr_InsertNumOfReView(int sellerId,int num){
        String str = String.format("INSERT INTO `info`.`%s` (`NumOfReview`) VALUES ('%s'); ",sellerId,num);
        return str;
    }
    /*
protected String getStr_createTableForSeller(int sellerId) {
    String str = String.format("CREATE TABLE `sellersinfo`.`%s` (\n" +
            "  `NumberPost` INT ROW_NUMBER(),\n" +
            "  `FreePromo` TINYINT NULL,\n" +
            "  `Time` TIME NULL,\n" +
            "  `Date` DATE NULL,\n" +INSERT INTO `info`.`sellerid` (`ByerId`) VALUES ('342424');
            "  `TimeMt` TIME NULL ,\n" +
            "  `NumLoops` INT NULL,\n" +
            "  `MyAgreement` TINYINT(4) NULL,\n" +
            "  `TextArbitary` VARCHAR(3000) NULL,\n" +
            "  `Category_A` VARCHAR(45) NULL,\n" +
            "  `Category_B` VARCHAR(45) NULL, \n" +
            "  `Location_A` VARCHAR(100) NULL,\n" +
            "  `Price_A` VARCHAR(45) NULL, \n" +
            "   UNIQUE (`NumberPost`));", sellerId);
    return str;
}/*/
    protected String getStr_is_exsist_TableForSeller(int sellerId){
        String str = String.format("SELECT * FROM `sellersinfo`.`%s`" ,sellerId);
        return str;
    }
    protected String getStr_is_exsist_ReViewTable(int sellerId){
        String str = String.format("SELECT * FROM `info`.`%s`" ,sellerId);
        return str;
    }
    protected String getStr_is_exsist_TableForReView(int sellerId){
        String str = String.format("SELECT * FROM `info`.`%s`" ,sellerId);
        return str;
    }
    protected String getStr_GetAllPosts(int sellerId){
        String str = String.format("SELECT * FROM `sellersinfo`.`%s`" ,sellerId);
        return str;
    }
    protected String getStr_GetAllReViews(int sellerId){
        String str = String.format("SELECT * FROM `info`.`%s`" ,sellerId);
        return str;
    }
}
