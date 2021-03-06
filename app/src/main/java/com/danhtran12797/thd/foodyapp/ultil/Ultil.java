package com.danhtran12797.thd.foodyapp.ultil;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;

import com.danhtran12797.thd.foodyapp.model.AddressShipping;
import com.danhtran12797.thd.foodyapp.model.ShopingCart;
import com.danhtran12797.thd.foodyapp.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Ultil {
    public static String url_image_banner = "http://avapp.000webhostapp.com/foody/anh/quangcao/";
    public static String url_image_product = "http://avapp.000webhostapp.com/foody/anh/";
    public static String url_image_category = "http://avapp.000webhostapp.com/foody/anh/danhmuc/";
    public static String url_image_avatar = "http://avapp.000webhostapp.com/foody/anh/anhuser/";
    public static String NAME_SHARED_PREFERENCES = "shared preferences";
    public static String INFOR_USER = "infor user";
    public static String SHOPING_CART = "shoping cart";
    public static String ADDRESS_SHIPPING = "address shipping";
    public static ArrayList<ShopingCart> arrShoping = null;
    public static ArrayList<AddressShipping> arrAddressShipping = null;
    public static User user = null;
    public static ProgressDialog progressDialog;

    public static void add_product_shoping_cart(ShopingCart cart) {
        boolean check = false;
        if (arrShoping != null) {
            for (int i = 0; i < arrShoping.size(); i++) {
                if (arrShoping.get(i).getId().equals(cart.getId())) {
                    int quantity = arrShoping.get(i).getQuantity();
                    arrShoping.get(i).setQuantity(quantity + 1);
                    check = true;
                    break;
                }
            }
            if (!check) {
                arrShoping.add(cart);
            }
        } else {
            Log.d("ooo", "arrShoping = null ");
            arrShoping = new ArrayList<>();
            arrShoping.add(cart);
        }
    }

    public static void removeAddressShipping(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(ADDRESS_SHIPPING);
        editor.apply();
    }

    public static ArrayList<AddressShipping> getAddressShipping(Context context) {
        ArrayList<AddressShipping> addressShipping = null;
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, context.MODE_PRIVATE);
        String json_shoping_cart = preferences.getString(ADDRESS_SHIPPING, "");
        if (json_shoping_cart.equals("")) {
            return addressShipping;
        } else {
            Gson gson = new Gson();
            Type familyType = new TypeToken<ArrayList<AddressShipping>>() {
            }.getType();
            addressShipping = gson.fromJson(json_shoping_cart, familyType);
            return addressShipping;
        }
    }

    public static void setAddressShipping(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json_address_shipping = gson.toJson(arrAddressShipping);
        editor.putString(ADDRESS_SHIPPING, json_address_shipping);
        editor.apply();
    }

    public static ArrayList<ShopingCart> getShopingCart(Context context) {
        ArrayList<ShopingCart> shopingCarts = null;
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, context.MODE_PRIVATE);
        String json_shoping_cart = preferences.getString(SHOPING_CART, "");
        Log.d("yyy", json_shoping_cart);
        if (json_shoping_cart.equals("")) {
            return shopingCarts;
        } else {
            Gson gson = new Gson();
            Type familyType = new TypeToken<ArrayList<ShopingCart>>() {
            }.getType();
            shopingCarts = gson.fromJson(json_shoping_cart, familyType);
            return shopingCarts;
        }
    }

    public static void setShopingCart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json_shoping_cart = gson.toJson(arrShoping);
        editor.putString(SHOPING_CART, json_shoping_cart);
        editor.apply();
    }

    public static void removeShopingCart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(SHOPING_CART);
        editor.apply();
    }

    public static User getUserPreference(Context context) {
        User user = null;
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, context.MODE_PRIVATE);
        String json_user = preferences.getString(INFOR_USER, "");
        if (json_user.equals("")) {
            return user;
        } else {
            Gson gson = new Gson();
            user = gson.fromJson(json_user, User.class);
        }
        return user;
    }

    public static void setUserPreference(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        if (user != null) {
            String json_user = gson.toJson(user);
            editor.putString(INFOR_USER, json_user);
            editor.apply();
        }
    }

    public static void removeUserPreference(Context context) {
        user = null;
        SharedPreferences preferences = context.getSharedPreferences(NAME_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove(INFOR_USER);
        editor.apply();
    }

    public static void showDialog(Context context, String title, String message, Drawable drawable, View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setTitle(title)
                .setIcon(drawable)
                .setView(view)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

//    public static void showProgressDialog(Context context, String title, String message, Drawable drawable) {
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setIcon(drawable);
//        progressDialog.setTitle(title);
//        progressDialog.setMessage(message);
//        progressDialog.show();
//    }

//    public static void hideProgressDialog() {
//        progressDialog.hide();
//    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
