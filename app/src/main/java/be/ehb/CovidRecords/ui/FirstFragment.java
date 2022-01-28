package be.ehb.CovidRecords.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.ehb.CovidRecords.MainActivity;
import be.ehb.CovidRecords.R;
import be.ehb.CovidRecords.databinding.ActivityMainBinding;
import be.ehb.CovidRecords.databinding.FragmentFirstBinding;
import be.ehb.CovidRecords.databinding.FragmentSecondBinding;
import be.ehb.CovidRecords.model.FormPost;
import be.ehb.CovidRecords.model.FormViewModel;
import be.ehb.CovidRecords.ui.util.FormPostAdapter;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding binding;
    private FormPostAdapter adapter ;

    RecyclerView recyclerView;
    public FirstFragment(){

    }
    public static FirstFragment newInstance(){
           return new FirstFragment();
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {



        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View rootview = inflater.inflate(R.layout.fragment_first, container,false);
        recyclerView = rootview.findViewById(R.id.rv_psots);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        FormViewModel formViewModel = new ViewModelProvider(this).get(FormViewModel.class);

        formViewModel.getFormpost().observe(getActivity(), new Observer<ArrayList<FormPost>>() {
            @Override
            public void onChanged(ArrayList<FormPost> formPosts) {
                adapter = new FormPostAdapter(formPosts);
                recyclerView.setAdapter(adapter);

            }
        });

        return rootview;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search by municipality");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                adapter.getFilter().filter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}