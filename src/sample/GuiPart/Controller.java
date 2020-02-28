package sample.GuiPart;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import sample.TelegramPart.Promo.PostManager;
import sample.TelegramPart.Promo.PostWithTime;
import sample.UsersData.Post;
import sample.UsersData.Reviews.ReView;
import sample.UsersData.Users.AUser;
import sample.UsersData.Users.Seller;
import sample.UsersData.UsersManager;

public class Controller {


    //------------------------------------------------------------Общее------------------------------------------------------------------------------------//
    private Post currentPost;
    private PostWithTime currentTimeSettingsPost;
    private Seller currentPostSeller;
    private Seller currentSeller;
    private String currentGroupId;
    private ReView currentReView;

    @FXML
    private TextFlow Erros;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    private UsersManager usersManager;
    private PostManager postManager;

    public void setUsersManager(UsersManager usersManager) {
        this.usersManager = usersManager;
        System.out.println(" set UserManger Controller" );
    }

    public void setPostManager(PostManager postManager) {
        this.postManager = postManager;
        System.out.println("  set PostManger Controller" );
    }

    public Controller(){
        System.out.println("Constructor of Controller ");
}

    private ObservableList<String> GroupsId = FXCollections.observableArrayList();

    private ObservableList<AUser> usersData = FXCollections.observableArrayList();

    private ObservableList<Seller> Sellers = FXCollections.observableArrayList();

    private ObservableList<PostWithTime> promoForAgrement = FXCollections.observableArrayList();

    private ObservableList<ReView> ReViews_list = FXCollections.observableArrayList();

    private ObservableList<PostWithTime> Promoes_FreePosts_TableData = FXCollections.observableArrayList();

    private ObservableList<PostWithTime> Promoes_MtPosts_TableData = FXCollections.observableArrayList();

    private ObservableList<PostWithTime> Promoes_OtPosts_TableData = FXCollections.observableArrayList();

    private ObservableList<Post> Promoes_AllPosts_Table_Data = FXCollections.observableArrayList();

    private ObservableList<PostWithTime> Promoes_MyPosts_Table_Data = FXCollections.observableArrayList();

    public void SavePostsLists(){
        try {
            postManager.SavePosts();
        } catch (IOException e) {
            Errors.AddError("Cann`t To SAVE");
            e.printStackTrace();
        }
    }
    public void LoadPostsLists(){
        try {
            postManager.LoadPosts();
        } catch (ClassNotFoundException e) {
            Errors.AddError("Cann`t To LOAD");
            e.printStackTrace();
        } catch (IOException e) {
            Errors.AddError("Cann`t To LOAD");
            e.printStackTrace();
        }
    }
//------------------------------------------------------------Вкладка с неподтвержденными рекламныит постами------------------------------------------------------------------------------------//


    @FXML
    private Pane Prom_ImagePane;

    @FXML
    private TextArea Prom_Locations;

    @FXML
    private TextArea Prom_Agreement;

    @FXML
    private Button Prom_DelButton;

    @FXML
    private TextArea Prom_KindB;

    @FXML
    private TextArea Prom_KindA;

    @FXML
    private TextArea Prom_Description;

    @FXML
    private TextArea Prom_Price;

    @FXML
    private TextArea Prom_IdUser;

    @FXML
    private TableColumn<PostWithTime, String> Prom_IdUserColumn;

    @FXML
    private TableColumn<PostWithTime, String> Prom_IsFree;

    @FXML
    private TableColumn<PostWithTime, String> Prom_ManyTimesColumn;

    @FXML
    private TableColumn<PostWithTime, String> Prom_OneTimeColumn;

    @FXML
    private Button Prom_AgreeButton;

    @FXML
    private TableView<PostWithTime> Promoes_AgrementTable;
   //----------------------------Действия



    @FXML
    public void pressKindA(KeyEvent event){
        switch (event.getCode()) {
            case SHIFT:
                if(Prom_KindA.getText() != null && Prom_KindA.getText() != ""){
                    currentPost.setKind(Prom_KindA.getText());
                }
                break;
            default:
                break;
        }
    }
    @FXML
    public void pressKindB(KeyEvent event){
        switch (event.getCode()) {
            case SHIFT:
                if(Prom_KindB.getText() != null && Prom_KindB.getText() != ""){
                    currentPost.setKind_B(Prom_KindB.getText());
                }
                break;
            default:
                break;
        }
    }
    @FXML
    public void ChangePost(){
        currentPost.AddPostToDb();

    }
    @FXML
    public void pressDescription(KeyEvent event){
        switch (event.getCode()) {
            case SHIFT:
                if(Prom_Description.getText() != null && Prom_Description.getText() != ""){
                    currentPost.setDescription(Prom_Description.getText());
                }
                break;
            default:
                break;
        }
    }

    @FXML
    public void ClickPromoes_AgrementTable(MouseEvent event) {
        if (event.getClickCount() == 2) //Checking double click
        {
            currentTimeSettingsPost = Promoes_AgrementTable.getSelectionModel().getSelectedItem();
            Post post = (Post)Promoes_AgrementTable.getSelectionModel().getSelectedItem().getPost();
           if(usersManager.GettingSeller(Promoes_AgrementTable.getSelectionModel().getSelectedItem().getUserId()) != null) { //находим у Поста Продавца и выставляем его во вкладку с продавцами
               currentSeller = InfofromSeller((Seller)usersManager.GettingSeller(Promoes_AgrementTable.getSelectionModel().getSelectedItem().getUserId()));
               currentPostSeller = currentSeller;
           }
           currentPost = post;
           Prom_IdUser.setText(String.valueOf(post.getUserId()));
           InfoFromPromoAgrementTable(post);

        }
    }


