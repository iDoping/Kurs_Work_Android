package com.example.kursrpmos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "mydatabase.db";

    public static final String TABLE_COSTS = "costs";
    public static final String COL_ID = "ID";
    public static final String COL_SUM = "COST_SUM";
    public static final String COL_DATE = "COST_DATE";
    public static final String COL_CAT = "COST_CATEGORY";


    public static final String TABLE_TYPECOSTS = "typecosts";
    public static final String COL_COST_TYPE_ID = "_id";
    public static final String COL_NAME_COST = "NAME_OF_COST";

    public static final String TABLE_INCOMES = "incomes";
    public static final String COL_INC_ID = "_id";
    public static final String COL_INC_SUM = "INC_SUM";
    public static final String COL_INC_DATE = "INC_DATE";
    public static final String COL_INC_CAT = "INC_CATEGORY";


    public static final String TABLE_TYPEINCOMES = "typeincomes";
    public static final String COL_INC_TYPE_ID = "ID_INCOME";
    public static final String COL_NAME_INC = "NAME_OF_INC";

    /**
     * Конструктор класса DBHelper
     *
     * @param context контекст Activity
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    /**
     * Создание таблиц в базе данных
     *
     * @param db Объект базы данных
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_COSTS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, COST_DATE TEXT, COST_CATEGORY INTEGER, COST_SUM REAL,CONSTRAINT COST_CATEGORY FOREIGN KEY(COST_CATEGORY) REFERENCES typecosts(_id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE " + TABLE_TYPECOSTS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME_OF_COST TEXT,START_LIMIT REAL,PERIODIC_LIMIT REAL,RESET_MONTH TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_INCOMES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, INC_DATE TEXT, INC_CATEGORY INTEGER, INC_SUM REAL, CONSTRAINT INC_CATEGORY FOREIGN KEY(INC_CATEGORY) REFERENCES typeincomes(_id) ON DELETE CASCADE)");
        db.execSQL("CREATE TABLE " + TABLE_TYPEINCOMES + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME_OF_INC TEXT)");
        db.execSQL("INSERT INTO typecosts(NAME_OF_COST) VALUES ('Еда')");
        db.execSQL("INSERT INTO typeincomes(NAME_OF_INC) VALUES ('Зарплата')");
    }

    /**
     * Пересоздание таблиц при изменении структуры БД
     *
     * @param db         Объект класса базы данных
     * @param oldVersion Старая версия базы данных
     * @param newVersion Новая версия базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPECOSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPEINCOMES);
        onCreate(db);
    }

    /**
     * Добавление названия новой категории расхода в таблицу
     *
     * @param label Название категории расхода
     */
    public void insertTypeCost(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO typecosts(NAME_OF_COST) VALUES ('" + label + "')");
        db.close();
    }

    /**
     * Добавление названия новой категории дохода в таблицу
     *
     * @param label Название категории дохода
     */
    public void insertTypeIncome(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO typeincomes(NAME_OF_INC) VALUES ('" + label + "')");
        db.close();
    }

    /**
     * Добавление нового расхода в таблицу
     *
     * @param label1 Сумма расхода
     * @param label2 Дата расхода
     * @param label3 Категория расхода
     */
    public void insertCost(String label1, String label2, String label3) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO costs(COST_SUM, COST_DATE, COST_CATEGORY) VALUES (" + label1 + ",'" + label2 + "',(SELECT _id FROM typecosts WHERE NAME_OF_COST = '" + label3 + "'))");
        db.close();
    }

    /**
     * Добавление нового дохода в таблицу
     *
     * @param label1 Сумма дохода
     * @param label2 Дата дохода
     * @param label3 Категория дохода
     */
    public void insertIncome(String label1, String label2, String label3) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO incomes(INC_SUM, INC_DATE, INC_CATEGORY) VALUES (" + label1 + ",'" + label2 + "',(SELECT _id FROM typeincomes WHERE NAME_OF_INC = '" + label3 + "'))");
        db.close();
    }

    /**
     * Удаление названия категории расхода в таблице
     *
     * @param label Название категорий расходов
     */
    public void deleteCost(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM typecosts where NAME_OF_COST = '" + label + "'");
        db.close();
    }

    /**
     * Удаление названия категории дохода в таблице
     *
     * @param label Название категорий доходов
     */
    public void deleteIncome(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM typeincomes where NAME_OF_INC = '" + label + "'");
        db.close();
    }

    /**
     * Выборка всех названий расходов в таблицу
     *
     * @return Возвращает названия всех категорий расходов
     */
    public List<String> getAllCosts() {
        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPECOSTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return labels;
    }

    /**
     * Выборка всех названий доходов в таблицу
     *
     * @return Возвращает названия всех категорий доходов
     */
    public List<String> getAllIncomes() {
        List<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_TYPEINCOMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return labels;
    }

    /**
     * Выборка суммы расходов, сгруппированной по категориям, в промежутке от даты начала до даты конца периода
     *
     * @param label1 Дата начала периода
     * @param label2 Дата конца периода
     * @return Возвращает сумму расходов по каждой из категорий
     */
    public ArrayList<BarEntry> getBarEntriesCosts(String label1, String label2) {
        String cost_sum_column = "COST_SUM";
        String cost_sum_column2 = "COST_CATEGORY";
        String date = "COSTS.COST_DATE >= '" + label1 + "' AND COSTS.COST_DATE <= '" + label2 + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{"SUM(COST_SUM) AS " + cost_sum_column, "COST_CATEGORY AS " + cost_sum_column2};
        Cursor csr = db.query("costs JOIN TYPECOSTS ON COSTS.COST_CATEGORY = TYPECOSTS._id", columns, date, null, "COST_CATEGORY", null, null);
        int barEntry = 1;
        ArrayList<BarEntry> rv = new ArrayList<>();
        while (csr.moveToNext()) {
            rv.add(new BarEntry(
                    barEntry,
                    csr.getFloat(csr.getColumnIndex(cost_sum_column))
            ));
            barEntry++;
        }
        csr.close();
        return rv;
    }

    /**
     * Выборка суммы доходов, сгруппированной по категориям, в промежутке от даты начала до даты конца периода
     *
     * @param label1 Дата начала периода
     * @param label2 Дата конца периода
     * @return Возвращает сумму доходов по каждой из категорий
     */
    public ArrayList<BarEntry> getBarEntriesIncomes(String label1, String label2) {
        String income_sum_column = "INC_SUM";
        String income_sum_column2 = "INC_CATEGORY";
        String date = "INCOMES.INC_DATE >= '" + label1 + "' AND INCOMES.INC_DATE <= '" + label2 + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{"SUM(INC_SUM) AS " + income_sum_column, "INC_CATEGORY AS " + income_sum_column2};
        Cursor csr = db.query("incomes JOIN typeincomes ON incomes.INC_CATEGORY = TYPEINCOMES._id", columns, date, null, "INC_CATEGORY", null, null);
        int barEntry = 1;
        ArrayList<BarEntry> rv = new ArrayList<>();
        while (csr.moveToNext()) {
            rv.add(new BarEntry(
                    barEntry,
                    csr.getFloat(csr.getColumnIndex(income_sum_column))
            ));
            barEntry++;
        }
        csr.close();
        return rv;
    }

    /**
     * Выборка сгруппированного списка категорий расходов
     *
     * @return Сгруппированный список категорий расходов
     */
    public List<String> getCostsBarLabel() {

        List<String> labels = new ArrayList<String>();
        labels.add(" ");
        String selectQuery = "SELECT NAME_OF_COST FROM COSTS JOIN TYPECOSTS ON COSTS.COST_CATEGORY = TYPECOSTS._id group by COST_CATEGORY ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return labels;
    }

    /**
     * Выборка сгруппированного списка категорий доходов
     *
     * @return Сгруппированный список категорий доходов
     */
    public List<String> getIncomesBarLabel() {

        List<String> labels = new ArrayList<String>();
        labels.add(" ");
        String selectQuery = "SELECT NAME_OF_INC FROM INCOMES JOIN TYPEINCOMES ON INCOMES.INC_CATEGORY = TYPEINCOMES._id group by INC_CATEGORY ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return labels;
    }

    /**
     * Выборка всех названий категорий расходов
     *
     * @return Возвращает результат запроса, в котором выбираются все имена расходов из таблицы
     */
    public Cursor selectCostsToList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT NAME_OF_COST FROM " + TABLE_TYPECOSTS;
        return db.rawQuery(query, null);
    }

    /**
     * Выборка всех названий расходов и начальных лимитов из таблицы
     *
     * @return Возвращает результат запроса, в котором выбираются все названия расходов и начальные лимиты из таблицы
     */
    public Cursor selectPlansToList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT NAME_OF_COST,START_LIMIT FROM " + TABLE_TYPECOSTS;
        return db.rawQuery(query, null);
    }

    /**
     * Выборка всех названий категорий доходов
     *
     * @return Возвращает результат запроса, в котором выбираются все названия доходов из таблицы
     */
    public Cursor selectIncomesToList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_TYPEINCOMES;
        return db.rawQuery(query, null);
    }

    /**
     * Установка лимитов для заданной категории
     *
     * @param label1 Значение лимита
     * @param label2 Название категории расхода
     */
    public void insertPlan(String label1, String label2, String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE typecosts SET START_LIMIT = " + label1 + ",RESET_MONTH = '" + month + "' WHERE NAME_OF_COST = '" + label2 + "'");
        db.execSQL("UPDATE typecosts SET PERIODIC_LIMIT = " + label1 + ",RESET_MONTH ='" + month + "' WHERE NAME_OF_COST = '" + label2 + "'");
        db.close();
    }

    /**
     * Выборка лимитов, изменяющихся в связи с действиями пользователя
     *
     * @param label Название категории расхода
     * @return Возвращает лимиты, которые изменяются в связи с действиями пользователя
     */
    public String getCostsPlans(String label) {
        String labels = "";
        String selectQuery = "SELECT PERIODIC_LIMIT FROM " + TABLE_TYPECOSTS + " WHERE NAME_OF_COST = '" + label + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                labels = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return labels;
    }

    /**
     * Добавление в таблицу измененного пользователем значения лимита
     *
     * @param label1 Лимит, который изменяется в связи с действиями пользователя
     * @param label2 Название категории расхода
     */
    public void insertChanges(double label1, String label2) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE typecosts SET PERIODIC_LIMIT = " + label1 + " WHERE NAME_OF_COST = '" + label2 + "'");
        db.close();
    }

    /**
     * Изменение значения полей для устранения лимита у выбранной категории
     *
     * @param label Название категории расхода
     */
    public void deletePlans(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE typecosts SET START_LIMIT = null WHERE NAME_OF_COST = '" + label + "'");
        db.execSQL("UPDATE typecosts SET PERIODIC_LIMIT = null WHERE NAME_OF_COST = '" + label + "'");
        db.close();
    }

    /**
     * Возвращает лимиты в начальное состояние каждое начало нового месяца
     *
     * @param label Номер месяца в году
     */
    public void setStartPlans(String label) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE typecosts SET PERIODIC_LIMIT = START_LIMIT WHERE RESET_MONTH != '" + label + "'");
        db.execSQL("UPDATE typecosts SET RESET_MONTH = '" + label + "' WHERE RESET_MONTH != '" + label + "'");
        db.close();
    }
}