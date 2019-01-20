package com.example.yu.xieyu20190120.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.yu.xieyu20190120.bean.DaoBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DAO_BEAN".
*/
public class DaoBeanDao extends AbstractDao<DaoBean, Long> {

    public static final String TABLENAME = "DAO_BEAN";

    /**
     * Properties of entity DaoBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Images = new Property(0, String.class, "images", false, "IMAGES");
        public final static Property Pid = new Property(1, long.class, "pid", true, "_id");
        public final static Property Price = new Property(2, double.class, "price", false, "PRICE");
        public final static Property Title = new Property(3, String.class, "title", false, "TITLE");
    }


    public DaoBeanDao(DaoConfig config) {
        super(config);
    }
    
    public DaoBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DAO_BEAN\" (" + //
                "\"IMAGES\" TEXT," + // 0: images
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 1: pid
                "\"PRICE\" REAL NOT NULL ," + // 2: price
                "\"TITLE\" TEXT);"); // 3: title
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DAO_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DaoBean entity) {
        stmt.clearBindings();
 
        String images = entity.getImages();
        if (images != null) {
            stmt.bindString(1, images);
        }
        stmt.bindLong(2, entity.getPid());
        stmt.bindDouble(3, entity.getPrice());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DaoBean entity) {
        stmt.clearBindings();
 
        String images = entity.getImages();
        if (images != null) {
            stmt.bindString(1, images);
        }
        stmt.bindLong(2, entity.getPid());
        stmt.bindDouble(3, entity.getPrice());
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 1);
    }    

    @Override
    public DaoBean readEntity(Cursor cursor, int offset) {
        DaoBean entity = new DaoBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // images
            cursor.getLong(offset + 1), // pid
            cursor.getDouble(offset + 2), // price
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // title
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DaoBean entity, int offset) {
        entity.setImages(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setPid(cursor.getLong(offset + 1));
        entity.setPrice(cursor.getDouble(offset + 2));
        entity.setTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DaoBean entity, long rowId) {
        entity.setPid(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DaoBean entity) {
        if(entity != null) {
            return entity.getPid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DaoBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
