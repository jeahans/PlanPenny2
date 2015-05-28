package com.example.jeanette.planpenny.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeanette.planpenny.Adapters.CategoryAdapter;
import com.example.jeanette.planpenny.DAO.CategoryDAO;
import com.example.jeanette.planpenny.Objects.Category;
import com.example.jeanette.planpenny.R;

import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;


public class CategoriesFragment extends ListFragment implements View.OnClickListener {

    public static final String TAG ="CategoriesFragment";

    public static final int REQUEST_CODE_ADD_COMPANY = 40;
    public static final String EXTRA_ADDED_COMPANY = "extra_key_added_category";

    private ListView listViewCategory;

    private ImageButton addCat;
    private TextView emptyList;

    private CategoryAdapter mAdapter;
    private List<Category> listCategory;
    private CategoryDAO mCategoryDAO;



    public CategoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.fragment_categories, container, false);

        emptyList = (TextView) frag.findViewById(R.id.emptyCategoryTextView);
        listViewCategory = (ListView) frag.findViewById(android.R.id.list);

        addCat = (ImageButton) frag.findViewById(R.id.buttonAddCategory);
        addCat.setOnClickListener(this);



        //fill the listview
        mCategoryDAO = new CategoryDAO(getActivity());
        listCategory = mCategoryDAO.getAllCategories();
        if(listCategory !=null && !listCategory.isEmpty()){
            mAdapter = new CategoryAdapter(getActivity(),listCategory);
            listViewCategory.setAdapter(mAdapter);

        }else{
            //If list is empty
            emptyList.setText("No categories yet press add button to create new");
            emptyList.setVisibility(View.VISIBLE);
            listViewCategory.setVisibility(View.INVISIBLE);
            mAdapter.notifyDataSetChanged();

        }

        mAdapter= new CategoryAdapter(getActivity(),listCategory);



        // Inflate the layout for this fragment
        return frag;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //instantiate custom adapter
        CategoryAdapter adapter = new CategoryAdapter(getActivity(),listCategory);
        setListAdapter(adapter);



    }



    @Override
    public void onClick(View v) {
        if(v== addCat){
            Toast.makeText(getActivity(), "Add new category", Toast.LENGTH_SHORT).show();
            addCategoryDialog();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        listCategory.clear();
        listCategory.addAll(mCategoryDAO.getAllCategories());

    }

    public void addCategoryDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose category");

        final EditText input = new EditText(getActivity());
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });
        input.setText("Category");
        input.setSelectAllOnFocus(true);


        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = input.getText().toString();
                colorPickerDialog(title);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


    public void colorPickerDialog(final String title){
        int color = Color.parseColor("#4c4cff");
        AmbilWarnaDialog colorDialog = new AmbilWarnaDialog(getActivity(),color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Category newCategory = mCategoryDAO.createCategory(title,color);
                List<Category> newlist = mCategoryDAO.getAllCategories();
                mAdapter.updateCategoryList(newlist);



            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // cancel was selected by the user
            }

        });
        colorDialog.show();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        mCategoryDAO.close();
    }
}
