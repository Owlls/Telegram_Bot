package sample.TelegramPart.Incoming_Messages;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;
import sample.TelegramPart.BotsManager;
import sample.TelegramPart.Finder.Filters;
import sample.TelegramPart.Finder.Finder;
import sample.TelegramPart.Incoming_Quary.ModdelForQuary;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.TelegramPart.ModelsForPosts.EditPost;
import sample.TelegramPart.Promo.PostManager;
import sample.TelegramPart.Text.TextForKeybors;
import sample.TelegramPart.Text.TextForMessages;
import sample.TelegramPart.Text.TextFromMesseges;
import sample.UsersData.Post;
import sample.UsersData.PromoteSettings.PromoContainer;
import sample.UsersData.Reviews.ReView;
import sample.UsersData.Users.AUser;
import sample.UsersData.Users.Seller;
import sample.UsersData.UsersManager;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageCheker {
    private boolean isNew;
    private EditPost editPost;
    private Message message;
    private int position;
    private int Q_position;
    private int Pre_position;
    private int Pre_Q_position;
    private AUser aUser;
    private Seller seller;
    private boolean isSeller;
    private int userId;
    private ModelOfMessage model;
    private ModdelForQuary modelQ;
    private UsersManager usersManager;
    private PostManager postManager;
    private String TextForMessage;
    private TextForMessages textTo = new TextForMessages();
    private TextFromMesseges textOfMesseges = new TextFromMesseges();
    private TextForKeybors getKeyBoardsData = new TextForKeybors();
    private KeyBoardData data;

    public MessageCheker(User user, Message message, PostManager postManager, UsersManager usersManager){ //1
        this.postManager = postManager;
        this.usersManager = usersManager;
        isNew = usersManager.IsNewUser(user);// Проверям если пользователь уже существует - делаем запрос к бд, через userManager по userId,если нет то за одно вносим его данные
        aUser = usersManager.GettingUser(user);
        if(usersManager.UserConnnecton(aUser) != null){

        }

        this.userId = user.getId();
        this.message = message;
        this.position = aUser.getPosition_in_dialog();
        ChekUser();
        chekType();

    }

    public ModelOfMessage getModel() {
        return model;
    }

    public ModdelForQuary getModelQ() {
        return modelQ;
    }

    private void ChekUser(){ //Проверяем если пользователь уже был и какие предыдущие кнопки он нажимал
        if(!isNew) {
            if (aUser.getQPosition_in_dialog() != 0) {
                this.Q_position = aUser.getQPosition_in_dialog();
            }
            if (aUser.isSeller()) {
                seller = usersManager.GettingSeller(userId);
                isSeller = true;
            }
        }
    }

    private void chekType(){ //Проверяем тип сообщения //2
        System.out.println("Текст из сообя " + message.getText());
        if(message.hasText()){
            Check_text(message.getText());

        }
        if(message.hasDocument()){}
        if(message.hasEntities()){}
        if(message.hasPhoto()){
            final List<PhotoSize> photos = message.getPhoto();
            final PhotoSize photoSize = photos.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null);
            Check_Photo(photoSize);
        }
        if(message.hasInvoice()){}
    }

    private void Check_Photo(final PhotoSize photo){
        File file;
        if (photo.hasFilePath()) {
            file = BotsManager.download_file(photo.getFilePath());
        } else {
            final GetFile getFileRequest = new GetFile();
            getFileRequest.setFileId(photo.getFileId());
            String fp = BotsManager.getFilePath(getFileRequest);
            file = BotsManager.download_file(fp);
        }
        Save_Photo(file);
    }

    private void Check_text(String text){ //Проверяем текст сообщения //3
        usersManager.SetWasLastTime(aUser.getUserId());
        if(textOfMesseges.pos_in_dialog(text) != -1) {

            int ClickON = textOfMesseges.pos_in_dialog(text);
            switch (ClickON){
                case 3: if(!aUser.isSeller()){
                            make_mes(10); /////*/************************************************
                        } else {
                            make_mes(ClickON);
                }break;//Если он не является продавцем то запрашиваем 10 клаву
                case 11:if(aUser.isSeller()){make_mes(3);} break; //После того, как пользователь уже согласился
                case 12:{


                    make_mes(ClickON);
                }break;
                case 4: if(aUser.isSeller()){make_mes(ClickON); seller.setQPosition_in_dialog(4);}break;
                case 6: if(aUser.isSeller()){make_mes(ClickON);}break;
                case 7: if(aUser.isSeller()){make_mes(ClickON);}break;
                case 506:{Q_position = 506;}break;
                case 5000:{aUser.createFinder(usersManager); make_mes(5000);}break;
                case 6000:{aUser.createFinder(usersManager);make_mes(6000);}break;
                case 99: if(usersManager.UserConnnecton(aUser) != null && (Q_position >= 500 && Q_position <= 506)){
                    int a = aUser.getQPreposition_in_dialog();
                    if(a >= 500 && a <= 506){
                        aUser.setQPosition_in_dialog_(a);
                        Q_position = a;

                    }else {
                        // aUser.setQPosition_in_dialog(500);
                        if(!aUser.isSeller()) {
                            Q_position = 500;
                        }
                    }
                } else{
                        try {
                            int a = aUser.getPreposition_in_dialog();
                            int b = aUser.getQPreposition_in_dialog();
                            if(b >= 500 && b <= 700 || (a == 0)){
                                make_mes(1);
                            } else {
                                make_mes(a);
                            }

                        }catch (java.lang.IllegalArgumentException e){
                            e.printStackTrace();
                            make_mes(1);
                        }
                    }
                 break;
                default: make_mes(ClickON);break;
            }
        } else { //Пользователь ввел, что-то чего нету на кнопке
            if(IsStartCommand(text)){


            }else if(isSeller  && Q_position >= 3000 && Q_position < 4000){
                AddData_ToPost(text);
                set_Text_Keyboard(3000);
                if(Q_position == 3200 ||  Q_position == 3300) {
                    Edit_mes(Q_position);
                }else{
                    make_mes(3000);
                }
            }else if(isSeller  && Q_position >= 1105 && Q_position < 1110){
                AddTTimeSettings_ToPost(text);
                seTimeKeyboard(Q_position);
                Edit_mes(Q_position);
            } else if(Q_position == 500 || (Q_position > 500 && Q_position <= 506)){

            } else if(isSeller && (Q_position >= 511 && Q_position <=514)){
                AddSellerReViewData(text);
            } else if(Q_position >= 5000 && Q_position <= 6000){
                set_filtres(text,Q_position);
            } else if(position == 6000){
                Seller sel = checkUserName(text);
                if(sel != null){ //Если юзернейм найдем
                    Finder finder = aUser.getFinder();
                    finder.setSeller(sel);

                    make_mes(6001);
                } else { // Если юзернейм не найден
                    make_mes(6002);
                }
            }
        }
    }
    private Seller checkUserName(String username){
        Seller seller;
        String user_name = username.replaceAll(" ","");
        seller = usersManager.findWithUsername(user_name);
        return seller;
    }
    private void set_filtres(String text, int ClickOn){
        Finder finder = aUser.getFinder();// 1 - Получаем из auser = finder
        Filters filters = finder.getFilter();
        switch (ClickOn){
            case 5002:filters.setIndex(Double.valueOf(text)); {data = getKeyBoardsData.getKeyboard_Fist_filtres(ClickOn,filters.getFirstFilterK_info(),filters.getFirstK_Text());
                Edit_mes(ClickOn);}break;
            case 5003:filters.setPrice(text); {data = getKeyBoardsData.getKeyboard_Fist_filtres(ClickOn,filters.getFirstFilterK_info(),filters.getFirstK_Text());
                Edit_mes(ClickOn);}break;
            case 5004:filters.setLocation(text); {data = getKeyBoardsData.getKeyboard_Fist_filtres(ClickOn,filters.getFirstFilterK_info(),filters.getFirstK_Text());
                Edit_mes(ClickOn);}break;
            case 5005:filters.setGrowType(text); {data = getKeyBoardsData.getKeyboard_Fist_filtres(ClickOn,filters.getFirstFilterK_info(),filters.getFirstK_Text());
                Edit_mes(ClickOn);}break;
            case 5006:filters.setType(text); {data = getKeyBoardsData.getKeyboard_Fist_filtres(ClickOn,filters.getFirstFilterK_info(),filters.getFirstK_Text());
                Edit_mes(ClickOn);}break;
            case 5021:try{filters.setConformiti(Double.valueOf(text));
                data = getKeyBoardsData.getKeyBoard_Second_Filtres(ClickOn,filters.getSecondK_info(),filters.getSecondK_Text());
                Edit_mes(ClickOn);
                }catch (Exception e){
                e.printStackTrace();
            }break;
            case 5022:
                try{
                    filters.setConformiti(Double.valueOf(text));
                    data = getKeyBoardsData.getKeyBoard_Second_Filtres(ClickOn,filters.getSecondK_info(),filters.getSecondK_Text());
                    Edit_mes(ClickOn);
                }catch (Exception e){
                    e.printStackTrace();
                }break;
            case 5023:
                try{filters.setService(Double.valueOf(text));
                    data = getKeyBoardsData.getKeyBoard_Second_Filtres(ClickOn,filters.getSecondK_info(),filters.getSecondK_Text());
                    Edit_mes(ClickOn);
                }catch (Exception e){e.printStackTrace();
                }break;
            case 5024:
                try{filters.setQuality(Double.valueOf(text));
                    data = getKeyBoardsData.getKeyBoard_Second_Filtres(ClickOn,filters.getSecondK_info(),filters.getSecondK_Text());
                    Edit_mes(ClickOn);
            }catch (Exception e){e.printStackTrace();
            }break;
            case 5025:
                try{filters.setQ_P(Double.valueOf(text));
                    data = getKeyBoardsData.getKeyBoard_Second_Filtres(ClickOn,filters.getSecondK_info(),filters.getSecondK_Text());
                    Edit_mes(ClickOn);
                }catch (Exception e){e.printStackTrace();
                }break;
            case 5026:
                try{filters.setNumReviews(Integer.valueOf(text));
                    data = getKeyBoardsData.getKeyBoard_Second_Filtres(ClickOn,filters.getSecondK_info(),filters.getSecondK_Text());
                    Edit_mes(ClickOn);
                }catch (Exception e){e.printStackTrace();
                }break;
        }
    }

    private boolean IsStartCommand(String text){//Проверяем содержит ли сообщени команду"/start", если да запускаем метод который обрабатывает начальную команду
        boolean is = false;
        Pattern patternb = Pattern.compile("/start");
        Matcher matcherb = patternb.matcher(text);
        if (matcherb.find() ) {
           //
            is = true;
        }
        return  is;
    }



    private void  preSend500(int ClickOn){
        aUser.setQPosition_in_dialog(ClickOn);
        make_mes(ClickOn);
     }

    private void AddSellerReViewData(String text){
        ReView reView = null;
        Collection<ReView> allrev = seller.getReViews();
        int id = seller.getPreMessage();
        for(ReView rev :allrev){
            if(rev.getRevId() == id){
                reView = rev;
            }
        }
        if(reView != null){
            int byerId = reView.getByer().getUserId();
            switch (Q_position){
            case 512:{
                if (IsYes(text)) {
                reView.setDealwasSucces(true);
                    usersManager.AddSuccesDealsTOUser(byerId);
                    send214(seller.getUserId(),reView,512);
                }else if(IsNo(text)){
                reView.setDealwasSucces(false);
                    usersManager.AddComplainNumUser(byerId);
                    send214(seller.getUserId(),reView,512);
                }
            }break;
            case 513:{
                if (IsYes(text)) {
                    if(seller.getUsername() != null && seller.getUsername() != " "){
                        usersManager.AddProtectedUsername(byerId,seller.getUsername());
                        send214(seller.getUserId(),reView,513);
                    }
                    reView.setAddProtectPoint(true);
                }else if(IsNo(text)){
                    reView.setAddProtectPoint(false);
                    send214(seller.getUserId(),reView,513);

                }
            }break;
            case 514:{
                if(text != null && text != "" && text != " "){
                   reView.setComlain(text);
                   usersManager.AddComplainToUser(byerId,text);
                    send214(seller.getUserId(),reView,514);
                }
            }break;
                default:send214(seller.getUserId(),reView,511);
            }
            //data = getKeyBoardsData.get_Seller511(reView,Q_position);
           // Edit_mes(Q_position);
        }

    }

    private void send214(int sellerId, ReView reView, int ClickOn){//Сообщение для продавца, которое будет изменятся по-мере того, как он нажимает на кнопки
        KeyBoardData Kdata = getKeyBoardsData.get_Seller511(reView,ClickOn);
        ModelOfMessage mess = new ModelOfMessage(ClickOn,Kdata);
        mess.setChatId(String.valueOf(sellerId));
        BotsManager.DeleteMessage(message.getChatId(),message.getMessageId());
        int revId = (BotsManager.SendMes(mess).getMessageId());
        reView.addRevId(revId);
        aUser.Add_Qmessage(revId);
    }

    private boolean IsYes(String txt){
        boolean res = false;
        Pattern pattern = Pattern.compile("כן");
        Matcher matcher = pattern.matcher(txt);
        if (matcher.find()){
            res = true;
        }
        return  res;
    }

    private boolean IsNo(String txt){
        boolean res = false;
        Pattern pattern = Pattern.compile("לא");
        Matcher matcher = pattern.matcher(txt);
        if (matcher.find()){
            res = true;
        }
        return  res;
    }
