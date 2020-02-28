package sample.UsersData;

import org.telegram.telegrambots.meta.api.objects.User;
import sample.TelegramPart.Promo.PostManager;
import sample.UsersData.Database.DataBaseHandler;
import sample.UsersData.Database.Posts_DataBase;
import sample.UsersData.Database.ReviewDataBase;
import sample.UsersData.PromoteSettings.PromoContainer;
import sample.UsersData.Reviews.ReView;
import sample.UsersData.Users.AUser;
import sample.UsersData.Users.Seller;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersManager {

    private int CountofReqToBot;
    private ReviewDataBase review_dataBase = new ReviewDataBase();
    private Posts_DataBase posts_dataBase = new Posts_DataBase();
    private DataBaseHandler db = new DataBaseHandler();
    private HashMap<AUser,Seller> usersWithConnection = new HashMap<AUser,Seller>();
    private ArrayList<Seller> sellers = new ArrayList<Seller>(); // Скорее всего, эта штука - не нужна
   // private ArrayList<ReView> ReViews = new ArrayList<ReView>();
    private AUser auser; // Скорее всего, эта штука - не нужна
    private ReviewDataBase revies = new ReviewDataBase();

    public DataBaseHandler getDataBase() {
        return db;
    }

    private ArrayList<Long> blockUsers = new ArrayList<>();

    private HashMap<Integer,AUser> usersOnline;

    public ArrayList<AUser> getUsersOnline() {
        ArrayList<AUser> users = new ArrayList<AUser>();
        users.addAll(usersOnline.values());
        return users;
    }

    public synchronized Collection<AUser> getUsersValOnline() {
        return usersOnline.values();
    }

    public synchronized Collection<Seller> getAllSellers() {
       return sellers;
    }

    public Collection<ReView> getReViews(){
        ArrayList<ReView> revs = new ArrayList<>();
        for(Seller seller : sellers){
            revs.addAll(seller.getReviews());
        }
        return revs;
    }

    public String setPersonalUserImut(AUser user){
        AUser aUser = user;
        String personal_number = createPesonalNumber();
        aUser.setPersonalNum(personal_number);
        db.insertPersonalSellerNumber(personal_number,aUser.getUserId());
        return personal_number;
    }

    private String createPesonalNumber(){
        Random pn = new Random();
        String s = String.valueOf(pn.nextInt(900000) + 10000);
        return s;
    }

    public void BLockUser(int userId){
        blockUsers.add(Long.valueOf(userId));
        db.BlockAuser(userId);

    }
    public void DeleteSeller(int sellerId){
        //Seller seller = (Seller) usersOnline.remove(sellerId);
        Seller seller = (Seller) usersOnline.get(sellerId);
        seller.setSeller(false);
        sellers.remove(seller);
        db.DeleteAuser(sellerId);
    }

    private void addCountofReqToBot(int num){
        CountofReqToBot = CountofReqToBot + num;
    }

    public  Seller UserConnnecton(AUser aUser){
        return usersWithConnection.get(aUser);
    }

    public Seller CreateConnection(AUser user, int sellerId){ //Создаем связь после того, как пользователь нажал на пост
        Seller seller = GettingSeller(sellerId);
        if(seller != null){
            seller.makeNewReview(user,new ReView(seller,user,revies));
            usersWithConnection.put(user,seller);
            return seller;
        }else {
            return null;
        }
    }
    public void LoadConnection(AUser user, int sellerId,ReView reView){ //Создаем связь после того, как пользователь нажал на пост
        Seller seller = GettingSeller(sellerId);
        if(seller != null){
            seller.makeNewReview(user,reView);
            usersWithConnection.put(user,seller);

        }
    }
    public void CloseConnection(AUser aUser){//Тут мы закрываем связь - удаляем из списков - один в этом классе, другой у продавца
        Seller seller = usersWithConnection.remove(aUser);
        ReView reView = seller.getReView(aUser);
        if(reView.isBuyerGiveReV()){
            //ReViews.add(reView);
            seller.addFReview(reView);
            seller.Remove_ReView(aUser);
        } else if((reView.isRightforReView_Was202() || reView.isRightforReView_Was201()) && reView.isAskReView_wasClicked()){
            //ReViews.add(reView);//Возможно сделает проблемы
            seller.addNotFinishedReview(reView);
            seller.Remove_ReView(aUser);
        } else {
            seller.Remove_ReView(aUser);
        }
    }
    public void deleteReView(ReView reView){
        Seller seller = reView.getSeller();
        review_dataBase.DeleteReView(seller.getUserId(),reView.getNum_ofReView());
        seller.DeleteReview(reView);//Явно ошибка
    }
    public void RemoveReView(AUser aUser){
        Seller seller = usersWithConnection.get(aUser);
        ReView reView = seller.getReView(aUser);
        if((!reView.isRightforReView_Was201() || !reView.isRightforReView_Was202())){
            reView.RemoveMySelf(seller.getUserId());
        }
        //seller.Remove_ReView(aUser);
    }

    public Seller findWithUsername(String username){
        Iterator<Seller> iterator = sellers.iterator();
        while (iterator.hasNext()) {
            Seller seller = iterator.next();
            if (seller.getUsername() != null && seller.getUsername() != "") {
                Pattern patternb = Pattern.compile(seller.getUsername());
                Matcher matcherb = patternb.matcher(username);
                if (matcherb.find() && seller.getUsername().equals(username)) {
                    return seller;
                }
            }
        }
        return null;
    }


    public synchronized void increaseCreditsToSeller(Seller seller, int value){
         //Получаем пользователя из нашего списка
        seller.increaseCredit(value); // увеличиваем число у самого экземпляра
        db.setCreditsToSeller(seller.getUserId(),seller.getCredit()); //Заносим в Бд
    }
    public synchronized void degreaseCreditsToSeller(Seller seller, int value){
        //Получаем пользователя из нашего списка
        seller.degreeaseCredit(value); // увеличиваем число у самого экземпляра
        db.setCreditsToSeller(seller.getUserId(),seller.getCredit()); //Заносим в Бд
    }

    public synchronized void incresePointsSeller(Seller seller,int points) { //Тут будет вся работа
        Seller seller1 = seller;
        seller1.incresePoints(points);
        db.addPointsToSeller(seller1.getUserId(),seller1.getPoints());
    }
    public synchronized void degreasePointsSeller(Seller seller,int points){
        Seller seller1 = seller;
        seller.degreeasePoints(points);
        db.addPointsToSeller(seller1.getUserId(),seller1.getPoints());
    }

    public UsersManager(){
        System.out.println("Constructor UserManager");
        usersOnline = new HashMap<Integer,AUser>();
        getData();
    }

    private AUser findUser(int userId){
        AUser currentUser = usersOnline.get(userId);
        return currentUser;
    }


    private void ad_AUser(int userId,AUser aUser){
        usersOnline.put(userId,aUser);
    }

    private boolean isUserinDB(User user){ //Делает запрос к базе данных и проверяет если пользователь там уже есть? Если его там не было вносит базовую информацию о нем в бд
        if(db.IsNewUser(user.getId())){//Новый
            db.insertNew_UserId(user.getId());
            db.insertNew_first_name(user.getId(),user.getFirstName());  //Вносим его данные в базу данных
            if (user.getUserName() != null){//метод, который вносит юзер нейм в таблицу при его наличии
                db.insertNew_UserName(user.getId(),user.getUserName());
            }
            if(user.getLastName() != null){ // метод, который вносит фамилию в таблицу при ее наличии
                db.insertNew_LastName(user.getId(),user.getLastName());
            }
            return false;
        }
        else{    //Уже есть в бд
            return true;
        }
    }

    public boolean IsNewUser(User user){ //Проверяем если пользователь новый
        int userId = user.getId();
        if(findUser(userId) == null){ //Если пользователя не ни в массиве, не в базе данных - возвращаем правду - ведь пользователь новый
            if(!isUserinDB(user)){
                ad_AUser(userId,new AUser(user)); // Эта строчка и та, что ниже делают одно и тоже, просто тут все происходит без обращений к бд
                //ad_AUser(userId,db.getUserData_with_userId(user.getId()));
                return true; }
        }
        return false;
    }


    public ArrayList<Post> getAllPostsDB(){
        ArrayList<Seller> sellerss = getAllSellersDB();
        ArrayList<Post> posts = new ArrayList<Post>();
        for (Seller seller: sellerss){
            posts.addAll(seller.getPosts_list());
        }
       return posts;
    }

    public ArrayList<Post> getAllPosts(){
        ArrayList<Post> posts = new ArrayList<Post>();
        for (Seller seller: sellers){
            posts.addAll(seller.getPosts_list());
        }
        return posts;
    }

    public ArrayList<Post> getAllAgreePosts(){
        ArrayList<Post> posts = new ArrayList<Post>();
        for (Seller seller: sellers){
            ArrayList<Post> posts1 = seller.getPosts_list();
            for(Post post : posts1){
                if(post.getAgreement()) {
                    posts.add(post);
                }
            }
        }
        return posts;
    }

    public ArrayList<Seller> getAllSellersDB(){ //
        //Скорее всего этот метод мне не понадобится
        ArrayList<Seller> result = new ArrayList<>();
        ArrayList<AUser> users = db.getData();
        Iterator <AUser> us = users.iterator();
        while (us.hasNext()){
            AUser user = us.next();
            if(user.isSeller()){ //Выполняем проверку Seller на то, что у него есть
                Seller seller = new Seller(user);
                db.SetSellerInfo(seller);
                if(db.HasSeller_posts(user.getUserId())) { //Если у него существуют посты
                    ///* *
                    // Тут надо написать так, чтобы у селера устанавливались доп поля при его восстановление
                    // из таблицы в шеме info которая будет содержать отзывы
                    // Еще необходимо также восстанавливать доп поля Seller
                    //
                    //
                    //
                    ArrayList<Post> posts = posts_dataBase.getPosts(seller.getUserId());
                    seller.setPosts_Data(posts);
                    seller.setPosts_Photo();
                    seller.set_Photo();
                    result.add(seller);
                } else { //Если у него нет постов
                    seller.set_Photo();
                    result.add(seller);
                }

            } else {
            }
        }
        return result;
    }

    public ArrayList<ReView> getReViewsDb(){ //В этом методе, мы будем получать данные всех пользователей и записывать их в список
        //Скорее всего этот метод мне не понадобится
        ArrayList<ReView> reViews = new ArrayList<>();
        ArrayList<AUser> users = db.getData();
        Iterator <AUser> us = users.iterator();
        while (us.hasNext()){
            AUser user = us.next();
            if(user.isSeller()){ //Выполняем проверку Seller на то, что у него есть
                Seller seller = new Seller(user);
                if(db.HasSeller_reviews(seller.getUserId())){
                    ArrayList<ReView> revs = review_dataBase.getReView(seller.getUserId(),this);
                    if(!revs.isEmpty()){
                        reViews.addAll(revs);
                        seller.setReviews(revs);
                    }
                }
            }
        }
        return reViews;

    }

    public void getData(){ //В этом методе, мы будем получать данные всех пользователей и записывать их в список
         //Скорее всего этот метод мне не понадобится
         ArrayList<AUser> users = db.getData();
         Iterator <AUser> us = users.iterator();
         while (us.hasNext()){
              AUser user = us.next();
              if(user.isSeller()){ //Выполняем проверку Seller на то, что у него есть
                  Seller seller = new Seller(user);

                  db.SetSellerInfo(seller);

                  if(db.HasSeller_posts(user.getUserId())) { //Если у него существуют посты
                      ///* *
                      // Тут надо написать так, чтобы у селера устанавливались доп поля при его восстановление
                      // из таблицы в шеме info которая будет содержать отзывы
                      // Еще необходимо также восстанавливать доп поля Seller
                      //
                      //
                      //
                      ArrayList<Post> posts = posts_dataBase.getPosts(seller.getUserId());
                      seller.setPosts_Data(posts);
                      seller.setPosts_Photo();
                      seller.set_Photo();


                  } else { //Если у него нет постов

                      seller.set_Photo();
                  }
                  sellers.add(seller);
                  usersOnline.put(seller.getUserId(), seller);


              } else {
              usersOnline.put(user.getUserId(),user);
              }
         }
        SetReViews(sellers);
         System.out.println(usersOnline.size());
         System.out.println(sellers.size());
    }
    private void SetReViews(ArrayList<Seller> sellers){
        for (int i = 0; i < sellers.size(); i++) {
            Seller seller = sellers.get(i);
            if (db.HasSeller_reviews(seller.getUserId())) {
                ArrayList<ReView> revs = review_dataBase.getReView(seller.getUserId(), this);
                if (!revs.isEmpty() && revs != null) {
                    //ReViews.addAll(revs);
                    seller.setReviews(revs);
                }
            }
        }
    }

    public boolean setAgreementToPost(Post post, PostManager postManager){
        boolean Succes = true;
        if(!post.getAgreement()) {
            post.AddAgreement(true,posts_dataBase);

            increaseCreditsToSeller(GettingSeller(post.getUserId()), post.getCredits());
            //Не только база данных!!!
            Seller seller = (Seller) usersOnline.get(post.getUserId());
            if (post.getHaveFree()) {
                postManager.Add_Free_Post(post, seller);
            }
            if (post.getListOfPromes() != null) {
                PromoContainer[] ListOfPromes = post.getListOfPromes();
                if (ListOfPromes[0] != null) {
                    post.setPromo(ListOfPromes[0]);
                    if (!postManager.addMtPost(post, seller)) {
                        Succes = false;
                    }
                    post.setNullPromo();
                }
                if (ListOfPromes[1] != null) {
                    post.setPromo(ListOfPromes[1]);
                    postManager.Add_Ot_Post(post, seller);
                    post.setNullPromo();
                }
            }
        }
        return Succes;
    }//*Метод, который вызывается при подверждении поста из Gui - он
    //проверяет количество рекламных настроек и выставляет кредит Продавцу */

    public void IncreseReqToSeller(int userId){
        AUser user = GettingUser(userId);
        user.adDReqToSellers(1);
        db.increaseReqToSeller(userId);
    }
    public void SetWasLastTime(int userId){
        AUser user = GettingUser(userId);
        Date date = new Date();
        user.setDate(date.toString());
        db.setWasLastTimeSeller(userId,date);
    }

    public void IncreaseCountOfReqToBot(AUser user){
        addCountofReqToBot(1);
        user.adDReqToBot(1);
        db.IncreaseReqToBot(user.getUserId());
    }

    ///-------------------------   Следующие методы устанавливают информацию о покупателе - их используют продавец  ->>>>
    public void AddProtectedUsername(int userId, String username){
        AUser user = GettingUser(userId);
        user.adDProtect_Points(username);
        db.ProtectedPoints(userId,username);
    }
    public void AddComplainToUser(int userId,String Comlain){
        AUser user = GettingUser(userId);
        user.adDComlains_fromSeller(Comlain);
        db.addComlaint(userId,Comlain);
    }
    public void AddComplainNumUser(int userId){
        AUser user = GettingUser(userId);
        user.adDComlains_number(1);
        db.increaseComlaintNum(userId);
    }
    public void AddSuccesDealsTOUser(int userId){
        AUser user = GettingUser(userId);
        user.adDSucces_Deals(1);
        db.increaseSuccesDealsNUm(userId);
    }
    //--------------------------------------------------<<<
    public void UserBecomesSelller(int userId){
            Seller seller = new Seller(findUser(userId));
            db.changeUser_to_Seller(userId);
            db.RegistSeller(userId,new Date());
            seller.setTimeOfRegistration(new Date());
            removeUser(userId);
            sellers.add(seller);
            ad_AUser(userId,seller);
    }

    public AUser GettingUser(User user){
        return findUser(user.getId());
    }
    public AUser GettingUser(int user){
        return usersOnline.get(user);
    }

    public Seller GettingSeller(int userId) {
        Seller seller;
        if (usersOnline.containsKey(userId)) {
            if (usersOnline.get(userId).isSeller()) { //Если пользователь впринципе не существует, а не только не является продавцем - то данный метод выкидывает ошибку
                seller = (Seller) usersOnline.get(userId);
            } else {
                seller = null;
            }
            return seller;
        } else {
            return null;
        }
    }

    private void removeUser(int userId){
        usersOnline.remove(userId);
    }

}
