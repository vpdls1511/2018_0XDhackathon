package com.keen.android.happckathon.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.keen.android.happckathon.R;
import com.keen.android.happckathon.libs.Item;
import com.keen.android.happckathon.libs.RecyclerAdapter;

import java.sql.DatabaseMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BoardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = df.format(c.getTime());


    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private List<Item> items = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View convertView = inflater.inflate(R.layout.fragment_board, container, false);


        recyclerView = convertView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        Item[] item = new Item[5];

        item[0] = new Item(R.drawable.first, "아이파크단지", "부산", formattedDate);
        item[1] = new Item(R.drawable.second, "해태상", "광화문", formattedDate);
        item[2] = new Item(R.drawable.third, "루브르 박물관", "프랑스 파리", formattedDate);
        item[3] = new Item(R.drawable.fourth, "타임스퀘어", "미국 뉴욕", formattedDate);
        item[4] = new Item(R.drawable.fifth, "피라미드", "이집트", formattedDate);

        for (int i=0;i<5;i++) items.add(item[i]);

        recyclerView.setAdapter(new RecyclerAdapter(getContext(),items,R.layout.fragment_board));

        return convertView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
