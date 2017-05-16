package com.bofsoft.sdk.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts.Photo;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人
 */
@SuppressLint("HandlerLeak")
public class Contacts {

    @SuppressLint("InlinedApi")
    private static final String[] PHONES_PROJECTION = new String[]{Phone.DISPLAY_NAME, Phone.NUMBER,
            Photo.PHOTO_ID, Phone.CONTACT_ID};
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;
    private static final int PHONES_PHOTO_ID_INDEX = 2;
    private static final int PHONES_CONTACT_ID_INDEX = 3;

    /**
     * 获取手机联系人信息
     */
    public static List<Contacts> getPhoneContacks(Context context) {
        ContentResolver resolver = context.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        List<Contacts> list = new ArrayList<Contacts>();
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                // 得到联系人ID
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);
                // 得到联系人头像ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);
                // 得到联系人头像Bitamp
//        Bitmap contactPhoto = null;
                // photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
//        if (photoid > 0) {
//          Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
//          InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
//          contactPhoto = BitmapFactory.decodeStream(input);
//        } else {
//          contactPhoto = BitmapFactory.decodeResource(context.getResources(), R.drawable.head);
//        }
                list.add(new Contacts(contactid, contactName, phoneNumber, null));
            }
            phoneCursor.close();
        }
        return list;
    }

    /**
     * 获取SIM联系人
     */
    public static List<Contacts> getSIMContacts(Context context) {
        ContentResolver resolver = context.getContentResolver();
        // 获取Sims卡联系人
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
        List<Contacts> list = new ArrayList<Contacts>();
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                // 得到手机号码
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                // 当手机号码为空的或者为空字段 跳过当前循环
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                // 得到联系人名称
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                list.add(new Contacts(0l, contactName, phoneNumber, null));
            }
            phoneCursor.close();
        }
        return list;
    }

    public static List<Contacts> getContacts(Context context) {
        List<Contacts> list = new ArrayList<Contacts>();
        list.addAll(getPhoneContacks(context));
        list.addAll(getSIMContacts(context));
        return list;
    }

    public static void getAsyncContacts(Context context, ContactsHandler handler) {
        new Thread(new AsyncTask(context, handler, 0)).start();
    }

    public static void getAsyncPhoneContacts(Context context, ContactsHandler handler) {
        new Thread(new AsyncTask(context, handler, 1)).start();
    }

    public static void getAsyncSIMContacts(Context context, ContactsHandler handler) {
        new Thread(new AsyncTask(context, handler, 2)).start();
    }

    public static class AsyncTask implements Runnable {

        private Context context;
        private static ContactsHandler handler;
        private int type;
        private static List<Contacts> list = new ArrayList<Contacts>();

        static class Mh extends Handler {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    getHandler().complate(list);
                }
            }

        }

        private Handler h = new Mh();

        /**
         * @param context
         * @param handler
         * @param type    0全部 1 手机联系人 2 sim联系人
         */
        public AsyncTask(Context context, ContactsHandler handler, int type) {
            this.context = context;
            AsyncTask.setHandler(handler);
            this.type = type;
        }

        @Override
        public void run() {
            if (type == 1) {
                list = getPhoneContacks(context);
            } else if (type == 2) {
                list = getSIMContacts(context);
            } else {
                list = getContacts(context);
            }
            Message m = new Message();
            m.what = 1;
            m.obj = list;
            h.sendMessage(m);
        }

        /**
         * @return the handler
         */
        public static ContactsHandler getHandler() {
            return handler;
        }

        /**
         * @param handler the handler to set
         */
        public static void setHandler(ContactsHandler handler) {
            AsyncTask.handler = handler;
        }
    }

    public interface ContactsHandler {
        void complate(List<Contacts> list);
    }


    private Long id;
    private String name;
    private String number;
    private Bitmap head;

    public Contacts() {
        super();
    }

    public Contacts(Long id, String name, String number, Bitmap head) {
        super();
        this.id = id;
        this.name = name;
        this.number = number;
        this.head = head;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Bitmap getHead() {
        return head;
    }

    public void setHead(Bitmap head) {
        this.head = head;
    }
}
