package sample.TelegramPart.Incoming_Quary;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import sample.TelegramPart.BotsManager;
import sample.TelegramPart.Finder.Filters;
import sample.TelegramPart.Finder.Finder;
import sample.TelegramPart.Incoming_Messages.ModelOfMessage;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.TelegramPart.ModelsForPosts.EditPost;
import sample.TelegramPart.ModelsForPosts.PostInGroup;
import sample.TelegramPart.ModelsForPosts.PostPreview;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


public class QuaryCheker {

    private TextFromMesseges textOfMesseges = new TextFromMesseges();
    private TextForMessages textTo = new TextForMessages();
    private int userId;
    private String TextForMessage;
    private Message message; //То сообщение, от которого это пришло
    private AUser aUser;
    private Seller seller;
    private String call_data;
    private ModelOfMessage modelOfMessage;
    private ModdelForQuary moddelForQuary;
    private PostPreview preview;
    private PostInGroup modelPost;
    private EditPost editPost;
    private UsersManager usersManager;
    private PostManager postManager;
    private TextForKeybors keybors = new TextForKeybors();
    private KeyBoardData data;
    private boolean ChangePos = true;

    public PostInGroup getModelPost() {
        return modelPost;
    }
    public EditPost getEditPost() {
        return editPost;
    }
    public ModdelForQuary getModdelForQuary() {
        return moddelForQuary;
    }
    public PostPreview getPreview() {
        return preview;
    }
    public ModelOfMessage getModelOfMessage() {
        return modelOfMessage;
    }


    public QuaryCheker(User user, CallbackQuery message, String str,PostManager postManager,UsersManager usersManager) {
        this.postManager = postManager;
        this.usersManager = usersManager;
        if (!usersManager.IsNewUser(user)) {
            this.userId = user.getId();
            aUser = usersManager.GettingUser(user);
            if(aUser.isSeller()){
                seller = usersManager.GettingSeller(userId);
            }
            this.message = message.getMessage();
            this.call_data = str;
            if(call_data.matches("[-+]?\\d+")){
                CheckInt();
            } else {
                CheckString();
            }
            System.out.println("  Working  " + call_data);
        }
    }

    private void CheckInt() {
        long calldata = Long.valueOf(call_data);
        if(calldata < 10000){
        int str = Integer.parseInt(call_data);
        if(str < 1000 ){
            int num_of_Post;
            int num_of_Button;
            num_of_Button = str % 10;
            num_of_Post = (str / 10) % 10;
            if (num_of_Button == 0) {//Показываем Пост
                seller.setPost(seller.getPosts_list().get(num_of_Post));
                data = keybors.makeInfoAboutPost(seller.getPost().galochki_InfoAboutPost(postManager), seller.getPost().data_InfoAboutPost(postManager));
                TextForMessage = seller.getPost().getText_InfoAboutPost();
                make_mes(1125);
                //LookAtPost(101,num_of_Post);
            } else if (num_of_Button == 1) { //Удаляем пост
                seller.Remove_Post((num_of_Post),postManager);
                MyPostsWin();
            } else if (num_of_Button == 2) { //Реклама
                seller.setPost(seller.getPosts_list().get(num_of_Post));
                make_mes(1102);
            }
        }else if(str > 1100 && str < 2000){
            switch (str){
                case 1111:{seller.getPost().setFreeNull(); if(postManager.Including_WA_List(seller.getPost())){ postManager.delPostFromWaitingList(seller.getPost());}}break;
                case 1112:{seller.getPost().setMtNull(); if(postManager.Including_WA_List(seller.getPost())){ postManager.delPostFromWaitingList(seller.getPost());}}break;
                case 1113:{seller.getPost().setOtNull(); if(postManager.Including_WA_List(seller.getPost())){ postManager.delPostFromWaitingList(seller.getPost());}}break;
                case 1114:{postManager.Delete_FreePosts(seller.getPost());}break;



            }
            System.out.println(seller.getQPosition_in_dialog());
            TextForMessage = seller.getPost().getText_InfoAboutPost();
            data = keybors.makeInfoAboutPost(seller.getPost().galochki_InfoAboutPost(postManager), seller.getPost().data_InfoAboutPost(postManager));
            Edit_mes(seller.getQPosition_in_dialog());


        }
        } else if(calldata > 10000){
            Post post = seller.getPost();
            postManager.DeletePostWithCurrentTime(post,calldata);
            TextForMessage = seller.getPost().getText_InfoAboutPost();
            data = keybors.makeInfoAboutPost(seller.getPost().galochki_InfoAboutPost(postManager), seller.getPost().data_InfoAboutPost(postManager));
            Edit_mes(seller.getQPosition_in_dialog());
        }
    }

