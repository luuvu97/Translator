package user.com.translator.common.list;

import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import user.com.translator.R;
import user.com.translator.common.viewholder.BaseViewHolder;
import user.com.translator.common.viewholder.LanguageVH;

public class ViewHolderProvider {
    private static HashMap<Integer, Class> map;

    private static void init() {
        map = new HashMap<>();
        map.put(R.layout.simple_list_item, LanguageVH.class);
    }

    public static BaseViewHolder getInstance(int layoutId, View itemView) {
        try {
            if (map == null) {
                init();
            }
            Class<? extends BaseViewHolder> c = map.get(layoutId);
            Constructor<? extends BaseViewHolder> con = c.getConstructor(View.class);
            return con.newInstance(itemView);
        } catch (Exception e) {
            return null;
        }
    }

}
