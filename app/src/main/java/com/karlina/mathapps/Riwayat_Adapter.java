package com.karlina.mathapps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

public class Riwayat_Adapter extends RecyclerView.Adapter<Riwayat_Adapter.MyViewHolder> {

    public Riwayat_Adapter(List<Riwayat> historyList) {
        this.historyList = historyList;
    }

    List<Riwayat> historyList;

    @NonNull
    @Override
    public Riwayat_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.itemstory,null,false);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        view.setLayoutParams(new RecyclerView.LayoutParams(width, RecyclerView.LayoutParams.WRAP_CONTENT));
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Riwayat_Adapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Riwayat item = historyList.get(position);
        holder.tv_num1.setText(item.getPertama()+" ");
        holder.tv_operator.setText(item.getOperator());
        holder.tv_num2.setText(item.getKedua()+" ");
        holder.tv_result.setText(item.getHasil()+" ");
        holder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), holder.ll_item);
                popup.inflate(R.menu.menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                historyList.remove(position);
                                notifyDataSetChanged();
                                Context context = view.getContext();
                                SharedPreferences sharedPreferences = context.getSharedPreferences(Config.shared_key, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                Gson gson = new Gson();

                                String json = gson.toJson(historyList);
                                editor.putString(Config.history_key, json);
                                editor.apply();

                                Toast.makeText(view.getContext(), "Terhapus", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv_num1,tv_operator,tv_num2,tv_result;
        LinearLayout ll_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_num1 = itemView.findViewById(R.id.text_num1);
            tv_operator = itemView.findViewById(R.id.text_operator);
            tv_num2 = itemView.findViewById(R.id.text_num2);
            tv_result = itemView.findViewById(R.id.text_result);
            ll_item = itemView.findViewById(R.id.linear_item);
        }
    }
}
