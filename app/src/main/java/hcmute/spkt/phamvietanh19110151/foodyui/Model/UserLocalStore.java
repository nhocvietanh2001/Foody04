package hcmute.spkt.phamvietanh19110151.foodyui.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocal;

    public UserLocalStore(Context context) {
        userLocal = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUser(User user) {
        SharedPreferences.Editor editor = userLocal.edit();
        editor.putString("phone", user.getPhone());
        editor.putString("name", user.getName());
        editor.putString("pass", user.getPass());
        editor.putString("address", user.getAddress());
        editor.commit();
    }

    public User getUser() {
        String phone = userLocal.getString("phone", "");
        String name = userLocal.getString("name", "");
        String pass = userLocal.getString("pass", "");
        String address = userLocal.getString("address", "");
        return new User(phone, name, pass, address);
    }

    public void setRemember(boolean remember) {
        SharedPreferences.Editor editor = userLocal.edit();
        editor.putBoolean("loggedIn", remember);
        editor.commit();
    }

    public boolean getRemember() {
        return userLocal.getBoolean("loggedIn", false);
    }

}
