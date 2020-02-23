package franklinvasquez.ufps.platzigram.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import franklinvasquez.ufps.platzigram.R;
import franklinvasquez.ufps.platzigram.model.Picture;
import franklinvasquez.ufps.platzigram.viem.fragment.SearchFragment;

public class PictureAdapterGridView extends BaseAdapter {

    private ArrayList<Picture> pictures;
    private Activity context;

    public PictureAdapterGridView(Activity context, ArrayList<Picture> pictures){
        this.context=context;
        this.pictures=pictures;
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public Object getItem(int position) {
        return pictures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_grid, null);
        }
        TextView nombreAutor = (TextView)view.findViewById(R.id.NombreAutor);
        ImageView picture = (ImageView)view.findViewById(R.id.pictureGrid);

        nombreAutor.setText(pictures.get(position).getUserName());
        Picasso.get().load(pictures.get(position).getPicture()).into(picture);
        return view;
    }
}
