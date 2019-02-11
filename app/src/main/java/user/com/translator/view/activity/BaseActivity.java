package user.com.translator.view.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import user.com.translator.interf.IScreen;

public abstract class BaseActivity<T extends ViewModel> extends AppCompatActivity implements IScreen<T> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindLayout(null);
    }
}