    private void CheckString(){
        if (textOfMesseges.pos_in_query(call_data.trim()) != -1){
            int ClickON = textOfMesseges.pos_in_query(call_data);
            if(ClickON == 99){ //Если пользлватель нажал кнопку "Назад"
                ClickON = seller.getQPreposition_in_dialog();
                while (ClickON  == 101  || ClickON == 1104|| ClickON == 1105 || ClickON ==  1106 || ClickON == 1108 || ClickON == 1009 || ClickON == 3100 || ClickON ==  3200 || ClickON == 3300 || ClickON == 3400 || ClickON ==  3003 || ClickON == 3002 || ClickON == 3500 || ClickON == 3600 || ClickON == 3000 || ClickON == 3700){
                    ClickON = seller.getQPreposition_in_dialog();
                }

                if(ClickON == 1125){
                    data = keybors.makeInfoAboutPost(seller.getPost().galochki_InfoAboutPost(postManager), seller.getPost().data_InfoAboutPost(postManager));
                    TextForMessage = seller.getPost().getText_InfoAboutPost();
                    make_mes(1125);
                }
                //ChangePos = false;
                if(ClickON < 100){ //Если
                    make_mes(ClickON);

                }
                if(ClickON == 1102){
                    make_mes(1102);
                }
            }//Если пользлватель нажал кнопку "Назад"
            if(ClickON <= 2000){
                if(ClickON >= 1104 && ClickON < 1110){ //Если пользователь устанавливает платную рекламу
                    seTimeKeyboard(ClickON);
                }
                switch (ClickON){
                    case 101:{LookAtPost(ClickON);}break; // Кнопка смотреть пост
                    case 250:{make_mes(ClickON);}break;
                    case 210:{AskForReView();}break;//Покупатель просит право отзыва
                    case 211:{AskForReViewSec();}break;
                    case 451:{Send451(aUser,usersManager.UserConnnecton(aUser).getUserId());}break;//Покупатель отменяет сделку
                    case 500:{}break;
                    case 511:{preSens214(ClickON);}break;
                    case 512:{preSens214(ClickON);}break;
                    case 513:{preSens214(ClickON);}break;
                    case 514:{preSens214(ClickON);}break;
                    case 515:{seller.setQPosition_in_dialog(1);seller.setPosition_in_dialog(1);make_mes(1); }break;
                    case 1000:{if (seller.IsIncludePost(seller.getPost()) || seller.getPost() == null){seller.make_Post();}setKeybor_forMenu(ClickON); make_mes(ClickON);}break; // Кнопка - готовить посты
                    case 1100:{MyPostsWin();}break;
                    case 1103:{setPromoAgreementMes(ClickON);}break; //бесплатная реклама
                    case 1126:{LookAtPost(101);}break; //бесплатная реклама
                    case 1110:{setPromoAgreementMes(ClickON);}break;
                    case 2000:{setKeybor_forMenu(ClickON);Edit_mes(ClickON);} break;
                }
            }else if(ClickON >= 3000 && ClickON < 4000){ // Этот блок срабатывает, когда пользователь вводит текст
                switch (ClickON){
                    case 3000: {//setKeyboard_forTextButtons(ClickON); //Пользователь нажал на кнопку текст первый раз
                        make_mes(ClickON);}break;
                    case 3002:{seller.increase_numPriceButtons(); //Пользователь нажал добавит строку для цены
                        setKeyboard_forTextButtons(3000);Edit_mes(3000);} break;
                    case 3003:{seller.increase_numLocButtons();//Пользователь нажал добавит строку для мнстоположения
                        setKeyboard_forTextButtons(3000);
                        Edit_mes(3000);} break;
                    case 3600:{seller.removeAllText_fromPost(); setKeybor_forMenu(1000); make_mes(1000);} break;
                    case 3700:{if(seller.isTextInPost()){setKeybor_forMenu(1000);} make_mes(1000);} break;
                    case 3900:{seller.remove_Current_Post(); Edit_mes(4);}break;
                    case 3999:{seller.Agree_Post(); make_mes(4);}break;
                    default:{setKeyboard_forTextButtons(ClickON);
                        Edit_mes(ClickON);}break;
                }
            } else if(ClickON >= 5000 && ClickON <= 6000){
                switch (ClickON){
                    case 5001: set_FindSystem(ClickON); break; //нажимаем настройки/фильтры
                    case 5002: reset_FindSystem(ClickON); break; //нажимаем "минимальный рейтинг продавца"
                    case 5003: reset_FindSystem(ClickON); break; //нажимаем "Цена"
                    case 5004: reset_FindSystem(ClickON);break; //нажимаем "место"
                    case 5005: reset_FindSystem(ClickON);break; //нажимаем "тип роста"
                    case 5006: reset_FindSystem(ClickON);break; //нажимаем "вид"
                    case 5007: set_FindSystem(5021); break; //нажимаем "доп настройки"
                    case 5008: resume_withFiltres(ClickON); break; //нажимаем "сохранить и продолжить"
                    case 5009: make_mes(5000);break; //нажимаем "назад" ***** скорее всего это не нужно
                    case 5022: reset_SecondSystem(ClickON);break; //нажимаем доп-настройки "Доверие"
                    case 5023: reset_SecondSystem(ClickON);break; //нажимаем доп-настройки "Обслуживание"
                    case 5024: reset_SecondSystem(ClickON);break; //нажимаем доп-настройки "Качество"
                    case 5025: reset_SecondSystem(ClickON);break; //нажимаем доп-настройки "Качество относительно Цены"
                    case 5026: reset_SecondSystem(ClickON);break; //нажимаем доп-настройки "Рекомендован ли"
                    case 5027: reset_SecondSystem(ClickON);break; //нажимаем доп-настройки "Количество отзывов"
                    case 5032: click_Secondfilter(ClickON);break; //нажимаем убрать доп-настройки "Доверие"
                    case 5033: click_Secondfilter(ClickON);break; //нажимаем убрать доп-настройки "Обслуживание"
                    case 5034: click_Secondfilter(ClickON);break; //нажимаем убрать доп-настройки "Качество"
                    case 5035: click_Secondfilter(ClickON);break; //нажимаем убрать доп-настройки "Качество относительно Цены"
                    case 5036: click_Secondfilter(ClickON);break; //нажимаем убрать доп-настройки "Рекомендован ли"
                    case 5037: click_Secondfilter(ClickON);break; //нажимаем убрать доп-настройки "Количество отзывов"
                    case 5028: set_null_secondFilters();break; //нажимаем Отменить доп-настройки = сбрасываем все доп настройки
                    case 5029: set_FindSystem(5001);;break; //нажимаем Сохранить доп-настройки = сохраняем и возращаемся к главному меню
                    case 5012:click_filter(ClickON); break; //нажимаем убрать фильтр "минимальный рейтинг продавца"
                    case 5013:click_filter(ClickON); break; //нажимаем убрать фильтр "цена"
                    case 5014:click_filter(ClickON); break; //нажимаем убрать фильтр "место"
                    case 5015:click_filter(ClickON); break; //нажимаем убрать фильтр "тип роста"
                    case 5016:click_filter(ClickON); break; //нажимаем убрать фильтр "вид"
                    case 5017:click_filter(ClickON); break; //нажимаем убрать фильтр "доп настройки" * - сбрасываем/обнуляем все доп настройки
                    case 5060:set_PostView(ClickON);break; //открываем посты
                    case 5061:reset_PostView(ClickON); break; //нажимаем кнопку "к первому посту"
                    case 5062:reset_PostView(ClickON); break; //нажимаем кнопку "назад на 10"
                    case 5063:reset_PostView(ClickON); break; //нажимаем кнопку "назад на 1"
                    case 5064:reset_PostView(ClickON); break; //нажимаем кнопку "вперед на 1"
                    case 5065:reset_PostView(ClickON); break; //нажимаем кнопку "вперед на 10"
                    case 5066:reset_PostView(ClickON); break; //нажимаем кнопку "к последнему"
                    case 5501:set_PostView(ClickON); break; //нажимаем кнопку "к последнему"
                }
            } else if(ClickON >= 6000 && ClickON <= 7000){
                   ReView_Preview(ClickON);
            }
        } else { //Seller ответил на обращение
            int byerId = textOfMesseges.userIdFromSellerMessage(call_data);
            int comand = textOfMesseges.ComandFromSellerMessage(call_data);
            if(comand != -1){
                ResponseFromSeller(byerId,comand);
            }
        }
    }

