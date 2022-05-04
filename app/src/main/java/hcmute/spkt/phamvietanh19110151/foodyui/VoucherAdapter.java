package hcmute.spkt.phamvietanh19110151.foodyui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
        return vouchers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        TextView tvVoucher;
        ImageView imgVoucher;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvVoucher = view.findViewById(R.id.tvVoucherName);
            holder.imgVoucher = view.findViewById(R.id.ivVoucherImage);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Voucher voucher = vouchers.get(i);
        holder.tvVoucher.setText(voucher.getName());

        if(i%2 == 0) {
            holder.imgVoucher.setImageResource(R.drawable.higreen);
        } else {
            holder.imgVoucher.setImageResource(R.drawable.higreen2);
        }

        return view;
    }
}