/*
    private void LoadCommand(){// Метод который запускается, когда нажали на пост
        if(usersManager.UserConnnecton(aUser) != null){
            Seller sel = usersManager.UserConnnecton(aUser);
            ReView reView = sel.getReView(aUser);
            if((reView.isRightforReView_Was201() || reView.isRightforReView_Was202()) && reView.isAskReView_wasClicked()){
                //Если продавец дал право отзыва и покупатель просил
                aUser.setQPosition_in_dialog(500);
                AddBuyerReViewData();
                usersManager.IncreaseCountOfReqToBot(aUser);
            } else if((!reView.isRightforReView_Was202() || !reView.isRightforReView_Was201()) && reView.isAskReView_wasClicked()){
                //Если продавец не дал право отзыва, но покупатель просил
                usersManager.CloseConnection(aUser);
                Seller seller = usersManager.CreateConnection(aUser, SellerId);
                if (seller != null) {
                    TextForMessage = textTo.getText_AskReview_200(seller.getUsername());

                }
                Send201(seller.getUserId());
                make_mes(200); //Тут должно создаваться два сообщения для пользователя и для продавца
                usersManager.IncreaseCountOfReqToBot(aUser);

            } else if((!reView.isRightforReView_Was202() || !reView.isRightforReView_Was201()) && !reView.isAskReView_wasClicked()){
                //Если покупатель не просил, а продавец не дал - просто удаляем
                usersManager.RemoveReView(aUser);//Удаляем из бд
                usersManager.CloseConnection(aUser);
                Seller seller = usersManager.CreateConnection(aUser, SellerId);
                if (seller != null) {
                    TextForMessage = textTo.getText_AskReview_200(seller.getUsername());

                }
                Send201(seller.getUserId());
                make_mes(200); //Тут должно создаваться два сообщения для пользователя и для продавца
                usersManager.IncreaseCountOfReqToBot(aUser);
            }

        }else {
            Seller seller = usersManager.CreateConnection(aUser, SellerId);
            if (seller != null) {
                TextForMessage = textTo.getText_AskReview_200(seller.getUsername());

            }
            Send201(seller.getUserId());
            make_mes(200); //Тут должно создаваться два сообщения для пользователя и для продавца
            usersManager.IncreaseCountOfReqToBot(aUser);
        }
    }
    */

    private void Send201(int sellerId){

    }

    private void setKeybor_forMenu(int Npos){
        Post post = seller.getPost();
        boolean [] galochki = post.galochkiFor_Menu();
        data = getKeyBoardsData.make_Menu_KeyBoard(galochki,Npos);
    }

    private void set_Text_Keyboard(int Npos){
        Post post = seller.getPost();
        int [] num = post.numTextButtons();
        boolean [] galochki = post.galochkiFor_Text();
        data = getKeyBoardsData.make_Text_KeyBoard(galochki,num,Npos);
     }

    private void AddData_ToPost(String text){
        System.out.println(Q_position  + "    Q  position");
        if(isSeller == true && Q_position != 0){
            switch (Q_position){
                case 3100: seller.addDescription(text); break;
                case 3200: seller.addPrice(text);break;
                case 3300: seller.addLocation(text); break;
                case 3400: seller.addKind(text);break;
                case 3500: seller.addKindB(text);break;
            }
        }
     }

    private void AddTTimeSettings_ToPost(String text){
        Post post = seller.getPost();
        PromoContainer promo = post.getPromo();
        if(isSeller == true && Q_position != 0){
            switch (Q_position){
                case 1105:{if (promo.type_is_Ot_promo()){promo.setOt_promoTime(text);}else if(promo.type_is_Mt_promo()){promo.setMt_promoTime(text);}}break;
                case 1106: promo.setOt_promoDate(text);break;
                case 1109: promo.setMt_promoLoop(text); break;
            }
        }
    }

    private void seTimeKeyboard(int Npos){//Тут нужно запускать метод создания
        Post post = seller.getPost();
        PromoContainer promo;
        promo = post.getPromo();
        data = getKeyBoardsData.makeTimeMenuKeyBoard(promo.Get_TextKeybordMas(Npos),promo.Get_DataKeybordMas());

    }

    private void Save_Photo(File file){
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!aUser.isSeller() && position == 12){
            usersManager.UserBecomesSelller(userId);
            Seller seller = usersManager.GettingSeller(userId);
            seller.setSeller_Photo(image);
            make_mes(13);
        }
        if(aUser.isSeller() && seller.HavePost()){
            seller.setImd(image);
            setKeybor_forMenu(1000);
            make_mes(1000);
        }

    }

    private void Edit_mes(int ClickON){
        if(data == null){

            data = getKeyBoardsData.get_keyboard_Data(ClickON);
        }
        if (TextForMessage != null) {

            modelQ = new ModdelForQuary(ClickON, data, TextForMessage);

        } else {
            modelQ = new ModdelForQuary(ClickON, data); //Настраиваем сообщение для отправки
        }
        modelQ.setChatId(String.valueOf(aUser.getUserId()));//В этой и следующей строчке, мы запрашиваем предыдущее сообщение из  списка, которое мы добавляем в тот момент, когда мы счелкаем по клавиатуре
        modelQ.setMessageId(aUser.getPreMessage());
        aUser.setQPosition_in_dialog(ClickON);
    }

    private void make_mes(int ClickON){
        System.out.println(TextForMessage);
        if(data == null) {
            data = getKeyBoardsData.get_keyboard_Data(ClickON);
        }
        if (TextForMessage != null) {
            model = new ModelOfMessage(ClickON, data, TextForMessage);
        } else {
            model = new ModelOfMessage(ClickON, data); //Настраиваем сообщение для отправки
        }
        aUser.setPosition_in_dialog(ClickON);
    }
    private void Edit_FindSystem(int ClickON, Post post, ArrayList<ReView> reViewArrayList){
        editPost = new EditPost(ClickON,post,data,reViewArrayList);
        editPost.setChatId(String.valueOf(aUser.getUserId()));
        editPost.setMessageId(aUser.getPreMessage());

    }
}
