package com.keen.android.happckathon.ui.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.keen.android.happckathon.R;
import com.keen.android.happckathon.libs.ImageDto;
import com.keen.android.happckathon.ui.dialogs.MapDialog;

import java.io.File;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GALLERY_CODE = 10;
    private FirebaseStorage storage;
    private FirebaseDatabase database;

    // 구글 맵에 표시할 마커에 대한 옵션 설정
    MarkerOptions makerOptions = new MarkerOptions();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private double mapLat;
    private double maplongitude;
    private String file_URL;
    private OnFragmentInteractionListener mListener;
    private GoogleMap mMap;

    private MapDialog mapDialog;

    StorageReference riversRef;
    Uri file;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // 권한
        requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View convertView = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);


        return convertView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(latLng -> {
            mapLat = latLng.latitude;
            maplongitude = latLng.longitude;
            makerOptions.position(new LatLng(mapLat, maplongitude));
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

            startActivityForResult(intent, GALLERY_CODE); // OK 눌렀을 때(수정해야댐)

            mMap.addMarker(makerOptions);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_CODE){
            StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-test-d8221.appspot.com");

            file_URL = getPath(data.getData());

            mapDialog = new MapDialog(getContext(), data.getData(), leftClickEvent, rightClickEvent);

            mapDialog.setCancelable(true);
            mapDialog.getWindow().setGravity(Gravity.CENTER);
            mapDialog.show();


            file = Uri.fromFile(new File(getPath(data.getData())));
            riversRef = storageRef.child("images/"+file.getLastPathSegment());

        }
    }

    private View.OnClickListener leftClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mapDialog.dismiss();
        }
    };

    private View.OnClickListener rightClickEvent = view -> {
        UploadTask uploadTask = riversRef.putFile(file);

        // 이미지 업로드
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> {
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
            @SuppressWarnings("VisibleForTests")
            Uri downloadUrl = taskSnapshot.getDownloadUrl();

            ImageDto imageDto = new ImageDto();
            imageDto.imageUrl = downloadUrl.toString();
            imageDto.title = MapDialog.titleData;
            imageDto.content = MapDialog.contentData;

            database.getReference().child("images").push().setValue(imageDto);

            Toast.makeText(getContext(), "File Upload Success", Toast.LENGTH_SHORT).show();
        });
    };

    public String getPath(Uri uri){
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader cursorLoader = new CursorLoader(getContext(), uri, proj, null, null, null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
