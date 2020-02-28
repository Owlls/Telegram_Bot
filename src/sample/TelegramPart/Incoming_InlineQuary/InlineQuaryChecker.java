package sample.TelegramPart.Incoming_InlineQuary;

import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto;
import sample.TelegramPart.BotsManager;
import sample.TelegramPart.Promo.PostManager;
import sample.UsersData.Post;
import sample.UsersData.Users.AUser;
import sample.UsersData.Users.Seller;
import sample.UsersData.UsersManager;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

public class InlineQuaryChecker {
    private UsersManager usersManager;
    private PostManager postManager;
    private int userId;
    private String QuaaryIs;
    private AUser aUser;
    private Seller seller;
    private Seller getpost;
    private static int count =0;

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        InlineQuaryChecker.count = count;
    }

    public InlineQuaryChecker(User user, InlineQuery query, UsersManager manager, PostManager postManager) {
        System.out.println("Second");
        count++;
        QuaaryIs = query.getId();
        this.postManager = postManager;
        this.usersManager = manager;

        if (!usersManager.IsNewUser(user)) {
            this.userId = user.getId();
            aUser = usersManager.GettingUser(user);
            if (aUser.isSeller()) {
                seller = usersManager.GettingSeller(userId);
            }


        }
        CheckQuary(query.getQuery());
    }

    private void CheckQuary(String string){
         getpost = usersManager.findWithUsername(string.trim());
         if(getpost != null){
             System.out.println("Third");
             findPosts(getpost.getUserId());
         }
    }
    private void findPosts(int userId){
        System.out.println("Find For " +userId);
        ArrayList<Post> posts = new ArrayList<Post>();
        Iterator<Post> iterator = postManager.getAllAgrementPost().iterator();
        System.out.println("Size " + postManager.getAllAgrementPost().size());
        while (iterator.hasNext()){
            Post post = iterator.next();
            System.out.println(post.getUserId());
                if(post.getUserId() == userId){
                    posts.add(post);
                }
        }
        System.out.println("Posts num is ");
        if(!posts.isEmpty()){
            System.out.println("10");
            try {
                anser(posts);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }

        }

     }
     private void anser(ArrayList<Post> posts) throws MalformedURLException {

         AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
         InlineQueryResult [] result = new InlineQueryResult[posts.size()];
         System.out.println("posts - s i z e " + posts.size());
         for(int i=0; i < result.length; i++){
            InlineQueryResultPhoto photo = new InlineQueryResultPhoto();
             System.out.println(" PHOTO " + posts.get(i).getImagePath().toString()  );
            photo.setPhotoUrl(posts.get(i).getImagePath().toURI().toURL().toString());
            photo.setId(String.valueOf(i));
            photo.setDescription(posts.get(i).getDescription());
            photo.setThumbUrl(posts.get(i).getThumb().toURI().toURL().toString());
             System.out.println(" PHOTO " + posts.get(i).getThumb().toString()  );
            //photo.setMimeType("photo");
            result[i] = photo;
             System.out.println("sffsf");
         }
         answerInlineQuery.setResults(result);
         answerInlineQuery.setInlineQueryId(QuaaryIs);
         answerInlineQuery.setPersonal(true);
         System.out.println("Fdddd");
         BotsManager.AnswerQuary(answerInlineQuery);

     }
}
