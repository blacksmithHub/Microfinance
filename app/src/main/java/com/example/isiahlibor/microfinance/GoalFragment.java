package com.example.isiahlibor.microfinance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoalFragment extends Fragment {

    EditText amount, rate;
    Button create;

    private DatabaseReference tblGoal;
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // load fragment_settings.xml layout
        View view = inflater.inflate(R.layout.fragment_goal, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        // get current user
        user = firebaseAuth.getCurrentUser();

        amount = (EditText)view.findViewById(R.id.amount);
        rate = (EditText)view.findViewById(R.id.rate);
        create = (Button)view.findViewById(R.id.create);

        create();

        return view;
    }

    public void create(){

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    tblGoal = FirebaseDatabase.getInstance().getReference().child("Goals").child(user.getUid());

                    tblGoal.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().child("amount").setValue(amount.getText().toString());
                            dataSnapshot.getRef().child("rate").setValue(rate.getText().toString());
                            dataSnapshot.getRef().child("status").setValue("ongoing");

                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoanFragment()).commit();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getContext(),""+e,Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
