package sample.UsersData.Reviews;

import sample.UsersData.Database.ReviewDataBase;
import sample.UsersData.Users.AUser;
import sample.UsersData.Users.Seller;

import java.io.Serializable;
import java.util.Date;

public class ReView implements Serializable {

    private Date time_request_toBot;
    private Date time_request_first;
    private Date time_request_second;
    private Date time_seller_response;
    private Date time_Ofinish;
    private double Index_q = 0;
    private boolean AskReView_wasClicked = false;//Покупатель попросил право отзыва
    private boolean AskReView_wasClickedTwice = false;//Покупатель попросил право отзыва уже дважды
    private boolean ReView_wasCreated = false; //Создан экземпляр данного класса
    private boolean RightforReView_Was202 = false; //Право отзыва полученно
    private boolean RightforReView_Was201 = false; //Право отзыва полученно со первой клавиатуры
    private boolean BuyerGiveReV = false;//Покупатель оставил отзыв
    private boolean SellerGiveReV = false; //Продавец оставил отзыв
    private int RevId;
    private ReviewDataBase dataBase;
    private Seller seller;
    private AUser byer;
    private int num_ofReView;
    //-------------Переменные отзыва покупателя
    private String description;
    private boolean conformity;
    private int service;
    private int quality;
    private int q_p;
    private boolean recomended;

    //--------------Переменные продавца
    private String comlain;
    private boolean dealwasSucces;
    private boolean addProtectPoint;
    private boolean IsComlains = false;
    private boolean IsDealWasSucces;
    private boolean IsAddProtectPoint;

    private String sellerId;
    private String userId;


    public String getSellerId() {
        return sellerId;
    }

    public String getUserId() {
        return userId;
    }

    public String getComlain() {
        return comlain;
    }

    public void setComlain(String comlain) {
        comlain = comlain;
    }

    public boolean getDealwasSucces() {
        return dealwasSucces;
    }

    public void setDealwasSucces(boolean dealwasSucces) {
        this.dealwasSucces = dealwasSucces;
    }

    public boolean getAddProtectPoint() {
        return addProtectPoint;
    }

    public void setAddProtectPoint(boolean addProtectPoint) {
        this.addProtectPoint = addProtectPoint;
    }


    //-----------------Методы переменных продавца

    public boolean isComlains() {
        return IsComlains;
    }

    public boolean isDealWasSucces() {
        return IsDealWasSucces;
    }

    public boolean isAddProtectPoint(){
        return IsAddProtectPoint;
    }


//----------------------------Булевые значения для клавиатуры Покупателя

//-----------------Методы переменных покупателя



    public String getDescription() {
        return description;
    }

    public void addDescription(String description) {
        this.description = description;
        dataBase.insertDescription(seller.getUserId(),num_ofReView,description);
    }

    public boolean getConformity() {
        return conformity;
    }

    public void addConformity(boolean conformity) {
        this.conformity = conformity;
        int myInt = conformity ? 1 : 0;
        dataBase.insertConformity(seller.getUserId(),num_ofReView,String.valueOf(myInt));
    }

    public int getService() {
        return service;
    }

    public void addService(int service) {
        this.service = service;
        dataBase.insertService(seller.getUserId(),num_ofReView,String.valueOf(service));
    }

    public int getQuality() {
        return quality;
    }

    public void addQuality(int quality) {
        this.quality = quality;
        dataBase.insertQuality(seller.getUserId(),num_ofReView,String.valueOf(quality));
    }

    public int getQ_p() {
        return q_p;
    }

    public void addQ_p(int q_p) {
        this.q_p = q_p;
        dataBase.insertQ_p(seller.getUserId(),num_ofReView,String.valueOf(q_p));
    }

    public boolean getRecomended() {
        return recomended;
    }

    public void addRecomended(boolean recomended) {
        this.recomended = recomended;
        int myInt = recomended ? 1 : 0;
        dataBase.insertRecomendet(seller.getUserId(),num_ofReView,String.valueOf(myInt));
    }