    private void InfoFromPromoAgrementTable(Post post){
         if(post.getDescription() != null){
            Prom_Description.setText(post.getDescription());
         }else {
             Prom_Description.setText("null");
         }
         if(post.getKind() != null){
             Prom_KindA.setText(post.getKind());
         }else {
             Prom_KindA.setText("null");
         }
         if(post.getKind_B() != null){
             Prom_KindB.setText(post.getKind_B());
         }else {
             Prom_KindB.setText("null");
         }
         if(post.getUserId() != 0){
             Prom_IdUser.setText(String.valueOf(post.getUserId()));
         }else {
             Prom_IdUser.setText("null");
         }
         if(post.getAgreement()){
             Prom_Agreement.setText("Yes");
         }else {
             Prom_Agreement.setText("NO");
         }

         if(post.getPrices().size() != 0){
             StringBuilder str = new StringBuilder();
             for(String price: post.getPrices()){
                 str.append(price + "\n");
             }
             Prom_Price.setText(str.toString());
         }else {
             Prom_Price.setText("");
         }
         if(post.getLocations().size() != 0){
             StringBuilder str = new StringBuilder();
             for(String loc: post.getLocations()){
                 str.append(loc + "\n");
             }
             Prom_Locations.setText(str.toString());

         }else {
             Prom_Locations.setText("");
         }
         if(post.getImagePath() != null){
            System.out.println(" Image PAth " + post.getImagePath());
             try {
                 String locurl = post.getImagePath().toURI().toURL().toString();
                 BackgroundImage bgImage = new BackgroundImage(
                         new Image(locurl),                                                 // image
                         BackgroundRepeat.NO_REPEAT,                            // repeatX
                         BackgroundRepeat.NO_REPEAT,                            // repeatY
                         BackgroundPosition.CENTER,                             // position
                         new BackgroundSize(-1, -1, false, false, true, false)  // size
                 );
                 Prom_ImagePane.setBackground(new Background(bgImage));

             } catch (MalformedURLException e) {
                 Errors.AddError("301 Can`t find Post`s Image ");
                 e.printStackTrace();
             }

         }
    }

    public void deletePost(){  //Удаляем пост из списка ожиданий и у самого пользователя
        if(currentPost != null){
            Seller seller;
            if(usersManager.GettingSeller(currentPost.getUserId()) != null) {
                seller = (Seller) usersManager.GettingSeller(currentPost.getUserId());
                if(seller.getPosts_list() != null && !seller.getPosts_list().isEmpty()){
                seller.Remove_Post(currentPost, postManager);
                }}
            }
        //postManager.DelPostFromWaitingList(currentPost);
    }


    public void ageePost(){
        if(currentPost != null){
            usersManager.setAgreementToPost(currentPost,postManager);
            postManager.DelPostFromWaitingList(currentPost);
        }
    }

    public void AddToFreeList(){
        if(currentSeller!=null &&currentPost!=null && currentSeller.getUserId() == currentPost.getUserId()){
        if(currentPost.getAgreement()) {
          postManager.Add_My_Post(currentPost, currentSeller);
        }
    }}

