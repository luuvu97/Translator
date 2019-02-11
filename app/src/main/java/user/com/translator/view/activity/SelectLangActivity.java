package user.com.translator.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

import user.com.translator.DefineVar;
import user.com.translator.R;
import user.com.translator.common.list.CommonList;
import user.com.translator.interf.ICommonListClickListener;
import user.com.translator.model.logic.Language;
import user.com.translator.viewmodel.TranslateLangVM;

public class SelectLangActivity extends BaseActivity<TranslateLangVM>{

    public static final String KEY = "ChangeLangFrom";
    private boolean mIsLangFrom;
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
    public TranslateLangVM getViewModel() {
        return ViewModelProviders.of(this).get(TranslateLangVM.class);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIsLangFrom = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(KEY)) {
            mIsLangFrom = bundle.getBoolean(KEY);
        }
        setDisplayData();
        mClLang.setData(mAllLang, new ICommonListClickListener<Language>() {
            @Override
            public void onListItemClicked(Language item, int position) {
                if (mIsLangFrom) {
                    getViewModel().setLangFrom(item.getCode());
                } else {
                    getViewModel().setLangTo(item.getCode());
                }
                finish();
            }
        });
        mClLang.scrollTo(getViewModel().getSelectLangPos());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setDisplayData() {
        if (mIsLangFrom) {
            getSupportActionBar().setTitle(DefineVar.TRANSLATE_FROM);
            mAllLang = getViewModel().getList();
        } else {
            getSupportActionBar().setTitle(DefineVar.TRANSLATE_TO);
            mAllLang = getViewModel().getListWithoutAutoDetectOption();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