    public boolean isBuyerGiveReV() {
        return BuyerGiveReV;
    }

    public AUser getByer(){
        return byer;
    }

    public Seller getSeller(){
        return seller;
    }

    public int getRevId() {
        return RevId;
    }

    public void addRevId(int revId) {
        if(this.RevId != revId){
            RevId = revId;
            dataBase.insertRevId(seller.getUserId(),num_ofReView,RevId);
        }
    }


    //--------------------Constructor
    public ReView(Seller seller, AUser byer, ReviewDataBase dataBase){
        this.dataBase = dataBase;
        this.byer = byer;
        this.userId = String.valueOf(byer.getUserId());
        this.sellerId = String.valueOf(seller.getUserId());
        this.seller = seller;
        onCreate();
    }

    public ReView(ReviewDataBase reviewDataBase){
        this.dataBase = reviewDataBase;
    }



    private void onCreate(){// Метод срабатывает, когда Пользователь обращается по посту
        ReView_wasCreated = true;
        if(dataBase.isExist(seller.getUserId())){//Проверяем если таблица уже существует
             num_ofReView = dataBase.getReViewIndex(seller.getUserId());//Получаем номер записи
             dataBase.insertNumOfReview(seller.getUserId(),num_ofReView);//Вносим этот самый номер
             dataBase.insertByerId(byer.getUserId(),seller.getUserId(),num_ofReView);//Вносим id Покупателя в таблицу с отзывом
             time_request_toBot = new Date();
             dataBase.insertFirstTime(seller.getUserId(),time_request_toBot,num_ofReView);//Вносим в базу данных время, когда пользователь обратился к боту

        } else{
              dataBase.create_Table(seller.getUserId());//Пытаемя создать таблицу, в случае успеха:
              num_ofReView = dataBase.getReViewIndex(seller.getUserId());//Получаем номер записи
              dataBase.insertNumOfReview(seller.getUserId(),num_ofReView); //Вносим этот самый номер
              dataBase.insertByerId(byer.getUserId(),seller.getUserId(),num_ofReView);//Вносим id Покупателя в таблицу с отзывом
              time_request_toBot = new Date();
              dataBase.insertFirstTime(seller.getUserId(),time_request_toBot,num_ofReView);//Вносим в базу данных время, когда пользователь обратился к боту
        }
    }


    public void AskForReView(){ //Пользователь попросил право отзыва
        AskReView_wasClicked = true;
        time_request_first = new Date();
        dataBase.insertTimeAskingForReView(seller.getUserId(),time_request_first,num_ofReView);//Вносим время, когда покупатель попросил право отзыва
        dataBase.insertWasReq210(true,seller.getUserId(),num_ofReView);//Вносим true, что пользователь попросил отзыв
    }

    public void AskForReViewSec(){ //Пользователь попросил право отзыва ещё раз
        AskReView_wasClickedTwice = true;
        time_request_second = new Date();
        dataBase.insertTimeAskingForReViewSec(seller.getUserId(),time_request_second,num_ofReView);

    }

    public void SetRightforReView(){//Продавец предоставляет право отзыва с 202
        RightforReView_Was202 = true;
        time_seller_response = new Date();
        dataBase.insertTimeResponse(seller.getUserId(), time_seller_response,num_ofReView);
        dataBase.Res202(true,seller.getUserId(),num_ofReView);
    }

    public void SetRightforReView(boolean y){ //Продавец предоставляет право отзыва с 201
        RightforReView_Was201 = true;
        time_seller_response = new Date();
        dataBase.insertTimeResponse(seller.getUserId(),time_seller_response,num_ofReView);
        dataBase.Res201(true,seller.getUserId(),num_ofReView);
    }
    public void addFinishTime(){
        time_Ofinish = new Date();
        dataBase.insertFinishTime(seller.getUserId(),num_ofReView,time_Ofinish);

    }