    @FXML
    void keyPressedAllPosts_Table(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
            case ENTER:
                System.out.println("to the left");
                AddToFreeList();
                break;
            case DOWN:
                ClickPromoes_AllPosts_Table();
            case UP:
                ClickPromoes_AllPosts_Table();
                break;
            default:
                break;
        }
    }
    @FXML
    void keyPressedFreePosts_Table(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
            case ENTER:
                System.out.println("to the left");

                break;
            case RIGHT:
            case DELETE:
                System.out.println("Delete");
                postManager.DeletePWT_FreePosts(currentTimeSettingsPost);
                break;
            case DOWN:
                ClickPromoes_FreePosts_Table();
            case UP:
                ClickPromoes_FreePosts_Table();
                break;
            default:
                break;
        }
    }
    @FXML
    void keyPressedMyPostsList_Table(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
            case ENTER:
                break;
            case RIGHT:
            case DELETE:
                System.out.println("Delete");
                postManager.DeleteMy_PostList(currentTimeSettingsPost);
                break;
            case DOWN:
                ClickPromoes_MyPosts_Table();
            case UP:
                ClickPromoes_MyPosts_Table();
                break;
            default:
                break;
        }
    }
    //------------------------------------------------------------Вкладка с неподтвержденными рекламныит постами

    @FXML
    private TableView<PostWithTime> Promoes_MyPosts_Table;

    @FXML
    private TableView<PostWithTime> Promoes_MtPosts_Table;

    @FXML
    private TableView<Post> Promoes_AllPosts_Table;

    @FXML
    private TableView<PostWithTime> Promoes_OtPosts_Table;

    @FXML
    private TableView<PostWithTime> Promoes_FreePosts_Table;

    @FXML
    private TableColumn<PostWithTime, String> Promoes_MyPostsNumberColumn;

    @FXML
    private TableColumn<PostWithTime, String> Promoes_MyPostsUserIdColumn;

    @FXML
    private TableColumn<PostWithTime, String> Promoes_MtPosts_manytimes;

    @FXML
    private TableColumn<PostWithTime, Integer> Promoes_OtPosts_id;

    @FXML
    private TableColumn<PostWithTime, String> Promoes_FreePosts_free;

    @FXML
    private TableColumn<PostWithTime, Integer> Promoes_FreePosts_id;

    @FXML
    private TableColumn<PostWithTime, Integer> Promoes_MtPosts_id;

    @FXML
    private TableColumn<PostWithTime, String> Promoes_OtPosts_onetime;

    @FXML
    private TableColumn<Post, Integer> Promoes_AllPosts_Number;

    @FXML
    private TableColumn<Post, Integer> Promoes_AllPosts_Id;




    //----------------------------Действия

    public void DeleteFromWaitingList(){
        postManager.DeletePostFromWaitingList(currentTimeSettingsPost);
    }


    public void GetPostsFromDB(){
        loadPostsFromDB();
    }

    public void ClickPromoes_AllPosts_Table(){
            Post post = (Post)Promoes_AllPosts_Table.getSelectionModel().getSelectedItem();
            if(usersManager.GettingSeller(Promoes_AllPosts_Table.getSelectionModel().getSelectedItem().getUserId()) != null) {
                currentSeller = InfofromSeller((Seller) usersManager.GettingSeller(Promoes_AllPosts_Table.getSelectionModel().getSelectedItem().getUserId()));
                currentPostSeller = currentSeller;
            }
            currentPost = post;
            Prom_IdUser.setText(String.valueOf(post.getUserId()));
            InfoFromPromoAgrementTable(post);

    }
    public void ClickPromoes_AllPosts_Table(MouseEvent event){
        if (event.getClickCount() == 1) //Checking double click
        {
            Post post = (Post)Promoes_AllPosts_Table.getSelectionModel().getSelectedItem();
            if(usersManager.GettingSeller(Promoes_AllPosts_Table.getSelectionModel().getSelectedItem().getUserId()) != null) {
                currentSeller = InfofromSeller((Seller) usersManager.GettingSeller(Promoes_AllPosts_Table.getSelectionModel().getSelectedItem().getUserId()));
                currentPostSeller = currentSeller;
            }
            currentPost = post;
            Prom_IdUser.setText(String.valueOf(post.getUserId()));
            InfoFromPromoAgrementTable(post);
        }
    }
    public void ClickPromoes_MtPosts_Table(MouseEvent event){ // 2-ной_Щелчек по одной из маленькой таблиц - во вкладке Promoes
        if (event.getClickCount() == 1) //Checking double click
        {
            currentTimeSettingsPost = (PostWithTime)Promoes_MtPosts_Table.getSelectionModel().getSelectedItem();
            Post post = (Post)Promoes_MtPosts_Table.getSelectionModel().getSelectedItem().getPost();
            if(usersManager.GettingSeller(Promoes_MtPosts_Table.getSelectionModel().getSelectedItem().getUserId()) != null) {
                currentSeller = InfofromSeller((Seller) usersManager.GettingSeller(Promoes_MtPosts_Table.getSelectionModel().getSelectedItem().getUserId()));
                currentPostSeller = currentSeller;
            }
            currentPost = post;
            System.out.println(currentTimeSettingsPost.getTtime());
            Prom_IdUser.setText(String.valueOf(post.getUserId()));
            InfoFromPromoAgrementTable(post);

        }
    }
    public void ClickPromoes_OtPosts_Table(MouseEvent event){ // 2-ной_Щелчек по одной из маленькой таблиц - во вкладке Promoes
        if (event.getClickCount() == 1) //Checking double click
        {
            currentTimeSettingsPost = (PostWithTime) Promoes_OtPosts_Table.getSelectionModel().getSelectedItem();
            Post post = (Post)Promoes_OtPosts_Table.getSelectionModel().getSelectedItem().getPost();
            if(usersManager.GettingSeller(Promoes_OtPosts_Table.getSelectionModel().getSelectedItem().getUserId()) != null) {
                currentSeller = InfofromSeller((Seller) usersManager.GettingSeller(Promoes_OtPosts_Table.getSelectionModel().getSelectedItem().getUserId()));
                currentPostSeller = currentSeller;
            }
            currentPost = post;
            Prom_IdUser.setText(String.valueOf(post.getUserId()));
            InfoFromPromoAgrementTable(post);

        }
    }
    public void ClickPromoes_FreePosts_Table(MouseEvent event){ // 2-ной_Щелчек по одной из маленькой таблиц - во вкладке Promoes
        if (event.getClickCount() == 1) //Checking double click
        {
            Seller seller;
            currentTimeSettingsPost = (PostWithTime)Promoes_FreePosts_Table.getSelectionModel().getSelectedItem();
            Post post = (Post)Promoes_FreePosts_Table.getSelectionModel().getSelectedItem().getPost();
            if(usersManager.GettingSeller(Promoes_FreePosts_Table.getSelectionModel().getSelectedItem().getUserId()) != null) {
                seller = InfofromSeller((Seller) usersManager.GettingSeller(Promoes_FreePosts_Table.getSelectionModel().getSelectedItem().getUserId()));
                currentPostSeller = seller;
            }
            currentPost = post;
            Prom_IdUser.setText(String.valueOf(post.getUserId()));
            InfoFromPromoAgrementTable(post);

        }
    }
    public void ClickPromoes_FreePosts_Table(){ // 2-ной_Щелчек по одной из маленькой таблиц - во вкладке Promoes
            Seller seller;
            currentTimeSettingsPost = (PostWithTime)Promoes_FreePosts_Table.getSelectionModel().getSelectedItem();
            Post post = (Post)Promoes_FreePosts_Table.getSelectionModel().getSelectedItem().getPost();
            if(usersManager.GettingSeller(Promoes_FreePosts_Table.getSelectionModel().getSelectedItem().getUserId()) != null) {
                seller = InfofromSeller((Seller) usersManager.GettingSeller(Promoes_FreePosts_Table.getSelectionModel().getSelectedItem().getUserId()));
                currentPostSeller = seller;
            }
            currentPost = post;
            Prom_IdUser.setText(String.valueOf(post.getUserId()));
            InfoFromPromoAgrementTable(post);


    }

    public void ClickPromoes_MyPosts_Table(MouseEvent event){
        if (event.getClickCount() == 1) //Checking double click
        {
            Seller seller;
            currentTimeSettingsPost = (PostWithTime)Promoes_MyPosts_Table.getSelectionModel().getSelectedItem();
            Post post = (Post)Promoes_MyPosts_Table.getSelectionModel().getSelectedItem().getPost();
            if(usersManager.GettingSeller(Promoes_MyPosts_Table.getSelectionModel().getSelectedItem().getUserId()) != null) {
                seller = InfofromSeller((Seller) usersManager.GettingSeller(Promoes_MyPosts_Table.getSelectionModel().getSelectedItem().getUserId()));
                currentPostSeller = seller;
            }
            currentPost = post;
            Prom_IdUser.setText(String.valueOf(post.getUserId()));
            InfoFromPromoAgrementTable(post);

        }
    }
    public void ClickPromoes_MyPosts_Table(){
            Seller seller;
            currentTimeSettingsPost = (PostWithTime)Promoes_MyPosts_Table.getSelectionModel().getSelectedItem();
            Post post = (Post)Promoes_MyPosts_Table.getSelectionModel().getSelectedItem().getPost();
            if(usersManager.GettingSeller(Promoes_MyPosts_Table.getSelectionModel().getSelectedItem().getUserId()) != null) {
                seller = InfofromSeller((Seller) usersManager.GettingSeller(Promoes_MyPosts_Table.getSelectionModel().getSelectedItem().getUserId()));
                currentPostSeller = seller;
            }
            currentPost = post;
            Prom_IdUser.setText(String.valueOf(post.getUserId()));
            InfoFromPromoAgrementTable(post);
    }
    public void Prom_DeleteMyButton1(){postManager.DeleteMy_PostList(currentTimeSettingsPost);}
    public void PromoesDeleteFree(){
        postManager.DeletePWT_FreePosts(currentTimeSettingsPost);
    }
    public void PromoesDeleteMt(){
        postManager.DeletePWT_MtPosts(currentTimeSettingsPost);
    }
    public void PromoesDeleteOt(){
        postManager.DeletePWT_OtList(currentTimeSettingsPost);
    }


    //------------------------------------------------------------Вкладка с Продавцами ------------------------------------------------------------------------------------//


    @FXML
    TextArea PesonalNumber;

    @FXML
    private TableView<Seller> Sellers_Table;

    @FXML
    private TableColumn<Seller, String> Sellers_lastNameColumn;

    @FXML
    private TableColumn<Seller, String> Sellers_idColumn;

    @FXML
    private TableColumn<Seller,String> Sellers_lastTimeColumn;

    @FXML
    private TableColumn<Seller,String> Sellers_usernameColumn;

    @FXML
    private TableColumn<Seller, String> Sellers_fistNameColumn;

    @FXML
    private TextArea Sellers_Points;

    @FXML
    private Button Sellers_AgreeButton;

    @FXML
    private TextArea Sellers__Agreement;

    @FXML
    private TextArea Sellers_TimeReg;

    @FXML
    private Pane Sellers_ImagePane;

    @FXML
    private TextArea Sellers_Credit;

    @FXML
    private Button Sellers_blockSeller;

    @FXML
    private TextField Sellers_NumberPoints;


    @FXML
    private TextArea Sellers_UserName;

    @FXML
    private TextArea Sellers_Id;

    @FXML
    private TextArea Sellers_fistName;

    @FXML
    private TextArea Sellers_lastName;

    @FXML
    private TextArea Sellers_allPoints;

    @FXML
    private Button Sellers_addPoints;

    @FXML
    private Button Sellers_deleteSeller1;
