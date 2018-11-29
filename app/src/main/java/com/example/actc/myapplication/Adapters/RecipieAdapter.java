package com.example.actc.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.actc.myapplication.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipieAdapter extends RecyclerView.Adapter<RecipieAdapter.ViewHolderRecipes> implements Serializable {
    private int mNumberVariable;

    final private ListMovieClickListener mOnClickListener;


    public interface ListMovieClickListener {
        public void onclickListener(int pos);
    }


    Context context;
    ArrayList<String> namesRecipie;
    public static String NUTELLA_PIE = "Nutella Pie";
    public static String BROUNIES = "Brownies";
    public static String YELLOW_CAKE = "Yellow Cake";
    public static String CHEESE_CAKE = "Cheesecake";


    public RecipieAdapter(Context context, int mNumberVariable, ArrayList<String> namesRecipie, ListMovieClickListener lsn) {
        this.mNumberVariable = mNumberVariable;
        this.namesRecipie = namesRecipie;
        mOnClickListener = lsn;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolderRecipes holder, int position) {
        System.out.println("$$$" + "called on bind ");

        holder.bind(namesRecipie.get(position));

    }


    @Override
    public int getItemCount() {
        return mNumberVariable;
    }

    @NonNull
    @Override
    public ViewHolderRecipes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.numberlistitem;
        boolean shouldAttackToparent = false;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttackToparent);

        ViewHolderRecipes viewHolderRecipes = new ViewHolderRecipes(view);
        return viewHolderRecipes;

    }

    class ViewHolderRecipes extends RecyclerView.ViewHolder implements View.OnClickListener, Serializable {

        TextView listitemNumber;
        ImageView imgRecipe;

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            mOnClickListener.onclickListener(pos);
        }

        public ViewHolderRecipes(View itemView) {
            super(itemView);

            listitemNumber = (TextView) itemView.findViewById(R.id.tv_item_number);
            imgRecipe = (ImageView) itemView.findViewById(R.id.imgViewRecipe);
            itemView.setOnClickListener(this);
        }

        void bind(String name) {
            listitemNumber.setText(name);
            if (name.equals(NUTELLA_PIE))
                Picasso.get().load(R.drawable.nutella).into(imgRecipe);
            else if (name.equals(CHEESE_CAKE))
                Picasso.get().load(R.drawable.cheesecake).into(imgRecipe);
            else if (name.equals(BROUNIES))
                Picasso.get().load(R.drawable.brownies).into(imgRecipe);
            if (name.equals(YELLOW_CAKE))
                Picasso.get().load(R.drawable.yellowcake).into(imgRecipe);

        }
    }
}
