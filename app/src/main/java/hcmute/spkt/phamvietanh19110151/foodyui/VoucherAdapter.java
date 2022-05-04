package hcmute.spkt.phamvietanh19110151.foodyui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VoucherAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    List<Voucher> vouchers;

    public VoucherAdapter(Context context, int layout, List<Voucher> vouchers) {
        this.context = context;
        this.layout = layout;
        this.vouchers = vouchers;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecyclerView.ViewHolder holder;
        return view;
    }
}
