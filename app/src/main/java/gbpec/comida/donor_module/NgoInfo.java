package gbpec.comida.donor_module;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;

import gbpec.comida.R;

public class NgoInfo extends AppCompatActivity {

    String name,contact,type,head,email,address,pic;
    TextView nNmae,nContact,nType,nHead,nEmail,nAddress;
    ImageView nPic;
    Bitmap image,bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_info);
        name=getIntent().getStringExtra("name");
        contact=getIntent().getStringExtra("contact");
        type=getIntent().getStringExtra("type");
        head=getIntent().getStringExtra("head");
        email=getIntent().getStringExtra("email");
        address=getIntent().getStringExtra("address");
        pic=getIntent().getStringExtra("pic");

        nNmae=(TextView)findViewById(R.id.name);
        nContact=(TextView)findViewById(R.id.contact);
        nType=(TextView)findViewById(R.id.type);
        nHead=(TextView)findViewById(R.id.head);
        nEmail=(TextView)findViewById(R.id.email);
        nAddress=(TextView)findViewById(R.id.address);
        nPic=(ImageView)findViewById(R.id.pic);

        nNmae.setText("NGO- "+name);
        nContact.setText("Contact- "+contact);
        nType.setText("Type- "+type);
        nHead.setText("Head- "+head);
        nEmail.setText("Email- "+email);
        nAddress.setText("Address- "+address);

        loadimage(pic);
        //Toast.makeText(getApplicationContext(),name+contact,Toast.LENGTH_LONG).show();
    }
    public void loadimage(final String picUrl){
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    bm = BitmapFactory.decodeStream((InputStream) new URL(picUrl).getContent());
                    nPic.post(new Runnable()
                    {
                        public void run()
                        {
                            if(bm !=null)
                            {
                                nPic.setImageBitmap(bm);
                            }
                        }
                    });
                } catch (Exception e)
                {
                    // TODO: handle exception
                }
            }
        }).start();
    }
}
