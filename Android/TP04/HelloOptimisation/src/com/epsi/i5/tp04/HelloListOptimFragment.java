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

public class HelloListOptimFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = inflater.inflate(R.layout.activity_hello_list_optim, container, false);
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