//----------------------------Действия
    @FXML
    public void AddingPoints(){
        if(currentSeller != null){
            try {
                usersManager.incresePointsSeller(currentSeller,Integer.parseInt(Sellers_NumberPoints.getText()));
            }catch (Exception ex){
                Errors.AddError("Number !!! ");
                ex.printStackTrace();
            }

        }
    }

    public void GetSellersFromDB(){
        loadSellersFromDB();
    }
    @FXML
    public void ClickSellers_Table(MouseEvent event){
        if(event.getClickCount() == 2){
            currentSeller = InfofromSeller(Sellers_Table.getSelectionModel().getSelectedItem());
        }
    }

    public void ClickSellers_Table(){
        currentSeller = InfofromSeller(Sellers_Table.getSelectionModel().getSelectedItem());
    }

    private Seller InfofromSeller(Seller seller){
        Sellers_fistName.setText(seller.getFirst_name());
        Sellers_lastName.setText(seller.getLast_name());
        Sellers_TimeReg.setText(seller.getTimeOfRegistration().toString());
        Sellers_Points.setText(String.valueOf(seller.getPoints()));
        Sellers_allPoints.setText(String.valueOf(seller.getAllPoints()));
        Sellers_Credit.setText(String.valueOf(seller.getCredit()));
        Sellers_Id.setText(String.valueOf(seller.getUserId()));
        if(seller.getUsername()!=null){
            Sellers_UserName.setText(seller.getUsername());
        }
        if(seller.getPersonalNum() != null){
            PesonalNumber.setText(seller.getPersonalNum());
        } else {
            PesonalNumber.setText("NO PN!!!");
        }
        Sellers__Agreement.setText(String.valueOf(seller.isAgreement()));
        if(seller.getSellerPhoto() != null){
            try {
                String loc = seller.getSellerPhoto().toURI().toURL().toString();
                BackgroundImage bgImage = new BackgroundImage(
                        new Image(loc),                                                 // image
                        BackgroundRepeat.NO_REPEAT,                            // repeatX
                        BackgroundRepeat.NO_REPEAT,                            // repeatY
                        BackgroundPosition.CENTER,                             // position
                        new BackgroundSize(-1, -1, false, false, true, false)  // size

                );
                Sellers_ImagePane.setBackground(new Background(bgImage));
            } catch (MalformedURLException e){
                e.printStackTrace();
                Errors.AddError("300 Can`t find Seller`s Image ");
            }
        }
        return seller;

    }

    @FXML
    void keyPressedSellers_Table(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
            case ENTER:
                System.out.println("to the left");

                break;
            case RIGHT:

            case DOWN:
                ClickSellers_Table();
            case UP:
                ClickSellers_Table();
                break;
            default:
                break;
        }
    }
    public void BlockSeller(){
        if(currentSeller != null){
            usersManager.BLockUser(currentSeller.getUserId());
        }
    }
    public void  DeleteSellerB(){
        if(currentSeller != null){
            usersManager.DeleteSeller(currentSeller.getUserId());
        }
    }
   //-------------------------------------------------------------Вкладка с Натсройками рекламы

    @FXML
    private TextArea Posts_Time_Input1;

    @FXML
    private ListView<String> GroupsIdList;

    @FXML
    private Button Posting_stopAllButton31;

    @FXML
    private Button Posting_stopOtButton3;

    @FXML
    private Button Posting_stopFreeButton1;

    @FXML
    private Button Posting_stopMtButton2;

    @FXML
    private TextArea Posts_GroupId_Input;

    @FXML
    private Button Posting_Settings_Button_Delete_Id;

    @FXML
    private Button Posting_startMtButton;

    @FXML
    private Button Posting_startOtButton;

    @FXML
    private Button Posting_startFreeButton;
    //----------------------------Действия

    public void Posting_stopAll(){
        postManager.StopPosting();
    }
    public void Posting_stopMt(){
        postManager.StopMtPosting();
    }
    public void Posting_stopOt(){
        postManager.StopOtPosting();
    }
    public void Posting_stopree(){
        postManager.StopFreePosting();
    }
    public void Posting_startFree(){
        postManager.SSFree();
        System.out.println("Start Posting !!! ");
    }
    public void Posting_stopMy(){
        postManager.StopMyPosting();
    }
    public void Posting_startMy(){
        postManager.SSMy();
    }
    public void Posting_startOt(){
        postManager.SSOt();
    }

    public void Posting_startMt(){
        postManager.SSMt();
    }

    private void Groups_SetIdList(){
        GroupsId.clear();
        GroupsId.addAll(postManager.getGroups_IdsList());
        GroupsIdList.setItems(GroupsId);
        GroupsIdList.refresh();
    }
    public void Posting_Settings_Button_Delete_GroupId(){
        GroupsId.remove(currentGroupId);
        postManager.Remove_GroupdId(currentGroupId);
    }
    public void ChooseGroupId(){
        currentGroupId = GroupsIdList.getSelectionModel().getSelectedItem();
    }
    public void Posting_Settings_Button_input_GroupId(){
        postManager.addGroupToPostingId(Posts_GroupId_Input.getText());
        Groups_SetIdList();
    }

    public void Posting_Settings_Button_input_Time(){

        int a = Integer.parseInt(Posts_Time_Input1.getText());
        postManager.setTimeToFreePosting(a);
    }
    //------------------------------------------------------Вкладка с отзывами==============================================================
    @FXML
    private TableView<ReView> Reviews_Table;

    @FXML
    private TableColumn<ReView,String> Reviews_TableColumn_Index;

    @FXML
    private TableColumn<ReView,Date> Reviews_TableColumn_Time;
    @FXML
    private TableColumn<ReView,String> Reviews_TableColumn_ByerId;

    @FXML
    private TableColumn<ReView,String> Reviews_TableColumn_SellerId;

