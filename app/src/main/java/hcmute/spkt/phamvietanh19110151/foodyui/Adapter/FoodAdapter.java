package hcmute.spkt.phamvietanh19110151.foodyui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Fragment.RestaurantFragment;
import hcmute.spkt.phamvietanh19110151.foodyui.MainActivity;
import hcmute.spkt.phamvietanh19110151.foodyui.Model.Food;
import hcmute.spkt.phamvietanh19110151.foodyui.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{
    private Context context;
    List<Food> Foods;

    public FoodAdapter(Context context) {
        this.context = context;
    }

    public void setFoods(List<Food> foods) {
        this.Foods = foods;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodAdapter.FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_food,parent,false);

        return new FoodAdapter.FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.FoodViewHolder holder, int position) {
        Food food = Foods.get(position);
        if(food == null){
            return;
        }
        holder.tvFood.setText(food.getName());
        holder.tvPrice.setText(Integer.toString(food.getPrice())+" vnd");
        holder.imgFood.setImageBitmap(food.getImageBitmap());


    }



    @Override
    public int getItemCount() {
        return Foods.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFood;
        private TextView tvFood, tvPrice;
        private LinearLayout layoutFoodView;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.imgFood);
            tvFood = itemView.findViewById(R.id.tvFoodName);
            tvPrice = itemView.findViewById(R.id.tvFoodPrice);

        }
    }
}
