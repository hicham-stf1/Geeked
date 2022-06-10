package com.aseds.geeked.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aseds.geeked.Adapter.ArticleAdapter;

import com.aseds.geeked.Model.Article;
import com.aseds.geeked.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;

public class ArticleDetailFragment extends Fragment {

    private String postId;
    private RecyclerView recyclerView;
    private ArticleAdapter articleAdapter;
    private List<Article> listOfArticls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        postId = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE).getString("postid", "none");

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listOfArticls = new ArrayList<>();
        articleAdapter = new ArticleAdapter(getContext(), listOfArticls);
        recyclerView.setAdapter(articleAdapter);


        //Get the data of the post clicked by the user
        FirebaseDatabase.getInstance().getReference().child("Articls").child(postId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfArticls.clear();
                listOfArticls.add(dataSnapshot.getValue(Article.class));

                articleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
