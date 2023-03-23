package hu.ppke.itk.android.papel1.nature_arm.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hu.ppke.itk.android.papel1.nature_arm.PlantDataActivity;
import hu.ppke.itk.android.papel1.nature_arm.R;
import hu.ppke.itk.android.papel1.nature_arm.data.model.Plant;

public class VerticalListAdapter extends RecyclerView.Adapter<VerticalListAdapter.ListViewHolder> implements Filterable
{
    private final Context context;
    private ItemFilter filter;
    private ArrayList<Plant> plantArrayList;
    private ArrayList<Plant> filterArrayList;

    public VerticalListAdapter(Context _context, ArrayList<Plant> _plantArrayList)
    {
        this.plantArrayList = _plantArrayList;
        this.context = _context;
        this.filterArrayList = _plantArrayList;
        this.filter = new ItemFilter();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row_plants, parent, false);
        return new ListViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position)
    {
        holder.name.setText(plantArrayList.get(position).name);
        holder.location.setText(plantArrayList.get(position).location);

        if (plantArrayList.get(position).imageUri.toString().isEmpty())
            holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.demo_plant));
        else
            holder.image.setImageURI(plantArrayList.get(position).imageUri);

        holder.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlantDataActivity.class);
            ArrayList<Plant> editablePlantList = new ArrayList<>();
            editablePlantList.add(plantArrayList.get(position));
            intent.putExtra(context.getString(R.string.plantArrayList), editablePlantList);

            if (plantArrayList.size() > 0)
                context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount()
    {
        return plantArrayList.size();
    }

    @Override
    public Filter getFilter()
    {
        if (filter == null)
        {
            filter = new ItemFilter();
        }
        return filter;
    }

    private class ItemFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0)
            {
                ArrayList<Plant> filterList = new ArrayList<>();
                for (int i = 0; i < filterArrayList.size(); i++)
                {
                    if ((filterArrayList.get(i).getName().toUpperCase()).contains(constraint.toString().toUpperCase())
                            || (filterArrayList.get(i).getLocation().toUpperCase()).contains(constraint.toString().toUpperCase()))
                    {
                        filterList.add(filterArrayList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else
            {
                results.count = filterArrayList.size();
                results.values = filterArrayList;
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            plantArrayList = (ArrayList<Plant>) results.values;
            notifyDataSetChanged();
        }
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView location;
        ImageView image;
        CardView card;
        ConstraintLayout plant_layout;

        public ListViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.plant_nameTextView);
            location = itemView.findViewById(R.id.plant_locationTextView);
            image = itemView.findViewById(R.id.plantImageView);
            card = itemView.findViewById(R.id.plant_card);
            plant_layout = itemView.findViewById(R.id.list_row_plants_layout);
        }
    }
}
