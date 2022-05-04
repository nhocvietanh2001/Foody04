package hcmute.spkt.phamvietanh19110151.foodyui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context mContext;
    List<Category> mCate;

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Category> list){
        this.mCate = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public int getItemCount() {

        if(mCate != null){
            return mCate.size();
        }

        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category cate = mCate.get(position);
        if(cate == null){
            return;
        }
        holder.img_cate.setImageResource(cate.getRsID());
        holder.txt_cate.setText(cate.getTitle());

    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{


        private ImageView img_cate;
        private TextView txt_cate;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            img_cate = itemView.findViewById(R.id.img_category);
            txt_cate = itemView.findViewById(R.id.cate_title);


        }
    }
}
