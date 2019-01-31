package user.com.translator.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import user.com.translator.ApplicationUtil;
import user.com.translator.R;
import user.com.translator.common.list.CommonList;
import user.com.translator.interf.ICommonListClickListener;
import user.com.translator.model.logic.Language;

public class SelectLangActivity extends BaseActivity{

    private CommonList mClLang;
    private List<Language> mAllLang;

    @Override
    public void bindLayout(View view) {
        mClLang = findViewById(R.id.list_lang);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_lang;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAllLang = ApplicationUtil.getAllLang(getApplicationContext());
        mClLang.setData(mAllLang, new ICommonListClickListener() {
            @Override
            public void onListItemClicked(int position) {
                Toast.makeText(getApplicationContext(), "Click: " + position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
