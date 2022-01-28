package be.ehb.CovidRecords.ui.util;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

import be.ehb.CovidRecords.R;
import be.ehb.CovidRecords.model.FormPost;

public class FormPostAdapter extends RecyclerView.Adapter<FormPostAdapter.FormPostHolder> implements Filterable , Serializable {

    String value;


    class FormPostHolder extends RecyclerView.ViewHolder{

        private final TextView  municipality, province, CASES;
        private final Button btn;
        public FormPostHolder(@NonNull View itemView) {
            super(itemView);
            municipality = itemView.findViewById(R.id.tv_municipality);
            province = itemView.findViewById(R.id.tv_province);
            CASES =itemView.findViewById(R.id.tv_CASES);
            btn = itemView.findViewById(R.id.btn_map);

        }
    }

    ArrayList<FormPost> data;
    ArrayList<FormPost> filteredList;

    public FormPostAdapter(ArrayList<FormPost> data) {
        this.data = data;
        filteredList = new ArrayList<>(data);

    }

    @NonNull
    @Override
    public FormPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        return new FormPostHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull FormPostHolder holder, int position) {
       FormPost formPost = data.get(position);
       holder.municipality.setText(formPost.getMunicipality());
       holder.province.setText(formPost.getProvince());
       holder.CASES.setText(formPost.getCASES());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                value = holder.municipality.getText().toString();
                bundle.putString("location", value);
                Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                 data.clear();
                 data.addAll(filteredList);
                ArrayList<FormPost> filteredItem = new ArrayList<>();
                if (charSequence == null || charSequence.length() == 0){
                    filteredItem.addAll(filteredList);
                }else {
                    String charString = charSequence.toString();
                    for (FormPost post : data){
                        if(post.getMunicipality().toLowerCase().contains(charString.toLowerCase())){
                            filteredItem.add(post);
                        }
                    }

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredItem;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data.clear();
                data.addAll((ArrayList)filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
