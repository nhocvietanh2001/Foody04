package hcmute.spkt.phamvietanh19110151.foodyui.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Database.DBHelper;
import hcmute.spkt.phamvietanh19110151.foodyui.Fragment.CartFragment;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.CartItem;
import hcmute.spkt.phamvietanh19110151.foodyui.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {

    private Context context;
    List<CartItem> CartItems;
    ButtonListener buttonListener;

    public CartAdapter(Context context) {this.context = context;}

    public void setButtonListener(ButtonListener listener) {this.buttonListener = listener;}

    public void setItems(List<CartItem> cartItems) {
        this.CartItems = cartItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cart_item,parent,false);
        return new CartAdapter.CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = CartItems.get(position);
        if(cartItem == null)
            return;
        holder.tvFoodName.setText(cartItem.getFoodName());
        holder.tvPrice.setText(Integer.toString(cartItem.getPrice()));
        holder.tvAmount.setText(Integer.toString(cartItem.getAmount()));
        holder.imgFood.setImageBitmap(cartItem.getImageBitmap());
        holder.tvTotal.setText(Integer.toString(cartItem.getTotal()));
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(holder.tvAmount.getText().toString());
                if(amount == 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete?").setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DBHelper MyDB = new DBHelper(context);
                            MyDB.updateAmount(cartItem.getCid(), 0);
                            CartItems.remove(cartItem);

                            buttonListener.onButtonClick(calTotal(CartItems));
                            return;
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                DBHelper MyDB = new DBHelper(context);
                amount = amount - 1;
                MyDB.updateAmount(cartItem.getCid(), amount);
                holder.tvAmount.setText(Integer.toString(amount));
                CartItems.get(position).setAmount(amount);
                buttonListener.onButtonClick(calTotal(CartItems));
            }
        });
        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(holder.tvAmount.getText().toString());
                amount = amount + 1;
                DBHelper MyDB = new DBHelper(context);
                MyDB.updateAmount(cartItem.getCid(), amount);
                holder.tvAmount.setText(Integer.toString(amount));
                CartItems.get(position).setAmount(amount);
                buttonListener.onButtonClick(calTotal(CartItems));
            }
        });
    }

    @Override
    public int getItemCount() {
        return CartItems.size();
    }

    public int calTotal(List<CartItem> cartItems) {
        int total = 0;
        for (CartItem item : cartItems)
            total += item.getTotal();
        return total;
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

    public List<CartItem> getCartItems() {
        return CartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        CartItems = cartItems;
    }

    public interface ButtonListener{
        void onButtonClick(int total);
    }
}
