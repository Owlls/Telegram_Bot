package sample.TelegramPart.ModelsForPosts;

import javafx.geometry.Pos;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.TelegramPart.KeyBoards.Keyboards;
import sample.UsersData.Post;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class PostPreview extends SendPhoto {
    private KeyBoardData data;

    public PostPreview(int position,Post post,KeyBoardData data){
        this.data = data;
        setPhoto(post.getImagePath());
        setCaption(makeStr(post));
        SetKeyboard(position);
    }
    private String makeStr(Post post){
        StringBuilder str = new StringBuilder();
        str.append(post.getDescription());
        str.append("\n");
        str.append("- - - - - - - - - - - - - - - - - - - - - - - -");
        str.append("\n");
        str.append(post.getKind());
        str.append("\n");
        str.append("- - - - - - - - - - - - - - - - - - - - - - - -");
        str.append("\n");
        str.append(post.getKind_B());
        str.append("\n");
        for (String a: post.getPrices()){
            str.append(a + " ");
            str.append("\n");
        }
        str.append("______________________________ ");
        str.append("\n");
        for (String a: post.getLocations()){
            str.append(a);
            str.append("\n");
        }

        str.append("______________________________");
        return str.toString();

    }
    private void SetKeyboard(int qr) {
        Keyboards keyboards = new Keyboards(qr,this, data);

    }
}
