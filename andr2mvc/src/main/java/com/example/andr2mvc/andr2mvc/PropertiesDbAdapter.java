package com.example.andr2mvc.andr2mvc;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by pichuginsi on 12.08.2014.
 */
public class PropertiesDbAdapter {
    private Context context;
    private SQLiteDatabase db;
    private LocalDbHelper dbHelper;

    // поля базы данных
    public static final String KEY_ROWID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_VALUE = "value";
    private static final String DATABASE_TABLE = "properties";

    public PropertiesDbAdapter(Context context){
        this.context=context;
    }
    public PropertiesDbAdapter open() throws SQLException {
        dbHelper = new LocalDbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }
    private ContentValues createContentValues(String name, String value) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_VALUE, value);
        return values;
    }
    /**
     * создать новый элемент списка дел. если создан успешно - возвращается номер строки rowId
     * иначе -1
     */
    public long insert(String name, String value) {
        ContentValues initialValues = createContentValues(name, value);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    /**
     * обновить список
     */
    public boolean update(long id, String name, String value) {
        ContentValues updateValues = createContentValues(name, value);

        return db.update(DATABASE_TABLE, updateValues, KEY_ROWID + "=" + id, null) > 0;
    }
    /**
     * удаляет элемент списка
     */
    public boolean delete(long id) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + id, null) > 0;
    }
    /**
     * возвращает курсор со всеми элементами списка дел
     *
     * @return курсор с результатами всех записей
     */
    public Cursor fetchAll() {
        return db.query(DATABASE_TABLE, new String[] { KEY_ROWID,
                        KEY_NAME, KEY_VALUE}, null, null, null,null, null);
    }
    /**
     * возвращает курсор, спозиционированный на указанной записи
     */
    public Cursor fetch(long rowId) throws SQLException {
        Cursor mCursor = db.query(true,
                                  DATABASE_TABLE,
                                  new String[] { KEY_ROWID, KEY_NAME, KEY_VALUE},
                                  KEY_ROWID + "=" + rowId,
                                  null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    /**
     * возвращает курсор, спозиционированный на указанной записи
     */
    public Cursor fetch(String name) throws SQLException {
        Cursor mCursor = db.query(true,
                DATABASE_TABLE,
                new String[] { KEY_ROWID, KEY_NAME, KEY_VALUE},
                KEY_NAME + "=" + name,
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void initData() {
        if(fetch("'ApplicationUrl'").getCount()==0)
        {
            setPropertyValue("ApplicationUrl","http://muscle-planet.ru:9980/mvcapplication1/home/");
        }
//        if(fetch("'ApplicationUrl'").getCount()>0)
//        {
//            setApplicationUrl("http://muscle-planet.ru:9980/mvcapplication1/home/");
//        }
    }
    public String getApplicationUrl() {
        return getPropertyValue("ApplicationUrl");
    }
    public void setApplicationUrl(String value) {
        setPropertyValue("ApplicationUrl",value);
    }
    public String getPropertyValue(String name) {
        Cursor cursor = fetch("'"+name+"'");

        return cursor.getString(cursor.getColumnIndex(KEY_VALUE));
    }
    public void setPropertyValue(String name, String value) {

        Cursor cursor = fetch("'" + name + "'");

        if (cursor.getCount() == 0) {
            insert(name, value);
        } else {
            int id = cursor.getInt(cursor.getColumnIndex(KEY_ROWID));
            update(id, name, value);
        }
    }

}
