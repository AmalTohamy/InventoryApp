package tohamy.amal.inventoryapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import tohamy.amal.inventoryapp.data.ProductContract;

public class ProductCursorAdapter extends CursorAdapter {

    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.product_name_text_view);
        final TextView quantityTextView = view.findViewById(R.id.quantity_value_text_view);
        TextView priceTextView = view.findViewById(R.id.price_value_text_view);
        Button saleButton = view.findViewById(R.id.sale_button);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE);
        final int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY));

        // Read the product attributes from the Cursor for the current product
        String productName = cursor.getString(nameColumnIndex);
        final String quantityString = cursor.getString(quantityColumnIndex);
        String price = cursor.getString(priceColumnIndex);

        final Uri uri = ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI,
                cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry._ID)));

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(productName);
        quantityTextView.setText(quantityString);
        priceTextView.setText(price);

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
                    Integer afterSale = quantity - 1;

                    ContentValues values = new ContentValues();
                    values.put(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, afterSale);
                    context.getContentResolver().update(uri, values, null, null);

                    quantityTextView.setText(afterSale.toString());
                }
            }
        });

    }
}
