package com.danhtran12797.thd.foodyapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.danhtran12797.thd.foodyapp.R;
import com.danhtran12797.thd.foodyapp.adapter.CategoryAdapter;
import com.danhtran12797.thd.foodyapp.fragment.ConnectionFragment;
import com.danhtran12797.thd.foodyapp.model.Category;
import com.danhtran12797.thd.foodyapp.service.APIService;
import com.danhtran12797.thd.foodyapp.service.DataService;
import com.danhtran12797.thd.foodyapp.ultil.Ultil;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Toolbar toolbar;
    CategoryAdapter adapter;
    ArrayList<Category> arrCategory;
    RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initView();
        intActionBar();
        if (Ultil.isNetworkConnected(this)) {
            getData();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.layout_container, new ConnectionFragment()).commit();
        }
    }

    private void intActionBar() {
        toolbar = findViewById(R.id.toolbar_category);
        toolbar.setTitle("Thể loại");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        rotateLoading = findViewById(R.id.rotateloading);
        recyclerView = findViewById(R.id.recyclerViewCategory);
    }

    private void getData() {
        rotateLoading.start();
        DataService dataService = APIService.getService();
        Call<List<Category>> callback = dataService.GetCategory();
        callback.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                arrCategory = (ArrayList<Category>) response.body();
                adapter = new CategoryAdapter(arrCategory);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                recyclerView.setAdapter(adapter);
                rotateLoading.stop();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                rotateLoading.stop();
            }
        });
    }


}
