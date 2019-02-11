package user.com.translator.interf;

public interface ICommonListClickListener<T extends IListItem> {
    public void onListItemClicked(T item, int position);
}
