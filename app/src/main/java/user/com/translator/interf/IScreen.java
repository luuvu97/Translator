package user.com.translator.interf;

import android.arch.lifecycle.ViewModel;
import android.view.View;

public interface IScreen<T extends ViewModel> {

    public void bindLayout(View view);

    public int getLayoutId();

    public T getViewModel();
}
