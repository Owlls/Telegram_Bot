package sample.TelegramPart.Finder;

import sample.TelegramPart.Promo.PostManager;
import sample.UsersData.Post;
import sample.UsersData.Reviews.ReView;
import sample.UsersData.Users.Seller;
import sample.UsersData.UsersManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Finder{



    private int position = 0;
    private int positionInReViews = 0;
    private boolean havefilter = false;
    //---------------Поля для поиска

    //----------------------
    private ArrayList<Post> Allposts;
    private ArrayList<Post> Filteredposts;
    private Filters filters;
    private UsersManager users_manager;
    //------------------------
    private double NUM_REVIEW = 0;
    private double Index = 0;
    private double Conformiti = 0;
    private double Service = 0;
    private double Quality = 0;
    private double Q_P = 0;
    private double Recomended = 0;
    private Seller seller;


    public ArrayList<ReView> get_AllReVies(){
        return seller.getReviews();
    }

    public boolean isSellerHaveReViews(){
        ArrayList<ReView> reviews = seller.getReviews();
        if(reviews!= null && !reviews.isEmpty()){
            return true;
        }else {
            return false;
        }
    }
    public int NumReViews(){
       return seller.getReviews().size();
    }
    public ReView getReview(){
        return seller.getReviews().get(positionInReViews);
    }

    public void increasePosInRev(){
        positionInReViews++;
    }
    public void decreasePosInRev(){
        if(positionInReViews >= 1) {
            positionInReViews--;
        }else {
            positionInReViews=0;
        }
    }
    public void Ten_increasePosInRev(){
        positionInReViews = positionInReViews + 10;
    }
    public void Ten_decreasePosInRev(){
        if(positionInReViews >= 11) {
            positionInReViews = positionInReViews -10;
        }else {
            positionInReViews=0;
        }
    }
    public Seller getSeller(){
        return seller;
    }


    public void setSeller(Seller seller){
        this.seller = seller;
        Allposts.clear();
        Allposts.addAll(seller.getPosts_list());

    }


    public void set_allNullFiltre(){
        filters = null;
        havefilter = false;
    }
    public Finder (UsersManager manager){
        this.users_manager = manager;
        Allposts = new ArrayList<>();
        filters = new Filters();
    }
    public void setAllPost(){ //Когда пользователь нажимает смотреть все посты
        Allposts.clear();
        Allposts.addAll(users_manager.getAllAgreePosts());
        havefilter = false;
    }

    public void set_LastNum(){
        if(havefilter){
           position = (Filteredposts.size() -1);
        } else {
            position = (Allposts.size() - 1);
        }
    }

    public void set_frstNum(){
        position = 0;
    }

    public int choose_fPosts(){//Метод, который будет запускаться до
        if(havefilter == false) {
            if (Allposts.isEmpty()) {
                return -1;
            } else if (!Allposts.isEmpty() && (Allposts.size() - 1) < position) {
                return 0;
            } else if (!Allposts.isEmpty() && (Allposts.size() - 1) >= position) {
                return 1;
            } else {
                return 3;
            }
        } else {
            if (Filteredposts.isEmpty()) {
                return -1;
            } else if (!Filteredposts.isEmpty() && (Filteredposts.size() - 1) < position) {
                return 0;
            } else if (!Filteredposts.isEmpty() && (Filteredposts.size() - 1) >= position) {
                return 1;
            } else {
                return 3;
            }
        }
    }
    public Post getPost(){
        if(havefilter){
           return Filteredposts.get(position);
        }else {
            return Allposts.get(position);
        }

    }

    public int getPosition(){
        return position;
    }
    public void increatePos(){
        position++;
    }
    public void decreatePos(){
        if(position >= 1) {
            position--;
        }else {
            position=0;
        }

    }
    public void TenIncreate(){
        position = position + 10;
    }
    public void TenDecreate(){
        if(position >= 11) {
            position = position -10;
        }else {
            position=0;
        }
    }

    public Filters getFilter(){
        return filters;
    }




    public void Checker(){
        Allposts = new ArrayList<>(users_manager.getAllAgreePosts());
        if(filters != null && filters.choose_filters() && !filters.checkSecond()){
            ArrayList<Post> result = new ArrayList<>();
            boolean add = false;
                for (Post p : Allposts) {
                    boolean block = true;
                    add = false;
                    Seller seller = users_manager.GettingSeller(p.getUserId());
                    if (!seller.getReviews().isEmpty()) {
                        setReViews(seller.getReviews());
                    } else {
                        set_nullAll();
                    }
                    if (filters.getIndex() != -1){
                        if(filters.getIndex() <= Index){
                            add = true;
                        }else {
                            add = false;
                            block = false;
                        }

                    }
                    if(filters.getPrice() != null && (p.getPrices() != null && !p.getPrices().isEmpty()) && block){
                        Pattern Price_pattern = Pattern.compile(filters.getPrice());
                        for(String str: p.getPrices()){
                            Matcher mather = Price_pattern.matcher(str);
                            if(mather.find()){
                                add = true;
                            }else {
                                add = false;
                            }
                        }
                        if(!add){
                            block = false;
                        }
                    }
                    if (filters.getLocation() != null && (p.getLocations() != null && !p.getLocations().isEmpty()) && block){
                        Pattern Loc_pattern = Pattern.compile(filters.getLocation());
                        for(String str : p.getLocations()) {
                            Matcher matcher = Loc_pattern.matcher(str);
                            if(matcher.find()){
                                add = true;
                            }else {
                                add = false;

                            }
                        }
                        if(!add){
                            block = false;
                        }
                    }
                    if (filters.getGrowType() != null && p.getKind_B() != null && block) {
                        Pattern TypeB_pattern = Pattern.compile(filters.getGrowType());
                        Matcher matcher = TypeB_pattern.matcher(p.getKind_B());
                        if(matcher.find()){
                            add = true;
                        }else {
                            add = false;
                            block = false;
                        }
                    }
                    if (filters.getType() != null && p.getKind() != null && block){
                        Pattern TypeA_pattern = Pattern.compile(filters.getType());
                        Matcher matcher = TypeA_pattern.matcher(p.getKind());
                        if(matcher.find()){
                            add = true;
                        }else {
                            add = false;
                            block = false;
                        }
                    }
                    /////////////////
                    if(add && block){
                        result.add(p);
                    }else {
                        add = false;
                    }
                }
                havefilter = true;
                Filteredposts = result;
                position = 0;

            } else if(filters != null && filters.choose_filters() && filters.checkSecond()){
                ArrayList<Post> result = new ArrayList<>();
                boolean add = false;
                for (Post p : Allposts) {
                    add = false;
                    boolean block = true;
                    Seller seller = users_manager.GettingSeller(p.getUserId());
                    if (!seller.getReviews().isEmpty()){
                        setReViews(seller.getReviews());
                          if (filters.getIndex() != -1) {
                            if(filters.getIndex() <= Index){
                                add = true;

                            }else {
                                add = false;
                                block = false;
                            }
                        }
                        if (filters.getLocation() != null && (p.getLocations() != null && !p.getLocations().isEmpty()) && block) {
                            Pattern Loc_pattern = Pattern.compile(filters.getLocation());
                            for(String str : p.getLocations()) {
                                Matcher matcher = Loc_pattern.matcher(str);
                                if(matcher.find()){
                                    add = true;
                                }else {
                                    add = false;
                                    block = false;
                                }

                            }
                        }
                        if (filters.getGrowType() != null && p.getKind_B() != null && block) {
                            Pattern TypeB_pattern = Pattern.compile(filters.getGrowType());
                            Matcher matcher = TypeB_pattern.matcher(p.getKind_B());
                            if(matcher.find()){
                                add = true;
                            }else {
                                add = false;
                                block = false;
                            }
                        }
                        if (filters.getType() != null && p.getKind() != null && block) {
                            Pattern TypeA_pattern = Pattern.compile(filters.getType());
                            Matcher matcher = TypeA_pattern.matcher(p.getKind());
                            if(matcher.find()){
                                add = true;
                            }else {
                                add = false;
                                block = false;
                            }
                        }
                        if(filters.getConformiti() != -1 && Conformiti != 0 && block){
                            if(Conformiti >= filters.getConformiti()){
                                add = true;
                            } else {
                                add = false;
                                block = false;
                            }
                        }
                        if(filters.getService() != -1 && Conformiti != 0 && block){
                            if(Service >= filters.getService()){
                                add = true;
                            } else {
                                add = false;
                                block = false;
                            }
                        }
                        if(filters.getQuality() != -1 && Quality != 0 && block){
                            if(Quality >= filters.getQuality()){
                                add = true;
                            } else {
                                add = false;
                                block = false;
                            }
                        }
                        if(filters.getQ_P() != -1 && Q_P != 0 && block){
                            if(Q_P >= filters.getQ_P()){
                                add = true;
                            } else {
                                add = false;
                                block = false;
                            }
                        }
                        if(filters.getRecomended() != -1 && Recomended != 0 && block){
                            if(Recomended >= filters.getRecomended()){
                                add = true;
                            } else {
                                add = false;
                                block = false;
                            }
                        }
                        if(filters.getNumReviews() != -1 && NUM_REVIEW != 0 && block){
                            if(NUM_REVIEW >= filters.getNumReviews()){
                                add = true;
                            } else {
                                add = false;
                                block = false;
                            }
                        }
                    /////////////////////
                        if(add){
                            result.add(p);
                        }
                }
                havefilter = true;
                Filteredposts = result;
                position = 0;
            }
        } else {
            havefilter = false;
        }
    }


    private void set_nullAll(){
         NUM_REVIEW = 0;
        Index = 0;
         Conformiti = 0;
        Service = 0;
         Quality = 0;
        Q_P = 0;
         Recomended = 0;
    }


    private void setReViews(ArrayList<ReView> list){
        ArrayList<ReView> Reviews = list;
        set_nullAll();
        int count = 0;
        for(int i = 0; i < Reviews.size(); i++){
            if(Reviews.get(i).isBuyerGiveReV()){
                count++;
            }
        }
        NUM_REVIEW = count;
        for(ReView reView : Reviews){
            Index = Index + (reView.getIndexQ() / count);
            Conformiti = Conformiti +(reView.getConformity()? (10/count):0);
            Service = Service + (reView.getService()/count);
            Quality = Quality + (reView.getQuality()/count);
            Q_P = Q_P + (reView.getQ_p()/count);
            Recomended = Recomended + (reView.getRecomended()? (10/count):0);
        }
       /* filters.setIndex(Index);
        filters.setConformiti(Conformiti);
        filters.setService(Service);
        filters.setQuality(Quality);
        filters.setQ_P(Q_P);
        filters.setRecomended(Recomended);*/

    }
    public int getNumPosts(){
        if(havefilter){
            return Filteredposts.size();
        }else {
            return Allposts.size();
        }
    }
    //-------------------------------------  Методы удаления и наполнения полей поиска


}
