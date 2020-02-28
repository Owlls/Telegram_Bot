package sample.TelegramPart.Incoming_Messages;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.TelegramPart.KeyBoards.Keyboards;
import sample.TelegramPart.Text.TextForKeybors;
import sample.TelegramPart.Text.TextForMessages;

public class ModelOfMessage extends SendMessage { //Будет получать позицию в диалоге и в зависимости от позиции будет составлять текст сообщения

    private TextForMessages textForMessages = new TextForMessages();
    private int position;
    private int quary_pos;
    private KeyBoardData data;

    public ModelOfMessage(int position, KeyBoardData data) {
        this.data = data;
        this.position = position;
        this.SetText();
        this.SetKeyboard();

    }
    public ModelOfMessage(int position, KeyBoardData data,String text) {

        this.data = data;
        this.position = position;
        this.SetKeyboard();
        this.setText(text);

    }
    private void SetText() {
        this.setParseMode("MarkdownV2");
        this.setText(TextForMessages.getMessege_for_users(position));

    }
    private void SetText(int quary_pos) {
        this.setText(TextForMessages.getMessege_for_users(quary_pos));

    }

    private void SetKeyboard() {
        this.enableMarkdown(true);
        Keyboards keyboards = new Keyboards(position,this,data);

    }
    private void SetKeyboard(int qr) {
        Keyboards keyboards = new Keyboards(qr,this, data);

    }
}