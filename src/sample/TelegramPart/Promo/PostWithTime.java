package sample.TelegramPart.Promo;

import sample.GuiPart.Errors;
import sample.UsersData.Post;
import sample.UsersData.PromoteSettings.PromoContainer;
import sample.UsersData.Users.Seller;

import java.io.Serializable;
import java.util.Date;

public class PostWithTime implements Serializable, Comparable<PostWithTime> {
    /**
     * Данный класс будет содержать ссылку на пост у пользователя и временные настройки
     *
     *
     *
     */
    ///private  int CurrentId;
    private int userId;
    private Date dateOfAdding;
    private Seller seller;
    private boolean Free = false;
    private boolean Mt = false;
    private boolean Ot = false;
    private PromoContainer promoContainer;
    private Post post;
    private String Ttime;

    public void increaseDate(){
        if(Mt) {
            promoContainer.getMt_promo().increaseDate();
        }
    }
    public void degLoops(){
        if(Mt) {
            promoContainer.getMt_promo().degreaseLoops();
        }
    }
    public int getLoops(){
        int loops = -1;
        if(Mt){
            loops = promoContainer.getMt_promo().getLoops();
        }
        return loops;
    }
    public String getTtime() {
        if(Ot){
            return promoContainer.getOtFullDate().toString();
        } else if(Mt){
            return promoContainer.getMt_promo().getTime().toString() + "|" + promoContainer.getMt_promo().getLoops();
        } else if(Free){
            return dateOfAdding.toString();
        } else {
            Errors.AddError("'Problem With Time of Post in +_\n_one of Tables 9 '");
            return null;
        }
    }

    public Long getSeconds() {
        if(Ot){
            return promoContainer.getOtFullDate().getTime();
        } else if(Mt){
            return promoContainer.getMt_promo().getTimeV().getTime();
        } else if(Free){
            return dateOfAdding.getTime();
        } else {
            Errors.AddError("'Problem With Time of Post in +_\n_one of Tables 9 '");
            return null;
        }
    }
    public String getTime(){
        if(Ot){
           return promoContainer.getOtFullDate().toString();
        } else if(Mt){
           return promoContainer.getMt_promo().getTimeV() + "|" + promoContainer.getMt_promo().getLoops();
        } else if(Free){
            return dateOfAdding.toString();
        } else {
            return null;
        }
    }
   /*public Seller getSeller(){
        return seller;
   }*/

    public PostWithTime(Post post,Seller seller){
       this.userId = post.getUserId();
       this.post = post;
       setType(post);
       this.seller = seller;
       this.dateOfAdding = new Date();
    }

    public PromoContainer getPromoContainer() {
        return promoContainer;
    }

    private void setType(Post post) {

       if(post.getHaveFree()){
           Free = true;
       }
       if(post.getPromo()!=null){
           promoContainer = new PromoContainer(post.getPromo());
           if(promoContainer.type_is_Ot_promo()){
               Ot = true;
           }else if(promoContainer.type_is_Mt_promo()) {
               Mt = true;
               promoContainer.getMt_promo().setDate();
           }
       }
   }

    @Override
    public int compareTo(PostWithTime o){
       if(Mt && o.isMt()){
           Long Odate = o.getSeconds();
           Long Tdate = this.getSeconds();
           return Tdate.compareTo(Odate);

       }else if(Ot && o.isOt()){
           Long Odate = o.getSeconds();
           Long Tdate = this.getSeconds();
           return Tdate.compareTo(Odate);
          /*
           Date Odate = o.getTime();
           Date Tdate = this.getTime();
           return Tdate.compareTo(Odate);*/
       }else if(Free && o.getFree()){
           Long Odate = o.getSeconds();
           Long Tdate = this.getSeconds();
           return Tdate.compareTo(Odate);
           /*
           Date Odate = o.getTime();
           Date Tdate = this.getTime();
           return Tdate.compareTo(Odate);*/
       } else {
           Errors.AddError("Error Comparing Post 10");
           return 0;
       }

    }

    public int getUserId() {
        return userId;
    }

    public Post getPost() {
        return post;
    }

    public void setFree() {
        Mt = false;
        Ot = false;
        Free = true;
    }

    public void setMt() {
        Mt = true;
        Ot = false;
        Free = false;
    }

    public void setOt() {
        Mt = false;
        Ot = true;
        Free = false;
    }

    public boolean getFree(){
        return Free;
    }

    public boolean isMt() {
        return Mt;
    }

    public boolean isOt() {
        return Ot;
    }
}
