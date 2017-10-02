package pilot.com.siangapp;

/**
 * Created by divyansh on 4/6/17.
 */

public class FeedbackData {


    public String id;
    public String text;
    public String choice1;
    public String choice2;
    public String from;
    public String userId;

    public FeedbackData(String id,String from , String text, String choice1, String choice2, String userId){

        this.id = id;
        this.text = text;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.from = from;
        this.userId = userId;

    }
}
