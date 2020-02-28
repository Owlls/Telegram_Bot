package sample.TelegramPart;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import sample.GuiPart.Errors;
import sample.TelegramPart.Incoming_Messages.ModelOfMessage;
import sample.TelegramPart.ModelsForPosts.PostInGroup;
import sample.TelegramPart.Promo.PostManager;
import sample.UsersData.UsersManager;

import java.io.File;

public class BotsManager implements Runnable {
    BotSession botSession;
    private static GetSendMessage getSendMessage;
    private IncomingUpdates updates;


    public static void DeleteMessage(Long chatId,int mesId){
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(mesId);
        try {
            getSendMessage.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
    public static void AnswerQuary( AnswerInlineQuery answerInlineQuery){
        try {
            getSendMessage.execute(answerInlineQuery);
            System.out.println("HOROSHO VSE BUDET HOROSHO PASHA!");
        } catch (TelegramApiException e) {
            System.out.println("PLoho");
            e.printStackTrace();
        }
    }
    public static boolean MakePost(PostInGroup post){

        try {
            getSendMessage.execute(post);
            return true;
        } catch (TelegramApiException e) {
            e.printStackTrace();
            Errors.AddError("False To Make Post in Group 8!");
            return false;
        }
    }
    public static void EditMes(EditMessageText model){
        getSendMessage.sen_message(model);
    }

    public static Message SendMes(ModelOfMessage model){
        Message mes = getSendMessage.sen_message(model);
        return mes;
    }
    public BotsManager(IncomingUpdates updates){
            this.updates = updates;
    }

    public static String getFilePath(GetFile getFileRequest){
        try {
            final org.telegram.telegrambots.meta.api.objects.File file = getSendMessage.execute(getFileRequest);
            return file.getFilePath();
        } catch (final TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File download_file(final String filePath){
        try {
            return getSendMessage.downloadFile(filePath);
        } catch (final TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void init(){

        ApiContextInitializer.init();
        System.out.println(Thread.currentThread().getName());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try{
            botSession = telegramBotsApi.registerBot(getSendMessage = new GetSendMessage(updates));
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public synchronized void  StartBOT(){

       botSession.start();
    }

    public synchronized void StopBot(){
        botSession.stop();
    }
    @Override
    public void run() {
        init();
        System.out.println("   START    ");


    }


}
