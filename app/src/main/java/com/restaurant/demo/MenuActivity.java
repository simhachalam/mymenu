package com.restaurant.demo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
	protected static final String RECYCLER_VIEW_TYPE = "recycler_view_type";
	private RecyclerViewType recyclerViewType;
	private RecyclerView recyclerView;
	private int menuSelection = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		// Get a support ActionBar corresponding to this toolbar and enable the Up button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		;
		//get enum type passed from MainActivity
		recyclerViewType = RecyclerViewType.LINEAR_VERTICAL;
		setUpRecyclerView();
		populateRecyclerView();

		FloatingActionButton floatingActionButton =
				(FloatingActionButton) findViewById(R.id.fab_filter);

		floatingActionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Handle the click.
				customDialog();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.cart_icon) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	//setup recycler view
	private void setUpRecyclerView() {
		recyclerView = (RecyclerView) findViewById(R.id.sectioned_recycler_view);
		recyclerView.setHasFixedSize(true);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(linearLayoutManager);
	}

	//populate recycler view
	private void populateRecyclerView() {
		ArrayList<SectionModel> sectionModelArrayList = new ArrayList<>();
		//for loop for sections
		for (int i = 1; i <= 2; i++) {
			ArrayList<String> itemArrayList = new ArrayList<>();
			//for loop for items
			for (int j = 1; j <= 4; j++) {
				if(j % 2 == 0){
					itemArrayList.add("Soups");
				}else{
					itemArrayList.add("Item " + j);
				}

			}

			//add the section and items to array list
			sectionModelArrayList.add(new SectionModel("Category " + i, itemArrayList));
		}

		SectionRecyclerViewAdapter adapter = new SectionRecyclerViewAdapter(this, recyclerViewType, sectionModelArrayList);
		recyclerView.setAdapter(adapter);
	}

	public void customDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setSingleChoiceItems(R.array.menu_type_array, menuSelection, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// The 'which' argument contains the index position
				// of the selected item
				menuSelection = which;
				dialog.dismiss();
			}
		});

		AlertDialog dialog = builder.create();
//		ListView listView=dialog.getListView();
//		listView.setDivider(new ColorDrawable(Color.LTGRAY)); // set color
//		listView.setDividerHeight(1); // set height

		Window window = dialog.getWindow();
//		window.setBackgroundDrawable(new ColorDrawable(0));
		WindowManager.LayoutParams param = window.getAttributes();
		// set the layout at right bottom<br />
		param.gravity = Gravity.BOTTOM | Gravity.RIGHT;

//		param.gravity = Gravity.TOP | Gravity.LEFT;
		param.x = 100;   //x position
		param.y = 200;   //y position
		dialog.show();
	}

}

