package user.com.translator.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.List;

import user.com.translator.ApplicationUtil;
import user.com.translator.R;
import user.com.translator.common.list.CommonAdapter;
import user.com.translator.model.logic.Language;
import user.com.translator.interf.ITranslateLanguage;
import user.com.translator.interf.ITranslateLanguageChanged;

public class TranslateLangFragment extends BaseFragment implements ITranslateLanguage {

    private static final String KEY_ONE_WAY = "OneWayTranslate";
    private boolean mOneWayTrans;

    private Spinner mSpLangFrom;
    private Spinner mSpLangTo;
    private ImageView mIvArrow;

    private ITranslateLanguageChanged mListener;

    private List<Language> mAllLanguages;
    private ArrayAdapter<Language> mLangFromAdapter;
    private ArrayAdapter<Language> mLangToAdapter;

    public static TranslateLangFragment getInstance(boolean isOneWayTrans) {
        TranslateLangFragment fragment = new TranslateLangFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_ONE_WAY, isOneWayTrans);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void bindLayout(View view) {
        mSpLangFrom = view.findViewById(R.id.sp_lang_form);
        mSpLangTo = view.findViewById(R.id.sp_lang_to);
        mIvArrow = view.findViewById(R.id.iv_arrow);
        initClickListener();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_translate_lang;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(KEY_ONE_WAY)){
            mOneWayTrans = bundle.getBoolean(KEY_ONE_WAY);
        }else{
            mOneWayTrans = true;
        }
        mAllLanguages = ApplicationUtil.getAllLang(getContext());
        mLangFromAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mAllLanguages);
        mLangToAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, mAllLanguages);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSpLangFrom.setAdapter(mLangFromAdapter);
        mSpLangFrom.setPrompt("Choose language");
        mSpLangTo.setAdapter(mLangToAdapter);
    }

    private void initClickListener() {
        mSpLangFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mListener != null){
                    Language langFrom = mLangToAdapter.getItem(position);
                    mListener.onLangToChanged(langFrom.code);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpLangTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(mListener != null){
                    Language langTo = mLangToAdapter.getItem(position);
                    mListener.onLangToChanged(langTo.code);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mIvArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mOneWayTrans && mListener != null) {
                    mListener.revertTranslate("", "");
                }
            }
        });
    }

    @Override
    public String getLangFrom() {
        Language langFrom = (Language) mSpLangFrom.getSelectedItem();
        return langFrom.code;
    }

    @Override
    public String getLangTo() {
        Language langTo = (Language) mSpLangFrom.getSelectedItem();
        return langTo.code;
    }

    @Override
    public void setAutoDetectLang(String detectLang) {
        mLangFromAdapter.getItem(0).setDetectLang(detectLang);
        mLangFromAdapter.notifyDataSetChanged();
    }
}
