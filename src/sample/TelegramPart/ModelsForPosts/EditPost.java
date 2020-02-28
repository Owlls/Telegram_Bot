package sample.TelegramPart.ModelsForPosts;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.TelegramPart.KeyBoards.Keyboards;
import sample.UsersData.Post;
import sample.UsersData.Reviews.ReView;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class EditPost extends EditMessageMedia {

    private double NUM_COMMENTS = 0;
    private double Index = 0;
    private double Conformiti = 0;
    private double Service = 0;
    private double Quality = 0;
    private double Q_P = 0;
    private double Recomended = 0;
    private InputMediaPhoto obj;

    private Post post;

    public EditPost(int pos,Post post, KeyBoardData keyboard, ArrayList<ReView> list) {
       // String pass = post.;

        //pass = String.format();

        obj = new InputMediaPhoto().setMedia(post.getImagePath(),post.getImagePath().toString());
        setReViews(list);
        String textpost = makeText(post);
        String textReview;
        obj.setCaption(textpost);
        new Keyboards(101,this, keyboard);
        setMedia(obj);
    }

    public EditPost(Post post,KeyBoardData keyboard){
        obj = new InputMediaPhoto().setMedia(post.getImagePath().toString());
        String textpost = makeText(post);
        String textReview;
        obj.setCaption(textpost);
        new Keyboards(101,this, keyboard);
        setMedia(obj);
    }

    private void setReViews(ArrayList<ReView> list){
        ArrayList<ReView> Reviews = list;
        int count = 0;
        for(int i = 0; i < Reviews.size(); i++){
            if(Reviews.get(i).isBuyerGiveReV()){
                count++;
            }
        }
        for(ReView reView : Reviews){
            Index = Index + (reView.getIndexQ() / count);
            Conformiti = Conformiti +(reView.getConformity()? (10/count):0);
            Service = Service + (reView.getService()/count);
            Quality = Quality + (reView.getQuality()/count);
            Q_P = Q_P + (reView.getQ_p()/count);
            Recomended = Recomended + (reView.getRecomended()? (10/count):0);
        }
        NUM_COMMENTS = count;
    }
    private String makeText(Post post){
        StringBuilder str = new StringBuilder();
        str.append("  מידע על הסוחר:");
        str.append("\n");
        str.append( "כמות ביקורות: " + NUM_COMMENTS);
        str.append("\n");
        str.append("ממוצע אמינות: "  + Conformiti +  " (/10)" );
        str.append("\n");
        str.append("ממוצע שירות ושילוח: " +  Service +  " (/10)");
        str.append("\n");
        str.append("איכות החומר: " + Quality + " (/10)");
        str.append("\n");
        str.append("איכות החומר ביחס למחיר: " + Q_P + " (/10)");
        str.append("\n");
        str.append("ממוצע המלצות: " + Recomended + " (/10)" );
        str.append("\n");
        str.append("________________________________");
        str.append("\n");
        str.append(post.getDescription());
        str.append("\n");
        str.append("- - - - - - - - - - - - - - - - - - - - - -");
        str.append("\n");
        str.append(post.getKind());
        str.append("\n");
        str.append("- - - - - - - - - - - - - - - - - - - - - -");
        str.append("\n");
        str.append(post.getKind_B());
        str.append("\n");
        for (String a: post.getPrices()){
            str.append(a + " ");
            str.append("\n");
        }
        str.append("_________________________________");
        str.append("\n");
        for (String a: post.getLocations()){
            str.append(a);
            str.append("\n");
        }


        return str.toString();

    }



}
