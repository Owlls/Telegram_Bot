package sample.TelegramPart;

import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sample.GuiPart.Errors;
import sample.TelegramPart.Incoming_Messages.ModelOfMessage;
import sample.TelegramPart.ModelsForPosts.PostInGroup;
import sample.TelegramPart.ModelsForPosts.PostPreview;

public class GetSendMessage extends TelegramLongPollingBot {   // Класс представляет бота умеет получать и отсыласть сообщения

    private final String BOT_TOKEN = "";//
    private final String BOT_NAME = "";
    private IncomingUpdates message;
    private CallbackQuery callbackQuery;

    public GetSendMessage(IncomingUpdates message){
        System.out.println("  ----------------------------  " + Thread.currentThread().getName());
        this.message = message;
    }



    @Override
    public void onUpdateReceived(Update update) { //Просто отсылает все полученные сообщения в класс IncomingUpdates
            message.GetUpdate(update);
        try {
            send();
        } catch (TelegramApiException e) {
            Errors.AddError(" Post ");
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public void sen_message(EditMessageText model) {
        try {
              execute(model);
              message.deleteModdels();
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

    public Message sen_message(ModelOfMessage model) {
        Message mes = null;
        try {
            mes = execute(model);
            message.deleteModdels();
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
        return mes;
    }

    public void sen_message(PostPreview model) {
        SendPhoto sendPhoto = new SendPhoto();
        try {
            execute(model);
            message.deleteModdels();
        } catch (TelegramApiException e) {
            e.printStackTrace();

        }
    }

    private void send() throws TelegramApiException {
        if(message.getModelOfQuery() != null){
            EditMessageText model = message.getModelOfQuery();
            sen_message(model);
        }
        if(message.getModelOfMessage() != null){
            ModelOfMessage model = message.getModelOfMessage();
            sen_message(model);
        }
        if(message.getPostPreview() != null){
            PostPreview sendPhoto = message.getPostPreview();
            sen_message(sendPhoto);
        }
        if(message.getEditPost() != null){
            execute(message.getEditPost());
        }
        if(message.getModelPost() != null)
            execute(message.getModelPost());
    }


}
