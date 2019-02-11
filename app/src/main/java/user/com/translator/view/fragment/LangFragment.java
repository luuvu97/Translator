package user.com.translator.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import user.com.translator.R;
import user.com.translator.view.activity.SelectLangActivity;
import user.com.translator.viewmodel.TranslateLangVM;

public class LangFragment extends BaseFragment<TranslateLangVM> {

    private static final String KEY_ONE_WAY = "OneWayTranslate";
    private boolean mOneWayTrans;

    private TextView mTvLangFrom;
    private TextView mTvLangTo;
    private ImageView mIvArrow;

    public static LangFragment getInstance(boolean isOneWayTrans) {
        LangFragment fragment = new LangFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_ONE_WAY, isOneWayTrans);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void bindLayout(View view) {
        mIvArrow = view.findViewById(R.id.iv_arrow);
        mTvLangFrom = view.findViewById(R.id.tv_lang_form);
        mTvLangTo = view.findViewById(R.id.tv_lang_to);
        initClickListener();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_lang;
    }

    @Override
    public TranslateLangVM getViewModel() {
        return ViewModelProviders.of(this).get(TranslateLangVM.class);
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
        getViewModel().getLangChanged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                mTvLangFrom.setText(getViewModel().getLangFrom().getEnglishName());
                mTvLangTo.setText(getViewModel().getLangTo().getEnglishName());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initClickListener() {
        mTvLangFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectLangActivity.class);
                intent.putExtra(SelectLangActivity.KEY, true);
                startActivity(intent);
            }
        });

        mTvLangTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SelectLangActivity.class);
                intent.putExtra(SelectLangActivity.KEY, false);
                startActivity(intent);
            }
        });

//        if (!mOneWayTrans) {
            mIvArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getViewModel().revertLang();
                }
            });
//        }
    }
}
