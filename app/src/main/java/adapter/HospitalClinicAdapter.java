package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.medicard.member.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.HospitalClinic;
import model.HospitalList;

/**
 * Created by John Paul Cas on 4/19/2017.
 */

public class HospitalClinicAdapter extends RecyclerView.Adapter<HospitalClinicAdapter.ViewHolder> {

    public static final String TAG = HospitalClinicAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private Context context;

    private List<HospitalList> hospitalClinics;
    private OnClickListener listener;

    public HospitalClinicAdapter(Context context, List<HospitalList> hospitalClinics, OnClickListener listener) {
        inflater = LayoutInflater.from(context);

        this.context = context;
        this.hospitalClinics = hospitalClinics;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = inflater.inflate(R.layout.item_hospital, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HospitalList hospital = hospitalClinics.get(position);
        holder.tvHospitalName.setText(hospital.getHospitalName());
    }

    public void update(List<HospitalList> hospitals) {
        this.hospitalClinics = hospitals;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return hospitalClinics.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.tvHospitalOrClinicName) TextView tvHospitalName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnClickListener {
        void onItemClick(int position);
    }

}
