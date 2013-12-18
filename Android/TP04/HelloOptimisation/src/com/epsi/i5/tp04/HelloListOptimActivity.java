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

public class HelloListOptimActivity extends ListActivity {

	// Constante utilisé dans les logs
	private static final String TAG = "HelloListActivity";

	// Text vide permettant d'afficher l'état
	private TextView emptyView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_hello_list_optim);
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

			ViewHolder viewHolder = null;
			View viewToReturn = null;

			if (convertView != null) {
				viewToReturn = convertView;
				viewHolder = (ViewHolder) convertView.getTag();
			} else {
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				viewToReturn = inflater.inflate(R.layout.view_optim, parent, false);
				viewHolder.title = (TextView) viewToReturn.findViewById(R.id.title);
				viewHolder.subTitle = (TextView) viewToReturn.findViewById(R.id.subtitle);
				viewHolder.image = (ImageView) viewToReturn.findViewById(R.id.image);
				viewHolder.image2 = (ImageView) viewToReturn.findViewById(R.id.image2);
				viewToReturn.setTag(viewHolder);
			}
			Data data = (Data) getItem(position);

			viewHolder.title.setText(data.getTitle());
			viewHolder.subTitle.setText(data.getSubTitle());
			viewHolder.image.setImageResource(data.getImage());
			viewHolder.image2.setImageResource(data.getImage());

			return viewToReturn;
		}

		class ViewHolder {
			private TextView title;
			private TextView subTitle;
			private ImageView image;
			private ImageView image2;
		}

	}

}