/*
    @FXML
    private TableColumn<,>  Reviews_TableColumn_N;*/

    @FXML
    private TextArea Rev_userId;

    @FXML
    private TextArea Rev_sellerId;

    @FXML
    private TextArea Rev_TRtoBot;

    @FXML
    private TextArea Rev_TA1;

    @FXML
    private TextArea Rev_TA2;

    @FXML
    private TextArea Rev_TA3;

    @FXML
    private TextArea Rev_TR;

    @FXML
    private TextArea Res_201;

    @FXML
    private TextArea Res_202;

    @FXML
    private TextArea Finished;

    @FXML
    private TextArea Time_Finish;

    @FXML
    private TextArea rev_description;

    @FXML
    private TextArea rev_index;

    @FXML
    private TextArea rev_quality;

    @FXML
    private TextArea rev_qp;

    @FXML
    private TextArea rev_service;

    @FXML
    private TextArea rev_Conformity;

    @FXML
    private TextArea rev_Numreview;

    @FXML
    private TextArea rev_recommendet;


    public void ClickReViewTable(MouseEvent event){
        if(event.getClickCount() == 2){
            currentReView = Reviews_Table.getSelectionModel().getSelectedItem();
            ReViewSetInfo(currentReView);
        }
    }
    public void ClickReViewTable(){
            currentReView = Reviews_Table.getSelectionModel().getSelectedItem();
            ReViewSetInfo(currentReView);

    }
    private void ReViewSetInfo(ReView reView){
        rev_Numreview.setText(String.valueOf(reView.getNum_ofReView()));
        if(reView.getByer() != null) {
            Rev_userId.setText(String.valueOf(reView.getByer().getUserId()) + reView.getByer() == null ? "no username " : reView.getByer().getUsername());
        } else {
            Rev_userId.setText("This Mistake" + reView.getUserId());
        }
        Rev_sellerId.setText(String.valueOf(reView.getSeller().getUserId()) + reView.getSeller() == null ? "no username " : reView.getSeller().getUsername());
        Rev_TRtoBot.setText(String.valueOf(reView.getTime_request_toBot()));
        Rev_TA1.setText(String.valueOf(reView.getTime_request_first()));
        Rev_TA2.setText(String.valueOf(reView.getTime_request_second()));
        Rev_TR.setText(String.valueOf(reView.getTime_seller_response()));
        Res_201.setText(String.valueOf(reView.isRightforReView_Was201()));
        Res_202.setText(String.valueOf(reView.isRightforReView_Was202()));
        Finished.setText(String.valueOf(reView.isBuyerGiveReV()));
        Time_Finish.setText(String.valueOf(reView.getTime_Ofinish()));
        rev_description.setText(reView.getDescription());
        rev_index.setText(String.valueOf(reView.getIndexQ()));
        rev_quality.setText(String.valueOf(reView.getQuality()));
        rev_qp.setText(String.valueOf(reView.getQ_p()));
        rev_service.setText(String.valueOf(reView.getService()));
        rev_Conformity.setText(String.valueOf(reView.getConformity()));
        rev_recommendet.setText(String.valueOf(reView.getRecomended()));

    }

    public void DeleteReView(){
        usersManager.deleteReView(currentReView);
    }

    public void GetReViewFromDb(){
        getReViewsFromDb();
    }




    @FXML
    void keyPressedReViews(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
            case ENTER:
                System.out.println("to the left");

                break;
            case RIGHT:

            case DOWN:
                ClickReViewTable();
            case UP:
                ClickReViewTable();
                break;
            default:
                break;
        }
    }

    //------------------Действия
    //------------------------------------------------------------Вкладка с базововой таблицей------------------------------------------------------------------------------------//


    @FXML
    private TableView<AUser> Simple_Users_Table;

    @FXML
    private TableColumn<AUser, String> Name_column;

    @FXML
    private TableColumn<AUser, String> username_column;

    @FXML
    private TableColumn<AUser, Integer> userId_column;

    @FXML
    private TableColumn<AUser, String> ifSeller_column;

    @FXML
    private TableColumn<AUser, Integer> NReq_toBot_column;

    @FXML
    private TableColumn<AUser, Integer> NReq_toSeller_column;

    @FXML
    private TableColumn<AUser, Integer> Num_ofDeals;

    @FXML
    private TableColumn<AUser, String> Ser_nameColumn;

    @FXML
    private TableColumn<AUser,String> LastTimeColumn;