    private void ReView_Preview(int ClickON){
        Finder finder = aUser.getFinder();
        switch (ClickON){
            case 6003:{}break;
            case 6004:{}break;
            case 6062:{}break;
            case 6063:{}break;
            case 6064:{}break;
            case 6065:{}break;
            case 6066:{}break;

        }
    }
    //***************************************************Право отзыва********************************************************************************/
    private void ResponseFromSeller(int byerId,int comand){
        AUser byer = usersManager.GettingUser(byerId);
        if(usersManager.UserConnnecton(byer) != null){
            ReView reView = seller.getReView(byer);
            switch (comand){
                case 0: {reView.SetRightforReView(true); Send213(byer.getUserId(),reView);Send214(seller.getUserId(),reView);}break;
                case 1:Send450(byer); seller.setQPosition_in_dialog(1);seller.setPosition_in_dialog(1);break;
                case 2: {reView.SetRightforReView(); Send213(byer.getUserId(),reView);Send214(seller.getUserId(),reView);}break;
                case 3:Send450(byer);seller.setQPosition_in_dialog(1);seller.setPosition_in_dialog(1);break;
            }
        }else{
            //Покупатель нажал отмена - 216 > Seller_у
        }
    }

    private void Send450(AUser byer){//Сообщение для покупателя о том, что продавец отменил сделку
        usersManager.RemoveReView(byer);
        usersManager.CloseConnection(byer);
        KeyBoardData Bdata = keybors.get_keyboard_Data(450);
        ModelOfMessage mesForByer = new ModelOfMessage(450,Bdata);
        mesForByer.setChatId(String.valueOf(byer.getUserId()));
        BotsManager.SendMes(mesForByer);

        make_mes(1);

    }

