package sample.TelegramPart;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import sample.TelegramPart.Incoming_InlineQuary.InlineQuaryChecker;
import sample.TelegramPart.Incoming_Messages.MessageCheker;
import sample.TelegramPart.Incoming_Messages.ModelOfMessage;
import sample.TelegramPart.Incoming_Quary.QuaryCheker;
import sample.TelegramPart.ModelsForPosts.EditPost;
import sample.TelegramPart.ModelsForPosts.PostInGroup;
import sample.TelegramPart.ModelsForPosts.PostPreview;
import sample.TelegramPart.Promo.PostManager;
import sample.UsersData.UsersManager;

public class IncomingUpdates {

    private Update upd;
    private ModelOfMessage modelOfMessage;
    private EditMessageText modelOfQuery;
    private PostPreview postPreview;
    private User user;
    private MessageCheker Mc;
    private QuaryCheker Qc;
    private InlineQuaryChecker Ic;
    private PostInGroup modelPost;
    private EditPost editPost;
    private int updId;
    private UsersManager usersManager;
    private PostManager postManager;

    public void setUsersManager(UsersManager usersManager) {
        this.usersManager = usersManager;
    }
    public void setPostManager(PostManager postManager) {
        this.postManager = postManager;
    }


    public PostInGroup getModelPost() {
        return modelPost;
    }

    public EditPost getEditPost() {
        return editPost;
    }

    public PostPreview getPostPreview() {
        return postPreview;
    }

    public EditMessageText getModelOfQuery() {
        return modelOfQuery;
    }

    public ModelOfMessage getModelOfMessage() {
        return modelOfMessage;
    }

    public IncomingUpdates(){ //Сюда получаем сообщение

    }
    public void GetUpdate(Update upd){
        this.upd = upd;
        modelOfQuery = null;
        modelOfMessage = null;
        postPreview = null;
        modelPost = null;
        editPost = null;
        System.out.println(" UPDATE - " + upd.toString());
        if(upd.hasCallbackQuery()){    //Для входящих сообщений

            updId =  upd.getUpdateId();
            user = upd.getCallbackQuery().getFrom();
            CallbackQuery_checker();
        }
        if(upd.hasMessage()){          //Для входящих обновлений

            user = upd.getMessage().getFrom();
            Message_cheker();
        }
        if(upd.getInlineQuery() != null){
            user = upd.getInlineQuery().getFrom();
            System.out.println("First");
            InlineQuery_checker();
        }
    }

    private void CallbackQuery_checker(){
        Qc = new QuaryCheker(user,upd.getCallbackQuery(),upd.getCallbackQuery().getData(),postManager,usersManager);
        set_Quary();
    }
    private void InlineQuery_checker(){
        Ic = new InlineQuaryChecker(user,upd.getInlineQuery(),usersManager,postManager);

    }

    private void Message_cheker(){//1
         //Создаем объект, который проверяет сообщения и с помощью его метода, мы будем менять позицию в диалоге
        Mc = new MessageCheker(user,upd.getMessage(),postManager,usersManager);
        set_Message();
    }

    private void set_Message(){ //3;
        if(Mc.getModel() !=null){
            modelOfMessage = Mc.getModel();
            finalStep();
        }
        if(Mc.getModelQ() !=null){
            modelOfQuery = Mc.getModelQ();
            finalStepQ();
        }
    }

    private void set_Quary(){
       if(Qc.getModdelForQuary() != null){
           modelOfQuery = Qc.getModdelForQuary();
       }
       if(Qc.getModelOfMessage() != null){
           modelOfMessage = Qc.getModelOfMessage();
       }
       if(Qc.getPreview() != null){
           postPreview = Qc.getPreview();
       }
       if(Qc.getModelPost() != null){
           modelPost = Qc.getModelPost();
       }
       if(Qc.getEditPost() != null){
           editPost = Qc.getEditPost();
       }
    }

    private void finalStep() {//4
        if(upd.hasMessage()) {
            modelOfMessage.setReplyToMessageId(upd.getMessage().getMessageId());
            modelOfMessage.setChatId(upd.getMessage().getChatId());
        } else {
            modelOfMessage.setChatId(upd.getCallbackQuery().getMessage().getChatId());
        }
    }

    private void finalStepQ() {//4
        //modelOfQuery.setChatId(upd.getCallbackQuery().getMessage().getChatId());
        //modelOfQuery.setMessageId(upd.getCallbackQuery().getMessage().getMessageId());
    }
    public void deleteModdels(){
        modelOfMessage = null;
        modelOfQuery = null;
        modelPost = null;
        postPreview = null;
        editPost = null;
    }
}