//----------------------------Действия



//-----------------------------------------------------------------Верх Панели--------------------------------------------------------------------------//
    @FXML
    private Button StartBotButton;

    @FXML
    private TextField Field_for_Seaching;

    @FXML
    private TabPane Panel_with_Panels;

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private Button Get_Data_Button;

    @FXML
    void initialize() {
        assert Erros != null : "fx:id=\"Erros\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_Agreement != null : "fx:id=\"Prom_Agreement\" was not injected: check your FXML file 'sample.fxml'.";
        assert Name_column != null : "fx:id=\"Name_column\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_DelButton != null : "fx:id=\"Prom_DelButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_IsFree != null : "fx:id=\"Prom_IsFree\" was not injected: check your FXML file 'sample.fxml'.";
        assert Ser_nameColumn != null : "fx:id=\"Ser_nameColumn\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_KindB != null : "fx:id=\"Prom_KindB\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_KindA != null : "fx:id=\"Prom_KindA\" was not injected: check your FXML file 'sample.fxml'.";
        assert NReq_toBot_column != null : "fx:id=\"NReq_toBot_column\" was not injected: check your FXML file 'sample.fxml'.";

        assert Num_ofDeals != null : "fx:id=\"Num_ofDeals\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_Description != null : "fx:id=\"Prom_Description\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_ManyTimesColumn != null : "fx:id=\"Prom_ManyTimesColumn\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_Locations != null : "fx:id=\"Prom_Locations\" was not injected: check your FXML file 'sample.fxml'.";
        assert Get_Data_Button != null : "fx:id=\"Get_Data_Button\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_OneTimeColumn != null : "fx:id=\"Prom_OneTimeColumn\" was not injected: check your FXML file 'sample.fxml'.";
        assert Simple_Users_Table != null : "fx:id=\"Simple_Users_Table\" was not injected: check your FXML file 'sample.fxml'.";
        assert ifSeller_column != null : "fx:id=\"ifSeller_column\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_IdUserColumn != null : "fx:id=\"Prom_IdUserColumn\" was not injected: check your FXML file 'sample.fxml'.";
        assert StartBotButton != null : "fx:id=\"StartBotButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_Price != null : "fx:id=\"Prom_Price\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_IdUser != null : "fx:id=\"Prom_IdUser\" was not injected: check your FXML file 'sample.fxml'.";
        assert username_column != null : "fx:id=\"username_column\" was not injected: check your FXML file 'sample.fxml'.";
        assert userId_column != null : "fx:id=\"userId_column\" was not injected: check your FXML file 'sample.fxml'.";
        assert Prom_AgreeButton != null : "fx:id=\"Prom_AgreeButton\" was not injected: check your FXML file 'sample.fxml'.";
        assert NReq_toSeller_column != null : "fx:id=\"NReq_toSeller_column\" was not injected: check your FXML file 'sample.fxml'.";
        assert Promoes_AgrementTable != null : "fx:id=\"Promoes_AgrementTable\" was not injected: check your FXML file 'sample.fxml'.";
        assert Field_for_Seaching != null : "fx:id=\"Field_for_Seaching\" was not injected: check your FXML file 'sample.fxml'.";
        assert Panel_with_Panels != null : "fx:id=\"Panel_with_Panels\" was not injected: check your FXML file 'sample.fxml'.";
        assert MainPanel != null : "fx:id=\"MainPanel\" was not injected: check your FXML file 'sample.fxml'.";
        new Errors(Erros); //Создаем класс для показа ошибок
        initTables();
    }
