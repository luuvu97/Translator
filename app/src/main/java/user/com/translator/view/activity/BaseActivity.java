package user.com.translator.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import user.com.translator.interf.IScreen;

public abstract class BaseActivity extends AppCompatActivity implements IScreen {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindLayout(null);
    }
}
