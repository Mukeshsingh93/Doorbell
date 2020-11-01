package com.rs.doorbell.DatabaseUtil;

import com.rs.doorbell.Utility.Utility;

public class DbQuery {
    private FavouriteQueries favouriteQueries;
    private CartQueries cartQueries;

    protected DbQuery() {

        Utility.Logger(DbQuery.class.getName(), "Setting : Working");
        favouriteQueries = new FavouriteQueries();
        cartQueries = new CartQueries();

    }

    public FavouriteQueries getFavouriteQueries() {
        return favouriteQueries;
    }

    public CartQueries getCartQueries() {
        return cartQueries;
    }
}
