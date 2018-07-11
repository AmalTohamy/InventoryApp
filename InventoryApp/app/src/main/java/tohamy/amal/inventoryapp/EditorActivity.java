package tohamy.amal.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import tohamy.amal.inventoryapp.data.ProductContract.ProductEntry;
import tohamy.amal.inventoryapp.data.ProductDbHelper;

public class EditorActivity extends AppCompatActivity {

    EditText mProductNameEditText;
    EditText mPriceEditText;
    EditText mQuantityEditText;
    EditText mSupplierNameEditText;
    EditText mPhoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_activity);

        mProductNameEditText = findViewById(R.id.product_name_edit_text);
        mPriceEditText = findViewById(R.id.price_edit_text);
        mQuantityEditText = findViewById(R.id.quantity_edit_text);
        mSupplierNameEditText = findViewById(R.id.supplier_name_edit_text);
        mPhoneNumberEditText = findViewById(R.id.phone_number_edit_text);

    }

    private void insertProduct() {
        String productNameString = mProductNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        int price = Integer.parseInt(priceString);
        String quantityString = mQuantityEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityString);
        String supplierNameString = mSupplierNameEditText.getText().toString().trim();
        String phoneNumberString = mPhoneNumberEditText.getText().toString().trim();

        // Create database helper
        ProductDbHelper mDbHelper = new ProductDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, productNameString);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, price);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, quantity);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME, supplierNameString);
        contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER, phoneNumberString);

        // Insert a new row for pet in the database, returning the ID of that new row.
        Long newRowId = db.insert(ProductEntry.TABLE_NAME, null, contentValues);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Product saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_save:
                insertProduct();
                finish();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
