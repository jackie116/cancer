//package com.example.huangyuwei.myapplication.mem;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.huangyuwei.myapplication.R;
//import com.example.huangyuwei.myapplication.database.CancerDatabase;
//import com.example.huangyuwei.myapplication.database.ChemCure;
//import com.example.huangyuwei.myapplication.mem.mem_cure_fragment_1.OnListFragmentInteractionListener;
//import com.example.huangyuwei.myapplication.mem.dummy.DummyContent.DummyItem;
//
//import java.util.List;
//
////import static com.example.huangyuwei.myapplication.MainActivity.getContext;
//import static com.example.huangyuwei.myapplication.database.CancerDatabase.getInMemoryDatabase;
//
//
//public class mem_cure_fragment_1_ItemRecyclerViewAdapter extends RecyclerView.Adapter<mem_cure_fragment_1_ItemRecyclerViewAdapter.ViewHolder> {
//    private Context context;
//    private final OnListFragmentInteractionListener mListener;
//    private List<ChemCure> mValues;
//
//    public mem_cure_fragment_1_ItemRecyclerViewAdapter(List<ChemCure> items, OnListFragmentInteractionListener listener, Context context) {
//        mListener = listener;
//        this.context = context;
//        mValues = items;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_mem_cure_1_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
////        if(position==0){
////            holder.mIdView.setText("日期");
////            holder.mContentView.setText("處方治療");
////        }else {
//            holder.mIdView.setText(String.valueOf(mValues.get(position).date_id));
//            holder.mContentView.setText(mValues.get(position).cure);
////        }
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mValues.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public ChemCure mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
//            mContentView = (TextView) view.findViewById(R.id.content);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }
//}
