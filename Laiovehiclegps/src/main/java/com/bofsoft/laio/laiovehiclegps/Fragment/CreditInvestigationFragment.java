package com.bofsoft.laio.laiovehiclegps.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bofsoft.laio.laiovehiclegps.R;

/**
 * Created by Bofsoft on 2017/5/15.
 */

public class CreditInvestigationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.fragment_credit_investigation_login,null,false);
        return view;
    }
}
