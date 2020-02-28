package sample.UsersData.Database;

import sample.UsersData.Post;
import sample.UsersData.PromoteSettings.PromoContainer;
import sample.UsersData.Users.AUser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Posts_DataBase extends DataBaseHandler {

    public void Add_Img(BufferedImage seller_Photo,File file,File thumb) {
        try {
            ImageIO.write(seller_Photo, "jpeg", file);
            saveScaledImage(seller_Photo,thumb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void saveScaledImage(BufferedImage filePath,File outputFile){
        try {

            BufferedImage sourceImage = filePath;
            int width = sourceImage.getWidth();
            int height = sourceImage.getHeight();

            if(width>height){
                float extraSize=    height-100;
                float percentHight = (extraSize/height)*100;
                float percentWidth = width - ((width/100)*percentHight);
                BufferedImage img = new BufferedImage((int)percentWidth, 100, BufferedImage.TYPE_INT_RGB);
                Image scaledImage = sourceImage.getScaledInstance((int)percentWidth, 100, Image.SCALE_SMOOTH);
                img.createGraphics().drawImage(scaledImage, 0, 0, null);
                BufferedImage img2 = new BufferedImage(100, 100 ,BufferedImage.TYPE_INT_RGB);
                img2 = img.getSubimage((int)((percentWidth-100)/2), 0, 100, 100);

                ImageIO.write(img2, "jpg", outputFile);
            }else{
                float extraSize=    width-100;
                float percentWidth = (extraSize/width)*100;
                float  percentHight = height - ((height/100)*percentWidth);
                BufferedImage img = new BufferedImage(100, (int)percentHight, BufferedImage.TYPE_INT_RGB);
                Image scaledImage = sourceImage.getScaledInstance(100,(int)percentHight, Image.SCALE_SMOOTH);
                img.createGraphics().drawImage(scaledImage, 0, 0, null);
                BufferedImage img2 = new BufferedImage(100, 100 ,BufferedImage.TYPE_INT_RGB);
                img2 = img.getSubimage(0, (int)((percentHight-100)/2), 100, 100);

                ImageIO.write(img2, "jpg", outputFile);
            }

        } catch (IOException e) {
            //
            e.printStackTrace();
        }

    }

    public ArrayList<Post> getPosts(int userId){
        Post post;
        ArrayList<Post> posts = new ArrayList<Post>();
        String SQL = getStr_GetAllPosts(userId);
        try{
            ResultSet set  = Connect_sellersinfo_shema().executeQuery(SQL);
            int count = 1;
            while (set.next()){
                post = setPostParams(set,new Post(count,userId));
                count++;
                posts.add(post);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return posts;
    }
    public void DELETEFROMDB(int userId,int numPost){ /// !!!!!!!!!!!!BUG баг с порядковыми номерами
        String Sql = getStr_DeletePost(userId,numPost);
        AddToDb(Sql);
    }

    public boolean ExMtSettings(int userId,int numPost){
        String SQL = getStr_GetAllPosts(userId); ; // Получаем инфу о конкретном посте
        boolean ifExist = false;
        try{
            ResultSet set  = Connect_sellersinfo_shema().executeQuery(SQL);
            int count = 1;
            if(set.absolute(numPost)){
                return false;
            }
            if(set.getDate(4) != null && set.getInt(5) != 0){
                ifExist = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ifExist;
    }

    public boolean ExOtSettings(int userId,int numPost){
        String SQL = getStr_GetAllPosts(userId);; // Получаем инфу о конкретном посте
        boolean ifExist = false;
        try{
            ResultSet set  = Connect_sellersinfo_shema().executeQuery(SQL);
            int count = 1;
            if(set.absolute(numPost)){
                return false;
            }
            if(set.getDate(2) != null && set.getDate(3) != null){
               ifExist = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ifExist;
    }

    public void Add_OtTime(int userId, String value, int numberOfPost){
        String SQL;
        SQL = getStr_Insert_InfoToPost(userId,"Time",value,numberOfPost);
        AddToDb(SQL);
    }

    public void Add_OtDate(int userId, String value, int numberOfPost){
        String SQL;
        SQL = getStr_Insert_InfoToPost(userId,"Date",value,numberOfPost);
        AddToDb(SQL);
    }

    public void Add_MtTime(int userId, String value, int numberOfPost){
        String SQL;
        SQL = getStr_Insert_InfoToPost(userId,"TimeMt",value,numberOfPost);
        AddToDb(SQL);
    }

    public void Add_MtNumLoops(int userId, int value, int numberOfPost){
        String SQL;
        SQL = getStr_Insert_IntegerInfoToPost(userId,"NumLoops",value,numberOfPost);
        AddToDb(SQL);
    }

    public void Add_HaveFree(int userId, int numberOfPost){
        String value = "1";
        String SQL;
        SQL = getStr_Insert_InfoToPost(userId,"FreePromo",value,numberOfPost);
        AddToDb(SQL);
    }

    public void SetNumberOfPost(int userId,int numberofPost){
        String SQL = getStr_Set_Number_of_Post(userId,numberofPost);
        AddToDb(SQL);
    }

    public void Add_Description(int userId,String value, int numberOfPost){//1
        String SQL;
        SQL = getStr_Insert_InfoToPost(userId,"TextArbitary",value,numberOfPost);
        AddToDb(SQL);
    }

    public void Add_Price(int userId, ArrayList<String> values, int numberOfPost){//2
             String SQL;
            if(values.size() < 2) {
                SQL = getStr_Insert_InfoToPost(userId, "Price_A", values.get(0), numberOfPost);
                AddToDb(SQL);
            }else{
                SQL = getStr_Insert_InfoToPost(userId, "Price_A", values.get(0), numberOfPost);
                AddToDb(SQL);
                for(int i = 2; i <= values.size(); i++){
                    SQL = getStr_Insert_NewColoumn(userId,"Price_",i); //Добавляем еще одну колонку
                    AddToDb(SQL);//Добавляем еще одну колонку
                    SQL = getStr_Insert_InfoToPost(userId, ("Price_" + Alphabet.get(i)), values.get(i-1), numberOfPost);//Добавляем в нее значение
                    AddToDb(SQL);//Добавляем в нее значение

            }
        }
    }

    public void Add_Location(int userId, ArrayList<String> values, int numberOfPost){
            String SQL;
            if(values.size() < 2) {
                SQL = getStr_Insert_InfoToPost(userId, "Location_A", values.get(0), numberOfPost);
                AddToDb(SQL);
            }else{
                SQL = getStr_Insert_InfoToPost(userId, "Location_A", values.get(0), numberOfPost);
                AddToDb(SQL);
                for(int i = 2; i <= values.size(); i++){
                    SQL = getStr_Insert_NewColoumn(userId,"Location_",i); //Добавляем еще одну колонку
                    AddToDb(SQL);//Добавляем еще одну колонку
                    SQL = getStr_Insert_InfoToPost(userId, ("Location_" + Alphabet.get(i)), values.get(i-1), numberOfPost);//Добавляем в нее значение
                    AddToDb(SQL);//Добавляем в нее значение

            }
        }
    }

    public void Add_KindA(int userId,String value, int numberOfPost){
        String SQL;
        SQL = getStr_Insert_InfoToPost(userId,"Category_A",value,numberOfPost);
        AddToDb(SQL);
    }

    public void Add_KindB(int userId,String value, int numberOfPost){
        String SQL;
        SQL = getStr_Insert_InfoToPost(userId,"Category_B",value,numberOfPost);
        AddToDb(SQL);
    }

    public void DeleteFreeTimeSettings(int userId, int numberOfPost){
        String Sql = getStr_DeleteFreeTimeSettings(userId,numberOfPost);
        AddToDb(Sql);
    }
    public void DeleteOtTimeSettings(int userId, int numberOfPost){
        String Sql = getStr_DeleteOtTimeSettings(userId,numberOfPost);
        AddToDb(Sql);
    }
    public void DeleteMtTimeSettings(int userId, int numberOfPost){
        String Sql = getStr_DeleteMtTimeSettings(userId,numberOfPost);
        AddToDb(Sql);
    }

    private void AddToDb(String SQL){
        try{
            Connect_sellersinfo_shema().executeUpdate(SQL);
            closeDb();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void AddAgrementToPost(int userId, int numberOfPost){
        String SQL = getStr_Insert_InfoToPost(userId,"MyAgreement","1",numberOfPost);
        AddToDb(SQL);
    }
    private Post setPostParams(ResultSet set,Post post) throws SQLException {
        ResultSetMetaData rsmd = set.getMetaData();
        int numberColumns = rsmd.getColumnCount();
        //Ниже я восстанавливаю настройки рекламы, предварительно проверив бд на наличие настроек
        post.setHaveFree(set.getBoolean(2));
        post.setNum_postDb(set.getInt(1));
        if(!set.getBoolean(7)){
        if((set.getString(3) != null && set.getString(4) != null) || (set.getString(5) != null && set.getInt(6) != 0)) {
            //По-идее методы ниже должны работать, только если пост неподтвержден
            if (set.getString(3) != null && set.getString(4) != null) {
                PromoContainer promo = post.createPromo();
                promo.setOt_promoTime(set.getString(3));
                promo.setOt_promoDate(set.getString(4));
                post.AddTimeSettings(promo);
            }
            if (set.getString(5) != null && set.getString(6) != null) {
                PromoContainer promo = post.createPromo();
                promo.Set_Mt_promo();
                promo.setMt_promoTime(set.getString(5));
                promo.setMt_promoLoop(set.getString(6));
                post.AddTimeSettings(promo);
            }
        }
        }
        post.setAgreement(set.getBoolean(7));
        if(set.getString(8) != null){
            post.setDescription(set.getString(8));
        }
        if(set.getString(9) != null){
            post.setKind(set.getString(9));
        }
        if(set.getString(10) != null){
            post.setKind_B(set.getString(10));
        }
        for(int i = 11; i <= numberColumns; i++){//Проходимся по каждой колонке до конца
            if (set.getString(i) != null){
                if(NameChosserIsALoc(rsmd.getColumnName(i))){ //Проверяем если название колонки "Location"
                    post.addLocation(set.getString(i)); // Если "да" - то добавляем ее в локации поста
                }else {
                    post.addPrice(set.getString(i)); // Если "нет" - то добавляем ее в цены
                }
            }
        }
        return post;
    }
    private boolean NameChosserIsALoc(String columnName){
        Pattern pattern = Pattern.compile("Location");
        Matcher matcher = pattern.matcher(columnName);
        if(matcher.find()){
            return true;
        }else {
            return false;
        }
    }
}
