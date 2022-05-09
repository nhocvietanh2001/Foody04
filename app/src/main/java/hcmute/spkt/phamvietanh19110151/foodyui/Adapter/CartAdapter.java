package hcmute.spkt.phamvietanh19110151.foodyui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Model.CartItem;
import hcmute.spkt.phamvietanh19110151.foodyui.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {

    private Context context;
    List<CartItem> CartItems;

    public CartAdapter(Context context) {this.context = context;}

    public void setItems(List<CartItem> cartItems) {
        this.CartItems = cartItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food,parent,false);

        return new CartAdapter.CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = CartItems.get(position);
        if(cartItem == null)
            return;
        holder.tvFoodName.setText(cartItem.getFoodName());
        holder.tvPrice.setText(cartItem.getPrice());
        holder.tvAmount.setText(cartItem.getAmount());
        holder.imgFood.setImageBitmap(cartItem.getImageBitmap());
        holder.tvTotal.setText(cartItem.getTotal());

    }

    @Override
    public int getItemCount() {
        return CartItems.size();
    }


    public class CartItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFood;
        private TextView tvFoodName, tvPrice, tvTotal, tvAmount;
        private LinearLayout layoutFoodCart;
        private Button btnGiam, btnTang;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.imgFoodCart);
            tvFoodName = itemView.findViewById(R.id.tvFoodNameCart);
            tvPrice = itemView.findViewById(R.id.tvFoodPriceCart);
            tvTotal = itemView.findViewById(R.id.tvFoodPriceTotalCart);
            tvAmount = itemView.findViewById(R.id.tvAmountCart);
            btnGiam = itemView.findViewById(R.id.btnGiam);
            btnTang = itemView.findViewById(R.id.btnThem);
            layoutFoodCart = itemView.findViewById(R.id.layoutFoodCart);

        }
    }
}
