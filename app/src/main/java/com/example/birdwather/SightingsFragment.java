package com.example.birdwather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;

public class SightingsFragment extends Fragment {
    RecyclerView recyclerView2;
    BirdAdapter birdAdapter;
    String docId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_sightings, container, false);

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        setUpRecyclerView();

        return  view;
    }
    void setUpRecyclerView() {
        Query query = Utility.getCollectionReferenceForData().orderBy("sciName",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<NearbyVariables> options = new FirestoreRecyclerOptions.Builder<NearbyVariables>()
                .setQuery(query, NearbyVariables.class).build();
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        birdAdapter = new BirdAdapter(options, getContext());
        recyclerView2.setAdapter(birdAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        birdAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        birdAdapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        birdAdapter.notifyDataSetChanged();
    }



}