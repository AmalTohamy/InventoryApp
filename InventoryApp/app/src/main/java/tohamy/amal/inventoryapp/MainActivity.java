package tohamy.amal.inventoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import tohamy.amal.inventoryapp.data.ProductContract.ProductEntry;
import tohamy.amal.inventoryapp.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {

    private ProductDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new ProductDbHelper(this);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new ProductDbHelper(this);
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    public void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection =
                {
                        ProductEntry._ID,
                        ProductEntry.COLUMN_PRODUCT_NAME,
                        String.valueOf(ProductEntry.COLUMN_PRODUCT_PRICE),
                        String.valueOf(ProductEntry.COLUMN_PRODUCT_QUANTITY),
                        ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME,
                        ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER
                };

        Cursor cursor = db.query(ProductEntry.TABLE_NAME, projection,
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView textView = findViewById(R.id.product_info_text_view);

        try {
            textView.setText(getResources().getString(R.string.info)
                    + cursor.getCount() + getResources().getString(R.string.products));
            textView.append(ProductEntry._ID + " - " +
                    ProductEntry.COLUMN_PRODUCT_NAME + " - " +
                    ProductEntry.COLUMN_PRODUCT_PRICE + " - " +
                    ProductEntry.COLUMN_PRODUCT_QUANTITY + " - " +
                    ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " - " +
                    ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
            int productNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(String.valueOf(ProductEntry.COLUMN_PRODUCT_PRICE));
            int quantityColumnIndex = cursor.getColumnIndex(String.valueOf(ProductEntry.COLUMN_PRODUCT_QUANTITY));
            int supplierNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME);
            int supplierPhoneNumberColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER);

            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(productNameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhoneNumber = cursor.getString(supplierPhoneNumberColumnIndex);

                textView.append(("\n" + currentId + " - " +
                        currentName + " - " +
                        currentPrice + "$ - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierPhoneNumber));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
}
