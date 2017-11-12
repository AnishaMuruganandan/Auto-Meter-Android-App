package in.hashio.dev.autometer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.EditTextPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;


public class MainActivity extends AppCompatActivity {

    double lat1=0,lat2=0,lon1=0,lon2=0;
    double distance;
    String dist;
    //LatLng from,LatLng to;
    //,dlat=0,dlon=0,a=0,c=0,d=0;
    //int r=6373;
 Button btn;
    // Bundle bundle = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if((lat1==0)||(lon1==0)||(lat2==0)||(lon2==0))
                    Toast.makeText(MainActivity.this, "Selet the address", Toast.LENGTH_SHORT).show();
                else
                {
                    final String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+String.valueOf(lat1)+","+String.valueOf(lon1)+"&destinations="+String.valueOf(lat2)+","+String.valueOf(lon2)+"&mode=driving&language=fr-FR&key=%20AIzaSyCN3pIxdh3CTD1l2nMrvfn2295j5xq9XDc";

                    Log.e("Tag","url"+url);
                    Thread timer = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try

                            {

                                final String genreJson = IOUtils.toString(new URL(url.trim()).openStream());

                                JSONObject genreJsonObject = (JSONObject) JSONValue.parseWithException(genreJson);
                                Log.e("Tag", "hi am in");
                                JSONArray genreArray = (JSONArray) genreJsonObject.get("rows");

                                System.out.println(genreArray);
                                JSONObject firstGenre = (JSONObject) genreArray.get(0);
                                System.out.println(firstGenre);
                                Log.e("Tag", "firstGenre" + firstGenre);

                                JSONArray arra = (JSONArray) firstGenre.get("elements");


                                JSONObject arr = (JSONObject) arra.get(0);
                                Log.e("Tag", "arr" + arr);

                                JSONObject a = (JSONObject) arr.get("distance");

                                String string = a.values().toString();

                                int index = string.indexOf("km");

                                String newstring = string.substring(1, index - 1);
                                Log.e("Tag", "new string" + newstring);

                                String newstring1 = newstring.replace(",", ".");
                                Log.e("Tag", "newstring1" + newstring1);

                                distance = Double.parseDouble(newstring1);

                                Log.e("Tag", "distance" + distance);
                                runOnUiThread(new Runnable() {
                                    public void run()
                                    {
                                        dist=Double.toString(distance);
                                        Toast.makeText(MainActivity.this, String.valueOf(dist + "km"), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext() , Main2Activity.class);
                                        //Create a bundle object
                                        Bundle b = new Bundle();

                                        //Inserts a String value into the mapping of this Bundle
                                        b.putString("distance", dist.toString());
                                        intent.putExtras(b);

                                        //start the DisplayActivity
                                        startActivity(intent);
                                    }
                                });

                            } catch (IOException |
                                    ParseException e
                                    )

                            {
                                e.printStackTrace();
                            }

                        }

                    });
                    timer.start();
                }
            }
        });
    }

    /*
     * In this method, Start PlaceAutocomplete activity
     * PlaceAutocomplete activity provides--
     * a search box to search Google places
     */


    public void findPlace(View view) {
        try {

            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            intent.putExtra("name","source");
            //bundle.putString("name",name1);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.

        }
    }
    public void findPlace1(View view) {
        try {

            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
//bundle.putString("name",name2);

            startActivityForResult(intent, 2);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A plabce has been received; use requestCode to track the request.
    //@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                lat1 = place.getLatLng().latitude;
                lon1 = place.getLatLng().longitude;
                Log.e("Tag", "Place: " + place.getLatLng().latitude + place.getAddress() + place.getPhoneNumber()+lat1+" "+lon1);

                ((EditText) findViewById(R.id.findPlace))
                        .setText(place.getName() + ",\n" +
                                place.getAddress() + "\n" + place.getPhoneNumber());


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                lat2 = place.getLatLng().latitude;
                lon2 = place.getLatLng().longitude;
                Log.e("Tag", "Place: " + place.getLatLng().latitude + place.getAddress() + place.getPhoneNumber()+lat2+" "+lon2);

                ((EditText) findViewById(R.id.findPlace1))
                        .setText(place.getName() + ",\n" +
                                place.getAddress() + "\n" + place.getPhoneNumber());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

    }





}