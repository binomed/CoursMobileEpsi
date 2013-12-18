package com.epsi.i5.tp04;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.debug.hv.ViewServer;

public class HelloListActivity extends ListActivity {

	// Constante utilisé dans les logs
	private static final String TAG = "HelloListActivity";

	// Text vide permettant d'afficher l'état
	private TextView emptyView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_hello_list);
		// On récupère le texte vide
		emptyView = (TextView) findViewById(android.R.id.empty);
		setListAdapter(new MyAdapter(this));
		ViewServer.get(this).addWindow(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ViewServer.get(this).removeWindow(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ViewServer.get(this).setFocusedWindow(this);
		// On vérifie qu'on a pas déjà des données
	}

	class MyAdapter extends BaseAdapter {

		// Données à afficher
		private List<Data> datas = Data.getAllDatas();
		private final Context context;

		public MyAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.view_unoptim, parent, false);

			TextView title = (TextView) rowView.findViewById(R.id.title);
			TextView subtitle = (TextView) rowView.findViewById(R.id.subtitle);
			ImageView image = (ImageView) rowView.findViewById(R.id.image);
			ImageView image2 = (ImageView) rowView.findViewById(R.id.image2);

			Data data = (Data) getItem(position);

			title.setText(data.getTitle());
			subtitle.setText(data.getSubTitle());
			image.setImageResource(data.getImage());
			image2.setImageResource(data.getImage());

			return rowView;
		}

	}

}
