package org.teamfulp.fulp.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.teamfulp.fulp.app.R;
import org.teamfulp.fulp.app.domain.Income;

/**
 * Created by roystraub on 24-04-14.
 */
public class IncomeAdapter extends ArrayAdapter<Income> {

    private Context context;
    private Income[] values;

    public IncomeAdapter(Context context, int resourceId, Income[] values) {
        super(context, resourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.summary_list_item, parent, false);
        TextView itemTitle = (TextView) rowView.findViewById(R.id.summary_item_title);
        TextView itemAmount = (TextView) rowView.findViewById(R.id.summary_item_amount);
        TextView itemInterval = (TextView) rowView.findViewById(R.id.summary_item_title2);
        TextView itemDate = (TextView) rowView.findViewById(R.id.summary_item_date);

        Income income = values[position];
        itemTitle.setText(income.getName());
        itemAmount.setText("" + income.getAmount());
        itemInterval.setText(income.getInterval());

        if((income.getStart() != null || income.getStart().isEmpty()) && (income.getEnd() != null || income.getEnd().isEmpty()))
        itemDate.setText(income.getStart() + " - " + income.getEnd());

        return rowView;
    }
}
