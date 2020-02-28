package sample.TelegramPart.KeyBoards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Inline_Keyboard {

    private List<List<InlineKeyboardButton>> rowList;
    private KeyBoardData data;

    public Inline_Keyboard(KeyBoardData data){
        this.data = data;
        init();

    }

    private void init(){
        rowList = new ArrayList<List<InlineKeyboardButton>>();
        if(data.IsHave_SwitchData() && data.IsHave_URLData()){
            set_AllDataRows(data.getNumber_buttons(), data.getText_for_rows(), data.getData_for_ibuttons(),data.getUrldata_for_ibuttons(),data.getSwitch_Data());
        } else if(data.IsHave_URLData()){
            set_URLRows(data.getNumber_buttons(), data.getText_for_rows(), data.getData_for_ibuttons(),data.getUrldata_for_ibuttons());
        } else if(data.IsHave_SwitchData()){
            set_SwitchDataRows(data.getNumber_buttons(), data.getText_for_rows(), data.getData_for_ibuttons(),data.getSwitch_Data());
        } else {
            set_DataRows(data.getNumber_buttons(), data.getText_for_rows(), data.getData_for_ibuttons());
        }
    }

    private void set_DataRows(int [] number_row,String [][] text,String [][]dataT){ //Клавиатуру которую пользователь видеит после нажатия на кнопку продавец
        for(int i = 0 ; i < number_row.length; i++){
            List<InlineKeyboardButton> row = new ArrayList<InlineKeyboardButton>();
            for(int j = 0; j < number_row[i]; j++){
                row.add(make_Button(text[i][j], dataT[i][j]));
                System.out.println(" Data2 " + text[i][j]);
            }
            rowList.add(row);
        }
    }
    private void set_URLRows(int [] number_row,String [][] text,String [][]dataT, String[][] url_data){ //Клавиатуру которую пользователь видеит после нажатия на кнопку продавец
        for(int i = 0 ; i < number_row.length; i++){
            List<InlineKeyboardButton> row = new ArrayList<InlineKeyboardButton>();
            for(int j = 0; j < number_row[i]; j++){
                if(url_data[i] != null){
                    if (url_data[i].length > j) {
                        if (url_data[i][j] != null) {
                            row.add(make_Button(text[i][j], dataT[i][j], url_data[i][j]));
                        }else{
                            row.add(make_Button(text[i][j], dataT[i][j]));
                        }
                    }else{
                        row.add(make_Button(text[i][j], dataT[i][j]));
                    }
                }else{
                    row.add(make_Button(text[i][j], dataT[i][j]));
                }
            }
            rowList.add(row);
        }
    }
    private void set_SwitchDataRows(int [] number_row,String [][] text,String [][]dataT,String[][] switch_data){ //Клавиатуру которую пользователь видеит после нажатия на кнопку продавец
        for(int i = 0 ; i < number_row.length; i++){
            List<InlineKeyboardButton> row = new ArrayList<InlineKeyboardButton>();
            for(int j = 0; j < number_row[i]; j++){
                 if (switch_data[i][j] != null && switch_data[i][j] != ""){
                    row.add(make_SitchDataButton(text[i][j], switch_data[i][j]));
                } else if(dataT[i][j] != null && dataT[i][j] != ""){
                    row.add(make_Button(text[i][j], dataT[i][j]));
                }
            }
            rowList.add(row);
        }
    }
    private void set_AllDataRows(int [] number_row,String [][] text,String [][]dataT,String[][] url_data,String[][] switch_data){ //Клавиатуру которую пользователь видеит после нажатия на кнопку продавец
        for(int i = 0 ; i < number_row.length; i++){
            List<InlineKeyboardButton> row = new ArrayList<InlineKeyboardButton>();
            for(int j = 0; j < number_row[i]; j++){
                if(url_data[i][j] != null && url_data[i][j] != "") {
                    row.add(make_URLButton(text[i][j],url_data[i][j]));
                } else if (switch_data[i][j] != null && switch_data[i][j] != ""){
                    row.add(make_SitchDataButton(text[i][j], switch_data[i][j]));
                } else if(dataT[i][j] != null && dataT[i][j] != ""){
                    row.add(make_Button(text[i][j], dataT[i][j]));
                }
                System.out.println(" all " + text[i][j]);
            }
            rowList.add(row);
        }
    }
    private InlineKeyboardButton make_Button(String text,String dataT){ //Клавиатурa
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setCallbackData(dataT);
        return inlineKeyboardButton;
    }
    private InlineKeyboardButton make_URLButton(String text,String dataT){ //
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setUrl(dataT);
        return inlineKeyboardButton;
    }
    private InlineKeyboardButton make_SitchDataButton(String text,String dataT){ //
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setSwitchInlineQueryCurrentChat(dataT);
        return inlineKeyboardButton;
    }

    private InlineKeyboardButton make_Button(String text,String dataT,String switch_data){ //
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(text);
        inlineKeyboardButton.setUrl(switch_data);
        return inlineKeyboardButton;
    }
    public List<List<InlineKeyboardButton>> getRowList(){
        return rowList;
    }

}
