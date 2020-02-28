package sample.TelegramPart.KeyBoards;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import sample.TelegramPart.Incoming_Messages.ModelOfMessage;
import sample.TelegramPart.ModelsForPosts.EditPost;
import sample.TelegramPart.ModelsForPosts.PostInGroup;
import sample.TelegramPart.ModelsForPosts.PostPreview;
import sample.TelegramPart.Text.TextForKeybors;

public class Keyboards {


    ModelOfMessage modelOfMessage;
    Simple_Keyboard simple_keyboard;
    Inline_Keyboard inline_keyboard;
    private int position;
    private int position_in_Quary;
    private KeyBoardData data;
    private EditMessageText modelQuary;
    private PostPreview modelPreviewPost;
    private PostInGroup postInGroup;
    private EditPost Edit_Post;

    public Keyboards(int position,ModelOfMessage modelOfMessage, KeyBoardData data){
        this.data = data;
        this.position = position;
        this.modelOfMessage = modelOfMessage;
        Choose_kind_Keyboard(position);
    }
    public Keyboards(int position_in_Quary ,EditMessageText messageText,KeyBoardData data){
        this.modelQuary = messageText;
        this.data = data;
        this.position_in_Quary = position_in_Quary;
        Choose_kind_Keyboard(position_in_Quary);
    }
    public Keyboards(int position_in_Quary , PostPreview mypost, KeyBoardData data){
        this.modelPreviewPost = mypost;
        this.data = data;
        this.position = position_in_Quary;
        Choose_kind_Keyboard(position_in_Quary);
    }
    public Keyboards(int position_in_Quary , PostInGroup mypost, KeyBoardData data){
        this.postInGroup = mypost;
        this.data = data;
        this.position = position_in_Quary;
        Choose_kind_Keyboard(position_in_Quary);
    }
    public Keyboards(int position_in_Quary , EditPost mypost, KeyBoardData data){
        this.Edit_Post = mypost;
        this.data = data;
        this.position = position_in_Quary;
        set_Inline_keybord();
    }
    private void Choose_kind_Keyboard(int pos){ //Этот метод проверит позицию и в зависимости от результата запустит метод, который установит нужную клавиатуру
        switch (pos){
            case 1 : set_Simple_keyboard(); break;

            case 2 : set_Simple_keyboard(); break;

            case  3: set_Inline_keybord(); break;
        }
    }

    private void set_Simple_keyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        simple_keyboard = new Simple_Keyboard(data);
        replyKeyboardMarkup.setKeyboard(simple_keyboard.getRowList());
        if(modelPreviewPost != null) {
            modelPreviewPost.setReplyMarkup(replyKeyboardMarkup);
        }else if(modelOfMessage != null){
         modelOfMessage.setReplyMarkup(replyKeyboardMarkup);
        }

    }
    private void set_Simple_keyboard(int pos) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        simple_keyboard = new Simple_Keyboard(data);
        replyKeyboardMarkup.setKeyboard(simple_keyboard.getRowList());
        if(modelPreviewPost != null) {
            modelPreviewPost.setReplyMarkup(replyKeyboardMarkup);
        }else if(modelOfMessage != null){
            modelOfMessage.setReplyMarkup(replyKeyboardMarkup);
        }

    }
    private void set_Inline_keybord(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inline_keyboard = new Inline_Keyboard(data);
        inlineKeyboardMarkup.setKeyboard(inline_keyboard.getRowList());
        if(modelQuary != null) {
            modelQuary.setReplyMarkup(inlineKeyboardMarkup);
        } else if(modelOfMessage != null){
            modelOfMessage.setReplyMarkup(inlineKeyboardMarkup);
        } else if(modelPreviewPost != null){
            modelPreviewPost.setReplyMarkup(inlineKeyboardMarkup);
        } else if(postInGroup != null){
            postInGroup.setReplyMarkup(inlineKeyboardMarkup);
        } else if(Edit_Post != null){
            Edit_Post.setReplyMarkup(inlineKeyboardMarkup);
        }
    }

}
