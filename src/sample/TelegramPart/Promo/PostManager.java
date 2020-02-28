package sample.TelegramPart.Promo;

import sample.GuiPart.Errors;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.TelegramPart.Text.TextForKeybors;
import sample.UsersData.Post;
import sample.UsersData.PromoteSettings.PromoContainer;
import sample.UsersData.Users.Seller;
import sample.UsersData.UsersManager;

import java.io.*;
import java.util.*;

public class PostManager implements Serializable{

    private ArrayList<String> GroupsIdList = new ArrayList<String>();
    private TextForKeybors textForKeybors = new TextForKeybors();
    private UsersManager usersManager;
    private long Timee = 30000;
    private long Time = 300000;

    private Timer FreeTimer;
    private Timer OtTimer;
    private Timer MtTimer;
    private Timer MyTimer;
    private int CountPost_inMyList = 0;
    private ArrayList<PostWithTime> WaitingAgrement = new ArrayList<PostWithTime>(); //Содержиь подданные, но не подтвержденные посты, данный список существует для меня(оператора)
    private TreeSet<PostWithTime> MtPostss = new TreeSet<PostWithTime>();
    private ArrayList<PostWithTime> MyPostsList = new ArrayList<PostWithTime>();
    private TreeSet<PostWithTime> OtPosts = new TreeSet<PostWithTime>();
    private TreeSet<PostWithTime> FreePosts = new TreeSet<PostWithTime>();
    private HashSet<Post> AllAgreementPosts = new HashSet<>();
    File file = new File("C:\\Users\\pavel\\Desktop\\Aizi\\Posts");
    File Otposts_FileName = new File("C:\\Users\\pavel\\Desktop\\Aizi\\Posts\\OneTimePosts.ser");
    File MyList = new File("C:\\Users\\pavel\\Desktop\\Aizi\\Posts\\MyListPosts.ser");
    File Mtposts_FileName = new File("C:\\Users\\pavel\\Desktop\\Aizi\\Posts\\ManyTimesPosts.ser");
    File Freeposts_FileName = new File("C:\\Users\\pavel\\Desktop\\Aizi\\Posts\\FreePosts.ser");
    File WaitingAgrement_FileName = new File("C:\\Users\\pavel\\Desktop\\Aizi\\Posts\\WaitingAgrementPosts.ser");
    //File GroupsId_FileName = new File("C:\\Users\\pavel\\Desktop\\Aizi\\AllAgrementPosts.ser");