    private void Send451(AUser byer,int sellerId){//Сообщение для продавца о том, что продавец отменил сделку
        usersManager.RemoveReView(aUser);
        usersManager.CloseConnection(aUser);
        KeyBoardData Bdata = keybors.get_keyboard_Data(451);
        ModelOfMessage mesForSeller = new ModelOfMessage(451,Bdata);
        mesForSeller.setChatId(String.valueOf(sellerId));
        BotsManager.SendMes(mesForSeller);

        byer.setQPosition_in_dialog_(1);
        byer.setPosition_in_dialog(1);
        make_mes(1);
    }

    private void AskForReView(){//Пользователь запросил право отзыва
        Seller seller;
        if(usersManager.UserConnnecton(aUser) != null){ //
            seller = usersManager.UserConnnecton(aUser);
            ReView reView = seller.getReView(aUser);
            reView.AskForReView();

            if(aUser.getIdMesToSeller() != 0){
                Edit202(seller.getUserId(),aUser.getIdMesToSeller()); //Продавец получает сообщение 202
            } else {
                Send202(seller.getUserId()); //Продавец получает сообщение 202
            }
             Edit_mes(210);//Покупатель получает сообщение 210
        }

    }

    private void AskForReViewSec(){//Пользователь запросил право отзыва
        Seller seller;
        if(usersManager.UserConnnecton(aUser) != null){ //
            seller = usersManager.UserConnnecton(aUser);
            ReView reView = seller.getReView(aUser);
            reView.AskForReViewSec();
            //Продавец получает сообщение 203
            //Покупатель получает сообщение 211

        }

    }
   //-------------------------------------------------Сообщения отзывов для продавцев
    private void preSens214(int ClickOn){
        seller.setQPosition_in_dialog(ClickOn);
        ReView reView = null;
        Collection<ReView> allrev= seller.getReViews();
        for(ReView rev :allrev){
            if(rev.getRevId() == message.getMessageId()){
                reView = rev;
            }
        }
        if(reView != null) {
            send214(seller.getUserId(), reView, ClickOn);
        }
    }

