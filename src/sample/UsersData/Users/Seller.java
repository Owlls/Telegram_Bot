package sample.UsersData.Users;

import sample.TelegramPart.Promo.PostManager;
import sample.UsersData.Database.DataBaseHandler;
import sample.UsersData.Database.ReviewDataBase;
import sample.UsersData.Post;
import sample.UsersData.Reviews.ReView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Seller extends AUser implements Serializable {
   private int number_Photo = 0; //Количество фотографий селфи
   private transient ArrayList<Post> posts_list = new ArrayList<Post>();
   private boolean Agreement = false;
   private int Num_ReqTo = 0; // Количество обращенний
   private Date timeOfRegistration; // Время регистрации
   private int Points = 0; // Очки - деньги\
   private int Credit = 0;
   private int AllPoints; // Все очки вместе
   private int Current_post;
   private boolean havePhoto = false; //Проверяем если у пользователя есть фото
   private File UserDir;//Корреная папка для пользователя
   private File UserFN; ////папка для хранения фоток и личной информации
   private File UserFN2; ////запасная папка для хранения фоток и личной информации\
   private File Posts; //папка для постов
   private File new_post;
   private File SellerPhoto;
   private transient BufferedImage Seller_Photo;
   private Post post;
   private HashMap<AUser,ReView> reViews = new HashMap<AUser,ReView>();
   private ArrayList<ReView> Reviews = new ArrayList<ReView>();
   private ArrayList<ReView> reviewsNotFinished = new ArrayList<ReView>();


    public void makeNewReview(AUser user, ReView reView){
        reViews.put(user,reView);
    }


    public void setPost(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public boolean IsIncludePost(Post post){
        boolean res = false;
        Iterator<Post> iterator = posts_list.iterator();
        while (iterator.hasNext()){
           Post p = iterator.next();
               if(p.equals(post)){
                   System.out.println("Успех !!!");
                   res = true;
           }
        }
        return res;
    }

    public Seller(AUser aUser){
        super(aUser);
        boolean mm = make_Direktory();
        System.out.println(mm);
    }

    public void setPosts_Data(ArrayList<Post> posts){
        posts_list = new ArrayList<Post>(posts);

    }

    public void setPosts_Photo(){
        for(int j = 1; j<= posts_list.size();j++){
            Post p = posts_list.get(j-1);
            p.setImg(make_Oldpost_Dir(j));
            p.setThumb(make_Oldpost_DirThumb(j));
            System.out.println("OK");
        }
    }



    public void set_Photo(){
        int i = 0;
        while (!getDir_forPhoto(i, UserFN).exists() && i != 10){
            i++;
        }
        if(i == 10 && (UserFN2).exists()){
            System.out.println("Error !!!");
            SellerPhoto =  UserFN2;
        } else {
            System.out.println(" Not Error!");
            SellerPhoto = getDir_forPhoto(i, UserFN);
        }
    }

    public Date getTimeOfRegistration() {
        return timeOfRegistration;
    }

    public void setTimeOfRegistration(Date timeOfRegistration) {
        this.timeOfRegistration = timeOfRegistration;
    }

    public File getSellerPhoto() {
        return SellerPhoto;
    }

    public boolean HavePost(){
        if(post == null){
            return false;
        }
        else{
            return true;
        }
    }

    public ReView getReView(AUser user) {
        return reViews.get(user);
    }
    public ReView DeleteReView(AUser user) {
        return reViews.remove(user);
    }



    public void setReviews(ArrayList<ReView> reviews) {
        for(int i = 0; i < reviews.size(); i++){
            if(reviews.get(i).isBuyerGiveReV()){
                Reviews.add(reviews.get(i));
            } else {
                reviewsNotFinished.add(reviews.get(i));
            }
        }
        System.out.println(reviews.size() + "HERE   ");

    }

    public Collection<ReView> getReViews() {
        return reViews.values();
    }
    public ArrayList<ReView> getReviews(){
        return Reviews;
    }

    public ReView Remove_ReView(AUser aUser){ //Удаляем из связи продавца
        return reViews.remove(aUser);
    }

    public void DeleteReview(ReView reView){
        if(reviewsNotFinished.contains(reView)) {
            reviewsNotFinished.remove(reView);
        }
        if(Reviews.contains(reView)) {
            Reviews.remove(reView);
        }
    }
    public void addFReview(ReView reView){ //Добавляем во все отзывы продавца
        Reviews.add(reView);
    }
    public void addNotFinishedReview(ReView reView){ //Добавляем в незаконФЧеные отзывы продавца
        reviewsNotFinished.add(reView);
    }


    public void Remove_Post(int num_of_Post, PostManager manager){ //Этот метод вызывается мной и пользователем, делает запрос к рекламному менеджеру, который удаляет к себя данный пост
        Post Del = posts_list.remove(num_of_Post);
        Del.DELETEPOST(num_of_Post);
        /*if(manager.Including_WA_List(Del)){
            manager.delPostFromWaitingList(Del);
        }*//*
        if(Del.getAgreement()){
            manager.DeletePost(Del);
        }*/
        manager.DeletePost(Del);
        Del = null;
        post = null;
    }
    public void Remove_Post(Post Del, PostManager manager){ //Этот метод для, контролера
        Del.DELETEPOST(Del.getNumber_Post());
        manager.DeletePost(Del);
        posts_list.remove(Del);
        Del = null;
        post = null;
    }
    public void remove_Current_Post(){
        post = null;
    }

    public void make_Post(){
        post = new Post((posts_list.size() + 1),this.getUserId());
    }

    public boolean isTextInPost(){
        boolean answer = false;
        if(!post.getLocations().isEmpty() || !post.getPrices().isEmpty() || post.getDescription() != null || post.getKind() != null || post.getKind_B() != null){
            answer = true;
        }
        return answer;
    }

    public void addDescription(String description){
        post.setDescription(description);
    }

    public void addKind(String Kind){
        post.setKind(Kind);
    }

    public void addKindB(String KindB){
        post.setKind_B(KindB);
    }

    public void addLocation(String loc){
        post.addLocation(loc);
    }

    public void addPrice(String price){
        post.addPrice(price);
    }

    public void Agree_Post(){
        if(post.AddPostToDb()) {
            posts_list.add(post);
            remove_Current_Post();
        }
    }

    public void removeAllText_fromPost(){
        this.post.setDescription(null);
        this.post.cleanPrices();
        this.post.cleanLocations();
        this.post.setKind(null);
        this.post.setKind_B(null);
    }

    public void setImd(BufferedImage image){
        post.AddToDb_Image(make_Newpost_Dir(),make_Newpost_DirThumb(),image);
    }

    public void setSeller_Photo(BufferedImage seller_Photo) {
        number_Photo++;
        if(getDir_forPhoto(number_Photo, UserFN).exists()){
            number_Photo++;
        }
            try {
                ImageIO.write(seller_Photo, "jpg", getDir_forPhoto(number_Photo, UserFN));
                SellerPhoto = getDir_forPhoto(number_Photo, UserFN);
                havePhoto = true;
                Seller_Photo = seller_Photo;
            } catch (IOException e) {
                setSecSellerPhoto(seller_Photo);
                e.printStackTrace();
            }

    }
    private void setSecSellerPhoto(BufferedImage seller_Photo) {
        if(getDir_forPhoto(number_Photo, UserFN2).exists()){
            number_Photo++;
        }
            try{
                ImageIO.write(seller_Photo, "jpg", getDir_forPhoto(number_Photo, UserFN2));
            } catch (IOException a){
                a.printStackTrace();
            }
        Seller_Photo = seller_Photo;
    }



    public void increase_numLocButtons(){
        post.increase_numLocationByttons();
    }

    public void increase_numPriceButtons(){
       post.increase_numPriceByttons();
    }

    public boolean isHavePhoto() {
        return havePhoto;
    }




    private File getDir_forPhoto(int num){//Удалить если он не найдет применение
        UserFN.getAbsolutePath();
        File photo = new File("C:\\Users\\pavel\\Desktop\\Aizi\\" + this.getUserId() + "\\ " + "photo" + "\\" + num);
        return photo;
    }

    private File make_Newpost_Dir(){ //Создает папку для конкретного поста
        new_post = new File(Posts.getAbsolutePath() + "\\" + (posts_list.size() + 1) + ".jpeg");
        new_post.mkdir();
        return new_post;
    }
    private File make_Newpost_DirThumb(){ //Создает папку для конкретного поста
        new_post = new File(Posts.getAbsolutePath() + "\\" + (posts_list.size() + 1) + "thumb.jpeg");
        new_post.mkdir();
        return new_post;
    }
    public ArrayList<Post> getPosts_list() {
        return posts_list;
    }

    private File make_Oldpost_Dir(int i){ //Создает папку для конкретного поста
        new_post = new File(Posts.getAbsolutePath() + "\\" + i + ".jpeg");
        return new_post;
    }
    private File make_Oldpost_DirThumb(int i){ //Создает папку для конкретного поста
        new_post = new File(Posts.getAbsolutePath() + "\\" + i + "thumb.jpeg");
        return new_post;
    }

    private File getDir_forPhoto(int num,File dir){
        File photo = new File(dir.getAbsolutePath() + "\\" + num + ".jpeg");
        return photo;
    }

    public boolean isAgreement() {
        return Agreement;
    }

    public void setAgreement(boolean agreement) {
        Agreement = agreement;
    }

    public int getNum_ReqTo() {
        return Num_ReqTo;
    }

    public void setNum_ReqTo(int num_ReqTo) {
        Num_ReqTo = num_ReqTo;
    }

    public int getPoints() {
        return Points;
    }



    public void setCredit(int credit) {
        Credit = credit;
    }

    public int getCredit() {
        return Credit;
    }

    public void DecreaseCredit(int credit) {
        Credit = Credit + credit;
    }

    public void increaseCredit(int credit) {
        Credit = Credit + credit;
    }
    public void degreeaseCredit(int credit) {
        Credit = Credit - credit;
    }
    public void setPoints(int points) { //Тут будет вся работа
        Points = points;
    }

    public void incresePoints(int points) { //Тут будет вся работа
        Points = Points + points;
    }
    public void degreeasePoints(int points) { //Тут будет вся работа
        Points = Points - points;
    }
    public int getAllPoints() {
        return AllPoints;
    }

    public void setAllPoints(int allPoints) {
        AllPoints = allPoints;
    }


    private boolean make_Direktory(){
        UserDir = new File("C:\\Users\\pavel\\Desktop\\Aizi\\" + this.getUserId()); //Создаем корреную папку для пользователя
        UserDir.mkdir();
        Posts  = new File("C:\\Users\\pavel\\Desktop\\Aizi\\" + this.getUserId() + "\\Post"); //Создаем папку для постов
        Posts.mkdir();
        String str = this.getFirst_name();
        System.out.println(this);
        String result = str.replaceAll("\"","");
        //String result = str.replaceAll("\\p{Cntrl}", "");
        UserFN2 = new File("C:\\Users\\pavel\\Desktop\\Aizi\\" + this.getUserId() + "\\ " + "photo"); //Создаем папку для хранения фоток и личной информации
        UserFN = new File("C:\\Users\\pavel\\Desktop\\Aizi\\" + this.getUserId() + "\\ " + result); //Создаем папку для хранения фоток и личной информации
        UserFN2.mkdir();
        return UserFN.mkdir();
    }

}
