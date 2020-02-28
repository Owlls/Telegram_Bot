package sample.TelegramPart.ModelsForPosts;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.TelegramPart.KeyBoards.Keyboards;
import sample.UsersData.Post;
import sample.UsersData.Reviews.ReView;
import sample.UsersData.Users.Seller;

import java.util.ArrayList;

public class PostInGroup extends SendPhoto {

    private double Index = 0;
    private double NUM_COMMENTS = 0;
    private double Conformiti = 0;
    private double Service = 0;
    private double Quality = 0;
    private double Q_P = 0;
    private double Recomended = 0;

    private Post post;

    public PostInGroup(Post post, KeyBoardData keyboard, ArrayList<ReView> list){
        setPhoto(post.getImagePath());
        setReViews(list);
        String textpost = makeText(post);
        String textReview;
        setCaption(textpost);
        new Keyboards(101,this, keyboard);
    }

    public PostInGroup(int pos,Post post, KeyBoardData keyboard, ArrayList<ReView> list){
        setPhoto(post.getImagePath());
        setReViews(list);
        String textpost = makeText(post);
        String textReview;
        setCaption(textpost);
        new Keyboards(pos,this, keyboard);
    }

    public PostInGroup(Post post,KeyBoardData keyboard){
        setPhoto(post.getImagePath());
        String textpost = makeText(post);
        String textReview;
        setCaption(textpost);
        new Keyboards(101,this, keyboard);
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
