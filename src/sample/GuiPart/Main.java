package sample.GuiPart;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.TelegramPart.BotsManager;
import sample.TelegramPart.IncomingUpdates;
import sample.TelegramPart.Promo.PostManager;
import sample.TelegramPart.Text.TextForMessages;
import sample.UsersData.UsersManager;

import static javafx.application.Application.launch;

public class Main extends Application {
    private Controller controller;
    private UsersManager usersManager;
    private PostManager postManager;
    private IncomingUpdates updates;
    private BotsManager botsManager;

    public Main(){
        System.out.println("Constructor of Main  ");


    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = (AnchorPane)loader.load();
        controller = loader.getController();
        primaryStage.setTitle("Aizi");
        primaryStage.setScene(new Scene(root, 1668, 1000));
        primaryStage.show();
        LOADIND_DATA();
        setController();
        initBotManager();

    }
    private void LOADIND_DATA(){
         usersManager = new UsersManager(); //При создании загружает базу данных
        postManager = new PostManager(usersManager);  //При создании десереализует Файлы


        updates = new IncomingUpdates();
    }
    public void setController(){
        controller.setPostManager(postManager);
        controller.setUsersManager(usersManager);
    }

    private void initBotManager(){ //Нстраиваем бота перед первым запуском
        updates.setPostManager(postManager);
        updates.setUsersManager(usersManager);
        botsManager = new BotsManager(updates);
        Thread listen_telegram = new Thread(botsManager);
        listen_telegram.start();

    }


    public static void main(String[] args) {
        launch(args);
    }

 //------------------------------------------------------------------------------------------
/*
    public class Main{
    private UsersManager usersManager = new UsersManager();
    private PostManager postManager = new PostManager(usersManager);
    private IncomingUpdates updates = new IncomingUpdates();
    private Controller controller;
    private BotsManager botsManager;

     public static void main(String[] args) {
        Main main = new Main();
        main.LoadData();
        main.initBotManager();


    }
      private void initBotManager(){ //Нстраиваем бота перед первым запуском
        updates.setPostManager(postManager);
        updates.setUsersManager(usersManager);
        botsManager = new BotsManager(updates);
        Thread listen_telegram = new Thread(botsManager);
        listen_telegram.start();

    }

    private void LoadData(){ //Загружаем данные
       // usersManager.getData();
        postManager = new PostManager(usersManager); //При создании десереализует Файлы
        updates = new IncomingUpdates();
        System.out.println("Razmer  " + usersManager.getUsersOnline().size());
    }








    public Main(){
        System.out.println("Constructor of Main  ");


    }
*/
}
