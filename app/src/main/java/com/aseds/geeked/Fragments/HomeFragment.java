package com.aseds.geeked.Fragments;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<String> followingList;

    private RecyclerView recyclerViewForArticls;
    private ArticleAdapter articleAdapter;
    private List<Article> listOfArticls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        recyclerViewForArticls = view.findViewById(R.id.recycler_view_posts);
        recyclerViewForArticls.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerViewForArticls.setLayoutManager(linearLayoutManager);
        listOfArticls = new ArrayList<>();

        articleAdapter = new ArticleAdapter(getActivity().getApplicationContext(), listOfArticls);
        recyclerViewForArticls.setAdapter(articleAdapter);

        followingList = new ArrayList<>();

        //Get the following users
        verifyFollowingUsers();
        return view;
    }

    private void verifyFollowingUsers() {

        //read following users from database
        FirebaseDatabase.getInstance().getReference().child("follow").child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid()).child("following").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }
                followingList.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                //read all posts of followings users
                getAllArticls();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getAllArticls() {

        //read all posts from databases relted to the followings of user
        FirebaseDatabase.getInstance().getReference().child("Articls").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOfArticls.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Article post = snapshot.getValue(Article.class);

                    for (String id : followingList) {
                        if (post.getPublisher().equals(id)){
                            listOfArticls.add(post);
                        }
                    }
                }
                articleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
