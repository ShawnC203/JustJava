/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

 package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    // Global variables referenced by methods below:

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder( View view ) {

        // Get user's name
        EditText nameEditText = ( EditText ) findViewById( R.id.name_field__view );
        String userName = nameEditText.getText().toString();
        Log.v( "MainActivity", "Name: " + userName );

        // Figure out if user wants whipped cream
        CheckBox whippedCreamCheckBox = ( CheckBox ) findViewById( R.id.whipped_cream_checkbox );
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v( "MainActivity", "Has whipped cream: " + hasWhippedCream );

        // Figure out if user wants chocolate
        CheckBox chocolateCheckBox = ( CheckBox ) findViewById( R.id.chocolate_checkbox );
        boolean hasChocolate = chocolateCheckBox.isChecked();
        Log.v( "MainActivity", "Has chocolate: " + hasChocolate );


        // Calculate the price
        int price = calculatePrice( hasWhippedCream, hasChocolate );

        // Display the order summary on the screen
        String priceMessage = createOrderSummary( price, hasWhippedCream, hasChocolate, userName );

        // Send orderSummary to email/gmail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + userName );
        // Body of the email - OrderSummary: priceMessage
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage );
        // Similar to below method that looks for a tool/app to run our program
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

          // No longer using displayMessage as we send message to email
          // displayMessage function at bottom has been commented out
//        displayMessage( priceMessage );

        // Opens up GoogleMaps with the below coordinates
        Intent intent1 = new Intent( Intent.ACTION_VIEW );
        intent.setData(Uri.parse( "geo:47.6, -122.3" ));
        if ( intent.resolveActivity( getPackageManager()) != null ) {

            startActivity( intent );
        }

        /* could have also done in place of lines47 and 48 the below code:
        displayMessage( createOrderSummary( price ) )
         */

    }

    /*
    Returns summary of order
    * @param price parameter entered is price of order
    * @param addWhippedCream is whether or not the user wants to add whipped cream
    * * @param addChocolate is whether or not the user wants to add chocolate
    */

    private String createOrderSummary( int price, boolean addWhippedCream, boolean addChocolate,
                                       String missStarbucks) {
        String priceMessage = getString( R.string.name_field__view, missStarbucks );
        // Replaced this using string resource format
        //String priceMessage = "Name: " + missStarbucks;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate; //turns whole thing into string
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + calculatePrice( addWhippedCream, addChocolate );
        priceMessage += "\n" + getString( R.string.Thank_You );
        return priceMessage;

    }

    /**
     * Calculates price of the order
     * @return total price ($5 per cup)
     */
    private int calculatePrice( boolean whippedCream, boolean choco) {

        int basePrice = 5;

        if ( whippedCream ) {

            basePrice += 1;
        }

        if ( choco ) {

            basePrice += 2;
        }

        return basePrice * quantity;
    }

    /**
     * This is for Increment XML button
     * no return value
     */
    public void increment( View view ) {

        if ( quantity == 100 ) {

            // Show error message as a toast
            Toast.makeText( this, "That's too much caffeine!", Toast.LENGTH_SHORT ).show();

            // Exit the method early b/c upper bound is 100, this won't let user enter more than 100
            return;
        }

        quantity = quantity + 1;

        displayQuantity( quantity );
    }

    /**
     * This is for Decrement XML button
     * no return value
     * updates the UI in the displayQuantity() method
     */
    public void decrement( View view ) {

        if ( quantity == 1 ) {

            // Show error message as a toast
            Toast.makeText( this, "Negative coffee? Preposterous!", Toast.LENGTH_SHORT ).show();

            //Exit this method early b/c 1 is our lower bound, user can't enter negatives
            return;
        }

        quantity = quantity - 1;

        displayQuantity( quantity );
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    // No longer using displayMessage as we send message to email
    // displayMessage function at bottom has been commented out
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = ( TextView ) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}

