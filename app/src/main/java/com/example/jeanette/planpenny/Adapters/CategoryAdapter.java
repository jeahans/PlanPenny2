package com.example.jeanette.planpenny.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeanette.planpenny.DAO.CategoryDAO;
import com.example.jeanette.planpenny.Objects.Category;
import com.example.jeanette.planpenny.R;

import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;


/**
 * Created by Jeanette on 15-05-2015.
 */
public class CategoryAdapter extends BaseAdapter {

    public static final String TAG = "MyCustomCategoryAdapter";

    private CategoryDAO mCategoryDAO;
    private List<Category> list;
    private Context context;
    private LayoutInflater mInflater;

    public CategoryAdapter(Context context, List<Category> list) {
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Category getItem(int position){
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }




    @Override
    public long getItemId(int position){
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getCategoryid() : position;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.simple_list_item, null);
            context = view.getContext();
            holder = new ViewHolder();
            holder.item = (TextView) view.findViewById(android.R.id.list);
            holder.delete = (Button) view.findViewById(R.id.buttonDelete);


            holder.item.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    changeColorDialog(position);
                    notifyDataSetChanged();

                    Log.d(TAG, "Change color");

                    Toast.makeText(context, "Delete button Clicked", Toast.LENGTH_LONG).show();
                }});

            holder.delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mCategoryDAO = new CategoryDAO(context);
                    Category category = getItem(position);
                    mCategoryDAO.deleteCategory(category);
                    list.remove(position);
                    List<Category> newList = mCategoryDAO.getAllCategories();
                    updateCategoryList(newList);



                    Log.d(TAG, "delete button pressed");
                }});

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        // fill row data
        Category currentItem = getItem(position);
        if(currentItem != null) {
            holder.item.setText(currentItem.getCategoryName());
            holder.item.setBackgroundColor(currentItem.getColorCode());

        }


        return view;
    }


    public List<Category> getItems(){
        return list;
    }

    public void seItems(List<Category> list){
        this.list = list;
    }

    public void refresh(List<Category> list){
        this.list = list;
        notifyDataSetChanged();

    }


    class ViewHolder {
        TextView item;
        Button delete;
    }
    public void changeColorDialog(final int position){
        int color = Color.parseColor("#4c4cff");
        AmbilWarnaDialog colorDialog = new AmbilWarnaDialog(context,color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                mCategoryDAO = new CategoryDAO(context);
                Category category = getItem(position);
                long categoryId = category.getCategoryid();
                String categoryName = category.getCategoryName();
                mCategoryDAO.updateCategory(categoryId,categoryName,color);
                List<Category> newlist = mCategoryDAO.getAllCategories();
                updateCategoryList(newlist);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // cancel was selected by the user
            }

        });
        colorDialog.show();
    }

    public void updateCategoryList(List<Category> newlist) {
        list.clear();
        list.addAll(newlist);
        this.notifyDataSetChanged();
    }

}
