package sample.UsersData;

import sample.TelegramPart.Promo.PostManager;
import sample.TelegramPart.Promo.PostWithTime;
import sample.UsersData.Database.Posts_DataBase;
import sample.UsersData.PromoteSettings.PromoContainer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Post implements Serializable {

    private transient Posts_DataBase posts_dataBase ;
    private int number_Post;
    private int userId;
    private int num_PriceButtons = 1;
    private int num_LocationButtons = 1;
    private String description;
    private  boolean Agreement = false;
    private ArrayList<String> prices = new ArrayList<String>();
    private ArrayList<String> locations = new ArrayList<String>();;
    private String kind;
    private String kind_B;
    private transient BufferedImage image;
    private File Post_Image;
    private File thumb;
    private PromoContainer Promo;
    private transient PromoContainer [] ListOfPromes = new PromoContainer[2];
    private boolean HaveFree = false;
    private String OneTime;
    private String ManyTimes;
    private int num_postDb = -1;

    public void setNum_postDb(int num_postDb) {
        this.num_postDb = num_postDb;
    }
    /*public boolean SaveVPromoSettings(){
        boolean Succes = true;
        if(Promo != null){
            //Тут мы будем вызывать метод, который проверяет тип рекламы и делает нужную запись в таблице
            if(Promo.type_is_Mt_promo()){
                if(posts_dataBase.ExMtSettings(userId,number_Post)){ //метод, который будет вызывать экземпляр Post_DataBase и вносить в нее время и количество повторений, перед этим надо проверить существует ли эта реклама
                    return false; //Реклама уже существует
                } else {
                    //Тут я буду вносить нужные значения
                    posts_dataBase.Add_MtTime(userId,Promo.getMt_promoTime(),number_Post);
                    posts_dataBase.Add_MtNumLoops(userId,Promo.getMt_promoLoops(),number_Post);
                }
            }else if(Promo.type_is_Ot_promo()){
                if(posts_dataBase.ExOtSettings(userId,number_Post)){  //метод, который будет вызывать экземпляр Post_DataBase и вносить в нее время и дату, перед этим надо проверить существует ли эта реклама. Если да то весь метод вернет false
                    return false; //Реклама уже существует
                }else{
                    //Тут я буду вносить нужные значения
                    posts_dataBase.Add_OtTime(userId,Promo.getOt_promoTime(),number_Post);
                    posts_dataBase.Add_OtDate(userId,Promo.getOt_promoDate(),number_Post);
                }
            }
        }
        return Succes;
    }*/

    //public Post(){posts_dataBase = new Posts_DataBase();}
    public boolean AddTimeSettings() {// Даный метод будет вносить рекламные настройки, конечно только в случае если этот пост не подтвержден
        if (Promo.type_is_Mt_promo()) {
                //if (ListOfPromes[0] == null) {
                    ListOfPromes[0] = Promo;
                    ReWritePromoSettings();
                    Promo = null;
                    return true;
               // }
        } else if(Promo.type_is_Ot_promo()){
            //if (ListOfPromes[1] == null) {
                ListOfPromes[1] = Promo;
                ReWritePromoSettings();
                Promo = null;
                return true;
           // }
        } else {
                return false;
        }
    }

    public boolean AddTimeSettings(PromoContainer promo) {// Даный метод будет вносить рекламные настройки
        if (promo.type_is_Mt_promo()) {
            if (ListOfPromes[0] == null) {
                ListOfPromes[0] = promo;
                Promo = null;
                return true;
            }
        } else if(promo.type_is_Ot_promo()){
            if (ListOfPromes[1] == null) {
                ListOfPromes[1] = promo;
                Promo = null;
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    private boolean ReWritePromoSettings(){
        boolean Succes = true;
        if(Promo != null){
            //Тут мы будем вызывать метод, который проверяет тип рекламы и делает нужную запись в таблице
            if(Promo.type_is_Mt_promo()){ //Добавить позиции
                    posts_dataBase.Add_MtTime(userId,Promo.getMt_promoTime(),number_Post);
                    posts_dataBase.Add_MtNumLoops(userId,Promo.getMt_promoLoops(),number_Post);
                    Succes = true;

            }else if(Promo.type_is_Ot_promo()){
                    //Тут я буду вносить нужные значения
                    posts_dataBase.Add_OtTime(userId,Promo.getOt_promoTime(),number_Post);
                    posts_dataBase.Add_OtDate(userId,Promo.getOt_promoDate(),number_Post);
                    Succes = true;
                }
            }
        return Succes;
    }

    public PromoContainer createPromo(){
        Promo = new PromoContainer();
        return Promo;
    }

    public PromoContainer getPromo(){
     if(Promo != null){
        return Promo;
     }else {
         return null;
     }
    }

    public Post(int numberOfPost, int userId){
        posts_dataBase = new Posts_DataBase();
        this.number_Post = numberOfPost;
        this.userId = userId;
    }

    public String getText_InfoAboutPost(){
        StringBuilder string = new StringBuilder();
        string.append("");
        if(Agreement) {
            string.append("ה");
        }else {
            string.append("");
        }
        return string.toString();
    }

    public  ArrayList<ArrayList<String>> galochki_InfoAboutPost(PostManager postManager){
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        if(!Agreement) { // В случае, если пост не подтвержден
            if(HaveFree){
                ArrayList<String> row = new ArrayList<>();
                row.add("");
                row.add("ביטול");
                res.add(row);
            }
            if(ListOfPromes[0] != null){
                ArrayList<String> row = new ArrayList<>();
                row.add(ListOfPromes[0].getMt_promoTime() + " | " + ListOfPromes[0].getMt_promoLoops());
                row.add("ביטול");
                res.add(row);
            }
            if(ListOfPromes[1] != null){
                ArrayList<String> row = new ArrayList<>();
                row.add(ListOfPromes[1].getOt_promoTime() + " | " + ListOfPromes[1].getOt_promoDate());
                row.add("ביטול");
                res.add(row);
            }
        } else {// В случае, если пост подтвержден
            if(postManager.IsnclFree(this)){
                ArrayList<String> row = new ArrayList<>();
                row.add("פרסומת חינם");
                row.add("ביטול");
                res.add(row);
            }
            if(postManager.IsnclMt(this)){
                ArrayList<String> row = new ArrayList<>();
                ArrayList<PostWithTime> MtPromoList = postManager.AllMtSettingsForCurrentPost(this);
                for (PostWithTime a : MtPromoList){
                    row.add(a.getTtime());
                    row.add("");
                    res.add(row);
                    row = new ArrayList<>();
                }
            }
            if(postManager.IsnclOt(this)){
                ArrayList<String> row = new ArrayList<>();
                ArrayList<PostWithTime> OtPromoList = postManager.AllOtSettingsForCurrentPost(this);
                for(PostWithTime a : OtPromoList){
                    row.add(a.getTtime());
                    row.add("");
                    res.add(row);
                    row = new ArrayList<>();
                }
            }
        }
        ArrayList<String> Bl_row = new ArrayList<String>();
        Bl_row.add(("לראות את הפוסט"));
        res.add(Bl_row);
        ArrayList<String> Last_row = new ArrayList<String>();
        Last_row.add(("חזרה"));
        res.add(Last_row);
        /*----------------------------------------------------*/
        return res;
    }

    public  ArrayList<ArrayList<String>> data_InfoAboutPost(PostManager postManager){
        int i = 1115;
        int y = 1150;
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        if(!Agreement) { // В случае, если пост не подтвержден
            if(HaveFree){
                ArrayList<String> row = new ArrayList<>();
                row.add("");
                row.add("1111");
                res.add(row);
            }
            if(ListOfPromes[0] != null){
                ArrayList<String> row = new ArrayList<>();
                row.add(ListOfPromes[0].getMt_promoTime() + " | " + ListOfPromes[0].getMt_promoLoops());
                row.add("1112");
                res.add(row);
            }
            if(ListOfPromes[1] != null){
                ArrayList<String> row = new ArrayList<>();
                row.add(ListOfPromes[1].getOt_promoTime() + " | " + ListOfPromes[1].getOt_promoDate());
                row.add("1113");
                res.add(row);
            }
        } else {
            if(postManager.IsnclFree(this)){
                ArrayList<String> row = new ArrayList<>();
                row.add("");
                row.add("1114");
                res.add(row);
            }
            if(postManager.IsnclMt(this)){
                ArrayList<String> row = new ArrayList<>();
                ArrayList<PostWithTime> MtPromoList = postManager.AllMtSettingsForCurrentPost(this);

                for (PostWithTime a : MtPromoList){
                    row.add(a.getTtime());
                    row.add(String.valueOf(a.getSeconds()));
                    res.add(row);
                    row = new ArrayList<>();
                    i++;
                }
            }
            if(postManager.IsnclOt(this)){
                ArrayList<String> row = new ArrayList<>();
                ArrayList<PostWithTime> OtPromoList = postManager.AllOtSettingsForCurrentPost(this);
                for(PostWithTime a : OtPromoList){
                    row.add(a.getTtime());
                    row.add(String.valueOf(a.getSeconds()));
                    res.add(row);
                    row = new ArrayList<>();
                    y++;
                }
            }
        }
        ArrayList<String> Bl_row = new ArrayList<String>();
        Bl_row.add((""));
        res.add(Bl_row);
        ArrayList<String> Last_row = new ArrayList<String>();
        Last_row.add(("חזרה"));
        res.add(Last_row);
        /*----------------------------------------------------*/
        return res;
    }

    public boolean[] galochkiFor_Text(){
        int i,j;
        int Nb[] = numTextButtons();
        boolean [] mas = new boolean[(5 + Nb[0])];
        if(this.description != null){
            mas[0] = true;
        }
        if(!this.prices.isEmpty()){
            for (i = 1; i <= prices.size(); i++){
                mas[i] = true;
            }
        }
        if(!this.locations.isEmpty()){
            for(j = (1 + Nb[1]); j <=(Nb[1] + locations.size()); j++){
                mas[j] = true;
            }
        }
        if(this.kind != null){
            mas[1 + Nb[1] + Nb[2]] = true;
            //Предпоследней
        }
        if(this.kind_B != null){
            mas[2 + Nb[1] + Nb[2]] = true;
            //Присваиваем последней позиции массива
        }
        return mas;
    }

    public boolean[] galochkiFor_Menu(){
        boolean [] mas = new boolean[6];
        if(this.Post_Image != null){
            mas[0] = true;
        }
        if(this.description == null && this.locations.isEmpty() && this.prices.isEmpty() && this.kind == null && this.kind_B == null){
            mas[1] = false;
        } else {
            mas[1] = true;
        }
        return mas;
    }

    public void DELETEPOST(int numPost){
        if(num_postDb == -1) {
            posts_dataBase.DELETEFROMDB(this.userId, (numPost + 1));
        } else {
            posts_dataBase.DELETEFROMDB(this.userId,num_postDb);
        }

    }

    public int[] numTextButtons(){
       int [] info = new int[3];
       int numP_buttons = 0;
       int numL_buttons = 0;
       int i = 0;
       if(this.prices.size() == 0){
            numP_buttons = num_PriceButtons;
       } else {
            numP_buttons = num_PriceButtons;
            //numP_buttons = numP_buttons + prices.size();
       }
       if(this.locations.size() == 0){
           numL_buttons = num_LocationButtons;
       } else {
           numL_buttons = num_LocationButtons;
           //numL_buttons = numL_buttons + locations.size();
       }
        info [0] = (i + numL_buttons + numP_buttons);
        info [1] = numP_buttons;
        info [2] = numL_buttons;
        return info;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPrice(String price) {
        prices.add(price);
        if(prices.size() < num_PriceButtons){

        }else if(prices.size() == num_PriceButtons){
            num_PriceButtons++;
        }else if(prices.size() > num_PriceButtons){
            num_PriceButtons = prices.size();
        }
    }

    public void addLocation(String location) {
        //locations.add((num_LocationButtons - 1),location);
        locations.add(location);
        if(locations.size() < num_LocationButtons){

        }else if(locations.size() == num_LocationButtons){
            num_LocationButtons++;
        }else if(locations.size() > num_LocationButtons){
            num_LocationButtons = locations.size();
        }
    }

    public PromoContainer[] getListOfPromes() {
        return ListOfPromes;
    }
    public void setNullPromo(){
        this.Promo = null;
    }
    public void setPromo(PromoContainer promo){
        this.Promo = promo;
    }

    public int getCredits() {
        /*
        * Этот метод будет возращать кредиты, за каждую имеющуюся у него рекламу
        *
        * */
        int Credit = 0;

        if(getHaveFree()){
          // postManager.Add_Free_Post(this);
        }
        if(ListOfPromes != null){
        if(ListOfPromes[0] != null){
           Credit = Credit + (5 * ListOfPromes[0].getMt_promoLoops());
           //this.Promo = ListOfPromes[0];
           // postManager.Add_Mt_Post(this);
        } else if(ListOfPromes[1] != null){
          //  this.Promo = ListOfPromes[1];
            Credit = Credit + 10;
            //postManager.Add_Ot_Post(this);
        }
        }
       // postManager.delPostFromWaitingList(this);

        this.Promo = null;
        System.out.println(" Cred: " + Credit);
        return Credit;
    }

    public void AddAgreement(boolean agreement,Posts_DataBase db) {
        if(num_postDb != -1) {
            db.AddAgrementToPost(userId, num_postDb);
            System.out.println("NumberPost DB " + num_postDb);
        } else {
            db.AddAgrementToPost(userId, number_Post);
            System.out.println("NumberPost " + number_Post);
        }


        Agreement = agreement;

    }
    public void setAgreement(boolean agreement){
        Agreement = agreement;
    }

    public void setHaveFree(boolean haveFree) {
        HaveFree = haveFree;
    }

    public void AddHaveFree(boolean haveFree) {
        HaveFree = haveFree;
        posts_dataBase.Add_HaveFree(userId,number_Post);
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setKind_B(String kind_B) {
        this.kind_B = kind_B;
    }

    public void setImg(File file){
        this.Post_Image = file;
    }

    public void setThumb(File thumb) {
        this.thumb = thumb;
    }

    public void setPrices(ArrayList<String> prices) {
        this.prices = prices;
    }

    public void setLocations(ArrayList<String> locations) {
        this.locations = locations;
    }

    public void cleanPrices() {
        this.prices.clear();
    }

    public void cleanLocations() {
        this.locations.clear();
    }

    public boolean AddPostToDb(){
        boolean IsHave = false;
        if(Post_Image != null && (description != null || kind != null || kind_B != null || !prices.isEmpty() || !locations.isEmpty() ))
            IsHave = true;

            posts_dataBase.SetNumberOfPost(userId, number_Post);
            if (description != null) {
                AddToDb_Description(userId, description, number_Post);
            }
            if (kind != null) {
                AddToDb_Kind(userId, kind, number_Post);
            }
            if (kind_B != null) {
                AddToDb_Kind_B(userId, kind_B, number_Post);
            }
            if (!prices.isEmpty()) {
                AddToDb_Price(userId, prices, number_Post);
            }
            if (!locations.isEmpty()) {
                AddToDb_Location(userId, locations, number_Post);
            }

        return IsHave;
    }

    public void setMtNull(){
        ListOfPromes[0] = null;
        posts_dataBase.DeleteMtTimeSettings(userId,num_postDb);

    }

    public void setOtNull(){
        ListOfPromes[1] = null;
        posts_dataBase.DeleteOtTimeSettings(userId,num_postDb);

    }
    public void setFreeNull(){
        HaveFree = false;
        posts_dataBase.DeleteFreeTimeSettings(userId,num_postDb);

    }

    private void AddToDb_Location(int userId, ArrayList<String> locs, int numberOfPost) {
        posts_dataBase.Add_Location(userId,locs,numberOfPost);
    }

    private void AddToDb_Price(int userId,ArrayList<String>prices,int numberOfPost){
        posts_dataBase.Add_Price(userId,prices,numberOfPost);
    }

    private void AddToDb_Kind(int userId,String value,int numberOfPost) {
        posts_dataBase.Add_KindA(userId,value,numberOfPost);
    }

    private void AddToDb_Kind_B(int userId,String value,int numberOfPost) {
        posts_dataBase.Add_KindB(userId,value,numberOfPost);
    }

    private void AddToDb_Description(int userId,String value,int numberOfPost) {
        posts_dataBase.Add_Description(userId,value,numberOfPost);
    }

    public void AddToDb_Image(File file, File filethumb,BufferedImage image) {
        setImg(file);
        setThumb(filethumb);
        posts_dataBase.Add_Img(image,file,filethumb);
    }

    public String getOneTime() {
        if(ListOfPromes != null) {
            if (ListOfPromes[1] != null) {
                return ListOfPromes[1].getOtFullDate().toString();
            } else {
                return null;
            }
        }else {
            return null;
        }

    }

    public String getManyTimes() {
        if(ListOfPromes != null) {
           /* if (ListOfPromes[0] != null) {
                return (ListOfPromes[0].getMt_promoTime() + " | " + ListOfPromes[0].getMt_promoLoops());
            }*/
            return ManyTimes;
        }else {
            return null;
        }
    }

    public boolean getHaveFree() {
        return HaveFree;
    }

    public int getUserId() {
        return userId;
    }

    public int getNumber_Post() {
        return number_Post;
    }

    public String getKind_B() {
        return kind_B;
    }

    public String getDescription() {
        return description;
    }

    public String getKind() {
        return kind;
    }

    public File getImagePath() {
        return Post_Image;
    }


    public void setNumber_Post(int number_Post) {
        this.number_Post = number_Post;
    }

    public File getThumb() {
        return thumb;
    }

    public int getNum_PriceButtons() {
        return num_PriceButtons;
    }

    public int getNum_LocationButtons() {
        return num_LocationButtons;
    }

    public ArrayList<String> getPrices() {
        return prices;
    }

    public ArrayList<String> getLocations() {
        return locations;
    }

    public boolean getAgreement() {
        return Agreement;
    }
    public void increase_numLocationByttons(){
        num_LocationButtons++;
    }

    public void increase_numPriceByttons(){
        num_PriceButtons++;
    }

}
