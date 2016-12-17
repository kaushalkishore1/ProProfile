package com.k2infosoft.proprofile;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.k2infosoft.proprofile.managers.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Profragment extends Fragment {
    @BindView(R.id.profile)
    ImageView _profile_pic;
    @BindView(R.id.tv_name)
    android.support.design.widget.TextInputEditText _name;
    @BindView(R.id.etLocation)
    android.support.design.widget.TextInputEditText _address;


    public Profragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profragment, container, false);
        ButterKnife.bind(this, rootView);
        // _profile_pic.setImageResource(R.drawable.profile);

        Uri myUri = Uri.parse(SharedPrefs.getString(getActivity(), SharedPrefs.Profilepic));
        Toast.makeText(getActivity(), "Profile : " + myUri, Toast.LENGTH_SHORT).show();
        _profile_pic.setImageURI(myUri);
        _name.setText(SharedPrefs.getString(getActivity(), SharedPrefs.Username));
        _address.setText(SharedPrefs.getString(getActivity(), SharedPrefs.Address));
        return rootView;
    }

}
