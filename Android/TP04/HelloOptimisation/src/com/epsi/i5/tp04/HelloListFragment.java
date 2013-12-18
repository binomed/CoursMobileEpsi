package com.epsi.i5.tp04;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HelloListFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.activity_hello_list, container, false);
		((ListView) mainView.findViewById(android.R.id.list)).setEmptyView(mainView.findViewById(android.R.id.empty));
		return mainView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		getListView().setAdapter(new MyAdapter(getActivity()));
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
