package com.nsmiles.happybeingsdklib.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

public class MySql extends SQLiteOpenHelper {

    public static final String db = "mydb";
    public static final int version = 1;
    private SQLiteDatabase writeDb, readDb;
    private ContentValues cv;

    // Notification Table
    private final String NOTIFICATION_TABLE = "NotificationsTimings";
    private final String NOTIFICATION_ID = "_id";
    private final String NOTIFICATION_SYNC = "sync_flag";
    private final String NOTIFICATION_COACH_MORNING = "MIND_GYM_START_TIME";
    private final String NOTIFICATION_COACH_EVENING = "MIND_GYM_END_TIME";
    private final String NOTIFICATION_ID_RELAX_START = "RELAX_START_TIME";
    private final String NOTIFICATION_ID_RELAX_END = "RELAX_END_TIME";
    private final String NOTIFICATION_ID_HAPPY_MOMENT = "HAPPY_MOMENT_TIME";

    // Activity Table
    private final String ACTIVITY_DATA_TABLE = "New_Activity_Table";
    private final String ACTIVITY_ID = "_id";
    private final String ACTIVITY_USER_ID = "user_id";
    private final String ACTIVITY_DATE_TIME = "date_time";
    private final String ACTIVITY_DEVICE_ID = "device_id";
    private final String ACTIVITY_DEVICE_OS = "device_os";
    private final String ACTIVITY_FEATURE_NAME = "feature_name";
    private final String ACTIVITY_ACTIVITY_NAME = "activity_name";
    private final String ACTIVITY_ACTIVITY_DETAILS = "activity_detail";
    private final String ACTIVITY_START_TIME = "start_date_time";
    private final String ACTIVITY_END_TIME = "end_date_time";
    private final String ACTIVITY_EMAIL = "email";
    private final String ACTIVITY_SYNC = "sync_flag";

    // How are your feeling table
    private final String FEELING_TABLE_NAME = "New_Feelings_Table";
    private final String FEELING_ID = "_id";
    private final String FEELING_USER_ID = "user_id";
    private final String FEELING_DATE_TIME = "date_time";
    private final String FEELING_START_DATE = "start_date_time";
    private final String FEELING_END_DATE = "end_date_time";
    private final String FEELING_DEVICE_ID = "device_id";
    private final String FEELING_FEATURE = "feature";
    private final String FEELING_ACTIVITY = "activity";
    private final String FEELING_EMOTION = "emotion1";
    private final String FEELING_SYNC_FLAG = "sync_flag";
    private final String FEELING_EMAIL = "email";

    // Gratitude Table
    private static final String GRATITUDE_TABLE_NAME = "New_Gratitude_Table";
    private static final String GRATITUDE_ID = "_id";
    private static final String GRATITUDE_USER_ID = "user_id";
    private static final String GRATITUDE_DATE_TIME = "date_time";
    private static final String GRATITUDE_TYPE_OF_GRATITUDE = "type_of_gratitude";
    private static final String GRATITUDE_DESCRIPTION = "description";
    private static final String GRATITUDE_LINK = "link";
    private static final String GRATITUDE_PICTURE = "pic";
    private static final String GRATITUDE_TITLE = "title";
    private static final String GRATITUDE_EMAIL_ID = "email_id";
    private static final String GRATITUDE_EXPRESS_YOUR_GRATITUDE = "express_your_gratitude";
    private static final String GRATITUDE_SUBJECT = "subject";
    private static final String GRATITUDE_CREATED_AT = "createdAt";
    private static final String GRATITUDE_UPDATED_AT = "updatedAt";
    private static final String GRATITUDE_SERVER_ID = "id";
    private static final String GRATITUDE_SYNC_FLAG = "syncFlag";


    // My Coach Audio Entry Table
    private static final String MY_COACH_AUDIO_TABLE_NAME = "New_MyCoach_Audio_Table";
    private static final String MY_COACH_AUDIO_ID = "_id";
    private static final String MY_COACH_AUDIO_USER_ID = "user_id";
    private static final String MY_COACH_AUDIO_SERVER_ID = "server_id";
    private static final String MY_COACH_AUDIO_DATE_TIME = "date_time";
    private static final String MY_COACH_AUDIO_EMAIL = "email";
    private static final String MY_COACH_AUDIO_PRIMARY_PROFILE = "primary_profile";
    private static final String MY_COACH_AUDIO_MIND_GYM_TYPE = "mind_gym_type";
    private static final String MY_COACH_AUDIO_SECONDARY_PROFILE = "secondary_profile";
    private static final String MY_COACH_AUDIO_TITLE = "title";
    private static final String MY_COACH_AUDIO_DESCRIPTION = "description";
    private static final String MY_COACH_AUDIO_URL = "url";
    private static final String MY_COACH_AUDIO_COMPLETED_DAYS = "mycoach_completed_days";
    private static final String MY_COACH_AUDIO_COMPLETED_DATE = "mycoach_completed_date";
    private static final String MY_COACH_AUDIO_REPEAT = "repeat";
    private static final String MY_COACH_AUDIO_DURATION = "duration";
    private static final String MY_COACH_AUDIO_AUDIO_COUNT = "audio_count";
    private static final String MY_COACH_AUDIO_SYNC_FLAG = "sync_flag";

    // My Coach and Affirmation Table
    private static final String COACH_AFFIRMATION_TABLE_NAME = "New_MyCoach_Affirmation_Table";
    private static final String COACH_AFFIRMATION_ID = "_id";
    private static final String COACH_AFFIRMATION_USER_ID = "user_id";
    private static final String COACH_AFFIRMATION_SERVER_ID = "server_id";
    private static final String COACH_AFFIRMATION_DATE_TIME = "date_time";
    private static final String COACH_AFFIRMATION_EMAIL = "email";
    private static final String COACH_AFFIRMATION_PRIMARY_PROFILE = "primary_profile";
    private static final String COACH_AFFIRMATION_MIND_GYM_TYPE = "mind_gym_type";
    private static final String COACH_AFFIRMATION_SECONDARY_PROFILE = "secondary_profile";
    private static final String COACH_AFFIRMATION_TITLE = "title";
    private static final String COACH_AFFIRMATION_DESCRIPTION = "description";
    private static final String COACH_AFFIRMATION_URL = "url";
    private static final String COACH_AFFIRMATION_COMPLETED_DATE = "completed_date";
    private static final String COACH_AFFIRMATION_COACH_COMPLETED_DATE = "mycoach_completed_date";
    private static final String COACH_AFFIRMATION_SYNC_FLAG = "sync_flag";

