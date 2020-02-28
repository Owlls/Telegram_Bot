package sample.TelegramPart.KeyBoards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class Simple_Keyboard  { //Класс для простой клавиатуры

    List<KeyboardRow> rowList;
    private KeyBoardData data;

    private void init(){
        rowList  = new ArrayList<KeyboardRow>();
        set_Rows(data.getNumber_buttons(),data.getText_for_rows());
    }

    private void set_Rows(int [] number_row,String [][] text){ //Клавиатуру которую пользователь видеит после нажатия на кнопку продавец
        for(int i = 0 ; i < number_row.length; i++){
            KeyboardRow row = make_Row();
            for(int j = 0; j < number_row[i]; j++){
                row.add(make_Button(text[i][j]));
                System.out.println(" TEXTTT " + text[i][j]);
            }
            rowList.add(row);

        }
    }

    private KeyboardButton make_Button(String text){ //Клавиатуру которую пользователь видеит после нажатия на кнопку продавец
        KeyboardButton kb = new KeyboardButton();
        kb.setText(text);
        return kb;
    }
    private KeyboardRow make_Row(){ //Клавиатуру которую пользователь видеит после нажатия на кнопку продавец
        KeyboardRow row = new KeyboardRow();
        return row;
    }


    public List<KeyboardRow> getRowList(){
        return rowList;
    }



    public Simple_Keyboard(KeyBoardData data){
        this.data = data;
        init();


    }
}
