package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createWeatherMessage(77,"Chandigarh");
    }
    private String createWeatherMessage(int temperature,String cityName){
        return ("Welcome to "+ cityName+" where the temperature is "+ temperature +" F" );
    }


    /**
     * This method is called when we have to increase the quantity
     */
    public void increment(View view) {

        if(quantity==100){
            return;
        }
            quantity = quantity + 1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when we have to decrease the quantity
     */
    public void decrement(View view) {
        if(quantity==1)
        {return;}
        quantity=quantity-1;

        displayQuantity(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text=(EditText) findViewById(R.id.album_description_view) ;
        String name=text.getText().toString();
        Log.v("Main Activity","Name:"+name);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        Log.v("Main Activity","has whipped cream:"+hasWhippedCream);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        Log.v("Main Activity","has whipped cream:"+hasChocolate);
        int price=calculatePrice(hasWhippedCream,hasChocolate);
    String priceMessage=createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JUST JAVA order for "+ name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    private int calculatePrice(boolean addWhippedCream,boolean addChocolate)
    {
        int basePrice=5;
        if (addWhippedCream){
            basePrice=basePrice+1;
        }
        if(addChocolate){
            basePrice=basePrice+2;
        }

        int price=quantity*basePrice;
        return price;

    }
    private String createOrderSummary(String name,int price,boolean addWhippedCream,boolean addChocolate ){

        String priceMessage=getString(R.string.order_summary_name,name) +"\n";
        priceMessage+= "Quantity: " +quantity +"\n";
        priceMessage+="Has Whipped Cream (for $ 1): "+addWhippedCream+"\n";
        priceMessage+="Has Chocolate (for $ 2): "+addChocolate+"\n";
        priceMessage+="Total: $"+price +"\n";
       priceMessage+=getString(R.string.thank_you);
       return priceMessage;

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}