    // Relax Alarm Notification Table
    private static final String RELAX_ALARM_NOTIFICATION_TABLE_NAME = "RelaxAlarmNotification";
    private static final String RELAX_ALARM_NOTIFICATION_ID = "_id";
    private static final String RELAX_ALARM_NOTIFICATION_RANGE = "alarm_range";
    private static final String RELAX_ALARM_NOTIFICATION_TIME = "alarm_time";
    private static final String RELAX_ALARM_NOTIFICATION_SYNC_FLAG = "sync_flag";

    // Relax and Relieve Audio Details
    private static final String RELAX_AUDIO_TABLE_NAME = "relax_audio";
    private static final String RELAX_AUDIO_ID = "_ID";
    private static final String RELAX_AUDIO_SYNC_FLAG = "SYNC_FLAG";
    private static final String RELAX_AUDIO_BASE_URL = "AUDIO_BASE_URL";
    private static final String RELAX_AUDIO_SUB_URL = "AUDIO_SUB_URL";
    private static final String RELAX_AUDIO_CREATED_AT = "CREATED_AT";
    private static final String RELAX_AUDIO_UPDATED_AT = "UPDATED_AT";
    private static final String RELAX_AUDIO_TITLE = "AUDIO_TITLE";
    private static final String RELAX_AUDIO_DESCRIPTION = "AUDIO_DESCRIPTION";
    private static final String RELAX_AUDIO_DOWNLOAD_STATUS = "DOWNLOAD_STATUS";
    private static final String RELAX_AUDIO_SD_CARD_PATH = "SD_CARD_PATH";
    private static final String RELAX_AUDIO_DRAWABLE_IMAGE = "DRAWABLE_IMAGE";
    private static final String RELAX_AUDIO_FILE_TYPE = "FILE_TYPE";
    private static final String RELAX_AUDIO_DURATION = "DURATION";
    private static final String RELAX_AUDIO_DAY_NUMBER = "DAY_NUMBER";
    private static final String RELAX_AUDIO_AUDIO_NUMBER = "AUDIO_NUMBER";

    // Student Coach Audio Details
    private static final String STUDENT_COACH_AUDIO_TABLE_NAME = "coach_audio_student_job";
    private static final String STUDENT_COACH_AUDIO_ID = "_ID";
    private static final String STUDENT_COACH_AUDIO_SYNC_FLAG = "SYNC_FLAG";
    private static final String STUDENT_COACH_AUDIO_BASE_URL = "AUDIO_BASE_URL";
    private static final String STUDENT_COACH_AUDIO_SUB_URL = "AUDIO_SUB_URL";
    private static final String STUDENT_COACH_AUDIO_CREATED_AT = "CREATED_AT";
    private static final String STUDENT_COACH_AUDIO_UPDATED_AT = "UPDATED_AT";
    private static final String STUDENT_COACH_AUDIO_TITLE = "AUDIO_TITLE";
    private static final String STUDENT_COACH_AUDIO_DESCRIPTION = "AUDIO_DESCRIPTION";
    private static final String STUDENT_COACH_AUDIO_DOWNLOAD_STATUS = "DOWNLOAD_STATUS";
    private static final String STUDENT_COACH_AUDIO_SD_CARD_PATH = "SD_CARD_PATH";
    private static final String STUDENT_COACH_AUDIO_FILE_TYPE = "FILE_TYPE";
    private static final String STUDENT_COACH_AUDIO_DAY_NUMBER = "DAY_NUMBER";
    private static final String STUDENT_COACH_AUDIO_DURATION = "DURATION";
    private static final String STUDENT_COACH_AUDIO_NUMBER = "AUDIO_NUMBER";

    // Others Coach Audio Details
    private static final String OTHERS_COACH_AUDIO_TABLE_NAME = "coach_audio_all_other";
    private static final String OTHERS_COACH_AUDIO_ID = "_ID";
    private static final String OTHERS_COACH_AUDIO_SYNC_FLAG = "SYNC_FLAG";
    private static final String OTHERS_COACH_AUDIO_SUB_URL = "AUDIO_SUB_URL";
    private static final String OTHERS_COACH_AUDIO_CREATED_AT = "CREATED_AT";
    private static final String OTHERS_COACH_AUDIO_BASE_URL = "AUDIO_BASE_URL";
    private static final String OTHERS_COACH_AUDIO_UPDATED_AT = "UPDATED_AT";
    private static final String OTHERS_COACH_AUDIO_TITLE = "AUDIO_TITLE";
    private static final String OTHERS_COACH_AUDIO_DESCRIPTION = "AUDIO_DESCRIPTION";
    private static final String OTHERS_COACH_AUDIO_DOWNLOAD_STATUS = "DOWNLOAD_STATUS";
    private static final String OTHERS_COACH_AUDIO_SD_CARD_PATH = "SD_CARD_PATH";
    private static final String OTHERS_COACH_AUDIO_FILE_TYPE = "FILE_TYPE";
    private static final String OTHERS_COACH_AUDIO_DAY_NUMBER = "DAY_NUMBER";
    private static final String OTHERS_COACH_AUDIO_DURATION = "DURATION";
    private static final String OTHERS_COACH_AUDIO_NUMBER = "AUDIO_NUMBER";

