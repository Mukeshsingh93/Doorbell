package com.rs.doorbell.DatabaseUtil;

import com.rs.doorbell.ConstantUtil.Constant;
import com.rs.doorbell.InterfaceUtil.SqlQueries;
import com.rs.doorbell.Utility.Utility;

public class FavouriteQueries implements SqlQueries {


    public FavouriteQueries() {
        Utility.Logger(FavouriteQueries.class.getName(), "Setting : Working");
    }

    @Override
    public String retrieving() {
        return "SELECT * FROM " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME;
    }

    @Override
    public String update() {
        return "UPDATE " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME + " SET "
                + Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID + "=" + "%s"
                + " WHERE " + Constant.DatabaseColumn.FAVOURITES_COLUMN_ID + "=" + "%s";
    }

    @Override
    public String insert() {
        return "INSERT INTO " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME
                + "(" + Constant.DatabaseColumn.FAVOURITES_COLUMN_RESTAURANT_ID + ","
                + Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID + ") VALUES (%s,%s)";
    }

    @Override
    public String delete() {
        return "DELETE FROM " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME + " WHERE " +
                Constant.DatabaseColumn.FAVOURITES_COLUMN_ID + " = " + "%s  ";
    }

    @Override
    public String retrieveSpecificType() {
        return "SELECT * FROM " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.FAVOURITES_COLUMN_RESTAURANT_ID + " =%s AND "
                +Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID + " = " + "%s";
    }


    @Override
    public String retrieveSpecificTags() {
        return "SELECT * FROM " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID + " =%s";

    }

    @Override
    public String deleteSpecificType() {
        return "DELETE FROM " + Constant.DatabaseColumn.FAVOURITES_TABLE_NAME + " WHERE "
                + Constant.DatabaseColumn.FAVOURITES_COLUMN_RESTAURANT_ID + " =%s AND "+
                Constant.DatabaseColumn.FAVOURITES_COLUMN_USER_ID +" = %s";
    }

    @Override
    public String findLastInsertId() {
        return "Select last_insert_rowid()";
    }
}
