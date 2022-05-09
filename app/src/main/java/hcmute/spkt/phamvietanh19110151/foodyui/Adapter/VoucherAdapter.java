package hcmute.spkt.phamvietanh19110151.foodyui.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.R;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Voucher;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    Context context;
    List<Voucher> vouchers;
    voucherOnClick onClick;

    public VoucherAdapter(Context context) {
        this.context = context;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
        notifyDataSetChanged();
    }

    public void setVoucherOnClick(voucherOnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_voucher,parent,false);
        return new VoucherAdapter.VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = vouchers.get(position);
        if (voucher == null)
            return;
        holder.tvVoucher.setText(voucher.getName());
        if (position%2 == 0) {
            holder.imgVoucher.setImageResource(R.drawable.higreen);
        }
        else {
            holder.imgVoucher.setImageResource(R.drawable.higreen2);
        }
        holder.layoutVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "hell0", Toast.LENGTH_SHORT).show();
                onClick.onVoucherClick(voucher.getType(), voucher.getAmount());
            }
        });
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public class VoucherViewHolder extends RecyclerView.ViewHolder{
        TextView tvVoucher;
        ImageView imgVoucher;
        LinearLayout layoutVoucher;
        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVoucher = itemView.findViewById(R.id.tvVoucherName);
            imgVoucher = itemView.findViewById(R.id.ivVoucherImage);
            layoutVoucher = itemView.findViewById(R.id.layoutVoucher);
        }

    }

    public interface voucherOnClick{
        void onVoucherClick(String type, int value);
    }
}
