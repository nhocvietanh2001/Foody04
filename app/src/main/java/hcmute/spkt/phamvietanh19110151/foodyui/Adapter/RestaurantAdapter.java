package hcmute.spkt.phamvietanh19110151.foodyui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.spkt.phamvietanh19110151.foodyui.Model.Restaurant;
import hcmute.spkt.phamvietanh19110151.foodyui.R;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    Context context;
    List<Restaurant> restaurants;

    public void setRestaurantList(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    public RestaurantAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_restaurant,parent,false);
        return new RestaurantAdapter.RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant res = restaurants.get(position);
        if(res == null){
            return;
        }
        holder.tvName.setText(res.getName());
        holder.tvAddress.setText(res.getAddress());
        holder.tvPhone.setText(res.getPhone());
        holder.layoutRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("restaurant");
                intent.putExtra("rid", res.getId());
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(restaurants != null){
            return restaurants.size();
        }
        return 0;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName, tvAddress, tvPhone;
        private LinearLayout layoutRestaurant;

        public RestaurantViewHolder(@NonNull View view) {
            super(view);

            tvName = view.findViewById(R.id.tvRestaurantName);
            tvAddress = view.findViewById(R.id.tvRestaurantAddress);
            tvPhone = view.findViewById(R.id.tvRestaurantPhone);
            layoutRestaurant = view.findViewById(R.id.layoutRestaurant);
        }
    }
}