    private void send214(int sellerId, ReView reView, int ClickOn){//Сообщение для продавца, которое будет изменятся по-мере того, как он нажимает на кнопки
        KeyBoardData Kdata = keybors.get_Seller511(reView,ClickOn);
        ModelOfMessage mess = new ModelOfMessage(ClickOn,Kdata);
        mess.setChatId(String.valueOf(sellerId));
        BotsManager.DeleteMessage(message.getChatId(),message.getMessageId());
        int revId = (BotsManager.SendMes(mess).getMessageId());
        reView.addRevId(revId);
        aUser.Add_Qmessage(revId);
    }

    private void Send214(int sellerId,ReView reView){//Сообщение для продавца, через которое он может оставить отзыв
        KeyBoardData Kdata = keybors.get_Seller511(reView,511);
        ModelOfMessage mess = new ModelOfMessage(214,Kdata);
        mess.setChatId(String.valueOf(sellerId));
        int revId = (BotsManager.SendMes(mess).getMessageId());
        reView.addRevId(revId);
        aUser.Add_Qmessage(revId);
    }

    private void Edit202(int sellerId, int mesid){//Продавцу приходит сообщение 202

    }

    private void Send202(int sellerId){//Продавцу приходит сообщение 202

    }

    /////////////////////----------------------------Сообщения отзывов для покупателя
    private void Send213(int userId, ReView reView){//Сообщение для покупателя, через которое он может оставить отзыв

    }

    /*---------------------------------------------------КЛАВИАТУРЫ-------------------------------------------------------------------------------------*/

    private void setPromoAgreementMes(int Npos){
        Post post = seller.getPost();
        if(post.getPromo() != null && Npos == 1110) {
            PromoContainer promo = post.getPromo();
            if (promo.type_is_Ot_promo()) {
                if ((promo.getOtFullDate().after(new Date(new Date().getTime() + 86400000)))) {
                    if (seller.getPoints() > (10 + seller.getCredit())) {//Тут также надо проверить, если у пользователя уже есть подтвержденные рекламы и вычесть их тоже
                        if (post.getAgreement()) { //Пост подтвержден
                            /*Тут я буду добавлять пост в PromoManager*/

                            postManager.Add_Ot_Post(post,seller);// ------------ Добавляем в список-публигаций Одноразовых
                            usersManager.increaseCreditsToSeller(seller,10);
                            make_mes(1151);
                            //1112
                        } else {//Пост НЕ подтвержден
                            if (!postManager.Including_WA_CurrentTimeSeting(post,promo)) {//Если пост еще не подан на рассмотрение
                                //post.ReWritePromoSettings(); //Вносим его в бд, до тех пор, пока он не получит подтверждение
                                postManager.AddToQute(post,seller); // ********** Добавляем одноразовую рекламу в список рассмотрения
                                post.AddTimeSettings();
                                make_mes(1113);
                            } else { //Если пост не подтвержденный и пользователь уже вносил его в рекламу
                                post.AddTimeSettings();
                                make_mes(1114);
                            }
                        }
                    } else {
                        //Говорим, что нет никудот
                        make_mes(1115);
                    }
                } else {
                    seTimeKeyboard(1116);   //Говорим, что надо измкеить время
                }
            } else if (promo.type_is_Mt_promo()) {
                if (promo.IsMtHaveLoops() && promo.IsMtHaveTime()) {
                    int k = (5 * promo.getMt_promoLoops());
                    int p = k + seller.getCredit();

                    if (seller.getPoints() > p) {
                        if (post.getAgreement()) { //Пост подтвержден

                            postManager.addMtPost(post,seller); // ------------ Добавляем в список-публигаций Многоразовых
                            usersManager.increaseCreditsToSeller(seller,k);
                            make_mes(1151);
                        } else {
                            if (!postManager.Including_WA_CurrentTimeSeting(post,promo)) {//Если пост еще не подан на рассмотрение
                                postManager.AddToQute(post,seller); // ********** Добавляем многоразовую рекламу в список рассмотрения
                                //post.ReWritePromoSettings(); //Вносим его в бд, до тех пор, пока он не получит подтверждение
                                post.AddTimeSettings();
                                make_mes(1113);

                            } else { //Если пост не подтвержденный и пользователь уже вносил его в рекламу
                                post.AddTimeSettings();
                                make_mes(1114);
                            }
                        }
                    } else {
                        //Говорим, что нет никудот
                        make_mes(1115);
                    }
                } else {
                    seTimeKeyboard(1116);
                }

            }
        } else { //Бесплатная Реклама
            if(Npos == 1103){
                if(post.getAgreement()) {
                    if (postManager.IsnclFree(post)) {

                    } else {

                        postManager.Add_Free_Post(post, seller);// ------------ Добавляем в список-публигаций Бесплатных
                    }
                    make_mes(1151);
                } else {
                    if (postManager.Including_WA_Free(post)) {
                        make_mes(1117);
                    } else {
                        post.AddHaveFree(true);
                        postManager.AddToQute(post,seller,true);//********** Добавляем в список бесплатной рекламы
                        make_mes(Npos);
                    }
                }
            }
        }
    }

