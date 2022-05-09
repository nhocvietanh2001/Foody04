package hcmute.spkt.phamvietanh19110151.foodyui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Model.InvoiceFood;
import hcmute.spkt.phamvietanh19110151.foodyui.R;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {

    Context context;
    List<InvoiceFood> Invoices;

    public InvoiceAdapter(Context context) {
        this.context = context;
    }

    public void setInvoice(List<InvoiceFood> invoice) {
        this.Invoices = invoice;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InvoiceAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_invoiceitem,parent,false);

        return new InvoiceAdapter.InvoiceViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        InvoiceFood invoice = Invoices.get(position);
        if (invoice == null)
            return;

        holder.tvInvoiceItem.setText(invoice.getFname());
        holder.tvInvoicePrice.setText(Integer.toString(invoice.getFprice()));
    }

    @Override
    public int getItemCount() {
        return Invoices.size();
    }

    public int calTotal() {
        int total = 0;
        for (InvoiceFood item : Invoices)
            total += item.total();
        return total;
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder{

        TextView tvInvoiceItem, tvInvoicePrice;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInvoiceItem = itemView.findViewById(R.id.tvInvoiceFood);
            tvInvoicePrice = itemView.findViewById(R.id.tvInvoicePrice);
        }

    }
}
