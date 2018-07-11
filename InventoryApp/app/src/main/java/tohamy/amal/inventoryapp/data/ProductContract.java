package tohamy.amal.inventoryapp.data;

import android.provider.BaseColumns;

public final class ProductContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ProductContract() {
    }

    public static final class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "inventory";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PRODUCT_NAME = "productName";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_SUPPLIER_NAME = "supplierName";
        public static final String COLUMN_PRODUCT_SUPPLIER_PHONE_NUMBER = "supplierPhoneNumber";
    }
}
