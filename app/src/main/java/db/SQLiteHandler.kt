package db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import db.model.Contact
import android.content.ContentValues

class SQLiteHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "ContactForm"
        private val TABLE_CONTACTS = "ContactTable"
        private val KEY_ID = "id"
        private val FULL_NAME_FIELD = "full_name"
        private val SUBJECT_FIELD = "subject"
        private val EMAIL_FIELD = "email"
        private val PHONE_FIELD = "phone"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + SUBJECT_FIELD + " TEXT,"
                + FULL_NAME_FIELD + " STRING," + PHONE_FIELD + " STRING,"
                + EMAIL_FIELD + " STRING" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }

    fun addContact(contact: Contact):Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, contact.contactId)
        contentValues.put(FULL_NAME_FIELD, contact.fullName)
        contentValues.put(PHONE_FIELD, contact.phone)
        contentValues.put(SUBJECT_FIELD, contact.subject)
        contentValues.put(EMAIL_FIELD, contact.email)
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        db.close()
        return success
    }

}