    // Relax Favourite
    private static final String RELAX_FAVOURITE_TABLE_NAME = "relax_favourite";
    private static final String RELAX_FAVOURITE_ID = "_ID";
    private static final String RELAX_FAVOURITE_SYNC_FLAG = "SYNC_FLAG";
    private static final String RELAX_FAVOURITE_EMAIL = "EMAIL";
    private static final String RELAX_FAVOURITE_USER_ID = "USER_ID";
    private static final String RELAX_FAVOURITE_PRIMARY_PROFILE = "PRIMARY_PROFILE";
    private static final String RELAX_FAVOURITE_SECONDARY_PROFILE = "SECONDARY_PROFILE";
    private static final String RELAX_FAVOURITE_FAVOURITE = "FAVOURITE";
    private static final String RELAX_FAVOURITE_BASE_URL = "AUDIO_BASE_URL";
    private static final String RELAX_FAVOURITE_SUB_URL = "AUDIO_SUB_URL";
    private static final String RELAX_FAVOURITE_CREATED_AT = "CREATED_AT";
    private static final String RELAX_FAVOURITE_UPDATED_AT = "UPDATED_AT";
    private static final String RELAX_FAVOURITE_TITLE = "AUDIO_TITLE";
    private static final String RELAX_FAVOURITE_DESCRIPTION = "AUDIO_DESCRIPTION";
    private static final String RELAX_FAVOURITE_DOWNLOAD_STATUS = "DOWNLOAD_STATUS";
    private static final String RELAX_FAVOURITE_SD_CARD_PATH = "SD_CARD_PATH";
    private static final String RELAX_FAVOURITE_DRAWABLE_IMAGE = "DRAWABLE_IMAGE";
    private static final String RELAX_FAVOURITE_FILE_TYPE = "FILE_TYPE";
    private static final String RELAX_FAVOURITE_DURATION = "DURATION";
    private static final String RELAX_FAVOURITE_NUMBER = "AUDIO_NUMBER";

    // Relax Coach Student Audio Table
    private static final String RELAX_COACH_STUDENT_TABLE_NAME = "relax_coach_student_job";
    private static final String RELAX_COACH_STUDENT_ID = "_ID";
    private static final String RELAX_COACH_STUDENT_SYNC_FLAG = "SYNC_FLAG";
    private static final String RELAX_COACH_STUDENT_BASE_URL = "AUDIO_BASE_URL";
    private static final String RELAX_COACH_STUDENT_SUB_URL = "AUDIO_SUB_URL";
    private static final String RELAX_COACH_STUDENT_CREATED_AT = "CREATED_AT";
    private static final String RELAX_COACH_STUDENT_UPDATED_AT = "UPDATED_AT";
    private static final String RELAX_COACH_STUDENT_TITLE = "AUDIO_TITLE";
    private static final String RELAX_COACH_STUDENT_DESCRIPTION = "AUDIO_DESCRIPTION";
    private static final String RELAX_COACH_STUDENT_DOWNLOAD_STATUS = "DOWNLOAD_STATUS";
    private static final String RELAX_COACH_STUDENT_SD_CARD_PATH = "SD_CARD_PATH";
    private static final String RELAX_COACH_STUDENT_FILE_TYPE = "FILE_TYPE";
    private static final String RELAX_COACH_STUDENT_DAY_NUMBER = "DAY_NUMBER";
    private static final String RELAX_COACH_STUDENT_DURATION = "DURATION";
    private static final String RELAX_COACH_STUDENT_AUDIO_NUMBER = "AUDIO_NUMBER";

    // Othres Coach Audio Table
    private static final String OTHERS_AUDIO_TABLE_NAME = "relax_coach_others";
    private static final String OTHERS_AUDIO_ID = "_ID";
    private static final String OTHERS_AUDIO_SYNC_FLAG = "SYNC_FLAG";
    private static final String OTHERS_AUDIO_BASE_URL = "AUDIO_BASE_URL";
    private static final String OTHERS_AUDIO_SUB_URL = "AUDIO_SUB_URL";
    private static final String OTHERS_AUDIO_CREATED_AT = "CREATED_AT";
    private static final String OTHERS_AUDIO_UPDATED_AT = "UPDATED_AT";
    private static final String OTHERS_AUDIO_TITLE = "AUDIO_TITLE";
    private static final String OTHERS_AUDIO_DESCRIPTION = "AUDIO_DESCRIPTION";
    private static final String OTHERS_AUDIO_DOWNLOAD_STATUS = "DOWNLOAD_STATUS";
    private static final String OTHERS_AUDIO_SD_CARD_PATH = "SD_CARD_PATH";
    private static final String OTHERS_AUDIO_FILE_TYPE = "FILE_TYPE";
    private static final String OTHERS_AUDIO_DAY_NUMBER = "DAY_NUMBER";
    private static final String OTHERS_AUDIO_DURATION = "DURATION";
    private static final String OTHERS_AUDIO_NUMBER = "AUDIO_NUMBER";

    // Coach Offline
    private static final String COACH_OFFLINE_TABLE_NAME = "coach_offline";
    private static final String COACH_OFFLINE_ID = "_ID";
    private static final String COACH_OFFLINE_SYNC_FLAG = "SYNC_FLAG";
    private static final String COACH_OFFLINE_DATE_DIFFERENCE = "DATE_DIFFERENCE";
    private static final String COACH_OFFLINE_ACTUAL_SERVER_DATE = "ACTUAL_SERVER_DATE";
    private static final String COACH_OFFLINE_DD_DATE = "DD_DATE";
    private static final String COACH_OFFLINE_MM_MONTH = "MM_MONTH";
    private static final String COACH_OFFLINE_PLAY_STATUS = "PLAY_STATUS";
    private static final String COACH_OFFLINE_DATE_SYNCED = "DATA_SYNCED";
    private static final String COACH_OFFLINE_YYYY_MM_DD_DATE = "YYYY_MM_DD_DATE";
    private static final String COACH_OFFLINE_CURRENT_DATE = "CURRENT_DATEE";
    private static final String COACH_OFFLINE_PAYMENT_STATUS = "PAYMENT_STATUS";
    private static final String COACH_OFFLINE_FEEDBACK_POPUP = "FEEDBACK_POPUP";
    private static final String COACH_OFFLINE_OFFER_40_POPUP = "OFFER_40_POPUP";
    private static final String COACH_OFFLINE_OFFER_20_POPUP = "OFFER_20_POPUP";