    private void MyPostsWin(){
        if(seller.getPosts_list().size() > 0) {

            make_mes(1100);
        } else {

            make_mes(1100);
        }
    }

    private void seTimeKeyboard(int Npos){//Тут нужно запускать метод создания
        Post post = seller.getPost();
        PromoContainer promo;
        if(post.getPromo() == null){
            promo = post.createPromo();
        } else {
            promo = post.getPromo();
        }
        switch (Npos){
            case 1104:{promo.Set_Ot_promo();}break;
            case 1105:{/*  Вноим время */}break;
            case 1106:{/*  Вноим дату  */}break;
            case 1107:{promo.Set_Ot_promo();/* Выставляем клавву времени  */}break;
            case 1108:{promo.Set_Mt_promo();/* Переключаем класс, который хранит данные  */}break;
            case 1109:{/*  Количество повторенний  */}break;
            case 1116:{promo = post.createPromo();}break;
        }
        data = keybors.makeTimeMenuKeyBoard(promo.Get_TextKeybordMas(Npos),promo.Get_DataKeybordMas());
        Edit_mes(Npos);
    }

    private void setKeybor_forMenu(int Npos){
        Post post = seller.getPost();
        boolean [] galochki = post.galochkiFor_Menu();
        data = keybors.make_Menu_KeyBoard(galochki,Npos);
    }

    private void setKeyboard_forTextButtons(int Npos){
        Post post = seller.getPost();
        int [] num = post.numTextButtons();
        boolean [] galochki = post.galochkiFor_Text();
        data = keybors.make_Text_KeyBoard(galochki,num,Npos);
    }


    //*--------------------------------------------------------Методы поиска Фильтры
    //
    //
    //                                                                                                                                    */

    private void set_PostView(int ClickOn){ //Открываем посты
        Finder finder = aUser.getFinder();// 1 - Получаем из auser = finder
        switch (ClickOn){
            case 5060:{ //Пользователь нажал смотреть все посты
                finder.setAllPost(); // Тут мы запускаем в классе поиска метод, который устанавливает все посты
                Post post = finder.getPost(); // 2 - Из finder получаем пост
                int sellerId = post.getUserId();
                Seller seller1 = usersManager.GettingSeller(sellerId); // 3 - Из поста > Продавца и его отзывы
                ArrayList<ReView> rev = seller1.getReviews(); //
                data = keybors.getKeyboard_preView(finder.getPosition(),finder.getNumPosts(),sellerId);
                Open_FindSystem(ClickOn,post,rev);}break;
            case 5501:{
                finder.set_allNullFiltre();// Убираем фильтры
                finder.setAllPost();
                Post post = finder.getPost(); // 2 - Из finder получаем пост
                int sellerId = post.getUserId();
                Seller seller1 = usersManager.GettingSeller(sellerId); // 3 - Из поста > Продавца и его отзывы
                ArrayList<ReView> rev = seller1.getReviews(); //
                data = keybors.getKeyboard_preView(finder.getPosition(),finder.getNumPosts(),sellerId);
                Open_FindSystem(5001,post,rev);}break;
        }

    }