//---------------------------------------------------Инициализируем таблицы
    private void initTables(){
        initBasicTableValues();
        initPromo_AgreementTable();
        initSellersTable();
        initPromo_OtherTables();
        initiReViewsTableValues();
    }

    public void initSellersTable(){ //Таблица Sellers
        // устанавливаем тип и значение которое должно хранится в колонке
         Sellers_lastNameColumn.setCellValueFactory(new PropertyValueFactory<Seller,String>("last_name"));
         Sellers_idColumn.setCellValueFactory(new PropertyValueFactory<Seller,String>("userId"));
         Sellers_fistNameColumn.setCellValueFactory(new PropertyValueFactory<Seller,String>("first_name"));
         Sellers_lastTimeColumn.setCellValueFactory(new PropertyValueFactory<Seller,String>("date"));
         Sellers_usernameColumn.setCellValueFactory(new PropertyValueFactory<Seller,String>("Username"));
    }


    public void initBasicTableValues(){ //Таблица Базовая
        // устанавливаем тип и значение которое должно хранится в колонке
        Name_column.setCellValueFactory(new PropertyValueFactory<AUser,String>("first_name"));
        username_column.setCellValueFactory(new PropertyValueFactory<AUser, String>("Username"));
        Ser_nameColumn.setCellValueFactory(new PropertyValueFactory<AUser,String>("first_name"));
        userId_column.setCellValueFactory(new PropertyValueFactory<AUser, Integer>("userId"));
        ifSeller_column.setCellValueFactory(new PropertyValueFactory<AUser,String>("isSeller"));
        NReq_toBot_column.setCellValueFactory(new PropertyValueFactory<AUser,Integer>("ReqToBot"));
        NReq_toSeller_column.setCellValueFactory(new PropertyValueFactory<AUser,Integer>("ReqToSellers"));
        Num_ofDeals.setCellValueFactory(new PropertyValueFactory<AUser,Integer>("Succes_Deals"));
        LastTimeColumn.setCellValueFactory(new PropertyValueFactory<AUser,String>("date"));
    }
    public void initiReViewsTableValues(){
        // устанавливаем тип и значение которое должно хранится в колонке
        Reviews_TableColumn_Index.setCellValueFactory(new PropertyValueFactory<ReView,String>("Index_q"));

        Reviews_TableColumn_ByerId.setCellValueFactory(new PropertyValueFactory<ReView,String>("userId"));

        Reviews_TableColumn_SellerId.setCellValueFactory(new PropertyValueFactory<ReView,String>("sellerId"));

        Reviews_TableColumn_Time.setCellValueFactory(new PropertyValueFactory<ReView, Date>("time_request_toBot"));
    }
    public void initPromo_AgreementTable(){ //Таблица С неподтвержденными постами
        // устанавливаем тип и значение которое должно хранится в колонке
        Prom_IdUserColumn.setCellValueFactory(new PropertyValueFactory<PostWithTime,String>("userId"));
        Prom_IsFree.setCellValueFactory(new PropertyValueFactory<PostWithTime,String>("Free"));
        Prom_ManyTimesColumn.setCellValueFactory(new PropertyValueFactory<PostWithTime,String>("Time"));
        Prom_OneTimeColumn.setCellValueFactory(new PropertyValueFactory<PostWithTime,String>("Time"));
    }
    public void initPromo_OtherTables(){
        Promoes_MyPostsNumberColumn.setCellValueFactory(new PropertyValueFactory<PostWithTime,String>("userId"));
        Promoes_MyPostsUserIdColumn.setCellValueFactory(new PropertyValueFactory<PostWithTime,String>("Time"));
        Promoes_AllPosts_Number.setCellValueFactory(new PropertyValueFactory<Post,Integer>("number_Post"));
        Promoes_AllPosts_Id.setCellValueFactory(new PropertyValueFactory<Post,Integer>("userId"));
        Promoes_OtPosts_onetime.setCellValueFactory(new PropertyValueFactory<PostWithTime,String>("Time"));
        Promoes_MtPosts_id.setCellValueFactory(new PropertyValueFactory<PostWithTime,Integer>("userId"));
        Promoes_FreePosts_id.setCellValueFactory(new PropertyValueFactory<PostWithTime,Integer>("userId"));
        Promoes_FreePosts_free.setCellValueFactory(new PropertyValueFactory<PostWithTime,String>("Time"));
        Promoes_OtPosts_id.setCellValueFactory(new PropertyValueFactory<PostWithTime,Integer>("userId"));
        Promoes_MtPosts_manytimes.setCellValueFactory(new PropertyValueFactory<PostWithTime,String>("Time"));
    }


 //-----------------------------------------------Закидываем данные в таблицы
    private void Promoes_OtherTablesInsertData(){

        Promoes_AllPosts_Table_Data.clear();
        Promoes_FreePosts_TableData.clear();
        Promoes_OtPosts_TableData.clear();
        Promoes_MtPosts_TableData.clear();
        Promoes_MyPosts_Table_Data.clear();
        Promoes_MyPosts_Table_Data.addAll(postManager.getMyPostList());
        Promoes_AllPosts_Table_Data.addAll(usersManager.getAllPosts());
        Promoes_FreePosts_TableData.addAll(postManager.getFreePostList());
        Promoes_OtPosts_TableData.addAll(postManager.getOtPostList());
        Promoes_MtPosts_TableData.addAll(postManager.getMtPostList());
        Promoes_MyPosts_Table.setItems(Promoes_MyPosts_Table_Data);
        Promoes_AllPosts_Table.setItems(Promoes_AllPosts_Table_Data);
        Promoes_MtPosts_Table.setItems(Promoes_MtPosts_TableData);
        Promoes_OtPosts_Table.setItems(Promoes_OtPosts_TableData);
        Promoes_FreePosts_Table.setItems(Promoes_FreePosts_TableData);
        Promoes_MyPosts_Table.refresh();
        Promoes_AllPosts_Table.refresh();
        Promoes_MtPosts_Table.refresh();
        Promoes_OtPosts_Table.refresh();
        Promoes_FreePosts_Table.refresh();
    }
    private void PromoAgrTableInsertData(){
        promoForAgrement.clear();
        promoForAgrement.addAll(postManager.getWaitingPostList());
        Promoes_AgrementTable.setItems(promoForAgrement);
        Promoes_AgrementTable.refresh();
    }
    public void ReViewsTableInsertData(){
        ReViews_list.clear();
        ReViews_list.addAll(usersManager.getReViews());
        Reviews_Table.setItems(ReViews_list);
        Reviews_Table.refresh();

    }
    public void BasicTableInsertData(){
        usersData.clear();
        usersData.addAll(usersManager.getUsersValOnline());
        Simple_Users_Table.setItems(usersData);
        Simple_Users_Table.refresh();
    }

    private void SellersTableInsertData(){
        Sellers.clear();
        Sellers.addAll(usersManager.getAllSellers());
        Sellers_Table.setItems(Sellers);
        Sellers_Table.refresh();
    }

    public void STARTBOT(){

    }
    public void LOADALLDATA(){
        ReViewsTableInsertData();
        BasicTableInsertData();
        PromoAgrTableInsertData();
        SellersTableInsertData();
        Promoes_OtherTablesInsertData();
        Groups_SetIdList();
    }
    //-----------------------------------------Экстренная загрузка данных из бд
    private void loadPostsFromDB(){
        Promoes_AllPosts_Table_Data.clear();
        Promoes_AllPosts_Table_Data.addAll(usersManager.getAllPostsDB());
        Promoes_AllPosts_Table.setItems(Promoes_AllPosts_Table_Data);
        Promoes_AllPosts_Table.refresh();
    }
    private void loadSellersFromDB(){
        Sellers.clear();
        Sellers.addAll(usersManager.getAllSellersDB());
        System.out.println("Size               : " + Sellers.size());
        Sellers_Table.setItems(Sellers);
        Sellers_Table.refresh();
    }
    public void getReViewsFromDb(){
        ReViews_list.clear();
        ReViews_list.addAll(usersManager.getReViewsDb());
        Reviews_Table.setItems(ReViews_list);
        Reviews_Table.refresh();
    }

}