    // Corporate Wellbeing
    /*private static final String CORPORATE_WELLBEING_TABLE_NAME = "corporate_well_being";
    private static final String CORPORATE_WELLBEING_ID = "id";
    private static final String CORPORATE_WELLBEING_USER_ID = "user_id";
    private static final String CORPORATE_WELLBEING_VERSION = "version";
    private static final String CORPORATE_WELLBEING_CATEGORY = "category";
    private static final String CORPORATE_WELLBEING_QUESTION_NO = "question_no";
    private static final String CORPORATE_WELLBEING_USER_ROLE = "user_role";
    private static final String CORPORATE_WELLBEING_OPTION = "option";
    private static final String CORPORATE_WELLBEING_VALUE = "value";
    private static final String CORPORATE_WELLBEING_QUESTION_TYPE = "question_type";
    private static final String CORPORATE_WELLBEING_TEXT_OPTION = "text_option";
    private static final String CORPORATE_WELLBEING_TEXT_VALUE = "text_value";*/

    public MySql(Context context, String name, CursorFactory factory, int version) {

        super(context, name, factory, version);
        System.out.println("db created successfully");
        Log.i(getClass().getSimpleName(), "mydb created successfully");
        // TODO Auto-generated constructor stub
    }


    /*From application provider passing the context, Database Name and Version Number*/
    public MySql(Context context,
                 String dbName,
                 Integer version) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {


        db.execSQL("CREATE TABLE IF NOT EXISTS Splash_screen_images(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "IMAGE_NUMBER TEXT, " +
                "DOWNLOAD_STATUS TEXT, " +
                "SD_CARD_PATH TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Affirmation_Data(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "AFFIRMATION_DATE_NUMBER INTEGER, " +
                "AFFIRMATION_DAY INTEGER)");

    /*How are your feeling table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + FEELING_TABLE_NAME + "("
                        + FEELING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FEELING_USER_ID + " TEXT, "
                        + FEELING_DATE_TIME + " DATE, "
                        + FEELING_START_DATE + " TEXT, "
                        + FEELING_END_DATE + " TEXT, "
                        + FEELING_DEVICE_ID + " TEXT, "
                        + FEELING_FEATURE + " TEXT, "
                        + FEELING_ACTIVITY + " TEXT, "
                        + FEELING_EMOTION + " TEXT, "
                        + FEELING_SYNC_FLAG + " TEXT, "
                        + FEELING_EMAIL + " TEXT" + ")");
        /*How are your feeling table*/

        /*Activity MultipleReportData Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + ACTIVITY_DATA_TABLE + "("
                        + ACTIVITY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ACTIVITY_SYNC + " TEXT, "
                        + ACTIVITY_USER_ID + " TEXT, "
                        + ACTIVITY_DATE_TIME + " DATE, "
                        + ACTIVITY_DEVICE_ID + " TEXT, "
                        + ACTIVITY_DEVICE_OS + " TEXT, "
                        + ACTIVITY_FEATURE_NAME + " TEXT, "
                        + ACTIVITY_ACTIVITY_NAME + " TEXT, "
                        + ACTIVITY_ACTIVITY_DETAILS + " TEXT, "
                        + ACTIVITY_START_TIME + " TEXT, "
                        + ACTIVITY_END_TIME + " TEXT, "
                        + ACTIVITY_EMAIL + " TEXT" + ")");
        /*Activity MultipleReportData Table*/

        /*Notification Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + NOTIFICATION_TABLE + "("
                        + NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + NOTIFICATION_SYNC + " TEXT, "
                        + NOTIFICATION_COACH_MORNING + " TEXT, "
                        + NOTIFICATION_COACH_EVENING + " TEXT, "
                        + NOTIFICATION_ID_RELAX_START + " TEXT, "
                        + NOTIFICATION_ID_RELAX_END + " TEXT, "
                        + NOTIFICATION_ID_HAPPY_MOMENT + " TEXT" + ")");
        /*Notification Table*/

        /*Gratitude Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + GRATITUDE_TABLE_NAME + "("
                        + GRATITUDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + GRATITUDE_USER_ID + " TEXT, "
                        + GRATITUDE_DATE_TIME + " DATE, "
                        + GRATITUDE_TYPE_OF_GRATITUDE + " TEXT, "
                        + GRATITUDE_DESCRIPTION + " TEXT, "
                        + GRATITUDE_LINK + " TEXT, "
                        + GRATITUDE_PICTURE + " BLOB, "
                        + GRATITUDE_TITLE + " TEXT, "
                        + GRATITUDE_EMAIL_ID + " TEXT, "
                        + GRATITUDE_EXPRESS_YOUR_GRATITUDE + " TEXT, "
                        + GRATITUDE_SUBJECT + " TEXT, "
                        + GRATITUDE_CREATED_AT + " TEXT, "
                        + GRATITUDE_UPDATED_AT + " TEXT, "
                        + GRATITUDE_SERVER_ID + " TEXT, "
                        + GRATITUDE_SYNC_FLAG + " TEXT" + ")");
        /*Gratitude Table*/

        /*My Coach Audio Entry Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + MY_COACH_AUDIO_TABLE_NAME + "("
                        + MY_COACH_AUDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + MY_COACH_AUDIO_USER_ID + " TEXT, "
                        + MY_COACH_AUDIO_SERVER_ID + " TEXT, "
                        + MY_COACH_AUDIO_DATE_TIME + " TEXT, "
                        + MY_COACH_AUDIO_EMAIL + " TEXT, "
                        + MY_COACH_AUDIO_PRIMARY_PROFILE + " TEXT, "
                        + MY_COACH_AUDIO_MIND_GYM_TYPE + " TEXT, "
                        + MY_COACH_AUDIO_SECONDARY_PROFILE + " TEXT, "
                        + MY_COACH_AUDIO_TITLE + " TEXT, "
                        + MY_COACH_AUDIO_DESCRIPTION + " TEXT, "
                        + MY_COACH_AUDIO_URL + " TEXT, "
                        + MY_COACH_AUDIO_COMPLETED_DAYS + " TEXT, "
                        + MY_COACH_AUDIO_COMPLETED_DATE + " TEXT, "
                        + MY_COACH_AUDIO_REPEAT + " TEXT, "
                        + MY_COACH_AUDIO_DURATION + " TEXT, "
                        + MY_COACH_AUDIO_AUDIO_COUNT + " TEXT, "
                        + MY_COACH_AUDIO_SYNC_FLAG + " TEXT" + ")");
        /*My Coach Audio Entry Table*/

        /*My Coach and Affirmation Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + COACH_AFFIRMATION_TABLE_NAME + "("
                        + COACH_AFFIRMATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COACH_AFFIRMATION_USER_ID + " TEXT, "
                        + COACH_AFFIRMATION_SERVER_ID + " TEXT, "
                        + COACH_AFFIRMATION_DATE_TIME + " TEXT, "
                        + COACH_AFFIRMATION_EMAIL + " TEXT, "
                        + COACH_AFFIRMATION_PRIMARY_PROFILE + " TEXT, "
                        + COACH_AFFIRMATION_MIND_GYM_TYPE + " TEXT, "
                        + COACH_AFFIRMATION_SECONDARY_PROFILE + " TEXT, "
                        + COACH_AFFIRMATION_TITLE + " TEXT, "
                        + COACH_AFFIRMATION_DESCRIPTION + " TEXT, "
                        + COACH_AFFIRMATION_URL + " TEXT, "
                        + COACH_AFFIRMATION_COMPLETED_DATE + " TEXT, "
                        + COACH_AFFIRMATION_COACH_COMPLETED_DATE + " TEXT, "
                        + COACH_AFFIRMATION_SYNC_FLAG + " TEXT" + ")");
        /*My Coach and Affirmation Table*/

        /*Relax Notification Alarm Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + RELAX_ALARM_NOTIFICATION_TABLE_NAME + "("
                        + RELAX_ALARM_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + RELAX_ALARM_NOTIFICATION_RANGE + " INTEGER, "
                        + RELAX_ALARM_NOTIFICATION_TIME + " INTEGER, "
                        + RELAX_ALARM_NOTIFICATION_SYNC_FLAG + " TEXT" + ")");
        /*Relax Notification Alarm Table*/

        /*Relax and Relieve Audio Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + RELAX_AUDIO_TABLE_NAME + "("
                        + RELAX_AUDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + RELAX_AUDIO_SYNC_FLAG + " TEXT, "
                        + RELAX_AUDIO_BASE_URL + " TEXT, "
                        + RELAX_AUDIO_SUB_URL + " TEXT, "
                        + RELAX_AUDIO_CREATED_AT + " TEXT, "
                        + RELAX_AUDIO_UPDATED_AT + " TEXT, "
                        + RELAX_AUDIO_TITLE + " TEXT, "
                        + RELAX_AUDIO_DESCRIPTION + " TEXT, "
                        + RELAX_AUDIO_DOWNLOAD_STATUS + " TEXT, "
                        + RELAX_AUDIO_SD_CARD_PATH + " TEXT, "
                        + RELAX_AUDIO_DRAWABLE_IMAGE + " INTEGER, "
                        + RELAX_AUDIO_FILE_TYPE + " TEXT, "
                        + RELAX_AUDIO_DURATION + " TEXT, "
                        + RELAX_AUDIO_DAY_NUMBER + " TEXT, "
                        + RELAX_AUDIO_AUDIO_NUMBER + " TEXT" + ")");
        /*Relax and Relieve Audio Table*/


        /*Relax and Relieve Student Looking For Job Audio Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + STUDENT_COACH_AUDIO_TABLE_NAME + "("
                        + STUDENT_COACH_AUDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + STUDENT_COACH_AUDIO_SYNC_FLAG + " TEXT, "
                        + STUDENT_COACH_AUDIO_BASE_URL + " TEXT, "
                        + STUDENT_COACH_AUDIO_SUB_URL + " TEXT, "
                        + STUDENT_COACH_AUDIO_CREATED_AT + " TEXT, "
                        + STUDENT_COACH_AUDIO_UPDATED_AT + " TEXT, "
                        + STUDENT_COACH_AUDIO_TITLE + " TEXT, "
                        + STUDENT_COACH_AUDIO_DESCRIPTION + " TEXT, "
                        + STUDENT_COACH_AUDIO_DOWNLOAD_STATUS + " TEXT, "
                        + STUDENT_COACH_AUDIO_SD_CARD_PATH + " TEXT, "
                        + STUDENT_COACH_AUDIO_FILE_TYPE + " INTEGER, "
                        + STUDENT_COACH_AUDIO_DAY_NUMBER + " TEXT, "
                        + STUDENT_COACH_AUDIO_DURATION + " TEXT, "
                        + STUDENT_COACH_AUDIO_NUMBER + " TEXT" + ")");
        /*Relax and Relieve Student Looking For Job Audio Table*/



        /*Relax and Relieve Others All Profile Audio Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + OTHERS_COACH_AUDIO_TABLE_NAME + "("
                        + OTHERS_COACH_AUDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + OTHERS_COACH_AUDIO_SYNC_FLAG + " TEXT, "
                        + OTHERS_COACH_AUDIO_BASE_URL + " TEXT, "
                        + OTHERS_COACH_AUDIO_SUB_URL + " TEXT, "
                        + OTHERS_COACH_AUDIO_CREATED_AT + " TEXT, "
                        + OTHERS_COACH_AUDIO_UPDATED_AT + " TEXT, "
                        + OTHERS_COACH_AUDIO_TITLE + " TEXT, "
                        + OTHERS_COACH_AUDIO_DESCRIPTION + " TEXT, "
                        + OTHERS_COACH_AUDIO_DOWNLOAD_STATUS + " TEXT, "
                        + OTHERS_COACH_AUDIO_SD_CARD_PATH + " TEXT, "
                        + OTHERS_COACH_AUDIO_FILE_TYPE + " INTEGER, "
                        + OTHERS_COACH_AUDIO_DAY_NUMBER + " TEXT, "
                        + OTHERS_COACH_AUDIO_DURATION + " TEXT, "
                        + OTHERS_COACH_AUDIO_NUMBER + " TEXT" + ")");
        /*Relax and Relieve Others All Profile Audio Table*/




        /*Favourite  Table*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + RELAX_FAVOURITE_TABLE_NAME + "("
                        + RELAX_FAVOURITE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + RELAX_FAVOURITE_SYNC_FLAG + " TEXT, "
                        + RELAX_FAVOURITE_EMAIL + " TEXT, "
                        + RELAX_FAVOURITE_USER_ID + " TEXT, "
                        + RELAX_FAVOURITE_PRIMARY_PROFILE + " TEXT, "
                        + RELAX_FAVOURITE_SECONDARY_PROFILE + " TEXT, "
                        + RELAX_FAVOURITE_FAVOURITE + " TEXT, "
                        + RELAX_FAVOURITE_BASE_URL + " TEXT, "
                        + RELAX_FAVOURITE_SUB_URL + " TEXT, "
                        + RELAX_FAVOURITE_CREATED_AT + " TEXT, "
                        + RELAX_FAVOURITE_UPDATED_AT + " TEXT, "
                        + RELAX_FAVOURITE_TITLE + " TEXT, "
                        + RELAX_FAVOURITE_DESCRIPTION + " TEXT, "
                        + RELAX_FAVOURITE_DOWNLOAD_STATUS + " TEXT, "
                        + RELAX_FAVOURITE_SD_CARD_PATH + " TEXT, "
                        + RELAX_FAVOURITE_DRAWABLE_IMAGE + " INTEGER, "
                        + RELAX_FAVOURITE_FILE_TYPE + " TEXT, "
                        + RELAX_FAVOURITE_DURATION + " TEXT, "
                        + RELAX_FAVOURITE_NUMBER + " TEXT" + ")");


    /*40 day student audio*/
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + RELAX_COACH_STUDENT_TABLE_NAME + "("
                        + RELAX_COACH_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + RELAX_COACH_STUDENT_SYNC_FLAG + " TEXT, "
                        + RELAX_COACH_STUDENT_BASE_URL + " TEXT, "
                        + RELAX_COACH_STUDENT_SUB_URL + " TEXT, "
                        + RELAX_COACH_STUDENT_CREATED_AT + " TEXT, "
                        + RELAX_COACH_STUDENT_UPDATED_AT + " TEXT, "
                        + RELAX_COACH_STUDENT_TITLE + " TEXT, "
                        + RELAX_COACH_STUDENT_DESCRIPTION + " TEXT, "
                        + RELAX_COACH_STUDENT_DOWNLOAD_STATUS + " TEXT, "
                        + RELAX_COACH_STUDENT_SD_CARD_PATH + " TEXT, "
                        + RELAX_COACH_STUDENT_FILE_TYPE + " TEXT, "
                        + RELAX_COACH_STUDENT_DAY_NUMBER + " TEXT, "
                        + RELAX_COACH_STUDENT_DURATION + " TEXT, "
                        + RELAX_COACH_STUDENT_AUDIO_NUMBER + " TEXT" + ")");

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + OTHERS_AUDIO_TABLE_NAME + "("
                        + OTHERS_AUDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + OTHERS_AUDIO_SYNC_FLAG + " TEXT, "
                        + OTHERS_AUDIO_BASE_URL + " TEXT, "
                        + OTHERS_AUDIO_SUB_URL + " TEXT, "
                        + OTHERS_AUDIO_CREATED_AT + " TEXT, "
                        + OTHERS_AUDIO_UPDATED_AT + " TEXT, "
                        + OTHERS_AUDIO_TITLE + " TEXT, "
                        + OTHERS_AUDIO_DESCRIPTION + " TEXT, "
                        + OTHERS_AUDIO_DOWNLOAD_STATUS + " TEXT, "
                        + OTHERS_AUDIO_SD_CARD_PATH + " TEXT, "
                        + OTHERS_AUDIO_FILE_TYPE + " TEXT, "
                        + OTHERS_AUDIO_DAY_NUMBER + " TEXT, "
                        + OTHERS_AUDIO_DURATION + " TEXT, "
                        + OTHERS_AUDIO_NUMBER + " TEXT" + ")");
        /*40 day others audio*/

        /* Coach Offline MultipleReportData Storage */
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + COACH_OFFLINE_TABLE_NAME + "("
                        + COACH_OFFLINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COACH_OFFLINE_SYNC_FLAG + " TEXT, "
                        + COACH_OFFLINE_DATE_DIFFERENCE + " TEXT, "
                        + COACH_OFFLINE_ACTUAL_SERVER_DATE + " TEXT, "
                        + COACH_OFFLINE_DD_DATE + " TEXT, "
                        + COACH_OFFLINE_MM_MONTH + " TEXT, "
                        + COACH_OFFLINE_PLAY_STATUS + " TEXT, "
                        + COACH_OFFLINE_DATE_SYNCED + " TEXT, "
                        + COACH_OFFLINE_YYYY_MM_DD_DATE + " TEXT, "
                        + COACH_OFFLINE_CURRENT_DATE + " TEXT, "
                        + COACH_OFFLINE_PAYMENT_STATUS + " TEXT, "
                        + COACH_OFFLINE_FEEDBACK_POPUP + " TEXT, "
                        + COACH_OFFLINE_OFFER_20_POPUP + " TEXT, "
                        + COACH_OFFLINE_OFFER_40_POPUP + " TEXT" + ")");
        /* Coach Offline MultipleReportData Storage */

        /*Corporate Well Being Assessment Table*/
        /*db.execSQL(
                "CREATE TABLE IF NOT EXISTS "
                        + CORPORATE_WELLBEING_TABLE_NAME + "("
                        + CORPORATE_WELLBEING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + CORPORATE_WELLBEING_USER_ID + " TEXT, "
                        + CORPORATE_WELLBEING_VERSION + " TEXT, "
                        + CORPORATE_WELLBEING_CATEGORY + " TEXT, "
                        + CORPORATE_WELLBEING_QUESTION_NO + " TEXT, "
                        + CORPORATE_WELLBEING_USER_ROLE + " TEXT, "
                        + CORPORATE_WELLBEING_OPTION + " TEXT, "
                        + CORPORATE_WELLBEING_VALUE + " TEXT, "
                        + CORPORATE_WELLBEING_QUESTION_TYPE + " TEXT, "
                        + CORPORATE_WELLBEING_TEXT_VALUE + " TEXT, "
                        + CORPORATE_WELLBEING_TEXT_OPTION + " TEXT" + ")");*/

        /*Corporate Well Being Assessment Table*/

        /********************************/

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_category_array_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_score TEXT," +
                " category_array_value TEXT)");


        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_category_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " category_common_id TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_id TEXT," +
                " category_score TEXT," +
                " category_value TEXT)");


        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_define_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " define_value TEXT)");

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_week_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_start_date_milliseconds INTEGER," +
                " week_end_date_milliseconds INTEGER," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " week_number TEXT," +
                " no_of_week TEXT," +
                " total_days TEXT," +
                " category_name TEXT," +
                " table_name_id TEXT)");

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_burning_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " burning_value TEXT)");

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_obstacles_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " obstacles_selection_value TEXT," +
                " obstacles_value TEXT)");

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_skills_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " skill_value TEXT)");


        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_image_emotion_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " image_uri TEXT," +
                " image_path TEXT)");

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_motivation_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " motivation_value TEXT)");

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_people_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " name_of_the_person TEXT," +
                " emotional_support TEXT," +
                " take_action TEXT)");

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_achieve_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " achieve_value_one TEXT," +
                " achieve_value_two TEXT)");

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_most_important_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_no TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " most_important TEXT)");

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_task_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " week_number TEXT," +
                " no_of_week TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " total_days TEXT," +
                " table_name_id TEXT," +
                " category_name TEXT," +
                " task_position TEXT," +
                " task_value TEXT," +
                " task_status TEXT)");

        //*Goal Alarm Table
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_alarm_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " day_name TEXT," +
                " week_name TEXT," +
                " category_name TEXT," +
                " reporting_or_remind TEXT," +
                " alarm_on_or_off TEXT," +
                " time_milliseconds INTEGER," +
                " total_remind_days TEXT)");


        //*Goal Alarm Table
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_daily_alarm_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " day_name TEXT," +
                " day_status TEXT," +
                " week_name TEXT," +
                " category_name TEXT," +
                " reporting_or_remind TEXT," +
                " alarm_on_or_off TEXT," +
                " time_milliseconds INTEGER," +
                " total_remind_days TEXT)");

        // Goals Sync Table

        //*Goal table creation on version above 4.5 version code 230 Will published in next version*//**//*
        db.execSQL("CREATE TABLE IF NOT EXISTS goal_sync_table " +
                "(auto_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " sync_flag TEXT," +
                " created_at TEXT," +
                " updated_at TEXT," +
                " user_id TEXT," +
                " user_email TEXT," +
                " user_name TEXT," +
                " primary_profile TEXT," +
                " secondary_profile TEXT," +
                " category_name TEXT," +
                " task_value TEXT," +
                " task_status TEXT," +
                " task_header TEXT," +
                " task_result TEXT," +
                " task_created_at TEXT," +
                " task_updated_at TEXT," +
                " week_start_date TEXT," +
                " week_end_date TEXT," +
                " week_number TEXT," +
                " no_of_week TEXT," +
                " total_days TEXT," +
                " define_text TEXT," +
                " burning_text TEXT," +
                " obstacles_text TEXT," +
                " image_value BLOB," +
                " skill_text TEXT," +
                " motivation_text TEXT," +
                " name_of_the_person TEXT," +
                " emotional_support TEXT," +
                " take_action TEXT," +
                " achieve_text_one TEXT," +
                " achieve_text_two TEXT," +
                " most_important TEXT," +
                " common_id TEXT)");

   /*Relax Alarm Slider Create at the version of 4.5   230 and version 7  will go live on next version*/
        db.execSQL("CREATE TABLE IF NOT EXISTS well_being_assess(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " SYNC_FLAG TEXT," +
                " email TEXT," +
                " user_id TEXT," +
                " primary_profile TEXT," +
                " description TEXT," +
                " subcontent TEXT," +
                " score TEXT," +
                " range TEXT," +
                " ip_title TEXT," +
                " ip_message TEXT," +
                " range_percentage TEXT," +
                " range_percentage_max TEXT," +
                " report_title TEXT," +
                " report_content TEXT," +
                " report_ip_title TEXT," +
                " report_ip_content TEXT," +
                " version TEXT," +
                " wellbeing_development_plan TEXT," +
                " wellbeingSubPlan TEXT," +
                " version_sub TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Assessments_Table(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ASSESSMENT_TYPE TEXT, " +
                "QUESTION_LIST TEXT," +
                " ASSESSMENT_INSTRUCTION TEXT," +
                "ASSESSMENT_COMPLETED INTEGER," +
                "ASSESSMENT_VERSION INTEGER)" );
        db.execSQL("CREATE TABLE IF NOT EXISTS Assessment_Tiles(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TILES_LIST TEXT, " +
                "REPORT_NAME TEXT, " +
                "REPORT_CATEGORY TEXT, " +
                "REPORT_VERSION INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Assessment_Answers_Table (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ASSESSMENT_NAME TEXT, " +
                "ANSWER_LIST TEXT, " +
                "UPLOADED_STATUS INTEGER, " +
                "SERVICE_OBJECT TEXT, " +
                "TOKEN TEXT, " +
                "ASSESSMENT_SUB_TYPE TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Affirmations_Audio (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "AFFIRMATION_AUDIO_PATH TEXT, " +
                "AUDIO_EXISTS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Current version is gretter than oldest version");

        if (oldVersion < newVersion) {
            db.execSQL("CREATE TABLE IF NOT EXISTS Splash_screen_images(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "IMAGE_NUMBER INTEGER, " +
                    "DOWNLOAD_STATUS TEXT, " +
                    "SD_CARD_PATH TEXT)");

            db.execSQL("CREATE TABLE IF NOT EXISTS Affirmation_Data(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "AFFIRMATION_DATE_NUMBER INTEGER, " +
                    "AFFIRMATION_DAY INTEGER)");

            /* check the drop table */

            // Relax table .... if any data is changed drop and recreate, data loss will not occur. just deleting the details.
            db.execSQL("DROP TABLE IF EXISTS relax_audio");
            db.execSQL("DROP TABLE IF EXISTS coach_audio_all_other");
            db.execSQL("DROP TABLE IF EXISTS coach_audio_student_job");

            db.execSQL("CREATE TABLE IF NOT EXISTS Assessments_Table(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ASSESSMENT_TYPE TEXT, " +
                    "QUESTION_LIST TEXT, " +
                    " ASSESSMENT_INSTRUCTION TEXT," +
                    "ASSESSMENT_COMPLETED INTEGER," +
                    "ASSESSMENT_VERSION INTEGER)" );
            db.execSQL("CREATE TABLE IF NOT EXISTS Assessment_Tiles(_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TILES_LIST TEXT, " +
                    "REPORT_NAME TEXT, " +
                    "REPORT_CATEGORY TEXT, " +
                    "REPORT_VERSION INTEGER)");
            db.execSQL("CREATE TABLE IF NOT EXISTS Assessment_Answers_Table (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ASSESSMENT_NAME TEXT, " +
                    "ANSWER_LIST TEXT, " +
                    "UPLOADED_STATUS INTEGER, " +
                    "SERVICE_OBJECT TEXT, " +
                    "TOKEN TEXT, " +
                    "ASSESSMENT_SUB_TYPE TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS Affirmations_Audio (_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "AFFIRMATION_AUDIO_PATH TEXT, " +
                    "AUDIO_EXISTS INTEGER)");

            //   db.execSQL("ALTER TABLE NotificationsTimings ADD COLUMN sync_flag TEXT");  //  sync_flag TEXT
            // ALTER TABLE foo RENAME TO bar
            /// old 25  new 26
            for (int i = oldVersion; i < newVersion; i++) {

                switch (i + 1) {
                    case 26:
                        db.execSQL("DROP TABLE IF EXISTS New_Gratitude_Table");
                        db.execSQL("ALTER TABLE goal_sync_table ADD COLUMN image_value BLOB");  // db current version 25 update to 27   " image_value BLOB," +
                        break;

                    //case 27:
                    // db.execSQL("ALTER TABLE goal_sync_table ADD COLUMN image_valuefd BLOB");  // db current version 25 update to 27   " image_value BLOB," +
                    //break;

                    case 33:
                        db.execSQL("ALTER TABLE NotificationsTimings ADD COLUMN HAPPY_MOMENT_TIME TEXT");  // db current version 32 update to 33


                        Calendar set_calender = Calendar.getInstance();
                        long mindGymAffirmationsTime = 0, happyMomentTime = 0;
                        int _id;
                        ContentValues cv = new ContentValues();
                        Cursor cursor = db.rawQuery("SELECT * FROM NotificationsTimings", null);
                        if (cursor.getCount() > 0) {
                            cursor.moveToLast();
                            _id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                            mindGymAffirmationsTime = Long.parseLong(cursor.getString(cursor.getColumnIndex("MIND_GYM_END_TIME")));

                            set_calender.setTimeInMillis(mindGymAffirmationsTime);
                            set_calender.add(Calendar.HOUR, -2);
                            happyMomentTime = set_calender.getTimeInMillis();
                            cv.put("HAPPY_MOMENT_TIME", happyMomentTime);

                            db.update("NotificationsTimings", cv, "_id=" + _id, null);
                        }
                        cursor.close();
                        break;

                    default:
                        break;
                }
            }
            onCreate(db);
            Log.i(getClass().getSimpleName(), "onUpgrade Method Called");
        }


    }







    public boolean getViewMyDiaryData(String email)throws Exception {

        Cursor cursor = null;
        boolean returnStatus = false;
        try {

            readDb = this.getReadableDatabase();

            /*cursor = readDb.query(GRATITUDE_TABLE_NAME,
                    null,
                    GRATITUDE_EMAIL_ID + "=? AND " + GRATITUDE_TYPE_OF_GRATITUDE + "!=? AND " + GRATITUDE_SYNC_FLAG + "=?",
                    new String[]{email, "CRUS
                    H", "%2%"},
                    null,
                    null,
                    GRATITUDE_DATE_TIME);*/

            cursor = readDb.rawQuery("SELECT * FROM New_Gratitude_Table where email_id=? AND type_of_gratitude!=? AND syncFlag NOT LIKE '2%' ORDER BY date_time DESC",
                    new String[]{email, "CRUSH"}, null);


            if (cursor.getCount() > 0) {
                returnStatus = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            readDb.close();
            assert cursor != null;
            cursor.close();
        }

        return returnStatus;
    }


    public boolean getRelaxAudioStatus() throws Exception {
        Cursor cursor;
        readDb = this.getReadableDatabase();
        boolean status = false;
        cursor = readDb.query(RELAX_AUDIO_TABLE_NAME,null,null,null,null,null,null);
        try{
            if(cursor.getCount()>0){
                status = true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
        return status;

    }

    public boolean getRelaxCoachAudioStudentStatus() throws Exception {
        Cursor cursor;
        readDb = this.getReadableDatabase();
        boolean status = false;
        cursor = readDb.query(RELAX_COACH_STUDENT_TABLE_NAME,null,null,null,null,null,null);
        try{
            if(cursor.getCount()>0){
                status = true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
        return status;

    }

    public boolean getCoachAllAudioStatus() throws Exception {
        Cursor cursor;
        readDb = this.getReadableDatabase();
        boolean status = false;
        cursor = readDb.query(STUDENT_COACH_AUDIO_TABLE_NAME,null,null,null,null,null,null);
        try{
            if(cursor.getCount()>0){
                status = true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            cursor.close();
        }
        return status;

    }

}