    public void reset_PostView(int ClickOn){
        Finder finder = aUser.getFinder();// 1 - Получаем из auser = finder
        Filters filters = finder.getFilter();
        switch (ClickOn){
            case 5061: finder.set_frstNum();break; //нажимаем кнопку "к первому посту"
            case 5062: finder.TenDecreate();break; //нажимаем кнопку "назад на 10"
            case 5063: finder.decreatePos();break; //нажимаем кнопку "назад на 1"
            case 5064: finder.increatePos(); break; //нажимаем кнопку "вперед на 1"
            case 5065: finder.TenIncreate();break; //нажимаем кнопку "вперед на 10"
            case 5066: finder.set_LastNum();break; //нажимаем кнопку "к последнему"
        }
        int i = finder.choose_fPosts();
        switch (i){
            case -1:{
                make_mes(5500);
            } break;
            case 1:{
                Post post = finder.getPost(); // 2 - Из finder получаем пост
                int sellerId = post.getUserId();
                Seller seller1 = usersManager.GettingSeller(sellerId); // 3 - Из поста > Продавца и его отзывы
                ArrayList<ReView> rev = seller1.getReviews(); //
                data = keybors.getKeyboard_preView(finder.getPosition(),finder.getNumPosts(),sellerId);
                Edit_FindSystem(ClickOn,post,rev);
            } break;
        }
    }

    public void set_FindSystem(int ClickOn){
        // 4 - Создаем клавиатуру
        if(ClickOn == 5001) {
            Finder finder = aUser.getFinder();
            Filters filters = finder.getFilter();
            data = keybors.getKeyboard_Fist_filtres(ClickOn, filters.getFirstFilterK_info(), filters.getFirstK_Text());
        } else if(ClickOn == 5006){
            Finder finder = aUser.getFinder();
            Filters filters = finder.getFilter();
            data = keybors.getKeyBoard_Second_Filtres(ClickOn,filters.getSecondK_info(),filters.getSecondK_Text());

        }
        make_mes(ClickOn);
    }

    public void reset_FindSystem(int ClickOn){ //Метод где я просто меняю клавиату, запускается в момент, когда пользователь кликает на одну из настроек
        Finder finder = aUser.getFinder();
        Filters filters = finder.getFilter();
        data = keybors.getKeyboard_Fist_filtres(ClickOn,filters.getFirstFilterK_info(),filters.getFirstK_Text());
        Edit_mes(ClickOn);
    }

    public void reset_SecondSystem(int ClickOn){
        Finder finder = aUser.getFinder();
        Filters filters = finder.getFilter();
        data = keybors.getKeyBoard_Second_Filtres(ClickOn,filters.getSecondK_info(),filters.getSecondK_Text());
        Edit_mes(ClickOn);
    }

    public void click_Secondfilter(int ClickOn){//Метод там где сбрасывается один из фильтров на второй клавиатуре
        Finder finder = aUser.getFinder();
        Filters filters = finder.getFilter();
        switch (ClickOn){
            case 5032:filters.set_nullConformiti();break; //нажимаем убрать доп-настройки "Доверие"
            case 5033:filters.set_nullService();break; //нажимаем убрать доп-настройки "Обслуживание"
            case 5034:filters.set_nullQuality();break; //нажимаем убрать доп-настройки "Качество"
            case 5035:filters.set_nullQ_P();break; //нажимаем убрать доп-настройки "Качество относительно Цены"
            case 5036:filters.set_nullRecomended();break; //нажимаем убрать доп-настройки "Рекомендован ли"
            case 5037:filters.set_nullNumReviews();break; //нажимаем убрать доп-настройки "Количество отзывов"
        }
        data = keybors.getKeyBoard_Second_Filtres(5006,filters.getSecondK_info(),filters.getSecondK_Text());
        Edit_mes(ClickOn - 10);
    }

