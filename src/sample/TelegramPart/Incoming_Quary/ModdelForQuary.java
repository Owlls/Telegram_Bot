package sample.TelegramPart.Incoming_Quary;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.TelegramPart.KeyBoards.Keyboards;
import sample.TelegramPart.Text.TextForMessages;

public class ModdelForQuary extends EditMessageText {

    private KeyBoardData data;
    private int pos_query;
    private int dialilog_pos;
    private int prepos_query;

    public ModdelForQuary(int pos_query,KeyBoardData data){
        this.pos_query = pos_query;
        this.data = data;
        //this.setParseMode("MarkdownV2");
        SetTeext();
        SetKeyboard();

    }
    public ModdelForQuary(int pos_query,KeyBoardData data,String text){
        this.pos_query = pos_query;
        this.data = data;
        this.setParseMode("Markdown");
        this.setText(text);
        SetKeyboard();
    }

    private void SetTeext(){
        this.setText(TextForMessages.getMessege_for_users(pos_query));
    }

    private void SetKeyboard(){
        Keyboards keyboards = new Keyboards(pos_query,this,data);
    }
}
