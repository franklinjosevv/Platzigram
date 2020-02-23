package franklinvasquez.ufps.platzigram.viem.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import franklinvasquez.ufps.platzigram.R;
import franklinvasquez.ufps.platzigram.adapter.PictureAdapterRecyclerView;
import franklinvasquez.ufps.platzigram.model.Picture;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile,container, false);
        showToolbar("",false, view);

        RecyclerView picturesRecycler = (RecyclerView) view.findViewById(R.id.pictureProfileRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView = new PictureAdapterRecyclerView(buidPictures(), R.layout.cardview_picture, getActivity());
        picturesRecycler.setAdapter(pictureAdapterRecyclerView);

        return view;
    }

    public ArrayList<Picture> buidPictures(){
        ArrayList<Picture> picture = new ArrayList<>();
        picture.add(new Picture("https://img.freepik.com/foto-gratis/playa-phuket-tailandia_38810-691.jpg?size=626&ext=jpg","Uriel Ramirez", "4 dias","3 Me Gusta"));
        picture.add(new Picture("https://www.ngenespanol.com/wp-content/uploads/2018/08/Las-5-mejores-playas-desconocidas-en-M%C3%A9xico.jpg","Juan Pablo", "3 dias","30 Me Gusta"));
        picture.add(new Picture("https://cdn.ticbeat.com/src/uploads/2018/04/playa-810x540.jpg","Anahi Salgado", "2 dias","10 Me Gusta"));
        picture.add(new Picture("https://upload.wikimedia.org/wikipedia/commons/9/96/Barbados_beach.jpg","Franklin Vasquez", "1 dias","22 Me Gusta"));
        picture.add(new Picture("https://images.app.goo.gl/phebVUi8LKadnUANA","Ivan Jaramillo", "5 dias","14 Me Gusta"));
        return picture;
    }

    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}