    public void set_null_secondFilters(){
        Finder finder = aUser.getFinder();
        Filters filters = finder.getFilter();
        filters.set_allSnull();
        data = keybors.getKeyboard_Fist_filtres(5001, filters.getFirstFilterK_info(), filters.getFirstK_Text());
        make_mes(5001);
    }
    public void click_filter(int ClickOn){ //Метод там где сбрасывается один из фильтров на первой клавиатуре
        Finder finder = aUser.getFinder();
        Filters filters = finder.getFilter();
        switch (ClickOn){
            case 5012:filters.set_nullIndex(); break; //нажимаем убрать фильтр "минимальный рейтинг продавца"
            case 5013:filters.set_nullPrice(); break; //нажимаем убрать фильтр "место"
            case 5014:filters.set_nullLocation(); break; //нажимаем убрать фильтр "место"
            case 5015:filters.set_nullGrowType(); break; //нажимаем убрать фильтр "тип роста"
            case 5016:filters.set_nullType(); break; //нажимаем убрать фильтр "вид"
            case 5017:filters.set_allSnull(); break; //нажимаем убрать фильтр "доп настройки" * - сбрасываем/обнуляем все доп настройки
        }
        data = keybors.getKeyboard_Fist_filtres(5001,filters.getFirstFilterK_info(),filters.getFirstK_Text());
        Edit_mes((ClickOn -10));
    }
    private void start_finding(){
        Finder finder = aUser.getFinder();
        Filters filters = finder.getFilter();

    }
    private void resume_withFiltres(int ClickOn){
        Finder finder = aUser.getFinder();

        Filters filters = finder.getFilter();
        finder.Checker();
        int i = finder.choose_fPosts();
        switch (i){
            case -1:{
                make_mes(5500);
            } break;
            case 1:{
                Post post = finder.getPost(); // 2 - Из finder получаем пост
                int sellerId = post.getUserId();
                Seller seller1 = usersManager.GettingSeller(sellerId); // 3 - Из поста > Продавца и его отзывы
                ArrayList<ReView> rev = seller1.getReviews(); //
                data = keybors.getKeyboard_preView(finder.getPosition(),finder.getNumPosts(),sellerId);
                Open_FindSystem(ClickOn,post,rev);
            } break;
        }

    }
    /*----------------------------------------------------------МЕТОДЫ СООБЩЕНИЙ------------------------------------------------------------------------------*/

    private void Edit_mes(int ClickON){
        if(data == null){
            data = keybors.get_keyboard_Data(ClickON);
        }
        if (TextForMessage != null) {
            moddelForQuary = new ModdelForQuary(ClickON, data, TextForMessage);
        } else {
            moddelForQuary = new ModdelForQuary(ClickON, data);
        }

        moddelForQuary.setChatId(message.getChatId());
        moddelForQuary.setMessageId(message.getMessageId());
        aUser.Add_Qmessage(message.getMessageId());//Тут я сохраняю Id сообщений, которые нужно будет потом менять
        aUser.setQPosition_in_dialog(ClickON);
        //System.out.println("Edit Mess QuaryCheker" + ClickON);

    }
    private void Edit_mes(int ClickON,boolean i){
        if(data == null){
            data = keybors.get_keyboard_Data(ClickON);
        }
        if (TextForMessage != null) {
            moddelForQuary = new ModdelForQuary(ClickON, data, TextForMessage);
        } else {
            moddelForQuary = new ModdelForQuary(ClickON, data);
        }
        moddelForQuary.enableMarkdown(i);
        moddelForQuary.setChatId(message.getChatId());
        moddelForQuary.setMessageId(message.getMessageId());
        aUser.Add_Qmessage(message.getMessageId());//Тут я сохраняю Id сообщений, которые нужно будет потом менять
        aUser.setQPosition_in_dialog(ClickON);
        System.out.println("Edit Mess QuaryCheker" + ClickON);

    }

    private void make_mes(int ClickON) {
        if (data == null) {
            data = keybors.get_keyboard_Data(ClickON);
        }
        if (TextForMessage != null) {
            modelOfMessage = new ModelOfMessage(ClickON, data, TextForMessage);
        } else {
            modelOfMessage = new ModelOfMessage(ClickON, data); //Настраиваем сообщение для отправки
        }
        //aUser.Add_Qmessage();
        modelOfMessage.setChatId(message.getChatId());
        System.out.println("Make Mess QuaryCheker" + ClickON);
        aUser.setQPosition_in_dialog(ClickON);


    }

    private void LookAtPost(int ClickON){
        if(data == null){
            data = keybors.get_keyboard_Data(ClickON);
        }
        preview = new PostPreview(ClickON,seller.getPost(),data); //Настраиваем сообщение для отправки
        preview.setChatId(message.getChatId());
        aUser.setQPosition_in_dialog(ClickON);
    }

    private void Open_FindSystem(int ClickON, Post post, ArrayList<ReView> reViewArrayList){
        modelPost = new PostInGroup(ClickON,post,data,reViewArrayList); //Настраиваем сообщение для отправки
        modelPost.setChatId(message.getChatId());
        aUser.setQPosition_in_dialog(ClickON);
    }
    private void Edit_FindSystem(int ClickON, Post post, ArrayList<ReView> reViewArrayList){
        editPost = new EditPost(ClickON,post,data,reViewArrayList);
        editPost.setChatId(message.getChatId());
        editPost.setMessageId(message.getMessageId());

    }
}