    public Double Finish(){
        BuyerGiveReV = true;
        dataBase.insertBuyerFinish(seller.getUserId(),num_ofReView);
        addFinishTime();
        Index_q = 0;
        if(conformity){
            Index_q += 2;
        }
        if(recomended){
            Index_q += 2;
        }
        Index_q += service/5;
        Index_q += q_p/3;
        Index_q += quality /10;
        dataBase.insertIndexV(seller.getUserId(),num_ofReView,Index_q);
        return Index_q;
    }

    public double getIndex_q(){
        if(BuyerGiveReV) {
            return Index_q;
        } else {
            return -1;
        }
    }

    public void setIndex_q(double index_q) {
        Index_q = index_q;
    }

    public double getIndexQ(){
        return Index_q;
    }
    public void setAskReView_wasClicked(boolean askReView_wasClicked) {
        AskReView_wasClicked = askReView_wasClicked;
    }


    public void setRightforReView_Was202(boolean rightforReView_Was202) {
        RightforReView_Was202 = rightforReView_Was202;
    }

    public void setRightforReView_Was201(boolean rightforReView_Was201) {
        RightforReView_Was201 = rightforReView_Was201;
    }


    public void setTime_request_toBot(Date time_request_toBot) {
        this.time_request_toBot = time_request_toBot;
    }

    public void setTime_request_first(Date time_request_first) {
        this.time_request_first = time_request_first;
    }

    public void setTime_request_second(Date time_request_second) {
        this.time_request_second = time_request_second;
    }

    public void setTime_seller_response(Date time_seller_response) {
        this.time_seller_response = time_seller_response;
    }

    public void setTime_Ofinish(Date time_Ofinish) {
        this.time_Ofinish = time_Ofinish;
    }

    public Date getTime_request_toBot() {
        return time_request_toBot;
    }

    public Date getTime_request_first() {
        return time_request_first;
    }

    public Date getTime_request_second() {
        return time_request_second;
    }

    public Date getTime_seller_response() {
        return time_seller_response;
    }

    public Date getTime_Ofinish() {
        return time_Ofinish;
    }

    public int getNum_ofReView() {
        return num_ofReView;
    }

    public void setBuyerGiveReV(boolean buyerGiveReV) {
        BuyerGiveReV = buyerGiveReV;
    }

    public void setSellerGiveReV(boolean sellerGiveReV) {
        SellerGiveReV = sellerGiveReV;
    }

    public void setSeller(Seller seller) {
        sellerId = String.valueOf(seller.getUserId());
        this.seller = seller;
    }

    public void setByer(AUser byer) {
        this.byer = byer;
        userId = String.valueOf(byer.getUserId());

    }
    public void setByerId(int byerId) {
        userId = String.valueOf(byerId);

    }

    public void setNum_ofReView(int num_ofReView) {
        this.num_ofReView = num_ofReView;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setConformity(boolean conformity) {
        this.conformity = conformity;
    }

    public void setService(int service) {
        this.service = service;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public void setQ_p(int q_p) {
        this.q_p = q_p;
    }
    public void setRevId(int revId) {
        RevId = revId;
    }
    public void setRecomended(boolean recomended) {
        this.recomended = recomended;
    }

    public void setDealWasSucces(boolean dealWasSucces) {
        IsDealWasSucces = dealWasSucces;
    }

    public boolean isAskReView_wasClicked() {
        return AskReView_wasClicked;
    }

    public void RemoveMySelf(int sellerId){
        dataBase.DeleteReView(sellerId,num_ofReView);
    }

    public boolean isAskReView_wasClickedTwice() {
        return AskReView_wasClickedTwice;
    }

    public boolean isRightforReView_Was202() {
        return RightforReView_Was202;
    }

    public boolean isRightforReView_Was201() {
        return RightforReView_Was201;
    }
}
