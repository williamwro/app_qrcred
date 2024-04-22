package br.com.qrcred;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link QrcodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QrcodeFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public String mParam1;
    public String mParam2;
    public String nome_associado;
    public String num_cartao;
    TextView Associado;
    ImageView ivQRCode;

    public QrcodeFragment() {
        // Required empty public constructor
    }

    public static QrcodeFragment newInstance(String param1, String param2) {
        QrcodeFragment fragment = new QrcodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.fragment_qrcode, container, false);

        ivQRCode = v.findViewById(R.id.ivQRCode);

        gerarQRCode();

        return v;
    }

    private void gerarQRCode(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try{
            Intent intent    = getActivity().getIntent();

            nome_associado   = intent.getStringExtra("nome");

            num_cartao       = intent.getStringExtra("cartao");

            BitMatrix bitMatrix = multiFormatWriter.encode(num_cartao, BarcodeFormat.QR_CODE,2000,2000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQRCode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }
    }

}