    public PostManager(UsersManager s){
        file.mkdir();
        this.usersManager = s;
        try {
            LoadPosts();
        } catch (IOException e) {
            Errors.AddError("Cann`t to read Saved Posts 100");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addGroupToPostingId(String GroupId){
        GroupsIdList.add(GroupId);
        System.out.println(" Добавлен Id Group ");
    }
    public synchronized boolean Remove_GroupdId(String GropId){
        if(GroupsIdList != null && !GroupsIdList.isEmpty()){
           GroupsIdList.remove(GropId);
          /*  for (String str : GroupsIdList) {
                if (str.equals(GropId) || str == GropId) {
                    GroupsIdList.remove(str);
                }
            }*/
            return GroupsIdList.remove(GropId);
        }
        System.out.println("Cannot Remove");
        return false;
    }
    public ArrayList<String> getGroups_IdsList(){
        return GroupsIdList;
    }



    /* class PostTask extends TimerTask {
        TreeSet<PostWithTime> list;
        public PostTask(TreeSet<PostWithTime> list){
            this.list = list;
        }

        @Override
        public void run() {
            startFreeList(list.first());
            list.remove(list.first());

        }
    }*/
    public void setTimeToFreePosting(int a){
       Time = (a * 1000);
    }

    public synchronized void StopFreePosting(){
        FreeTimer.cancel();
    }
    public synchronized void StopOtPosting(){
        OtTimer.cancel();
    }
    public synchronized void StopMtPosting(){
        MtTimer.cancel();
    }
    public synchronized void StopMyPosting(){
        MyTimer.cancel();
    }
    public void StopPosting(){
        CountPost_inMyList = 0;
        FreeTimer.cancel();
        OtTimer.cancel();
        MtTimer.cancel();
        MyTimer.cancel();
    }
    public synchronized void ResumeMyPosing(){
        CountPost_inMyList++;
        if(CountPost_inMyList == (MyPostsList.size())){
            CountPost_inMyList = 0;
        }
        StartMyList(Time);
    }
    public synchronized void ResumeFreePosting(){
        FreePosts.remove(FreePosts.first());
        StartFree(180000);
    }
    public synchronized void ResumeMtPosting(PostWithTime po ){
        PostWithTime p = po;//MtPostss.first();
        MtPostss.remove(p);
        if(p.getLoops() != -1 && p.getLoops() != 0) {
            p.increaseDate();
            p.degLoops();
            MtPostss.add(p);
            StartMt();
        } else {
            StartMt();
        }
    }
    public synchronized void ResumeOtPosing(){
        OtPosts.remove(OtPosts.first());
        StartOt();
    }
    public void SSFree(){
        FreeTimer = new Timer();
        StartFree();
    }
    public void SSMt(){
        OtTimer = new Timer();
       StartMt();
    }
    public void SSOt(){
        MtTimer = new Timer();
        StartOt();
    }
    public void SSMy(){
        MyTimer = new Timer();
        StartMyList(5000);
    }

    public void StartMt(){//Метод получает отзыв продавца,берет первый пост из списка Mt и создает Объект публикации на определенное время
       /* PostWithTime post = MtPostss.first();
        KeyBoardData keyBoardData = textForKeybors.get_PostInGroupKeyboard(post.getUserId());
        Posting_Task posting_task = new Posting_Task(post,keyBoardData,GroupsIdList,this,usersManager);
        posting_task.setMt(true);
        System.out.println(new Date(post.getSeconds()).toString());
        OtTimer.schedule(posting_task,new Date(post.getSeconds()));*/

    }
    public void StartFree(){//Метод получает отзыв продавца,берет первый пост из списка Mt и создает Объект публикации на определенное время
    /*
        PostWithTime post = FreePosts.first();
        KeyBoardData keyBoardData = textForKeybors.get_PostInGroupKeyboard(post.getUserId());
        Posting_Task posting_task = new Posting_Task(post,keyBoardData,GroupsIdList,this,usersManager);
        posting_task.setFree(true);
        FreeTimer.schedule(posting_task,new Date(new Date().getTime() + 180000));*/
    }
    public void StartFree(long mills){//Метод получает отзыв продавца,берет первый пост из списка Mt и создает Объект публикации на определенное время

    }
    public void StartOt(){//Метод получает отзыв продавца,берет первый пост из списка Mt и создает Объект публикации на определенное время

    }
    public void StartMyList(long mills){//Этод метод будет запускать мой бесконечный список

    }

    //---------------------




///-----------------------Методы добавления Поста В один из Списков------------------------------------------------

    public boolean addMtPost(Post post,Seller seller){ //Добавляем пост в список платных публикаций
        PostWithTime postWithTime = new PostWithTime(post,seller);
        postWithTime.setMt();
        boolean Succes = MtPostss.add(postWithTime);
        AllAgreementPosts.add(post);
        return Succes;
    }

    public boolean Add_Ot_Post(Post post, Seller seller){
        PostWithTime postWithTime = new PostWithTime(post,seller);
        postWithTime.setOt();
        boolean Succes = OtPosts.add(postWithTime);
        AllAgreementPosts.add(post);
        return Succes;
    }

    public boolean Add_Free_Post(Post post,Seller seller){
        boolean Succes = false;
        PostWithTime postWithTime = new PostWithTime(post,seller);
        postWithTime.setFree();
        if(FreePosts.isEmpty()){
             Succes = FreePosts.add(postWithTime);
             SSFree();
        } else {
             Succes = FreePosts.add(postWithTime);
        }

        AllAgreementPosts.add(post);

        return Succes;
    }
    public boolean Add_My_Post(Post post,Seller seller){
        boolean Succes = false;
        PostWithTime postWithTime = new PostWithTime(post,seller);
        postWithTime.setFree();
        Succes = MyPostsList.add(postWithTime);
        AllAgreementPosts.add(post);
        return Succes;
    }

    //***************В Очередь-Ожидания
    public void AddToQute(Post post,Seller seller){ //Очередь ожидания
        post.setHaveFree(false);
        WaitingAgrement.add(new PostWithTime(post,seller));
        for (PostWithTime postWithTime : WaitingAgrement ){
            Post post1 = postWithTime.getPost();
           // System.out.println("decription  " + post1.getDescription() + " num of post " + post1.getNumber_Post() + " Time " + postWithTime.getTime().toString());
        }

    }
    public void AddToQute(Post post,Seller seller,boolean a){ //Очередь ожидания
        post.setNullPromo();
        WaitingAgrement.add(new PostWithTime(post,seller));
       /* for (PostWithTime postWithTime : WaitingAgrement ){
            Post post1 = postWithTime.getPost();
            // System.out.println("decription  " + post1.getDescription() + " num of post " + post1.getNumber_Post() + " Time " + postWithTime.getTime().toString());
        }*/

    }

//-------------------------------------------------------------------------------------------------------------------------


    public Collection<Post> getAllAgrementPost(){
        return AllAgreementPosts;
    }
    public Collection<PostWithTime> getFreePostList(){ //Возвращает список Одобренных бесплатных постов Для Таблицы
        return FreePosts;
    }
    public Collection<PostWithTime> getMtPostList(){ //Возвращает список Одобренных многоразовых постов Для Таблицы
        return MtPostss;
    }
    public Collection<PostWithTime> getOtPostList(){ //Возвращает список Одобренных Одноразовых постов Для Таблицы

        return OtPosts;
    }
    public Collection<PostWithTime> getMyPostList(){ //Возвращает список Одобренных Одноразовых постов Для Таблицы
        return MyPostsList;
    }
    public Collection<PostWithTime> getWaitingPostList(){ //Возвращает список Неодобренных постов Для Таблицы
    return WaitingAgrement;
  }


  //-------------------------------------------Следующие методы для проверки наличия постов в списках

    public boolean Including_WA_List(Post post){ //Проверяем нахождение поста в списке Постов-Ожидающих подтверждение
     boolean Succes = false;
     for(PostWithTime postTime: WaitingAgrement){
         if(postTime.getPost().equals(post)  && postTime.getUserId() == post.getUserId()){
             return true;
         }
     }
     return Succes;
  }
    public boolean Including_WA_CurrentTimeSeting(Post post,PromoContainer promoContainer){ //Проверяем нахождение поста в списке Постов-Ожидающих подтверждение
        boolean Succes = false;
        for(PostWithTime postTime: WaitingAgrement){
            if(postTime.getPost().equals(post)  && postTime.getUserId() == post.getUserId() && postTime.getSeconds().equals(promoContainer.type_is_Mt_promo()? promoContainer.getMt_promo().getTimeV():promoContainer.getOt_promo().FullTime())){
                return true;
            }
        }
        return Succes;
    }
    public boolean Including_WA_Free(Post post){ //Проверяем нахождение поста в списке Постов-Ожидающих подтверждение
        boolean Succes = false;
        for(PostWithTime postTime: WaitingAgrement){
            if(postTime.getPost().equals(post) && postTime.getUserId() == post.getUserId() && postTime.getFree()){
                Succes = true;
                return true;
            }
        }
        return Succes;
    }
  //----------------------------------------Следующие посты используются для устрановления галочек кнопок
    public ArrayList<PostWithTime> AllMtSettingsForCurrentPost(Post post){// Проверяет наличие поста в списке с рекламой, если он там есть возвращает список Временных настроек
      ArrayList<PostWithTime> promes = new ArrayList<>();
      for(PostWithTime p : MtPostss){
          if(p.getUserId() == post.getUserId() && p.getPost().getNumber_Post() == post.getNumber_Post()){
              promes.add(p);
          }
      }
      return promes;
  }
    public ArrayList<PostWithTime> AllOtSettingsForCurrentPost(Post post){// Проверяет наличие поста в списке с рекламой, если он там есть возвращает список Временных настроек
      ArrayList<PostWithTime> promes = new ArrayList<>();
      for(PostWithTime p : OtPosts){
          if(p.getUserId() == post.getUserId() && p.getPost().getNumber_Post() == post.getNumber_Post()){
              promes.add(p);
          }
      }
      return promes;
  }
    public ArrayList<PostWithTime> FreeTimeSettingsForCurrentPost(Post post){
      ArrayList<PostWithTime> promes = new ArrayList<>();
      for(PostWithTime p : FreePosts){
          if(p.getUserId() == post.getUserId() && p.getPost().getNumber_Post() == post.getNumber_Post()){
              promes.add(p);
          }
      }
      return promes;
  }
//----------------------------------------Следующие посты используются для устрановления галочек кнопок
    public boolean IsnclMt(Post post){
      boolean res = false;
        for(PostWithTime p : MtPostss){
            if(p.getUserId() == post.getUserId() && p.getPost().getImagePath().equals(post.getImagePath())){
                return true;
            }
        }
        return res;
    }

    public boolean IsnclOt(Post post){
      boolean res = false;
      for(PostWithTime p : OtPosts){
          if(p.getUserId() == post.getUserId() && p.getPost().getImagePath().equals(post.getImagePath())){
              return true;
          }
      }
      return res;
    }

    public boolean IsnclFree(Post post){ // Проверяет наличие поста в списке с рекламой, если он там есть возвращает список Временных настроек
      boolean res = false;
      for(PostWithTime p : FreePosts){
          if(p.getUserId() == post.getUserId() && p.getPost().getImagePath().equals(post.getImagePath())){
              return true;
          }
      }
      return res;
    }


//---------------------------------------------Методы удаления----------------------------

    public boolean DeletePostWithCurrentTime(Post post,long time){
        boolean succues = false;
        if(AllOtSettingsForCurrentPost(post) != null && !AllOtSettingsForCurrentPost(post).isEmpty()) {
            ArrayList<PostWithTime> OtList = AllOtSettingsForCurrentPost(post);
            for(PostWithTime Ot : OtList){
                if(Ot.getSeconds().equals(time)){
                    OtPosts.remove(Ot);
                    return true;
                }
            }
        }
        if(AllMtSettingsForCurrentPost(post) != null && !AllMtSettingsForCurrentPost(post).isEmpty()) {
            ArrayList<PostWithTime> MtList = AllMtSettingsForCurrentPost(post);
            for(PostWithTime Mt : MtList){
                Long a = new Long(Mt.getSeconds());
                Long b = new Long(time);
                if(a.equals(b)) {
                    System.out.println(Mt.getSeconds());
                    MtPostss.remove(Mt);
                }
            }
        }

        return succues;

    }

    public boolean DeletePWT_MtPosts(PostWithTime post){
        boolean succues = false;
        if(MtPostss.remove(post)){
            succues = true;
        }

        return succues;
    }
    public boolean DeletePWT_FreePosts(PostWithTime post){
        boolean succues = false;
        if(FreePosts.remove(post)){
            succues = true;
        }

        return succues;
    }
    public boolean DeleteMy_PostList(PostWithTime post){
        boolean succues = false;
        if(MyPostsList.remove(post)){
            succues = true;
        }

        return succues;
    }
    public boolean DeletePWT_OtList(PostWithTime post){
        boolean succues = false;
        if(OtPosts.remove(post)){
             succues = true;
        }

        return succues;
    }
    public boolean Delete_FreePosts(Post post){
        boolean succues = false;
        if(!FreePosts.isEmpty()){
            Iterator<PostWithTime> iterator = FreePosts.iterator();
            while (iterator.hasNext()){
                PostWithTime p = iterator.next();
                if(p.getPost().getUserId() == post.getUserId() && p.getPost().getImagePath().equals(post.getImagePath())) {
                    FreePosts.remove(p);
                    succues = true;
                }
            }

        }

        return succues;
    }



//----------------------------------------------------Удаление Постов из Списков
    public void DeletePost(Post post){
        int userId = post.getUserId();
        int numberPost = post.getNumber_Post();
        AllAgreementPosts.remove(post);
        for(int i = 0;i <= 4; i++) {
            Iterator<PostWithTime> iterator =  OtPosts.iterator();
            switch (i) {
                case 1:{iterator = MtPostss.iterator();}break;
                case 2:{iterator = FreePosts.iterator();}break;
                case 3:{iterator = WaitingAgrement.iterator();}break;
                case 4:{iterator = MyPostsList.iterator();}break;
            }
            while (iterator.hasNext()) {
                Post curP = iterator.next().getPost();
                if (userId == curP.getUserId() && numberPost == curP.getNumber_Post()) {
                    iterator.remove();
                }
            }
        }

    }

    public void DeletePostFromWaitingList(PostWithTime post){
        WaitingAgrement.remove(post);
    }

    public void delPostFromWaitingList(Post post){ // Удаляем Пост из Списка-Ожидания-Подтверждения
        Iterator<PostWithTime> iterator = WaitingAgrement.iterator();
        while (iterator.hasNext()){
            PostWithTime p = iterator.next();
            if(post.getPromo() != null) {
                if (p.getUserId() == post.getUserId() && p.getPost().getImagePath().equals(post.getImagePath()) && (p.getPromoContainer().equals(post.getPromo()) || p.getPromoContainer() == post.getPromo())) {
                    iterator.remove();
                }
            }else{
                if(post.getHaveFree() && p.getFree()){
                    iterator.remove();
                }
            }
        }

  }
    public void DelPostFromWaitingList(Post post){ // Удаляем Пост из Списка-Ожидания-Подтверждения
        Iterator<PostWithTime> iterator = WaitingAgrement.iterator();
        while (iterator.hasNext()){
            PostWithTime p = iterator.next();
            if(post == p.getPost() || p.getPost().equals(post)){
                iterator.remove();
            }
        }

    }

    public void SavePosts() throws IOException {

        //FileOutputStream GroupsIdStream = new FileOutputStream(GroupsId_FileName);
       // ObjectOutputStream GroupsIds = new ObjectOutputStream(GroupsIdStream);
       // GroupsIds.writeObject(AllAgreementPosts);
       // GroupsIds.close();

      FileOutputStream OtpostsStream = new FileOutputStream(Otposts_FileName);
      FileOutputStream MtpostsStream = new FileOutputStream(Mtposts_FileName);
      FileOutputStream FreepostsStream = new FileOutputStream(Freeposts_FileName);
      FileOutputStream WApostsStream = new FileOutputStream(WaitingAgrement_FileName);
      FileOutputStream MypostsStream = new FileOutputStream(MyList);
      ObjectOutputStream Otposts = new ObjectOutputStream(OtpostsStream);
      ObjectOutputStream Mtposts = new ObjectOutputStream(MtpostsStream);
      ObjectOutputStream Freeposts = new ObjectOutputStream(FreepostsStream);
      ObjectOutputStream WAposts = new ObjectOutputStream(WApostsStream);
      ObjectOutputStream Myposts = new ObjectOutputStream(MypostsStream);
      Otposts.writeObject(OtPosts);
      Mtposts.writeObject(MtPostss);
      Freeposts.writeObject(FreePosts);
      WAposts.writeObject(WaitingAgrement);
      Myposts.writeObject(MyPostsList);
      Otposts.close();
      Mtposts.close();
      Freeposts.close();
      Freeposts.close();
      Myposts.close();
  }

    public void LoadPosts() throws IOException, ClassNotFoundException {
      FileInputStream OtpostsStream = new FileInputStream(Otposts_FileName);
      FileInputStream MtpostsStream = new FileInputStream(Mtposts_FileName);
      FileInputStream FreepostsStream = new FileInputStream(Freeposts_FileName);
      FileInputStream WApostsStream = new FileInputStream(WaitingAgrement_FileName);
      FileInputStream MypostsStream = new FileInputStream(MyList);
      //FileInputStream GroupsIdStream = new FileInputStream(GroupsId_FileName);
      ObjectInputStream Otposts = new ObjectInputStream(OtpostsStream);
      ObjectInputStream Mtposts = new ObjectInputStream(MtpostsStream);
      ObjectInputStream Freeposts = new ObjectInputStream(FreepostsStream);
      ObjectInputStream WAposts = new ObjectInputStream(WApostsStream);
      ObjectInputStream Myposts = new ObjectInputStream(MypostsStream);
     // ObjectInputStream GroupsId = new ObjectInputStream(GroupsIdStream);
      OtPosts.addAll((TreeSet<PostWithTime>)Otposts.readObject());
      MtPostss.addAll((TreeSet<PostWithTime>)Mtposts.readObject());
      FreePosts.addAll((TreeSet<PostWithTime>)Freeposts.readObject());
      WaitingAgrement.addAll((ArrayList<PostWithTime>)WAposts.readObject());
      MyPostsList.addAll((ArrayList<PostWithTime>) Myposts.readObject());
     // AllAgreementPosts.addAll((HashSet<Post>)GroupsId.readObject());
        Otposts.close();
        Mtposts.close();
        Freeposts.close();
        WAposts.close();
        Myposts.close();
       // GroupsId.close();
  }





}
