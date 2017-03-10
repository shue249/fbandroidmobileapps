package shueching.mobileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import shueching.mobileapp.R;
import shueching.mobileapp.dataobject.ProductLineItem;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by shueching on 1/15/17.
 */
public class ShoppingCartAdapter extends ArrayAdapter<ProductLineItem> {

    private static DecimalFormat df2 = new DecimalFormat(".##");
    private Context context;
    private List<ProductLineItem> productLineItems;

    public ShoppingCartAdapter(Context context, List<ProductLineItem> productLineItems) {
        super(context, R.layout.productlineitem_layout, productLineItems);
        this.context = context;
        this.productLineItems = productLineItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.productlineitem_layout, parent, false);

        TextView textViewSequenceNumber = (TextView) rowView.findViewById(R.id.textViewSequenceNumber);
        textViewSequenceNumber.setText(String.valueOf(position+1));

        ProductLineItem productLineItem = productLineItems.get(position);
        TextView textViewDescription = (TextView) rowView.findViewById(R.id.textViewDescription);
        textViewDescription.setText(productLineItem.product.description);
        TextView textViewQty = (TextView) rowView.findViewById(R.id.textViewQty);
        textViewQty.setText(String.valueOf(productLineItem.qty));
        TextView textViewItemTotal = (TextView) rowView.findViewById(R.id.textViewItemTotal);
        textViewItemTotal.setText(df2.format(productLineItem.product.price * productLineItem.qty));

        return rowView;
    }
}
