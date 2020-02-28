package sample.TelegramPart.Promo;

import sample.GuiPart.Errors;
import sample.TelegramPart.BotsManager;
import sample.TelegramPart.KeyBoards.KeyBoardData;
import sample.TelegramPart.ModelsForPosts.PostInGroup;
import sample.UsersData.Post;
import sample.UsersData.Reviews.ReView;
import sample.UsersData.Users.Seller;
import sample.UsersData.UsersManager;

import java.util.ArrayList;
import java.util.TimerTask;

public class Posting_Task extends TimerTask {
    private PostInGroup postInGroup;
    private ArrayList<String> Grops_for_posting;
    private PostManager manager;
    private UsersManager usersManager;
    private PostWithTime post;
    private boolean free = false;
    private boolean mt = false;
    private boolean ot = false;
    private boolean my = false;
    Seller seller;


    public Posting_Task(PostWithTime post, KeyBoardData keyboard, ArrayList<String> Grops_for_posting,PostManager countine, UsersManager manager){
        this.usersManager = manager;
        this.manager = countine;
        this.Grops_for_posting = Grops_for_posting;
        this.post = post;
        if(usersManager.GettingSeller(post.getUserId()) != null){
            this.seller = usersManager.GettingSeller(post.getUserId());
            ArrayList<ReView> list = seller.getReviews();
            if (list != null || !list.isEmpty()) {
                postInGroup = new PostInGroup(post.getPost(), keyboard, list);
            } else {
                postInGroup = new PostInGroup(post.getPost(), keyboard);
            }
        }

    }


    @Override
    public void run() {
        if (postInGroup != null && seller != null) {
        if(!Grops_for_posting.isEmpty()){
            for(String groupUser: Grops_for_posting) {
                postInGroup.setChatId(groupUser);
                System.out.println(" Chat Id "  + groupUser);
                BotsManager.MakePost(postInGroup);
            }
        } else {
            Errors.AddError("Not Groups for Posting 7 ");

        }
        } else {
            System.out.println(" PostInGroup  == Null!!!");
        }
        if(free){
            manager.ResumeFreePosting();
        }else if(my){
            manager.ResumeMyPosing();
        }else if(ot){
            Seller seller = usersManager.GettingSeller(post.getUserId());
            usersManager.degreaseCreditsToSeller(seller,10);
            usersManager.degreasePointsSeller(seller,10);
            manager.ResumeOtPosing();
        }else if(mt){
            Seller seller = usersManager.GettingSeller(post.getUserId());
            usersManager.degreaseCreditsToSeller(seller,5);
            usersManager.degreasePointsSeller(seller,5);
            manager.ResumeMtPosting(post);
        }

    }

    public void setMy(boolean my) {
        this.my = my;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public void setMt(boolean mt) {
        this.mt = mt;
    }

    public void setOt(boolean ot) {
        this.ot = ot;
    }
}
