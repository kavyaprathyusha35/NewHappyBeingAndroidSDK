package com.nsmiles.happybeingsdklib.mycoachfragment;

import android.util.Log;

import com.nsmiles.happybeingsdklib.R;
import com.nsmiles.happybeingsdklib.Utils.AppConstants;

/**
 * Created by nSmiles on 11/29/2017.
 */

public class DailyGratitude {

    String gratitude_day;
    String gratitude_type;
    int[] imageId, day_no;
    String[] content;
    String[] action_type;

    String gratitude_content;
    int gratitude_image, gratitude_day_no, gratitude_size;

    public String getGratitude_day() {
        return gratitude_day;
    }

    public void setGratitude_day(String gratitude_day) {
        this.gratitude_day = gratitude_day;
    }

    public String getGratitude_type() {
        return gratitude_type;
    }

    public void setGratitude_type(String gratitude_type) {
        this.gratitude_type = gratitude_type;
    }

    public int[] getImageId() {
        return imageId;
    }

    public void setImageId(int[] imageId) {
        this.imageId = imageId;
    }

    public int[] getDay_no() {
        return day_no;
    }

    public void setDay_no(int[] day_no) {
        this.day_no = day_no;
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public String getGratitude_content() {
        return gratitude_content;
    }

    public void setGratitude_content(String gratitude_content) {
        this.gratitude_content = gratitude_content;
    }

    public int getGratitude_image() {
        return gratitude_image;
    }

    public void setGratitude_image(int gratitude_image) {
        this.gratitude_image = gratitude_image;
    }

    public int getGratitude_day_no() {
        return gratitude_day_no;
    }

    public String[] getAction_type() {
        return action_type;
    }

    public void setAction_type(String[] action_type) {
        this.action_type = action_type;
    }

    public void setGratitude_day_no(int gratitude_day_no) {
        this.gratitude_day_no = gratitude_day_no;
    }

    public int getGratitude_size() {
        return gratitude_size;
    }

    public void setGratitude_size(int gratitude_size) {
        this.gratitude_size = gratitude_size;
    }

    public DailyGratitude getDailyData(String primary, int position, String secondary, String gender) {
        DailyGratitude dailyGratitude = null;
        Log.i("Daily", "Primary profile is "+primary);
        switch (primary) {

            //  Employee professional and others
            case "employed":
            case "user":
                if (secondary != null) {
                    if (secondary.equalsIgnoreCase("pregnant") || secondary.equalsIgnoreCase("conceive") ||
                            secondary.equalsIgnoreCase("depression") || secondary.equalsIgnoreCase("chronic pain/illness")
                            || secondary.equalsIgnoreCase("none")) {

                        dailyGratitude = new DailyGratitude();

                        if (gender.equalsIgnoreCase("Male")) {
                            imageId = new int[]{
                        /*8*/   R.drawable.emp_boy_eight,
                            };
                        } else {
                            imageId = new int[]{
                            };
                        }

                        action_type = new String[]{

                        /*1*/    "DAILY_GRATITUDE",
                        /*2*/    "DAILY_GRATITUDE",
                        /*3*/    "DAILY_GRATITUDE",
                        /*4*/    "DAILY_GRATITUDE",
                        /*5*/    "DAILY_GRATITUDE",
                        /*6*/    "DAILY_GRATITUDE",
                        /*7*/    "CRUSH",
                        /*8*/    "DAILY_GRATITUDE",
                        /*9*/    "DAILY_GRATITUDE",
                        /*10*/   "DAILY_GRATITUDE",
                        /*11*/   "DAILY_GRATITUDE",
                        /*12*/   "CRUSH",
                        /*13*/   "DAILY_GRATITUDE",
                        /*14*/   "DAILY_GRATITUDE",
                        /*15*/   "DAILY_GRATITUDE",
                        /*16*/   "DAILY_GRATITUDE",
                        /*17*/   "DAILY_GRATITUDE",
                        /*18*/   "DAILY_GRATITUDE",
                        /*19*/   "DAILY_GRATITUDE",
                        /*20*/   "DAILY_GRATITUDE",
                        /*21*/   "DAILY_GRATITUDE",
                        /*22*/   "DAILY_GRATITUDE",
                        /*23*/   "DAILY_GRATITUDE",
                        /*24*/   "DAILY_GRATITUDE",
                        /*25*/   "DAILY_GRATITUDE",
                        /*26*/   "CRUSH",
                        /*27*/   "DAILY_GRATITUDE",
                        /*28*/   "CRUSH",
                        /*29*/   "DAILY_GRATITUDE",
                        /*30*/   "DAILY_GRATITUDE",
                        /*31*/   "DAILY_GRATITUDE"
                        };

                        day_no = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
                        content = new String[]{
                                "   Chose someone you are currently living with and have a difficult relation or feelings of dissatisfaction, and write minimum of 10 things you admire in them. Choose the person with  whom you have most arguments or friction with.",
                        "   Chose someone you are currently living with and write a minimum of 10  things you are grateful for about that person. If you are living with family, you can choose to write about  each member of family.   ",
                                "   Chose a person you want to improve the relationship with, currently living with you and write a minimum of 10  you are grateful for about that person. If you are currently living with your romantic partner/spouse, you can choose that person too.  ",
                                "   Write about 3 things that made you happy today because of people you are living with and think the reasons behind. Don\'t only look for special and loaded moments. Think about simple, plain and candid moments too.  ",
                        "   Think about a person you are currently living with and list some 10 good qualities about the person you like. Incase you have kids or siblings, you can choose to write about them too.   ",
                                "   Think about all the times you had good time together  with whom you are living with,  recently- Don't only look for special and loaded moments where you had fun with all people. Think about simple, plain and candid moments even between you and one more person. List down - a minimum of 10 things.  ",
                                "   Think back all the things that irritated or bothered you the most today. Express through writing, empty your system from all pent-up frustrations, experience the calmness, as a burden feels it\'s lifted off your shoulders.  ",
                        "   Think about all the help you received today or very recently from others you are living with or interacted with. List atleast 10 things on how others helped you  and how you felt about the experience.  ",
                                "   Think about 3 ways to genuinely Compliment people who prepared food for you today and express to them. Say thanks to them personally. Write down their reaction and how you felt about the experience. Don\'t forget to feel grateful if you have cooked for others and write about how you felt.   ",
                        "   List about a minimum of 10 things you have learned something from people you have interacted or living with today, however small or insignificant it may see. Don\'t forget to write about how you feelings about the experience.  ",
                        "   Express gratitude to the almighty for your existance  and list 10 things you are very thankful. Also write down, how you felt regarding this experience.  ",
                                "   Think about something that has upset you today and express your thoughts through writing - Experience the sense of lightness surrounding your heart, as you get the pent up emotions out of your system.  ",
                                "   Recognise and get present to people you are living with, who helped you  meet your daily needs. It could be family or friends or helpers or vendors who provided milk, grocery, clearing waste, excercising, providing electricity, internet etc Try to identify 10 such moments and write it. Make a note even if it sounds small or silly. Also write how you feel about it.    ",
                                "   Write 10 things people in your life did for your good health today. It could be providing fresh food, water, care, love, clean environment,excercising aids etc anything that helped you sustain health.    ",
                                "   Think about all the help you received to complete your tasks at home or at work. List down, even it appears small or minor to you. Don't think about big things only. Something, anything that you felt good, just make a note of it and your feelings.  ",
                                "   Think about all the times you have spent time today with your family or with someone you are living with, that you felt good about. List down a minimum of 10 things if you can.   ",
                                "   Think about chores like cooking, house cleaning, laundry, grocery, paying utility bills etc. List atleast 10 people behind the effort and thank them. write down your experience. You can also choose to think big picture regarding supply chain too.  ",
                                "   Compliment people you interact or living or spend time today, on atleast 10 good habits/values you admire in them and write about the experience.  ",
                                "   Think about 10 things you got help from someone you are living with - small or big and express thanks to them. Write about your experience.  ",
                                "   Think about all the friends, family and strangers who helped you during your stay at home and express your thanks. List atleast 10 people. Write about how you felt.  ",
                                "   Write about  all the  conversations you felt cared and loved today/recently from your family members or friends or  spouse or people  you stay with.Think what you enjoyed the most in it.  ",
                                "   Think about someone you have hurt knowingly or unknowingly in recent times. Ask for forgiveness through writing. You can be the big guy and seek forgiveness personally too. Think about how do you want to respond next time when such a situation occurs.  ",
                                "   Think about all the officials who are serving you to keep you safe - medical professionals, police or anyone and write your thanks. Remember to note how you feel about it. You can express it on your social media too.  ",
                                "   Think about atleast 10 people in your community or neighbourhood who helped you today or recent times?  Don't think only about the big things. Just small and a minute one is also fine. Write how you felt about the experience. Think about volunteers, waste picking staff, security, water management, power, internet providers, gas etc   ",
                                "   Write 10 reasons you felt your government is caring about your wellbeing.Express your gratitude through writing and you can send appreciation mail or message on your social media.  ",
                                "   Think about a difficult situation you have hurt someone knowingly or unknowingly. Let go, forgive and delete the hurt from your system.  ",
                                "   Think about a difficult situation with people you currently live with and try to identify what that experience has taught you and how you benefitted from the experience. Reflect on 10 situations and 10 learnings.  Don't think only about the big things. Just small and a minute one is also fine.   ",
                                "   Write 3 things that irritated you the most today. Vent through writing and delete. Let go of all the Irritation. Feel as the peace prevails.  ",
                                "   Think about all the bills you are paying..School fees, Grocery, EMI etc spend a few moments. Write a thank you for each of the services you received from it and that you have funds to pay for it.  ",
                                "   Write about a minimum of 10 times you have received food and specialty items, from your friends or neighbors in recent times. Think about the love and care they have for you and express gratitude.   ",
                                "  Think back and write down 10 recent events that had an impact on you positively. Notice the positive emotions, learning or changes you notice because of them.Try listing a minimum of 10things  "
                        };

                        gratitude_size = content.length;

                        if (position >= content.length) {
                            position = 0;

                        }

                        gratitude_day_no = day_no[position];
                        gratitude_content = content[position];
                        gratitude_image = imageId[position];
                        gratitude_type = action_type[position];
                        dailyGratitude.setGratitude_content(gratitude_content);
                        dailyGratitude.setGratitude_image(gratitude_image);
                        dailyGratitude.setGratitude_day_no(gratitude_day_no);
                        dailyGratitude.setGratitude_type(gratitude_type);
                        dailyGratitude.setGratitude_size(gratitude_size);

                    } else {
                        // employee
                        dailyGratitude = new DailyGratitude();

                        if (gender.equalsIgnoreCase("Male")) {
                            imageId = new int[]{
                        /*8*/   R.drawable.emp_boy_eight,
                            };
                        } else {
                            imageId = new int[]{

                            };
                        }

                        action_type = new String[]{

                        /*1*/    "DAILY_GRATITUDE",
                        /*2*/    "DAILY_GRATITUDE",
                        /*3*/    "DAILY_GRATITUDE",
                        /*4*/    "DAILY_GRATITUDE",
                        /*5*/    "DAILY_GRATITUDE",
                        /*6*/    "DAILY_GRATITUDE",
                        /*7*/    "CRUSH",
                        /*8*/    "DAILY_GRATITUDE",
                        /*9*/    "DAILY_GRATITUDE",
                        /*10*/   "DAILY_GRATITUDE",
                        /*11*/   "DAILY_GRATITUDE",
                        /*12*/   "CRUSH",
                        /*13*/   "DAILY_GRATITUDE",
                        /*14*/   "DAILY_GRATITUDE",
                        /*15*/   "DAILY_GRATITUDE",
                        /*16*/   "DAILY_GRATITUDE",
                        /*17*/   "DAILY_GRATITUDE",
                        /*18*/   "DAILY_GRATITUDE",
                        /*19*/   "DAILY_GRATITUDE",
                        /*20*/   "DAILY_GRATITUDE",
                        /*21*/   "DAILY_GRATITUDE",
                        /*22*/   "DAILY_GRATITUDE",
                        /*23*/   "DAILY_GRATITUDE",
                        /*24*/   "DAILY_GRATITUDE",
                        /*25*/   "DAILY_GRATITUDE",
                        /*26*/   "CRUSH",
                        /*27*/   "DAILY_GRATITUDE",
                        /*28*/   "CRUSH",
                        /*29*/   "DAILY_GRATITUDE",
                        /*30*/   "DAILY_GRATITUDE",
                        /*31*/   "DAILY_GRATITUDE"
                        };

                        day_no = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
                        content = new String[]{
                                "   Chose someone you are currently living with and have a difficult relation or feelings of dissatisfaction,  and write minimum of 10 things you admire in them. Choose the person with whom you have most arguments or friction with.   ",
                                "   Chose someone you are currently living with and write a minimum of 10  things you are grateful for about that person. If you are living with family, you can choose to write about  each member of family.   ",
                                "   Chose a person you want to improve the relationship with, currently living with you and write a minimum of 10  you are grateful for about that person. If you are currently living with your romantic partner/spouse, you can choose that person too.  ",
                                "   Write about 3 things that made you happy today because of people you are living with and think the reasons behind. Don\'t only look for special and loaded moments. Think about simple, plain and candid moments too.  ",
                                "   Think about a person you are currently living with and list some 10 good qualities about the person you like. Incase you have kids or siblings, you can choose to write about them too.   ",
                                "   Think about all the times you had good time together  with whom you are living with,  recently- Don't only look for special and loaded moments where you had fun with all people. Think about simple, plain and candid moments even between you and one more person. List down - a minimum of 10 things.  ",
                                "   Think back all the things that irritated or bothered you the most today. Express through writing, empty your system from all pent-up frustrations, experience the calmness, as a burden feels it\'s lifted off your shoulders.  ",
                                "   Think about all the help you received today or very recently from others you are living with or interacted with. List atleast 10 things on how others helped you  and how you felt about the experience.  ",
                                "   Think about 3 ways to genuinely Compliment people who prepared food for you today and express to them. Say thanks to them personally. Write down their reaction and how you felt about the experience. Don\'t forget to feel grateful if you have cooked for others and write about how you felt.   ",
                                "   List about a minimum of 10 things you have learned something from people you have interacted or living with today, however small or insignificant it may see. Don\'t forget to write about how you feelings about the experience.  ",
                                "   Express gratitude to the almighty for your existance  and list 10 things you are very thankful. Also write down, how you felt regarding this experience.  ",
                                "   Think about something that has upset you today and express your thoughts through writing - Experience the sense of lightness surrounding your heart, as you get the pent up emotions out of your system.  ",
                                "   Recognise and get present to people you are living with, who helped you  meet your daily needs. It could be family or friends or helpers or vendors who provided milk, grocery, clearing waste, excercising, providing electricity, internet etc Try to identify 10 such moments and write it. Make a note even if it sounds small or silly. Also write how you feel about it.    ",
                                "   Write 10 things people in your life did for your good health today. It could be providing fresh food, water, care, love, clean environment,excercising aids etc anything that helped you sustain health.    ",
                                "   Think about all the help you received to complete your tasks at home or at work. List down, even it appears small or minor to you. Don't think about big things only. Something, anything that you felt good, just make a note of it and your feelings.  ",
                                "   Think about all the times you have spent time today with your family or with someone you are living with, that you felt good about. List down a minimum of 10 things if you can.   ",
                                "   Think about chores like cooking, house cleaning, laundry, grocery, paying utility bills etc. List atleast 10 people behind the effort and thank them. write down your experience. You can also choose to think big picture regarding supply chain too.  ",
                                "   Compliment people you interact or living or spend time today, on atleast 10 good habits/values you admire in them and write about the experience.  ",
                                "   Think about 10 things you got help from someone you are living with - small or big and express thanks to them. Write about your experience.  ",
                                "   Think about all the friends, family and strangers who helped you during your stay at home and express your thanks. List atleast 10 people. Write about how you felt.  ",
                                "   Write about  all the  conversations you felt cared and loved today/recently from your family members or friends or  spouse or people  you stay with.Think what you enjoyed the most in it.  ",
                                "   Think about someone you have hurt knowingly or unknowingly in recent times. Ask for forgiveness through writing. You can be the big guy and seek forgiveness personally too. Think about how do you want to respond next time when such a situation occurs.  ",
                                "   Think about all the officials who are serving you to keep you safe - medical professionals, police or anyone and write your thanks. Remember to note how you feel about it. You can express it on your social media too.  ",
                                "   Think about atleast 10 people in your community or neighbourhood who helped you today or recent times?  Don't think only about the big things. Just small and a minute one is also fine. Write how you felt about the experience. Think about volunteers, waste picking staff, security, water management, power, internet providers, gas etc   ",
                                "   Write 10 reasons you felt your government is caring about your wellbeing.Express your gratitude through writing and you can send appreciation mail or message on your social media.  ",
                                "   Think about a difficult situation you have hurt someone knowingly or unknowingly. Let go, forgive and delete the hurt from your system.  ",
                                "   Think about a difficult situation with people you currently live with and try to identify what that experience has taught you and how you benefitted from the experience. Reflect on 10 situations and 10 learnings.  Don't think only about the big things. Just small and a minute one is also fine.   ",
                                "   Write 3 things that irritated you the most today. Vent through writing and delete. Let go of all the Irritation. Feel as the peace prevails.  ",
                                "   Think about all the bills you are paying..School fees, Grocery, EMI etc spend a few moments. Write a thank you for each of the services you received from it and that you have funds to pay for it.  ",
                                "   Write about a minimum of 10 times you have received food and specialty items, from your friends or neighbors in recent times. Think about the love and care they have for you and express gratitude.   ",
                                "  Think back and write down 10 recent events that had an impact on you positively. Notice the positive emotions, learning or changes you notice because of them.Try listing a minimum of 10things  "
                        };

                        gratitude_size = content.length;

                        if (position >= content.length) {
                            position = 0;
                        }
                        gratitude_day_no = day_no[position];
                        gratitude_content = content[position];
                        gratitude_image = imageId[position];
                        gratitude_type = action_type[position];
                        dailyGratitude.setGratitude_content(gratitude_content);
                        dailyGratitude.setGratitude_image(gratitude_image);
                        dailyGratitude.setGratitude_day_no(gratitude_day_no);
                        dailyGratitude.setGratitude_type(gratitude_type);
                        dailyGratitude.setGratitude_size(gratitude_size);
                    }
                }
                break;
            /// homemaker


            case "homemaker":
                dailyGratitude = new DailyGratitude();
                imageId = new int[]{
                };

                action_type = new String[]{

                        /*1*/    "DAILY_GRATITUDE",
                        /*2*/    "DAILY_GRATITUDE",
                        /*3*/    "DAILY_GRATITUDE",
                        /*4*/    "DAILY_GRATITUDE",
                        /*5*/    "DAILY_GRATITUDE",
                        /*6*/    "DAILY_GRATITUDE",
                        /*7*/    "CRUSH",
                        /*8*/    "DAILY_GRATITUDE",
                        /*9*/    "DAILY_GRATITUDE",
                        /*10*/   "DAILY_GRATITUDE",
                        /*11*/   "DAILY_GRATITUDE",
                        /*12*/   "CRUSH",
                        /*13*/   "DAILY_GRATITUDE",
                        /*14*/   "DAILY_GRATITUDE",
                        /*15*/   "DAILY_GRATITUDE",
                        /*16*/   "DAILY_GRATITUDE",
                        /*17*/   "DAILY_GRATITUDE",
                        /*18*/   "DAILY_GRATITUDE",
                        /*19*/   "DAILY_GRATITUDE",
                        /*20*/   "DAILY_GRATITUDE",
                        /*21*/   "DAILY_GRATITUDE",
                        /*22*/   "DAILY_GRATITUDE",
                        /*23*/   "DAILY_GRATITUDE",
                        /*24*/   "DAILY_GRATITUDE",
                        /*25*/   "DAILY_GRATITUDE",
                        /*26*/   "CRUSH",
                        /*27*/   "DAILY_GRATITUDE",
                        /*28*/   "CRUSH",
                        /*29*/   "DAILY_GRATITUDE",
                        /*30*/   "DAILY_GRATITUDE",
                        /*31*/   "DAILY_GRATITUDE"
                };

                day_no = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
                content = new String[]{

                     /*1*/    "Make a note of 3 things you like in your self( You may want to look into the mirror, cut the negative comments if any and focus on only things you like). Appreciate all that makes you - YOU - UNIQUE and SPECIAL.",
                     /*2*/    "Write two things you like about your workplace and people you work with. Also write why you like.",
                     /*3*/    "Think about 3 people who have positively contributed to your life. Write a thank you note and express in a way that makes them feel noticed and valued, over phone or face to face.",
                     /*4*/    "Write about one thing that made you happy today and think the reasons behind. Don't look only for special and loaded moments. Simple, plain and candid moments will do too.",
                     /*5*/    "Write about 3 things  you brought that you liked the most with the money  you have, how inexpensive or small it may seem to you. Capture how you felt while you were able to buy them.",
                     /*6*/    "Write 3 things you are thankful about your health irrespective of your current health condition.",
                     /*7*/    "Think back over recent times on a thing that irritated or bothered you the most. Write your deepest thoughts about your chosen event or experience continuously till you  run out of things to write. After writting, don't look back. Just delete and make a  note of how you feel.",
                     /*8*/    "Think about someone you helped in the past. Write what you did and how others felt. Also capture how you felt about the experience.",
                     /*9*/    "Give a genuine compliment to a friend and write about the experience. Also write how it made you feel.",
                    /*10*/    "Write about a talent of yours  properly utilised to complete a task or activity today at work, however small or insignificant it may seem to you .Don't forget to write about the feelings associated.",
                    /*11*/    "Write a thankyou note to yourself by admiring 3 things your body can do for you that makes you feel good about your health, despite of your current health condition. (Like your ability to do digest food, walk, kidneys functioning etc).",
                    /*12*/    "Write about something that’s upsetting at your work or home. Express freely  all that happened, vent all the hurt, anger or pain till you feel calmer, relaxed and lighter. Delete to completely get the upset out of the system.",
                    /*13*/    "Bless yourself with words. Try verbalizing your thoughts into words as a blessing --May I be.....",
                    /*14*/    "What is something you’ve been avoiding that you would really like to accomplish soon? Make a note even if it sounds small or silly. I want to achieve………accomplish by date.",
                    /*15*/    "Write about  a recent accomplishment, how small or minor it may seem to you. Don't think about big things only. Something, anything that you felt good, just make a note of it and your feelings.",
                    /*16*/    "Write about a quality time you spent today with your family or colleagues that you felt good about. Don't forget to make a note on what made the moment special for you.",
                    /*17*/    "Love yourself unconditionally . Allow yourself to drop the conditions that you have created to love yourself. Let go of: “I will love myself better if I am…” and choose to accept yourself as you are. Write 3 reasons  you love yourself for . Feel the feelings of love.",
                    /*18*/    "Compliment yourself on a good habit you have. Again, old or new , big or small doesn't matter. Acknowledge yourself for working towards making it a habit even if it's a while ago. Write how the habit made you a better individual.",
                    /*19*/    "Think about an area you felt your talents or efforts are being utilised for others benefit, even though no one praised or appreciated, however small or big, during recent times and write a thankyou note on the ability you have for it.",
                    /*20*/    "Write a thank you for the capacities allowed by your health however restricted or healthy you may feel  – like, you can climb stairs, can deal with rough weather conditions, can see  with your eyes etc.",
                    /*21*/    "Write about a conversation you really enjoyed  today with your family members, friends or colleagues.Also write what you enjoyed the most in it.",
                    /*22*/    "Write a letter to your colleague at your workplace or a friend thanking for all the support however small or big.",
                    /*23*/    "Think about a mistake you committed during your recent times or in the past, when the pain or negative consequence or price to pay were too high. Forgive yourself for the mistake and write about one way you can be kinder to yourself right now. Touch the place where you feel the pain in your body and tell some soothing words. (Think what you would say to a friend who have done similar mistake).",
                    /*24*/    "What is one good thing you learned today? Write how did it make you feel. Write who all contributed to your this learning.",
                    /*25*/    "Write 3 reasons you love your parents. Just think how your parents positively nurtured you in their own capacities, even if they have made some mistakes.  Tell them thanks for their contribution, over phone or face to face. Try it even if you havent done before or have been doing everyday.",
                    /*26*/    "Think about someone who have hurt you. Write about what happened, how you felt in as many words as possible. Write till you vent all the pain and hurt.  Forgive, feel the calmness as you click  delete and allow the hurt  let go from your system.",
                    /*27*/    "Bless any of your family members. Bless them peace, health and happiness. Write how you felt as you blessed them good health, happiness and peace.",
                    /*28*/    "Write 2 things that irritated you the most today. Vent through writing all your irritation and delete. Let go of all the Irritation. Feel as the peace prevails.",
                    /*29*/    "Think about all the bills you are paying..School fees, Grocery, EMI etc..spend few moments. Write a thank you for each of the service you received from it and that you have funds to pay for it.",
                    /*30*/    "Write about a food item you ate recently that you loved and cherished. Try and remember why you loved it, how you enjoyed eating it.",
                    /*31*/    "Think back and write down  five recent events that had an impact on you positively. Notice the positive emotions, learning or changes you notice because of them."
                };

                gratitude_size = content.length;

                if (position > content.length || position > day_no.length || position > imageId.length || position > action_type.length) {
                    position = 0;
                }

                gratitude_day_no = day_no[position];
                gratitude_content = content[position];
                gratitude_image = imageId[position];
                gratitude_type = action_type[position];
                dailyGratitude.setGratitude_content(gratitude_content);
                dailyGratitude.setGratitude_image(gratitude_image);
                dailyGratitude.setGratitude_day_no(gratitude_day_no);
                dailyGratitude.setGratitude_type(gratitude_type);
                dailyGratitude.setGratitude_size(gratitude_size);
                break;


            case "staff":
                dailyGratitude = new DailyGratitude();

                if (gender.equalsIgnoreCase("Male")) {
                    imageId = new int[]{
                        /*8*/   R.drawable.emp_boy_eight,

                    };
                } else {
                    imageId = new int[]{

                    };
                }

                action_type = new String[]{

                        /*1*/    "DAILY_GRATITUDE",
                        /*2*/    "DAILY_GRATITUDE",
                        /*3*/    "DAILY_GRATITUDE",
                        /*4*/    "DAILY_GRATITUDE",
                        /*5*/    "DAILY_GRATITUDE",
                        /*6*/    "DAILY_GRATITUDE",
                        /*7*/    "CRUSH",
                        /*8*/    "DAILY_GRATITUDE",
                        /*9*/    "DAILY_GRATITUDE",
                        /*10*/   "DAILY_GRATITUDE",
                        /*11*/   "DAILY_GRATITUDE",
                        /*12*/   "CRUSH",
                        /*13*/   "DAILY_GRATITUDE",
                        /*14*/   "DAILY_GRATITUDE",
                        /*15*/   "DAILY_GRATITUDE",
                        /*16*/   "DAILY_GRATITUDE",
                        /*17*/   "DAILY_GRATITUDE",
                        /*18*/   "DAILY_GRATITUDE",
                        /*19*/   "DAILY_GRATITUDE",
                        /*20*/   "DAILY_GRATITUDE",
                        /*21*/   "DAILY_GRATITUDE",
                        /*22*/   "DAILY_GRATITUDE",
                        /*23*/   "DAILY_GRATITUDE",
                        /*24*/   "DAILY_GRATITUDE",
                        /*25*/   "DAILY_GRATITUDE",
                        /*26*/   "CRUSH",
                        /*27*/   "DAILY_GRATITUDE",
                        /*28*/   "CRUSH",
                        /*29*/   "DAILY_GRATITUDE",
                        /*30*/   "DAILY_GRATITUDE",
                        /*31*/   "DAILY_GRATITUDE"
                };

                day_no = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
                content = new String[]{

                     /*1*/    "Make a note of 3 things you like in your self( You may want to look into the mirror, cut the negative comments if any and focus on only things you like). Appreciate all that makes you - YOU - UNIQUE and SPECIAL.",
                     /*2*/    "Write two things you like about your workplace and people you work with. Also write why you like.",
                     /*3*/    "Think about 3 people who have positively contributed to your life. Write a thank you note and express in a way that makes them feel noticed and valued, over phone or face to face.",
                     /*4*/    "Write about one thing that made you happy today and think the reasons behind. Don't look only for special and loaded moments. Simple, plain and candid moments will do too.",
                     /*5*/    "Write about 3 things  you brought that you liked the most with the money  you have, how inexpensive or small it may seem to you. Capture how you felt while you were able to buy them.",
                     /*6*/    "Write 3 things you are thankful about your health irrespective of your current health condition.",
                     /*7*/    "Think back over recent times on a thing that irritated or bothered you the most. Write your deepest thoughts about your chosen event or experience continuously till you  run out of things to write. After writting, don't look back. Just delete and make a  note of how you feel.",
                     /*8*/    "Think about someone you helped in the past. Write what you did and how others felt. Also capture how you felt about the experience.",
                     /*9*/    "Give a genuine compliment to a friend and write about the experience. Also write how it made you feel.",
                    /*10*/    "Write about a talent of yours  properly utilised to complete a task or activity today at work, however small or insignificant it may seem to you .Don't forget to write about the feelings associated.",
                    /*11*/    "Write a thankyou note to yourself by admiring 3 things your body can do for you that makes you feel good about your health, despite of your current health condition. (Like your ability to do digest food, walk, kidneys functioning etc).",
                    /*12*/    "Write about something that’s upsetting at your work or home. Express freely  all that happened, vent all the hurt, anger or pain till you feel calmer, relaxed and lighter. Delete to completely get the upset out of the system.",
                    /*13*/    "Bless yourself with words. Try verbalizing your thoughts into words as a blessing --May I be.....",
                    /*14*/    "What is something you’ve been avoiding that you would really like to accomplish soon? Make a note even if it sounds small or silly. I want to achieve………accomplish by date.",
                    /*15*/    "Write about  a recent accomplishment, how small or minor it may seem to you. Don't think about big things only. Something, anything that you felt good, just make a note of it and your feelings.",
                    /*16*/    "Write about a quality time you spent today with your family or colleagues that you felt good about. Don't forget to make a note on what made the moment special for you.",
                    /*17*/    "Love yourself unconditionally . Allow yourself to drop the conditions that you have created to love yourself. Let go of: “I will love myself better if I am…” and choose to accept yourself as you are. Write 3 reasons  you love yourself for . Feel the feelings of love.",
                    /*18*/    "Compliment yourself on a good habit you have. Again, old or new , big or small doesn't matter. Acknowledge yourself for working towards making it a habit even if it's a while ago. Write how the habit made you a better individual.",
                    /*19*/    "Think about an area you felt your talents or efforts are being utilised for others benefit, even though no one praised or appreciated, however small or big, during recent times and write a thankyou note on the ability you have for it.",
                    /*20*/    "Write a thank you for the capacities allowed by your health however restricted or healthy you may feel  – like, you can climb stairs, can deal with rough weather conditions, can see  with your eyes etc.",
                    /*21*/    "Write about a conversation you really enjoyed  today with your family members, friends or colleagues.Also write what you enjoyed the most in it.",
                    /*22*/    "Write a letter to your colleague at your workplace or a friend thanking for all the support however small or big.",
                    /*23*/    "Think about a mistake you committed during your recent times or in the past, when the pain or negative consequence or price to pay were too high. Forgive yourself for the mistake and write about one way you can be kinder to yourself right now. Touch the place where you feel the pain in your body and tell some soothing words. (Think what you would say to a friend who have done similar mistake).",
                    /*24*/    "What is one good thing you learned today? Write how did it make you feel. Write who all contributed to your this learning.",
                    /*25*/    "Write 3 reasons you love your parents. Just think how your parents positively nurtured you in their own capacities, even if they have made some mistakes.  Tell them thanks for their contribution, over phone or face to face. Try it even if you havent done before or have been doing everyday.",
                    /*26*/    "Think about someone who have hurt you. Write about what happened, how you felt in as many words as possible. Write till you vent all the pain and hurt.  Forgive, feel the calmness as you click  delete and allow the hurt  let go from your system.",
                    /*27*/    "Bless any of your family members. Bless them peace, health and happiness. Write how you felt as you blessed them good health, happiness and peace.",
                    /*28*/    "Write 2 things that irritated you the most today. Vent through writing all your irritation and delete. Let go of all the Irritation. Feel as the peace prevails.",
                    /*29*/    "Think about all the bills you are paying..School fees, Grocery, EMI etc..spend few moments. Write a thank you for each of the service you received from it and that you have funds to pay for it.",
                    /*30*/    "Write about a food item you ate recently that you loved and cherished. Try and remember why you loved it, how you enjoyed eating it.",
                    /*31*/    "Think back and write down  five recent events that had an impact on you positively. Notice the positive emotions, learning or changes you notice because of them."
                };

                gratitude_size = content.length;

                if (position > content.length || position > day_no.length || position > imageId.length || position > action_type.length) {
                    position = 0;
                }

                gratitude_day_no = day_no[position];
                gratitude_content = content[position];
                gratitude_image = imageId[position];
                gratitude_type = action_type[position];
                dailyGratitude.setGratitude_content(gratitude_content);
                dailyGratitude.setGratitude_image(gratitude_image);
                dailyGratitude.setGratitude_day_no(gratitude_day_no);
                dailyGratitude.setGratitude_type(gratitude_type);
                dailyGratitude.setGratitude_size(gratitude_size);
                break;


            // student looking for job
            case "Student":
                dailyGratitude = new DailyGratitude();
                if (gender.equalsIgnoreCase("Male")) {
                    imageId = new int[]{
                    };
                } else {
                    imageId = new int[]{


                    };
                }

                action_type = new String[]{

                        /*1*/    "DAILY_GRATITUDE",
                        /*2*/    "DAILY_GRATITUDE",
                        /*3*/    "DAILY_GRATITUDE",
                        /*4*/    "DAILY_GRATITUDE",
                        /*5*/    "DAILY_GRATITUDE",
                        /*6*/    "DAILY_GRATITUDE",
                        /*7*/    "CRUSH",
                        /*8*/    "DAILY_GRATITUDE",
                        /*9*/    "DAILY_GRATITUDE",
                        /*10*/   "DAILY_GRATITUDE",
                        /*11*/   "CRUSH",
                        /*12*/   "DAILY_GRATITUDE",
                        /*13*/   "DAILY_GRATITUDE",
                        /*14*/   "DAILY_GRATITUDE",
                        /*15*/   "DAILY_GRATITUDE",
                        /*16*/   "DAILY_GRATITUDE",
                        /*17*/   "DAILY_GRATITUDE",
                        /*18*/   "DAILY_GRATITUDE",
                        /*19*/   "DAILY_GRATITUDE",
                        /*20*/   "DAILY_GRATITUDE",
                        /*21*/   "DAILY_GRATITUDE",
                        /*22*/   "DAILY_GRATITUDE",
                        /*23*/   "CRUSH",
                        /*24*/   "DAILY_GRATITUDE",
                        /*25*/   "DAILY_GRATITUDE",
                        /*26*/   "CRUSH",
                        /*27*/   "DAILY_GRATITUDE",
                        /*28*/   "DAILY_GRATITUDE",
                        /*29*/   "DAILY_GRATITUDE",
                        /*30*/   "DAILY_GRATITUDE",
                        /*31*/   "DAILY_GRATITUDE"
                };

                day_no = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
                content = new String[]{

                     /*1*/    "Make a note of 3 things you like in your self( You may want to look into the mirror, cut the negative comments if any and focus on only things you like). Appreciate all that makes you - YOU - UNIQUE and SPECIAL.",
                      /*2*/    "Write two things you like about the college/school you study or the subject/majors. Again, don't think only big and mighty ones. Think and write 2 best things you love.",
                     /*3*/    "Write about someone in your life on how much you love them and share it with that  person over phone or face to face.",
                     /*4*/    "Write about 3 things  that made you happy today and think the reasons behind. Don't only look for special and loaded moments. Think about simple, plain and candid moments too.",
                     /*5*/    "Appreciate 1 thing  you brought that you liked the most with the money  you have, how inexpensive or small it may seem to you.Capture how you felt while you were able to buy them.",
                     /*6*/   "Write 3 things you are thankful about your health irrespective of your current health condition.",
                     /*7*/    "Think back over today  on 3  things that irritated or bothered you the most. Express through writing, empty your system from all pent-up frustrations, experience the calmness,  as a burden feels it's lifted off your shoulders.",
                     /*8*/    "Think about someone you helped in the past. Write what you did and how others felt. Also capture how you felt about the experience.",
                     /*9*/    "Give a genuine compliment to a friend and write about the experience.",
                    /*10*/    "Write one thing you used your talent today, however small or insignificant it may seem, to complete a task or activity. Don't forget to write about your feelings.",
                    /*11*/    "Write a thank you note to yourself by admiring 3 things your body can do for you that makes you feel strong and happy. (Like your ability to do a quick run, walk your dog, do dance etc).",
                    /*12*/    "Think about something that’s upsetting about  your performance be it at studies, sports or any other areas and express your thoughts through writing - see as you feel relaxed getting out of your system.",
                    /*13*/    "Bless yourself with all success in the world. Try verbalizing your thoughts into words as a blessing --May I be happy...May i be successful..may i ....",
                    /*14*/    "What is something you’ve been avoiding that you would really like to accomplish soon? I want to achieve…… …accomplish by date.",
                    /*15*/    "Write about  a latest accomplishment, how small or minor it may seem to you. Don't think about big things only. Something, anything that you felt good, just make a note of it and your feelings.",
                    /*16*/    "Write about a quality time you spent today with your family that you felt good about.",
                    /*17*/    "Love yourself unconditionally Allow yourself to drop the conditions that you have created to love yourself. Let go of: “I will love myself better if I am…” and choose to accept yourself as you are. Write 3 reasons  you love yourself for.",
                    /*18*/    "Compliment yourself for  a good habit you have. Write how the habit positively impacted you.",
                    /*19*/    "Think about 1 best academic achievement according to you, however small or big, during recent times and thank yourself by writing  the ability you have for it.",
                    /*20*/    "Write a thank you for the capacities allowed by your health however restricted or healthy you may feel – like, you can climb stairs, can deal with rough weather conditions, can digest food etc.",
                    /*21*/    "Write about one conversation you really enjoyed today with your family or friends. Think what you enjoyed the most in it.",
                    /*22*/    "Write a letter to your teacher thanking his/her support for your education. Capture the feelings about it.",
                    /*23*/    "Think about one mistake during your recent times or in the past when the pain or negative consequence or price to pay were too high. Forgive yourself for the mistake and write about one way you can be kinder to yourself right now.",
                    /*24*/    "What is one good thing you learned today? Don't think only about the big things. Just small and a minute one is also fine. Write how you felt about  being able to learn too.",
                    /*25*/    "Write 3 reasons you love your parents and say thanks over phone or face-face.",
                   /*26*/    "Think about someone who have hurt you. Write about what happened, how you felt in as many words as possible. Write till you vent all the pain and hurt.  Forgive, feel the calmness as you click  delete and allow the hurt  let go from your system.",
                     /*27*/    "Bless any of your family members or friends. Bless them success , health and happiness.Write how you felt as you blessed them good health, happiness and success.",
                    /*28*/    "Write 3 things that irritated you the most today. Vent through writing and delete. Let go of all the Irritation. Feel as the peace prevails.",
                    /*29*/    "Write about a happy moment that you enjoyed the most today. Also capture what made the moment happy for you.",
                    /*30*/    "Write about a food item you ate recently that you loved and cherished. Try and remember why you loved it, how you enjoyed eating it.",
                    /*31*/    "Think back and write down  five recent events that had an impact on you positively. Notice the positive emotions, learning or changes you notice because of them."
                };

                gratitude_size = content.length;

                if (position > content.length || position > day_no.length || position > imageId.length || position > action_type.length) {
                    position = 0;
                }

                gratitude_day_no = day_no[position];
                gratitude_content = content[position];
                gratitude_image = imageId[position];
                gratitude_type = action_type[position];
                dailyGratitude.setGratitude_content(gratitude_content);
                dailyGratitude.setGratitude_image(gratitude_image);
                dailyGratitude.setGratitude_day_no(gratitude_day_no);
                dailyGratitude.setGratitude_type(gratitude_type);
                dailyGratitude.setGratitude_size(gratitude_size);
                break;

            case "looking_job":
                dailyGratitude = new DailyGratitude();
                if (gender.equalsIgnoreCase("Male")) {
                    imageId = new int[] {
                    };
                } else {
                    imageId = new int[] {
                    };
                }

                action_type = new String[]{

                        /*1*/    "DAILY_GRATITUDE",
                        /*2*/    "DAILY_GRATITUDE",
                        /*3*/    "DAILY_GRATITUDE",
                        /*4*/    "DAILY_GRATITUDE",
                        /*5*/    "DAILY_GRATITUDE",
                        /*6*/    "DAILY_GRATITUDE",
                        /*7*/    "CRUSH",
                        /*8*/    "DAILY_GRATITUDE",
                        /*9*/    "DAILY_GRATITUDE",
                        /*10*/   "DAILY_GRATITUDE",
                        /*11*/   "CRUSH",
                        /*12*/   "DAILY_GRATITUDE",
                        /*13*/   "DAILY_GRATITUDE",
                        /*14*/   "DAILY_GRATITUDE",
                        /*15*/   "DAILY_GRATITUDE",
                        /*16*/   "DAILY_GRATITUDE",
                        /*17*/   "DAILY_GRATITUDE",
                        /*18*/   "DAILY_GRATITUDE",
                        /*19*/   "DAILY_GRATITUDE",
                        /*20*/   "DAILY_GRATITUDE",
                        /*21*/   "DAILY_GRATITUDE",
                        /*22*/   "DAILY_GRATITUDE",
                        /*23*/   "CRUSH",
                        /*24*/   "DAILY_GRATITUDE",
                        /*25*/   "DAILY_GRATITUDE",
                        /*26*/   "CRUSH",
                        /*27*/   "DAILY_GRATITUDE",
                        /*28*/   "DAILY_GRATITUDE",
                        /*29*/   "DAILY_GRATITUDE",
                        /*30*/   "DAILY_GRATITUDE",
                        /*31*/   "DAILY_GRATITUDE"
                };

                day_no = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
                content = new String[]{

                     /*1*/    "Make a note of 3 things you like in your self( You may want to look into the mirror, cut the negative comments if any and focus on only things you like). Appreciate all that makes you - YOU - UNIQUE and SPECIAL.",
                      /*2*/    "Write two things you like about the college/school you study or the subject/majors. Again, don't think only big and mighty ones. Think and write 2 best things you love.",
                     /*3*/    "Write about someone in your life on how much you love them and share it with that  person over phone or face to face.",
                     /*4*/    "Write about 3 things  that made you happy today and think the reasons behind. Don't only look for special and loaded moments. Think about simple, plain and candid moments too.",
                     /*5*/    "Appreciate 1 thing  you brought that you liked the most with the money  you have, how inexpensive or small it may seem to you.Capture how you felt while you were able to buy them.",
                     /*6*/   "Write 3 things you are thankful about your health irrespective of your current health condition.",
                     /*7*/    "Think back over today  on 3  things that irritated or bothered you the most. Express through writing, empty your system from all pent-up frustrations, experience the calmness,  as a burden feels it's lifted off your shoulders.",
                     /*8*/    "Think about someone you helped in the past. Write what you did and how others felt. Also capture how you felt about the experience.",
                     /*9*/    "Give a genuine compliment to a friend and write about the experience.",
                    /*10*/    "Write one thing you used your talent today, however small or insignificant it may seem, to complete a task or activity. Don't forget to write about your feelings.",
                    /*11*/    "Write a thank you note to yourself by admiring 3 things your body can do for you that makes you feel strong and happy. (Like your ability to do a quick run, walk your dog, do dance etc).",
                    /*12*/    "Think about something that’s upsetting about  your performance be it at studies, sports or any other areas and express your thoughts through writing - see as you feel relaxed getting out of your system.",
                    /*13*/    "Bless yourself with all success in the world. Try verbalizing your thoughts into words as a blessing --May I be happy...May i be successful..may i ....",
                    /*14*/    "What is something you’ve been avoiding that you would really like to accomplish soon? I want to achieve…… …accomplish by date.",
                    /*15*/    "Write about  a latest accomplishment, how small or minor it may seem to you. Don't think about big things only. Something, anything that you felt good, just make a note of it and your feelings.",
                    /*16*/    "Write about a quality time you spent today with your family that you felt good about.",
                    /*17*/    "Love yourself unconditionally Allow yourself to drop the conditions that you have created to love yourself. Let go of: “I will love myself better if I am…” and choose to accept yourself as you are. Write 3 reasons  you love yourself for.",
                    /*18*/    "Compliment yourself for  a good habit you have. Write how the habit positively impacted you.",
                    /*19*/    "Think about 1 best academic achievement according to you, however small or big, during recent times and thank yourself by writing  the ability you have for it.",
                    /*20*/    "Write a thank you for the capacities allowed by your health however restricted or healthy you may feel – like, you can climb stairs, can deal with rough weather conditions, can digest food etc.",
                    /*21*/    "Write about one conversation you really enjoyed today with your family or friends. Think what you enjoyed the most in it.",
                    /*22*/    "Write a letter to your teacher thanking his/her support for your education. Capture the feelings about it.",
                    /*23*/    "Think about one mistake during your recent times or in the past when the pain or negative consequence or price to pay were too high. Forgive yourself for the mistake and write about one way you can be kinder to yourself right now.",
                    /*24*/    "What is one good thing you learned today? Don't think only about the big things. Just small and a minute one is also fine. Write how you felt about  being able to learn too.",
                    /*25*/    "Write 3 reasons you love your parents and say thanks over phone or face-face.",
                   /*26*/    "Think about someone who have hurt you. Write about what happened, how you felt in as many words as possible. Write till you vent all the pain and hurt.  Forgive, feel the calmness as you click  delete and allow the hurt  let go from your system.",
                     /*27*/    "Bless any of your family members or friends. Bless them success , health and happiness.Write how you felt as you blessed them good health, happiness and success.",
                    /*28*/    "Write 3 things that irritated you the most today. Vent through writing and delete. Let go of all the Irritation. Feel as the peace prevails.",
                    /*29*/    "Write about a happy moment that you enjoyed the most today. Also capture what made the moment happy for you.",
                    /*30*/    "Write about a food item you ate recently that you loved and cherished. Try and remember why you loved it, how you enjoyed eating it.",
                    /*31*/    "Think back and write down  five recent events that had an impact on you positively. Notice the positive emotions, learning or changes you notice because of them."
                };

                gratitude_size = content.length;

                if (position > content.length || position > day_no.length || position > imageId.length || position > action_type.length) {
                    position = 0;
                }

                gratitude_day_no = day_no[position];
                gratitude_content = content[position];
                gratitude_image = imageId[position];
                gratitude_type = action_type[position];
                dailyGratitude.setGratitude_content(gratitude_content);
                dailyGratitude.setGratitude_image(gratitude_image);
                dailyGratitude.setGratitude_day_no(gratitude_day_no);
                dailyGratitude.setGratitude_type(gratitude_type);
                dailyGratitude.setGratitude_size(gratitude_size);
                break;


            case "seniorcitizen":
                dailyGratitude = new DailyGratitude();
                if (gender.equalsIgnoreCase("Male")) {
                    imageId = new int[]{
                    };
                } else {
                    imageId = new int[]{

                    };
                }

                action_type = new String[]{

                        /*1*/    "DAILY_GRATITUDE",
                        /*2*/    "DAILY_GRATITUDE",
                        /*3*/    "DAILY_GRATITUDE",
                        /*4*/    "DAILY_GRATITUDE",
                        /*5*/    "DAILY_GRATITUDE",
                        /*6*/    "DAILY_GRATITUDE",
                        /*7*/    "CRUSH",
                        /*8*/    "DAILY_GRATITUDE",
                        /*9*/    "DAILY_GRATITUDE",
                        /*10*/   "DAILY_GRATITUDE",
                        /*11*/   "DAILY_GRATITUDE",
                        /*12*/   "CRUSH",
                        /*13*/   "DAILY_GRATITUDE",
                        /*14*/   "DAILY_GRATITUDE",
                        /*15*/   "DAILY_GRATITUDE",
                        /*16*/   "DAILY_GRATITUDE",
                        /*17*/   "DAILY_GRATITUDE",
                        /*18*/   "DAILY_GRATITUDE",
                        /*19*/   "DAILY_GRATITUDE",
                        /*20*/   "DAILY_GRATITUDE",
                        /*21*/   "DAILY_GRATITUDE",
                        /*22*/   "DAILY_GRATITUDE",
                        /*23*/   "DAILY_GRATITUDE",
                        /*24*/   "DAILY_GRATITUDE",
                        /*25*/   "DAILY_GRATITUDE",
                        /*26*/   "CRUSH",
                        /*27*/   "DAILY_GRATITUDE",
                        /*28*/   "CRUSH",
                        /*29*/   "DAILY_GRATITUDE",
                        /*30*/   "DAILY_GRATITUDE",
                        /*31*/   "DAILY_GRATITUDE"
                };

                day_no = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
                content = new String[]{

                      /*1*/  "Make a note of 3 things you like in your self( You may want to look into the mirror, cut the negative comments if any and focus on only things you like). Appreciate all that makes you - YOU - UNIQUE and SPECIAL.",
                     /*2*/   "Write two things you like about the place you live in. Again, don't think only big and mighty only. Think and write 2 best things you love.",
                     /*3*/   "Write about someone in your life on how much you love them and share it with that person over phone or face to face.",
                     /*4*/   "Write about 3 things that made you happy today and think the reasons behind. Don't only look for special and loaded moments. Think about simple, plain and candid moments too.",
                     /*5*/   "Write about 3 things  you brought that you liked the most with the money  you have, how inexpensive or small it may seem to you.Capture how you felt while you were able to buy them.",
                     /*6*/   "Write 3 things you are thankful about your health irrespective of your current health condition.",
                     /*7*/   "Think back over recent times on 3 things that irritated or bothered you the most. Express through writing, empty your system from all pent-up frustrations, experience the calmness, as a burden feels it's lifted off your shoulders.",
                     /*8*/   "Think about someone you helped in the past. Write what you did and how others felt. Also capture how you felt about the experience.",
                     /*9*/   "Give a genuine Compliment to a friend and write about the experience.",
                    /*10*/    "Write one thing you used your talent today, however small or insignificant it may seem, to complete a task or activity.Don't forget to write about how you feelings.",
                    /*11*/    "Write a thankyou note to yourself by admiring 3 things your body can do for you that makes you feel good about your health, however your current health may seem. (Like your ability to do digest food, walk, kidneys functioning etc).",
                    /*12*/    "Think about something that’s upsetting in your life and  and express your thoughts through writing - Experience the sense of lightness surrounding your heart, as you get the pent up emotions out of your system.",
                    /*13*/    "Bless yourself with words. Try verbalizing your thoughts into words as a blessing --May I be.....",
                    /*14*/    "What is something you’ve been avoiding that you would really like to accomplish soon? Make a note even if it sounds small or silly. I want to achieve…… …accomplish by date.",
                    /*15*/    "Write about a latest accomplishment, how small or minor it may seem to you. Don't think about big things only. Something, anything that you felt good, just make a note of it and your feelings.",
                    /*16*/    "Write about a quality time you spent today with your family that you felt good about.",
                    /*17*/    "Love yourself unconditionally Allow yourself to drop the conditions that you have created to love yourself. Let go of: “I will love myself better if I am…” and choose to accept yourself as you are. Write 3 reasons  you love yourself for.",
                    /*18*/    "Compliment yourself for a good habit you have. Write how the habit positively impacted you.",
                    /*19*/    "Think about 1 area you felt your talents or efforts are being utilised for others benefit, even though no one praised or appreciated, however small or big, during recent times and write a thankyou note on the ability you have for it.",
                    /*20*/    "Write a thank you for the capacities allowed by your health however restricted or healthy you may feel – like, you can climb stairs, can deal with rough weather conditions, can see with your eyes etc.",
                    /*21*/    "Write about one conversation you really enjoyed today with your family members or friends.Think what you enjoyed the most in it.",
                    /*22*/    "Write a letter to your spouse thanking for all the support however small or big.",
                    /*23*/    "Think about one mistake during your recent times or in the past when the pain or negative consequence or price to pay were too high. Forgive yourself for the mistake and write about one way you can be kinder to yourself right now.",
                    /*24*/    "What is one good thing you learned today? Don't think only about the big things. Just small and a minute one is also fine. Write how you felt about being able to learn too.",
                    /*25*/    "Write 3 reasons you love your kids or family members and say thanks for being there  by talking to them over phone or by face to face.",
                    /*26*/    "Think about someone who have hurt you. Write about what happened, how you felt in as many words as possible. Write till you vent all the pain and hurt.  Forgive, feel the calmness as you click  delete and allow the hurt  let go from your system.",
                    /*27*/    "Bless any of your family members or friends. Bless them peace, health and happiness.Write how you felt as you blessed them good health, happiness and peace.",
                    /*28*/    "Write 3 things that irritated you the most today. Vent through writing and delete. Let go of all the Irritation. Feel as the peace prevails.",
                    /*29*/    "Think about all the bills you are paying. School fees, Grocery, EMI etc..spend few moments. Write a thank you for each of the service you received from it and that you have funds to pay for it.",
                    /*30*/    "Write about a food item you ate recently that you loved and cherished. Try and remember why you loved it, how you enjoyed eating it.",
                    /*31*/    "Think back and write down five recent events that had an impact on you positively. Notice the positive emotions, learning or changes you notice because of them."
                };

                gratitude_size = content.length;

                if (position > content.length || position > day_no.length || position > imageId.length || position > action_type.length) {
                    position = 0;
                }

                gratitude_day_no = day_no[position];
                gratitude_content = content[position];
                gratitude_image = imageId[position];
                gratitude_type = action_type[position];
                dailyGratitude.setGratitude_content(gratitude_content);
                dailyGratitude.setGratitude_image(gratitude_image);
                dailyGratitude.setGratitude_day_no(gratitude_day_no);
                dailyGratitude.setGratitude_type(gratitude_type);
                dailyGratitude.setGratitude_size(gratitude_size);
                break;


            default:

                // default
                dailyGratitude = new DailyGratitude();
                imageId = new int[]{

                };

                action_type = new String[]{

                        /*1*/    "DAILY_GRATITUDE",
                        /*2*/    "DAILY_GRATITUDE",
                        /*3*/    "DAILY_GRATITUDE",
                        /*4*/    "DAILY_GRATITUDE",
                        /*5*/    "DAILY_GRATITUDE",
                        /*6*/    "DAILY_GRATITUDE",
                        /*7*/    "CRUSH",
                        /*8*/    "DAILY_GRATITUDE",
                        /*9*/    "DAILY_GRATITUDE",
                        /*10*/   "DAILY_GRATITUDE",
                        /*11*/   "DAILY_GRATITUDE",
                        /*12*/   "CRUSH",
                        /*13*/   "DAILY_GRATITUDE",
                        /*14*/   "DAILY_GRATITUDE",
                        /*15*/   "DAILY_GRATITUDE",
                        /*16*/   "DAILY_GRATITUDE",
                        /*17*/   "DAILY_GRATITUDE",
                        /*18*/   "DAILY_GRATITUDE",
                        /*19*/   "DAILY_GRATITUDE",
                        /*20*/   "DAILY_GRATITUDE",
                        /*21*/   "DAILY_GRATITUDE",
                        /*22*/   "DAILY_GRATITUDE",
                        /*23*/   "DAILY_GRATITUDE",
                        /*24*/   "DAILY_GRATITUDE",
                        /*25*/   "DAILY_GRATITUDE",
                        /*26*/   "CRUSH",
                        /*27*/   "DAILY_GRATITUDE",
                        /*28*/   "CRUSH",
                        /*29*/   "DAILY_GRATITUDE",
                        /*30*/   "DAILY_GRATITUDE",
                        /*31*/   "DAILY_GRATITUDE"
                };

                day_no = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
                content = new String[]{

                      /*1*/  "Make a note of 3 things you like in your self( You may want to look into the mirror, cut the negative comments if any and focus on only things you like). Appreciate all that makes you - YOU - UNIQUE and SPECIAL.",
                     /*2*/   "Write two things you like about the place you live in. Again, don't think only big and mighty only. Think and write 2 best things you love.",
                     /*3*/   "Write about someone in your life on how much you love them and share it with that person over phone or face to face.",
                     /*4*/   "Write about 3 things that made you happy today and think the reasons behind. Don't only look for special and loaded moments. Think about simple, plain and candid moments too.",
                     /*5*/   "Write about 3 things  you brought that you liked the most with the money  you have, how inexpensive or small it may seem to you.Capture how you felt while you were able to buy them.",
                     /*6*/   "Write 3 things you are thankful about your health irrespective of your current health condition.",
                     /*7*/   "Think back over recent times on 3 things that irritated or bothered you the most. Express through writing, empty your system from all pent-up frustrations, experience the calmness, as a burden feels it's lifted off your shoulders.",
                     /*8*/   "Think about someone you helped in the past. Write what you did and how others felt. Also capture how you felt about the experience.",
                     /*9*/   "Give a genuine Compliment to a friend and write about the experience.",
                    /*10*/    "Write one thing you used your talent today, however small or insignificant it may seem, to complete a task or activity.Don't forget to write about how you feelings.",
                    /*11*/    "Write a thankyou note to yourself by admiring 3 things your body can do for you that makes you feel good about your health, however your current health may seem. (Like your ability to do digest food, walk, kidneys functioning etc).",
                    /*12*/    "Think about something that’s upsetting in your life and  and express your thoughts through writing - Experience the sense of lightness surrounding your heart, as you get the pent up emotions out of your system.",
                    /*13*/    "Bless yourself with words. Try verbalizing your thoughts into words as a blessing --May I be.....",
                    /*14*/    "What is something you’ve been avoiding that you would really like to accomplish soon? Make a note even if it sounds small or silly. I want to achieve…… …accomplish by date.",
                    /*15*/    "Write about a latest accomplishment, how small or minor it may seem to you. Don't think about big things only. Something, anything that you felt good, just make a note of it and your feelings.",
                    /*16*/    "Write about a quality time you spent today with your family that you felt good about.",
                    /*17*/    "Love yourself unconditionally Allow yourself to drop the conditions that you have created to love yourself. Let go of: “I will love myself better if I am…” and choose to accept yourself as you are. Write 3 reasons  you love yourself for.",
                    /*18*/    "Compliment yourself for a good habit you have. Write how the habit positively impacted you.",
                    /*19*/    "Think about 1 area you felt your talents or efforts are being utilised for others benefit, even though no one praised or appreciated, however small or big, during recent times and write a thankyou note on the ability you have for it.",
                    /*20*/    "Write a thank you for the capacities allowed by your health however restricted or healthy you may feel – like, you can climb stairs, can deal with rough weather conditions, can see with your eyes etc.",
                    /*21*/    "Write about one conversation you really enjoyed today with your family members or friends.Think what you enjoyed the most in it.",
                    /*22*/    "Write a letter to your spouse thanking for all the support however small or big.",
                    /*23*/    "Think about one mistake during your recent times or in the past when the pain or negative consequence or price to pay were too high. Forgive yourself for the mistake and write about one way you can be kinder to yourself right now.",
                    /*24*/    "What is one good thing you learned today? Don't think only about the big things. Just small and a minute one is also fine. Write how you felt about being able to learn too.",
                    /*25*/    "Write 3 reasons you love your kids or family members and say thanks for being there  by talking to them over phone or by face to face.",
                    /*26*/    "Think about someone who have hurt you. Write about what happened, how you felt in as many words as possible. Write till you vent all the pain and hurt.  Forgive, feel the calmness as you click  delete and allow the hurt  let go from your system.",
                    /*27*/    "Bless any of your family members or friends. Bless them peace, health and happiness.Write how you felt as you blessed them good health, happiness and peace.",
                    /*28*/    "Write 3 things that irritated you the most today. Vent through writing and delete. Let go of all the Irritation. Feel as the peace prevails.",
                    /*29*/    "Think about all the bills you are paying. School fees, Grocery, EMI etc..spend few moments. Write a thank you for each of the service you received from it and that you have funds to pay for it.",
                    /*30*/    "Write about a food item you ate recently that you loved and cherished. Try and remember why you loved it, how you enjoyed eating it.",
                    /*31*/    "Think back and write down five recent events that had an impact on you positively. Notice the positive emotions, learning or changes you notice because of them."
                };

                gratitude_size = content.length;

                if (position > content.length || position > day_no.length || position > imageId.length || position > action_type.length) {
                    position = 0;
                }

                gratitude_day_no = day_no[position];
                gratitude_content = content[position];
                gratitude_image = imageId[position];
                gratitude_type = action_type[position];
                dailyGratitude.setGratitude_content(gratitude_content);
                dailyGratitude.setGratitude_image(gratitude_image);
                dailyGratitude.setGratitude_day_no(gratitude_day_no);
                dailyGratitude.setGratitude_type(gratitude_type);
                dailyGratitude.setGratitude_size(gratitude_size);
                break;

        }
        return dailyGratitude;

    }


    public DailyGratitude getCoachImage(String primary, int position) {
        DailyGratitude dailyCoach = null;

        try {
            switch (primary) {

                case AppConstants.PROFILE_STUDENT:
                    dailyCoach = new DailyGratitude();
                    imageId = new int[]{
                              /*6*/   R.drawable.improve_self_discipline_six,
                            /*25*/  R.drawable.improve_body_awareness_twenty_five,
                    };

                    if (position > imageId.length) {
                        Log.d("eee", "index out of bound exception possibility occured");
                        gratitude_image = imageId[1];
                        dailyCoach.setGratitude_image(gratitude_image);
                    }
                    else {
                        gratitude_image = imageId[position];
                        dailyCoach.setGratitude_image(gratitude_image);
                    }
                    break;



                default:
                    dailyCoach = new DailyGratitude();
                    imageId = new int[]{
                           /*3*/   R.drawable.relax_at_a_place_in_deep_forest_three,
                            /*6*/   R.drawable.relaxation_through_light_six
                    };

                    if (position > imageId.length) {
                        Log.d("eee", "index out of bound exception possibility occured");
                        gratitude_image = imageId[1];
                        dailyCoach.setGratitude_image(gratitude_image);
                    }
                    else {
                        gratitude_image = imageId[position];
                        dailyCoach.setGratitude_image(gratitude_image);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dailyCoach;

    }
}
